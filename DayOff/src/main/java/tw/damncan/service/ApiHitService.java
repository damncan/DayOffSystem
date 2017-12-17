package tw.damncan.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiHitService {
    public <T> Object Post(String url, List<NameValuePair> params, Class<?> objclass) throws ClientProtocolException, IOException {
//		HttpClient httpclient = HttpClients.createDefault();
    	try(CloseableHttpClient httpclient = createAcceptSelfSignedCertificateClient()){
			HttpPost httppost = new HttpPost(url);
			
			// Request parameters and other properties.
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			// Execute and get the response.
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			if(response.getStatusLine().getStatusCode() == 200){
				String jsonString = EntityUtils.toString(entity);
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				Object object = (Object) objectMapper.readValue(jsonString, objclass);
	
				return object;
			}else{
				return null;
			}
    	}catch(NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e){
    		throw new RuntimeException(e);
    	}
    }
    
    public <T> Object Get(String url, Class<?> objclass) throws ClientProtocolException {
//    	HttpClient httpclient = HttpClients.createDefault();
    	try(CloseableHttpClient httpclient = createAcceptSelfSignedCertificateClient()){
    		HttpGet httpget = new HttpGet(url);

    		//Execute and get the response.
    		HttpResponse response = httpclient.execute(httpget);
    		HttpEntity entity = response.getEntity();

    		if(response.getStatusLine().getStatusCode() == 200){
    			String jsonString = EntityUtils.toString(entity);

    			ObjectMapper objectMapper = new ObjectMapper();
    			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    			Object object = (Object) objectMapper.readValue(jsonString, objclass);
    			
    			return object;
    		}else{
    			return null;
    		}
    	}catch(NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e){
    		throw new RuntimeException(e);
    	}
    }
    
    // Ignore Certificate Errors in Apache HttpClient 4.5: Accept Self Signed Certificates
    private static CloseableHttpClient createAcceptSelfSignedCertificateClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        SSLContext sslContext = SSLContextBuilder
        	.create()
        	.loadTrustMaterial(new TrustSelfSignedStrategy())
        	.build();

        // we can optionally disable hostname verification. 
        // if you don't want to further weaken the security, you don't have to include this.
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
        
        // create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
        // and allow all hosts verifier.
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
        
        // finally create the HttpClient using HttpClient factory methods and assign the ssl socket factory
        return HttpClients
        	.custom()
        	.setSSLSocketFactory(connectionFactory)
        	.build();
    }
}