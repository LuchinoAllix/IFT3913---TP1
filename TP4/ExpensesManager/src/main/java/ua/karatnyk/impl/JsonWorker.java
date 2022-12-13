package ua.karatnyk.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.json.JSONObject;
import com.google.gson.Gson;

import ua.karatnyk.IJsonWorker;

public class JsonWorker implements IJsonWorker{
	
	private static final String API_PROVDIER = "http://data.fixer.io/api/";
	private static final String API_KEY = "97c50db074b61540bfcf39f2760b31b1";
	private static final String API_GET_RATES = "latest";
	
	//connect to fixer and receive response

	public JSONObject readJSONObject() {
		
		URL fixer;
		URLConnection url;
		BufferedReader in = null;
		try {
			fixer = new URL(API_PROVDIER+API_GET_RATES+"?access_key="+API_KEY);
			url = fixer.openConnection();
		    in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		    String inputLine;
		    StringBuffer response = new StringBuffer();
		    while ((inputLine = in.readLine()) != null) {
		        response.append(inputLine);
		       }
		    JSONObject object = new JSONObject(response.toString());
		    return object;
		 } catch (UnknownHostException e) {
			return null;
		}
			catch (NoRouteToHostException e) {
			System.out.println("No internet connection");
			return null;
		}
			catch (Exception e) {
				e.printStackTrace();
				return null;
		 } finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//parse json to java object
	
	public CurrencyConversion parser() {
		try {
			String json = readJSONObject().toString();
			CurrencyConversion response = new Gson().fromJson(json, CurrencyConversion.class);
			return response;	
		} catch (NullPointerException e) {
			return null;
		}
	}	
	

}
