package us.mn.state.health.hrd.bodyart.security;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
public class SecretsManager {

    public static String getSecret(String secretName, String region) {

        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        String secret = null;
        String decodedBinarySecret = null;

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            log.error("Secrets Manager can't decrypt the protected secret text using the provided KMS key.");
            throw e;
        } catch (InternalServiceErrorException e) {
            log.error("An error occurred on the server side.");
            throw e;
        } catch (InvalidParameterException e) {
            log.error("You provided an invalid value for a parameter.");
            throw e;
        } catch (InvalidRequestException e) {
            log.error("You provided a parameter value that is not valid for the current state of the resource.");
            throw e;
        } catch (ResourceNotFoundException e) {
            log.error("We can't find the resource that you asked for. {}", secretName);
            throw e;
        }

        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
        } else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
        }

        return secret;
    }
}
