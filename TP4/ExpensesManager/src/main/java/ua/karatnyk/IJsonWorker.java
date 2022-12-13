package ua.karatnyk;

import org.json.JSONObject;

import ua.karatnyk.impl.CurrencyConversion;

public interface IJsonWorker {
	
	JSONObject readJSONObject();
	CurrencyConversion parser();

}
