import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// Simple CSV processor w/ some testing

public class CSVProcessor {

    public static HashMap<String, String> readCSVToMap(String filePath) {
        HashMap<String, String> keyValuePairs = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Ensure the row has at least two columns
                if (nextLine.length >= 2) {
                    String key = nextLine[0];
                    String value = nextLine[1];
                    keyValuePairs.put(key, value);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return keyValuePairs;
    }


    // Debugging
    public static void main(String[] args) {
        String inputFilePath = "pokemon_names.csv";
        String outputFilePath = "boosk.csv";
        HashMap<String, String> keyValuePairs = readCSVToMap(inputFilePath);

        // Print the key-value pairs
        for (Map.Entry<String, String> entry : keyValuePairs.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Write the HashMap to "boosk.csv"
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8))) {
            for (HashMap.Entry<String, String> entry : keyValuePairs.entrySet()) {
                String[] line = {entry.getKey(), entry.getValue()};
                writer.writeNext(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Data written to boosk.csv successfully.");
    }
}
