package ua.karatnyk.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class OfflineJsonWorker extends JsonWorker {

	private static final String offlineCurrenciesFilePath = "./src/offlineFixer.json";

	@Override
	public JSONObject readJSONObject() {

		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(offlineCurrenciesFilePath));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			JSONObject object = new JSONObject(response.toString());
			in.close();
			return object;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
