package showtime.booking.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonHelper {
	public static JsonObject fromFile(String jsonFilePath) {
//        // Specify the path to your JSON file
//        String jsonFilePath = "path/to/your/file.json";
		JsonObject jsonObject = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
            // Use Gson's JsonParser to parse the JSON file into a JsonObject
            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Now you can work with the JsonObject
            //System.out.println("JSON Object: " + jsonObject.toString());

            // Access specific fields in the JSON object, for example:
            // String value = jsonObject.get("fieldName").getAsString();
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
		return jsonObject;
    }

}
