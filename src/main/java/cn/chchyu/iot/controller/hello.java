package cn.chchyu.iot.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class hello {
	
	@RequestMapping("/hello")
	public String hellocontroller(HttpServletRequest request) throws  IOException, JSONException  {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost("http://120.76.57.30:8080/api/auth/login");
        StringEntity s = new StringEntity("{\"username\":\"liuwq1219@163.com\", \"password\":\"lwq1219\"}");
        httpPost.setEntity(s);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");
        client=HttpClients.createDefault();
        response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);

        JSONObject jsonObject=new JSONObject(result);
        
        
        HttpGet httpGet = new HttpGet("http://120.76.57.30:8080/api/plugins/telemetry/DEVICE/701d53c0-166c-11e9-a4cf-5d1883281505/values/timeseries");
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("X-Authorization", "Bearer "+jsonObject.getString("token"));
        client = HttpClients.createDefault();
        response = client.execute(httpGet);
        entity = response.getEntity();
        result = EntityUtils.toString(entity);
        return result;
	}
}
