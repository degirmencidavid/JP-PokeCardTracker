import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.apache.commons.lang3.ArrayUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

// Class to get every pokemon's name in English and Japanese

public class PokemonNameExtractor {

    // Function to extract tables from the Bulbapedia page
    public static List<String[]> extractTables(Document doc) {
        Elements tables = doc.select("table.roundy");
        List<String[]> tablesList = new ArrayList<>();
        for (int i = 0; i < tables.size(); i++) {
            Elements rows = tables.get(i).select("tr");
            for (int j = 0; j < rows.size(); j++) {
                Elements cols = rows.get(j).select("td");
                String[] rowData = new String[cols.size()];
                for (int k = 0; k < cols.size(); k++) {
                    rowData[k] = cols.get(k).text();
                }
                tablesList.add(rowData);
            }
        }
        return tablesList;
    }

    // Function to extract English and Japanese names from the table
    public static String[][] extractNames(List<String[]> table) {
        String[][] names = new String[1031][2]; // Excluding the first row for headers

        for (int i = 1; i < 1032; i++) {
            if (table.get(i).length >= 4) {
                names[i - 1][0] = table.get(i)[2].toLowerCase(); // Convert English name to lowercase
                names[i - 1][1] = table.get(i)[3]; // Japanese name
            } else if (table.get(i).length == 3) {
                names[i - 1][0] = table.get(i)[1].toLowerCase(); // Convert English name to lowercase
                names[i - 1][1] = table.get(i)[2]; // Japanese name
            } else {
                names[i - 1][0] = ""; // Empty string for English name
                names[i - 1][1] = ""; // Empty string for Japanese name
            }
        }

        return names;
    }

    // Function to save data to CSV file
    public static void saveToCSV(String[][] data, String filePath) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
            writer.writeAll(List.of(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void buldTranslationTable() {
        try {
            String url = "https://bulbapedia.bulbagarden.net/wiki/List_of_Japanese_Pok%C3%A9mon_names";
            Document doc = Jsoup.connect(url).get();

            List<String[]> poketables = extractTables(doc);
            poketables.remove(0); // Remove the first table as it's not needed

            String[][] nam = extractNames(poketables);
            nam = ArrayUtils.removeElement(nam, new String[]{"",""});


            // Put these into a CSV file
            saveToCSV(nam, "pokemon_names.csv");

            System.out.println("Data saved to CSV file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String url = "https://bulbapedia.bulbagarden.net/wiki/List_of_Japanese_Pok%C3%A9mon_names";
            Document doc = Jsoup.connect(url).get();

            List<String[]> poketables = extractTables(doc);
            poketables.remove(0); // Remove the first table as it's not needed

            String[][] nam = extractNames(poketables);
            nam = ArrayUtils.removeElement(nam, new String[]{"",""});


            // Put these into a CSV file
            saveToCSV(nam, "pokemon_names.csv");

            System.out.println("Data saved to CSV file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
