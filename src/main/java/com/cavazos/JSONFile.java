package com.cavazos;

import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class JSONFile {

    // Helper to read the json file from the resources folder
    public static String[] readCommandsFromResource(String resourceName) {
        JSONParser parser = new JSONParser();
        JSONArray data = null;

        try (
            InputStream is = JSONFile.class.getResourceAsStream(resourceName);
            InputStreamReader reader = new InputStreamReader(is)
        ) {
            if (is == null) return null;

            Object obj = parser.parse(reader);
            data = (JSONArray) obj;
        } catch (Exception e) {
            return null;
        }

        // Convert the JSONArray into a standard String array for the menu
        String[] commands = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            commands[i] = data.get(i).toString().trim();
        }
        return commands;
    }
}