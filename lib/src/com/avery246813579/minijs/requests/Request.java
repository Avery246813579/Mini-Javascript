package com.avery246813579.minijs.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

public class Request {
	public static String sendRequest(String route, RequestMethod requestMethod, Map<String, String> header,
			String body) {

		URL object = null;

		try {
			object = new URL(route);
		} catch (MalformedURLException e3) {
			e3.printStackTrace();
		}

		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) object.openConnection();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		if (header != null) {
			for (String key : header.keySet()) {
				con.setRequestProperty(key, header.get(key));
			}
		}

		try {
			if (requestMethod == RequestMethod.PATCH) {
				con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
				con.setRequestMethod("POST");
			} else {
				con.setRequestProperty("X-HTTP-Method-Override", requestMethod.toString());
				con.setRequestMethod(requestMethod.toString());
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		}

		if (body != null) {
			OutputStreamWriter wr;
			try {
				wr = new OutputStreamWriter(con.getOutputStream());
				wr.write(body);
				wr.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		StringBuilder sb = new StringBuilder();
		int HttpResult = 0;
		try {
			HttpResult = con.getResponseCode();
		} catch (IOException e) {

		}

		if (HttpResult == HttpURLConnection.HTTP_OK || HttpResult == HttpURLConnection.HTTP_CREATED) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				int BUFFER_SIZE = 1024;
				char[] buffer = new char[BUFFER_SIZE];
				int charsRead = 0;
				while ((charsRead = br.read(buffer, 0, BUFFER_SIZE)) != -1) {
					sb.append(buffer, 0, charsRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return sb.toString();
		} else {
			try {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					int BUFFER_SIZE = 1024;
					char[] buffer = new char[BUFFER_SIZE];
					int charsRead = 0;
					while ((charsRead = br.read(buffer, 0, BUFFER_SIZE)) != -1) {
						sb.append(buffer, 0, charsRead);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
