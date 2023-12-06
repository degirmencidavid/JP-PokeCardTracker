import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONObject;

// Class to correctly convert and keep track of currency using exchangerate-api

public class Currencies {

    private static HashMap<String, Double> exchangeRates;

    // Fetch exchange rates and initialize the exchangeRates HashMap
    static {
        exchangeRates = new HashMap<>();
        fetchExchangeRates();
    }

    private static void fetchExchangeRates() {
        String apiKey = "48047fec1ad8289e74880c42";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

        try {
            URL apiURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                // Parse JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject rates = jsonResponse.getJSONObject("conversion_rates");

                // JPY and GBP rates
                exchangeRates.put("JPY", rates.getDouble("JPY"));
                exchangeRates.put("GBP", rates.getDouble("GBP"));

            } else {
                System.out.println("Failed to fetch exchange rates. Response code: " + responseCode);
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double exchangeRateFrom = exchangeRates.getOrDefault(fromCurrency, 1.0);
        double exchangeRateTo = exchangeRates.getOrDefault(toCurrency, 1.0);

        // Convert the amount from 'fromCurrency' to 'toCurrency'
        return (amount / exchangeRateFrom) * exchangeRateTo;
    }

    public static void updateExchangeRate(String currency, double exchangeRate) {
        exchangeRates.put(currency, exchangeRate);
    }
    public static double getExchangeRate(String currency) {
        return exchangeRates.getOrDefault(currency, 1.0);
    }

    public static double getConversionRate(String fromCurrency, String toCurrency) {
        double exchangeRateFrom = exchangeRates.getOrDefault(fromCurrency, 1.0);
        double exchangeRateTo = exchangeRates.getOrDefault(toCurrency, 1.0);

        // Calculate the conversion rate from 'fromCurrency' to 'toCurrency'
        return exchangeRateTo / exchangeRateFrom;
    }

    public static void main(String[] args) {
  
        double jpyExchangeRate = Currencies.getExchangeRate("JPY");
        double gbpExchangeRate = Currencies.getExchangeRate("GBP");
        System.out.println("JPY Exchange Rate: " + jpyExchangeRate);
        System.out.println("GBP Exchange Rate: " + gbpExchangeRate);

        double conversionRateJPYtoGBP = Currencies.getConversionRate("JPY", "GBP");
        System.out.println("JPY to GBP Conversion Rate: " + conversionRateJPYtoGBP);
    }

}
