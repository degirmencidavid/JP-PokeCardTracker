import java.util.HashMap;

// Pokecard object

public class PokeCard {

    // Prices
    private static String[] currencyNames = {"JPY", "GBP"};

    // Hashmaps of cardshop data
    private HashMap<String, CardShop> cardShopMap;
    private HashMap<String, Double> cardPrices;

    // Properties hashmap
    private HashMap<String, Object> properties;

    // Default constructor with a sample card
    public PokeCard() {
        // Properties
        properties = new HashMap<>();
        properties.put("pokeName", "Charizard");
        properties.put("language", "Japanese");
        properties.put("pokeNameLang", "リザードン");
        properties.put("rarity", "SR");
        properties.put("setNo", 212);
        properties.put("setLimit", 172);
        properties.put("cardType", "Charizard Vstar"); // Change "cardName" to "cardType"
        properties.put("cardTypeLang", "Vstar");

        properties.put("gbpPrice", 50.00);
        properties.put("jpyPrice", 7980.0);

        double jpyPrice = 7980.0;
        double gbpPriceConverted = Currencies.convertCurrency(jpyPrice, "JPY", "GBP");
        properties.put("gbpPriceConverted", gbpPriceConverted);

        this.cardShopMap = new HashMap<>();
        for (int i = 0; i < CardShop.cardShops.length; i++) {
            CardShop shop = new CardShop(CardShop.cardShops[i], CardShop.cardShopURL[i], CardShop.cardShopSt[i]);
            cardShopMap.put(CardShop.cardShops[i], shop);
        }

        this.cardPrices = new HashMap<>();
        for (String currency : currencyNames) {
            cardPrices.put(currency, 0.0);
        }
    }

    // Constructor
    public PokeCard(String pokeName, String language, String pokeNameLang, String rarity, int setNo, int setLimit,
                    String cardType, String cardTypeLang, double jpyPrice, double gbpPrice) { // Change "cardName" to "cardType"

        // Properties
        properties = new HashMap<>();
        properties.put("pokeName", pokeName);
        properties.put("language", language);
        properties.put("pokeNameLang", pokeNameLang);
        properties.put("rarity", rarity);
        properties.put("setNo", setNo);
        properties.put("setLimit", setLimit);
        properties.put("cardType", cardType); // Change "cardName" to "cardType"
        properties.put("cardTypeLang", cardTypeLang);

        properties.put("jpyPrice", jpyPrice);
        properties.put("gbpPrice", gbpPrice);

        double gbpPriceConverted = Currencies.convertCurrency(jpyPrice, "JPY", "GBP");
        properties.put("gbpPriceConverted", gbpPriceConverted);

        // Generate the cardShopMap HashMap
        this.cardShopMap = new HashMap<>();
        for (int i = 0; i < CardShop.cardShops.length; i++) {
            CardShop shop = new CardShop(CardShop.cardShops[i], CardShop.cardShopURL[i], CardShop.cardShopSt[i]);
            cardShopMap.put(CardShop.cardShops[i], shop);
        }

        // Generate the cardPrices HashMap
        this.cardPrices = new HashMap<>();
        for (String currency : currencyNames) {
            cardPrices.put(currency, 0.0);
        }
    }

    //Property getter
    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    //Multiple property getter
    public Object[] getProperties(String[] propertyNames) {
        int len = propertyNames.length;
        Object[] values = new Object[len];
        for (int i = 0; i < propertyNames.length; i++) {
            values[i] = properties.get(propertyNames[i]);
        }
        return values;
    }

    //Property setter
    public void setProperty(String propertyName, Object value) {
        properties.put(propertyName, value);
    }

    //Multiple property setter
    public void setProperties(String[] propertyNames, Object[] values) {
        for (int i = 0; i < propertyNames.length; i++) {
            properties.put(propertyNames[i], values[i]);
        }
    }

    public static void main(String[] args) {
        // Create a default PokeCard
        PokeCard pokeCard = new PokeCard();

        // Get the properties of the default PokeCard
        String pokeName = (String) pokeCard.getProperty("pokeName");
        String language = (String) pokeCard.getProperty("language");
        String pokeNameLang = (String) pokeCard.getProperty("pokeNameLang");
        String rarity = (String) pokeCard.getProperty("rarity");
        int setNo = (int) pokeCard.getProperty("setNo");
        int setLimit = (int) pokeCard.getProperty("setLimit");
        String cardName = (String) pokeCard.getProperty("cardName");
        String cardNameLang = (String) pokeCard.getProperty("cardNameLang");
        double jpyPrice = (double) pokeCard.getProperty("jpyPrice");
        double gbpPrice = (double) pokeCard.getProperty("gbpPrice");
        double gbpPriceConverted = (double) pokeCard.getProperty("gbpPriceConverted");

        // Print the properties of the default PokeCard
        System.out.println("Poke Name: " + pokeName);
        System.out.println("Language: " + language);
        System.out.println("Poke Name Lang: " + pokeNameLang);
        System.out.println("Rarity: " + rarity);
        System.out.println("Set No: " + setNo);
        System.out.println("Set Limit: " + setLimit);
        System.out.println("Card Name: " + cardName);
        System.out.println("Card Name Lang: " + cardNameLang);
        System.out.println("JPY Price: " + jpyPrice);
        System.out.println("GBP Price: " + gbpPrice);
        System.out.println("GBP Price Converted: " + gbpPriceConverted);
    }

}
