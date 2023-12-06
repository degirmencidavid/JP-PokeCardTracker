import java.util.HashMap;
import org.jsoup.Jsoup;
import java.io.File;
import java.io.IOException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CardListing {

    private HashMap<String, Object> listingProperties;
    private final String[] propertyArray = {"cardName", "setNumber", "cardPrice", "cardImg", "cardStock"};

    public CardListing() {
        this.listingProperties = new HashMap<>();
        File img = new File("res/charizard212.jpg");
        Object[] valueArray = {"\u30EA\u30B6\u30FC\u30C9\u30F3vstar", "212/172", (double) 7980, img, (int) 1};
        for (int i = 0; i < this.propertyArray.length; i++) {
            this.listingProperties.put(propertyArray[i], valueArray[i]);
        }
    }

    public CardListing(Object[] valueArray) {
        this.listingProperties = new HashMap<>();
        for (int i = 0; i < this.propertyArray.length; i++) {
            this.listingProperties.put(propertyArray[i], valueArray[i]);
        }
    }

    public CardListing(String searchTerm) throws IOException {
        this.listingProperties = new HashMap<>();

        // Create an OkHttpClient instance to make the HTTP request
        OkHttpClient client = new OkHttpClient();

        // Create the request with headers
        Request request = new Request.Builder()
                .url(searchTerm)
                .addHeader("Accept-Language", "ja") // Set the preferred language to Japanese
                .build();

        // Make the request and get the response
        Response response = client.newCall(request).execute();

        // Parse the response body with Jsoup
        Document doc = Jsoup.parse(response.body().string(), searchTerm);

        // Set the correct encoding for reading the HTML content
        doc.outputSettings().charset("UTF-8");

        // Extract the required elements using CSS selectors
        Element itemBox = doc.selectFirst("div.itemlist_box.clearfix");
        Element imgElement = itemBox.selectFirst("img");
        Element cardNameElement = itemBox.selectFirst("p.item_name span.goods_name");
        Element setNumberElement = itemBox.selectFirst("p.item_name span.model_number span.model_number_value");
        Element cardPriceElement = itemBox.selectFirst("p.selling_price span.figure");
        Element cardStockElement = itemBox.selectFirst("p.stock");


        // Get the attributes and text from the elements
        String cardImg = imgElement.attr("src");
        String cardName = cardNameElement.text();
        String setNumber = setNumberElement.text();
        
        // Extract numerical characters from cardPrice and cardStock strings
        String cardPriceText = cardPriceElement.text().replaceAll("[^0-9]", "");
        String cardStockText = cardStockElement.text().replaceAll("[^0-9]", "");

        // Convert the extracted numerical characters to integers
        int cardPrice = Integer.parseInt(cardPriceText);
        int cardStock = Integer.parseInt(cardStockText);

        // Save the extracted properties to the HashMap
        listingProperties.put("cardImg", cardImg);
        listingProperties.put("cardName", cardName);
        listingProperties.put("setNumber", setNumber);
        listingProperties.put("cardPrice", cardPrice);
        listingProperties.put("cardStock", cardStock);
    }


    public Object getProperty(String property) {
        return this.listingProperties.get(property);
    }

    public static void main(String[] args) {
        // The URL to fetch data from
        String url = "https://www.cardrush-pokemon.jp/product-list?keyword=%E3%83%AA%E3%82%B6%E3%83%BC%E3%83%89%E3%83%B3+212";

        try {
            // Create a new CardListing object with the specified URL
            CardListing cardListing = new CardListing(url);

            // Display the extracted properties
            System.out.println("Card Name: " + cardListing.getProperty("cardName"));
            System.out.println("Set Number: " + cardListing.getProperty("setNumber"));
            System.out.println("Card Price: " + cardListing.getProperty("cardPrice"));
            System.out.println("Card Image URL: " + cardListing.getProperty("cardImg"));
            System.out.println("Card Stock: " + cardListing.getProperty("cardStock"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
