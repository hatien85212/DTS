package com.dts.adminportal.security.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;

public class HttpURLConnectionInit {
	private static JSONObject jsonData;

	private final static String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		jsonData = APIUtil.parseDataToJsonObject(APIData.JSON_DATA_FILE);
		//HttpURLConnectionInit http = new HttpURLConnectionInit();
		
		String csrf = EstablishConnection().get(0);
		System.out.println("csrf="+ csrf);
		String cookie = EstablishConnection().get(1);
		System.out.println("JSESSION="+cookie);
		
		String requestBody = jsonData.get(APIData.LOG_IN).toString();

		PostLogin("http://devportal.dts.com/saap/login", csrf, cookie, requestBody);

	}

	// HTTP GET request
	private static ArrayList<String> EstablishConnection() throws Exception {

			String url = "http://devportal.dts.com/saap";
			
			ArrayList<String> valuereturned = new ArrayList<String>();
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//Return csrf string
			String _csrf = response.substring(response.lastIndexOf("_csrf\" value=")+14,response.lastIndexOf("_csrf\" value=")+50);
			valuereturned.add(_csrf);
			// Get Cookie 
			Map<String, List<String>> headerFields = con.getHeaderFields();

			Set<String> headerFieldsSet = headerFields.keySet();
			Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

			while (hearerFieldsIter.hasNext()) {
				String headerFieldKey = hearerFieldsIter.next();
				if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
					List<String> headerFieldValue = headerFields
							.get(headerFieldKey);
					for (String headerValue : headerFieldValue) {
						String[] fields = headerValue.split(";s*");
						String cookieValue = fields[0];
						cookieValue = cookieValue.substring(cookieValue.lastIndexOf("=") + 1);
						valuereturned.add(cookieValue);
					}
				}
			}
		return valuereturned;
	}
	
	
	// Log into Portal
	public static String PostLogin(String url, String csrfToken, String cookie, String requestBody) throws Exception {
		try {
			// Create request connection
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			// add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			con.setRequestProperty("Cookie", cookie);
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(requestBody + csrfToken);
			wr.flush();
			wr.close();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + requestBody);
			// Get response code
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);
			// Get response body
			BufferedReader in;
			if (responseCode != 200) {
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
			
			return response.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	// HTTP POST request
//	private void sendPost() throws Exception {
//
//		String url = "https://selfsolve.apple.com/wcResults.do";
//		URL obj = new URL(url);
//		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//
//		// add reuqest header
//		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", USER_AGENT);
//		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//
//		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
//
//		// Send post request
//		con.setDoOutput(true);
//		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//		wr.writeBytes(urlParameters);
//		wr.flush();
//		wr.close();
//
//		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + urlParameters);
//		System.out.println("Response Code : " + responseCode);
//
//		BufferedReader in = new BufferedReader(new InputStreamReader(
//				con.getInputStream()));
//		String inputLine;
//		StringBuffer response = new StringBuffer();
//
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}
//		in.close();
//
//		// print result
//		System.out.println(response.toString());

//	}

}
