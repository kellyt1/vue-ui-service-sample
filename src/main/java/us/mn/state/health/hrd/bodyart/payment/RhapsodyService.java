package us.mn.state.health.hrd.bodyart.payment;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import us.mn.state.health.hrd.bodyart.payment.domain.Fee;
import us.mn.state.health.hrd.bodyart.payment.domain.Receipt;
import us.mn.state.health.hrd.bodyart.payment.domain.TransactionDetail;
import us.mn.state.health.hrd.bodyart.payment.domain.usbank.MakePayment;
import us.mn.state.health.hrd.bodyart.payment.domain.usbank.ObjectFactory;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RhapsodyService {

	@Inject
    private GatewayAccessToken gatewayAccessToken;

	@Value("${spring.profiles.active}")
	private String environment;

	@Value("${makepayment.endpoint}")
	private String makePaymentUrl;

	@Value("${base.url}")
	private String baseUrl;

	public MakePayment buildMakePaymentPayload(Receipt receipt) {
		ObjectFactory of = new ObjectFactory();
		MakePayment makePayment = of.createMakePayment();
		MakePayment.PaymentDetails details = of.createMakePaymentPaymentDetails();
		MakePayment.PaymentDetails.Itemkeys itemKeys = of.createMakePaymentPaymentDetailsItemkeys();

		int seq = 1;
		BigDecimal totalDue = BigDecimal.ZERO;
		for (Fee fee : receipt.getFees()) {
			MakePayment.PaymentDetails.Itemkeys.Itemkey itemKey = of.createMakePaymentPaymentDetailsItemkeysItemkey();
			itemKey.setSequence(StringUtils.leftPad(Integer.toString(seq), 2, "0"));
			itemKey.setSpeedchart(fee.getCode());
			itemKey.setAmount(fee.getAmount());
			itemKey.setSpeedchart("H12WMIW1");

			itemKeys.getItemkey().add(itemKey);
			seq++;
			totalDue = totalDue.add(fee.getAmount());
		}
		details.setItemkeys(itemKeys);

		details.setBillerGroupId(PaymentConstants.BILLER_GROUP);
		details.setBillerId(PaymentConstants.USBANK_BILLER_ID);

		details.setPaymentType(PaymentConstants.SINGLE_PAYMENT);
		details.setProductCode(PaymentConstants.GENERIC_PAYMENT);

		details.setAmountDue(totalDue);
		details.setTransactionID(buildEnvSpecificTransactionId(receipt.getTransactionNbr()));

		String successUrl = baseUrl + "/thanks";

		try {
			details.setReturnURL(URLDecoder.decode(successUrl, "UTF-8"));
		} catch (UnsupportedEncodingException uee) {
			log.error("Error setting successURL: " + successUrl, uee);
		}

		setDefaultsFromReceipt(details, receipt);

		makePayment.setPaymentDetails(details);

		return makePayment;
	}

	private String buildEnvSpecificTransactionId(Long txId) {
		StringBuilder sb = new StringBuilder();
		String transPrefix = StringUtils.upperCase(StringUtils.left(this.environment, 1));
		sb.append(transPrefix);
		sb.append(txId.toString());
		return sb.toString();
	}

	private void setDefaultsFromReceipt(MakePayment.PaymentDetails details, Receipt receipt) {
		details.setStreetAddress1(receipt.getAddressTxt1());
		details.setStreetAddress2(receipt.getAddressTxt2());
		details.setCity(receipt.getCity());
		details.setZipPostalcode(StringUtils.trim(receipt.getZipCode()));
		details.setStateRegion(receipt.getStateCode());
		details.setCountryCode(PaymentConstants.USA);

		if (StringUtils.equalsIgnoreCase(this.environment, "prod")) {
			details.setEmailAddress(receipt.getEmailAddress());
		}

		details.setPhoneNumber(receipt.getPhoneNumber());

		if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(receipt.getBusinessName()))) {
			details.setCompanyName(receipt.getBusinessName());
		}

		if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(receipt.getFirstName()))) {
			details.setFirstName(receipt.getFirstName());
		}

		if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(receipt.getLastName()))) {
			details.setLastName(receipt.getLastName());
		}
	}

	public String submitMakePayment(MakePayment payload) {
		String xml = StringUtils.EMPTY;

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(MakePayment.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.marshal(payload, sw);
			xml = sw.toString();
			log.info(xml);
		} catch (JAXBException e) {
			log.error("Error marshalling xml data " + e);
		}

		CloseableHttpClient client = HttpClients.createDefault();
		try {
			StringEntity entity = new StringEntity(xml, ContentType.create("text/xml", Consts.UTF_8));
			HttpPost httppost = new HttpPost(makePaymentUrl);
			httppost.addHeader("Content-Type", "text/xml");
			httppost.addHeader("cache-control", "no-cache");
			httppost.setEntity(entity);


			CloseableHttpResponse response = client.execute(httppost);

			try {
				HttpEntity responseEntity = response.getEntity();
				return EntityUtils.toString(responseEntity);
			} catch (IOException e) {
				log.error("Error submitting make payment " + e);
			} finally {
				try {
					response.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}

		return StringUtils.EMPTY;
	}

	public TransactionDetail decode(String billerId, String token) {
		String response = postDecodeRequest(billerId, token);
		return parseToken(response);
	}

	private String postDecodeRequest(String billerId, String token) {

		StringBuilder url = new StringBuilder(this.baseUrl);
		url.append('/');
		url.append(PaymentConstants.BILLER_GROUP);
		url.append('/');
		url.append(billerId);
		url.append("/session/aes");

		HttpClient client = HttpClientBuilder.create().useSystemProperties().build();
		HttpPost post = new HttpPost(url.toString());

		setBearerInterceptors(post);

		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("session", token));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);

			log.warn("Response Code : "
			                + response.getStatusLine().getStatusCode());

			return EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			log.error("Error decoding transaction session: {}", e);
		} catch (ClientProtocolException e) {
			log.error("Error decoding transaction session: {}", e);
		} catch (IOException e) {
			log.error("Error decoding transaction session: {}", e);
		}

		return StringUtils.EMPTY;
	}

	private TransactionDetail parseToken(String token) {
	    if (StringUtils.isNotEmpty(token)) {
	    	return new TransactionDetail();
	    }

		// Construct a full uri string
		 // The response from Rhapsody comes back with a leading "&", remove that.
	     String uri = "http://www.dummy.com?" + StringUtils.stripStart(token, "&");

	     MultiValueMap<String, String> parameters =
	             UriComponentsBuilder.fromUriString(uri).build().getQueryParams();

	     TransactionDetail detail = new TransactionDetail();

	     if (parameters.containsKey("TransactionConfirmationID")) {
	    	 detail.setConfirmationNbr(parameters.get("TransactionConfirmationID").get(0));
	     }

	     if (parameters.containsKey("TransactionID")) {
	    	 detail.setTransactionId(parameters.get("TransactionID").get(0));
	     }

	     if (parameters.containsKey("PaymentMethod")) {
	    	 detail.setPaymentMethod(parameters.get("PaymentMethod").get(0));
	     }

	     return detail;
	}

	private void setBearerInterceptors(HttpRequestBase client) {
		String accessToken  = gatewayAccessToken.getAccessToken();
		client.setHeader("Authorization", "Bearer " + accessToken);
	}
}
