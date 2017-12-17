package tw.damncan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;

import tw.damncan.model.LoginUser;

public class AuthorityService {
    public LoginUser login(String account, String pswd) throws ClientProtocolException, IOException {
    	List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("account", account));
		params.add(new BasicNameValuePair("pswd", pswd));
		
    	ApiHitService apiHitService = new ApiHitService();
    	LoginUser loginUser = (LoginUser) apiHitService.Post("http://localhost:8080/DayOffAP/rest/authority/login", params, LoginUser.class);
    	return loginUser;
    }
}