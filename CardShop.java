import java.util.HashMap;

public class CardShop {

    // Cardshop data
    public static String[] cardShops = {"CARDRUSH", "MANASOURCE", "DORASUTA", "FURU1"};
    public static String[] cardShopURL = {"https://www.cardrush-pokemon.jp/", "https://www.manasource.net/", "https://dorasuta.jp/pokemon-card", "https://www.furu1.online/"};
    public static String[] cardShopSt = {"product-list?keyword=", "product-list?keyword=", "product-list?kw=", "search?category=&kw="};

    // Properties
    private String language;
    private String name;
    private String URL;
    private String searchURL;
    
    public CardShop() {

    }

    public CardShop(String name, String URL, String searchURL) {
        this.name = name;
        this.URL = URL;
        this.searchURL = searchURL;
    }

    public String getSearchURL(String searchTerm) {
        return this.URL + this.searchURL;
    }

    public String getCardShopURL(String cardShopName) {
        for (int i = 0; i < cardShops.length; i++) {
            if (cardShops[i].equalsIgnoreCase(cardShopName)) {
                return cardShopURL[i];
            }
        }
        return ""; // Return an empty string if the cardShopName is not found
    }
    

    public String getPropertiesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Language: ").append(language).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Base URL: ").append(URL).append("\n");
        sb.append("Search URL: ").append(searchURL).append("\n");
        return sb.toString();
    }

    public HashMap<String, String> getPropertiesAsHashMap() {
        HashMap<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put("Language", language);
        propertiesMap.put("Base URL", URL);
        propertiesMap.put("Search URL", searchURL);
        return propertiesMap;
    }
}
