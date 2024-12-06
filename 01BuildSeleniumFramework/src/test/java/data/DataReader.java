package data;

import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataReader {

    // get the data form the JSON file
    // convert it into the List of HashMap
    public List<HashMap<String, String>> getJsonDataToMap() throws IOException
    {
        Path path = Paths.get("src/test/java/data/PurchaseOrder.json");

        //read json to string
        String jsonContent = Files.readString(path);

        //String to HashMap- Jackson Databind
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<>() {
        });
    }
}
