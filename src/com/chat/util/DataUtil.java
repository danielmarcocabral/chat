package com.chat.util;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

public class DataUtil {
    public static String buildJsonData(String username, String message){
		JsonObject jsonObject  = Json.createObjectBuilder().add("message",username+" : "+message).build();
		StringWriter stringWriter = new StringWriter();
		try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
			jsonWriter.write(jsonObject);
		}
		return stringWriter.toString();
	}
}
