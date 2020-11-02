package us.mn.state.health.hrd.bodyart.payment;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.keycloak.OAuth2Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GatewayAccessToken {

	@Value("${idam.url}")
	private String authUrl;

	@Value("${idam.clientid}")
	private String clientId;

	@Value("${idam.username}")
	private String username;

	@Value("${idam.client.secret}")
	private String clientSecret;

	public String getAccessToken() {
		// username=ehpay-user&password=xE@R+xm?BF8_73kS&grant_type=client_credentials&client_id=ehpay-client&client_secret=de8d8443-b51c-48e4-b62f-cc012ef4ca5c

		HttpClient client = HttpClientBuilder.create().useSystemProperties().build();
		HttpPost post = new HttpPost(this.authUrl);

		List<NameValuePair> params = new ArrayList<>();
	        params.add(new BasicNameValuePair(OAuth2Constants.GRANT_TYPE, "client_credentials"));
	        params.add(new BasicNameValuePair("username", this.username));
	        params.add(new BasicNameValuePair("client_id", this.clientId));
	        params.add(new BasicNameValuePair("client_secret", this.clientSecret));

	        try {
	        	UrlEncodedFormEntity form = new UrlEncodedFormEntity(params, "UTF-8");
	        	post.setEntity(form);

	        	HttpResponse response = client.execute(post);
	        	int status = response.getStatusLine().getStatusCode();

	        	String json = EntityUtils.toString(response.getEntity());

	        	if (status != 200) {
	        		log.error("Non OK Response from Token Serivce: " + status + ":   " + json);
	        		log.error(response.getStatusLine().getReasonPhrase());
	        	}

	        	JSONObject jsonObject = new JSONObject(json);
	        	if (jsonObject.has("access_token")) {
	        		Object accessTokenAttr = jsonObject.get("access_token");
	        		if (accessTokenAttr != null) {
	        			return accessTokenAttr.toString();
	        		}
	        	}
	        	return null;
	        } catch (IOException ioe) {
	            return null;
	        }
	    }
}
