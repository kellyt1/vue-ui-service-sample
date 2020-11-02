package us.mn.state.health.hrd.bodyart.service;

import java.security.PrivateKey;
import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import us.mn.state.health.hrd.bodyart.domain.TokenPayload;

import java.time.ZoneId;
import java.util.Date;

@Service
@Slf4j
public class TokenService {

    static String ISSUER = "MDH HRD";
    static String PROGRAM_CLAIM_ID = "programCode";
    static String INVOICE_CLAIM_ID = "invoiceId";
    static String CREDENTIAL_CLAIM_ID = "credentialId";
    static String PAYOR_CLAIM_ID = "payorId";
    static String RENEWAL_YEAR_CLAIM_ID = "renewalYear";
    static String BYPASS_PAYMENT= "bypassPayment";
    static String SHORTAGE_PAYMENT = "shortagePayment";

    String jwkJson =
            "jwk.json = {\"kty\":\"EC\", \"kid\":\"mdhhrd001\",\"use\":\"enc\",\"x\":\"gOOZ19iR1c_3vmwG8w6TfFwa9p3h5Uzfvh1v8enQRiQ\",\"y\":\"2YHDnIN6smeRy2c37sfp3X6_tnl70w9A7y_RPYXSBbI\",\"crv\":\"P-256\",\"d\":\"IyeRAp87_0T9AKXiOc2aAkcFlwB3mPXKRne3I2AhogE\"}";

