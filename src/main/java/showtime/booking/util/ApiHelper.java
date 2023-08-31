package showtime.booking.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import io.github.cdimascio.dotenv.Dotenv;

public class ApiHelper {
	static OkHttpClient client = new OkHttpClient();

	public static Request buildRequest(String url) {
		// get api key from dotenv
		Dotenv dotenv = Dotenv.load();

		// Read values from the .env file using the "get" method
		String apiKey = dotenv.get("TMDB_API_KEY");

		return new Request.Builder().url(url).get().addHeader("accept", "application/json")
				.addHeader("Authorization", apiKey).build();
	}

	public static JsonObject getJsonResponse(String url) {
		Request request = buildRequest(url);
		JsonObject jsonObject = null;
		try {
			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String responseBody = response.body().string();

				// Parse JSON using Gson
				Gson gson = new Gson();
				jsonObject = gson.fromJson(responseBody, JsonObject.class);
			} else {
				System.out.println("Request failed: " + response.code() + " - " + response.message());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	
//url to get showtimes json:
//https://serpapi.com/search.json?engine=google&q=GRAN%20TURISMO:%20BASED%20ON%20A%20TRUE%20STORY%20(2023)%20theater&location=Austin%2C+Texas%2C+United+States&google_domain=google.com&gl=us&hl=en&api_key=8e7d3c3c0bf98b4c85071f7f615127b8c93756cca96c1c2584c96f73bf3ff4f7


}