    public String encode(TokenPayload payload) {
        try {
            // parse and convert into PublicJsonWebKey/JsonWebKey objects
            PublicJsonWebKey parsedPublicKeyJwk = PublicJsonWebKey.Factory.newPublicJwk(jwkJson);
            PublicJsonWebKey parsedKeyPairJwk = PublicJsonWebKey.Factory.newPublicJwk(jwkJson);

            // the private key can be used to sign (JWS) or decrypt (JWE)
            PrivateKey privateKey = parsedKeyPairJwk.getPrivateKey();

            // Create the Claims, which will be the content of the JWT
            JwtClaims claims = new JwtClaims();
            claims.setIssuer(ISSUER);
            claims.setExpirationTime(
                    NumericDate.fromMilliseconds(
                            Date.from(daysFromToday(payload.getDaysToLive())
                                    .atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            claims.setSubject(payload.getSubject());
            claims.setClaim(PROGRAM_CLAIM_ID, payload.getProgramCode());

            if (StringUtils.isNotEmpty(payload.getInvoiceId())) {
                claims.setClaim(INVOICE_CLAIM_ID, payload.getInvoiceId());
            }

            if (StringUtils.isNotEmpty(payload.getCredentialId())) {
                claims.setClaim(CREDENTIAL_CLAIM_ID, payload.getCredentialId());
            }

            if (StringUtils.isNotEmpty(payload.getPayorId())) {
                claims.setClaim(PAYOR_CLAIM_ID, payload.getPayorId());
            }

            if (StringUtils.isNotEmpty(payload.getRenewalYear())) {
                claims.setClaim(RENEWAL_YEAR_CLAIM_ID, payload.getRenewalYear());
            }

            if (StringUtils.isNotEmpty(payload.getBypassPayment())) {
                claims.setClaim(BYPASS_PAYMENT, payload.getBypassPayment());
            }

            if (StringUtils.isNotEmpty(payload.getShortagePayment())) {
                claims.setClaim(SHORTAGE_PAYMENT, payload.getShortagePayment());
            }

            // A JWT is a JWS and/or a JWE with JSON claims as the payload.
            JsonWebSignature jws = new JsonWebSignature();

            // The payload of the JWS is JSON content of the JWT Claims
            jws.setPayload(claims.toJson());

            // The JWT is signed using the sender's private key
            jws.setKey(privateKey);

            // Set the Key ID (kid) header because it's just the polite thing to do.
            jws.setKeyIdHeaderValue(parsedKeyPairJwk.getKeyId());

            // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

            // Sign the JWS and produce the compact serialization, which will be the inner JWT/JWS
            // representation, which is a string consisting of three dot ('.') separated
            // base64url-encoded parts in the form Header.Payload.Signature
            String innerJwt = jws.getCompactSerialization();

            // The outer JWT is a JWE
            JsonWebEncryption jwe = new JsonWebEncryption();

            // The output of the ECDH-ES key agreement will encrypt a randomly generated content encryption key
            jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.ECDH_ES_A128KW);

            // The content encryption key is used to encrypt the payload
            // with a composite AES-CBC / HMAC SHA2 encryption algorithm
            String encAlg = ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256;
            jwe.setEncryptionMethodHeaderParameter(encAlg);

            // We encrypt to the receiver using their public key
            // There is no expectation that the receiver will read or access this token
            // other than returning to use via a URL.  In essence, we are the sender and receiver.
            jwe.setKey(parsedPublicKeyJwk.getKey());
            jwe.setKeyIdHeaderValue(parsedPublicKeyJwk.getKeyId());

            // A nested JWT requires that the cty (Content Type) header be set to "JWT" in the outer JWT
            jwe.setContentTypeHeaderValue("JWT");

            // The inner JWT is the payload of the outer JWT
            jwe.setPayload(innerJwt);

            // Produce the JWE compact serialization, which is the complete JWT/JWE representation,
            // which is a string consisting of five dot ('.') separated
            // base64url-encoded parts in the form Header.EncryptedKey.IV.Ciphertext.AuthenticationTag
            return jwe.getCompactSerialization();
        } catch (JoseException e) {
            log.error("/token/generate/", e);
            return null;
        }

    }

    public TokenPayload decode(String token) {
        try {
            // parse and convert into PublicJsonWebKey/JsonWebKey objects
            PublicJsonWebKey parsedPublicKeyJwk = PublicJsonWebKey.Factory.newPublicJwk(jwkJson);
            PublicJsonWebKey parsedKeyPairJwk = PublicJsonWebKey.Factory.newPublicJwk(jwkJson);

            // the private key can be used to sign (JWS) or decrypt (JWE)
            PrivateKey privateKey = parsedKeyPairJwk.getPrivateKey();

            // Use JwtConsumerBuilder to construct an appropriate JwtConsumer, which will
            // be used to validate and process the JWT.
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setRequireExpirationTime()
                    .setRequireSubject()
                    .setExpectedIssuer(ISSUER)
                    .setDecryptionKey(privateKey)
                    .setVerificationKey(parsedPublicKeyJwk.getKey())
                    .build();

            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);

            TokenPayload payload = new TokenPayload();
            payload.setSubject(jwtClaims.getSubject());
            if (jwtClaims.getClaimValue(PROGRAM_CLAIM_ID) != null) {
                payload.setProgramCode(jwtClaims.getClaimValue(PROGRAM_CLAIM_ID).toString());
            }

            if (jwtClaims.getClaimValue(INVOICE_CLAIM_ID) != null) {
                payload.setInvoiceId(jwtClaims.getClaimValue(INVOICE_CLAIM_ID).toString());
            }

            if (jwtClaims.getClaimValue(CREDENTIAL_CLAIM_ID) != null) {
                payload.setCredentialId(jwtClaims.getClaimValue(CREDENTIAL_CLAIM_ID).toString());
            }

            if (jwtClaims.getClaimValue(PAYOR_CLAIM_ID) != null) {
                payload.setPayorId(jwtClaims.getClaimValue(PAYOR_CLAIM_ID).toString());
            }

            if (jwtClaims.getClaimValue(RENEWAL_YEAR_CLAIM_ID) != null) {
                payload.setRenewalYear(jwtClaims.getClaimValue(RENEWAL_YEAR_CLAIM_ID).toString());
            }

            if (jwtClaims.getClaimValue(BYPASS_PAYMENT) != null) {
                payload.setBypassPayment(jwtClaims.getClaimValue(BYPASS_PAYMENT).toString());
            }

            if (jwtClaims.getClaimValue(SHORTAGE_PAYMENT) != null) {
                payload.setShortagePayment(jwtClaims.getClaimValue(SHORTAGE_PAYMENT).toString());
            }

            return payload;
        } catch (InvalidJwtException | MalformedClaimException | JoseException e) {
            log.error("/token/decode/" + token, e);
            return null;
        }
    }

    /**
     * Calculates the date a certain number of days into the future.
     *
     * @param days the days
     * @return the date
     */
    private LocalDate daysFromToday(int days) {
        LocalDate now = LocalDate.now();
        return (days > 0 ? now.plusDays(days) : now);
    }
}
