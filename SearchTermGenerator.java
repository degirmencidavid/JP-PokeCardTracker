import java.util.HashMap;
import java.io.File;

// Object used to create search terms for card shops 

public class SearchTermGenerator {

    private HashMap<String, String> enToJp;
    private HashMap<String, String> enToJpType;
    private HashMap<String, Object> stCardProperties;
    private String searchTerm;
    private String jpName;
    private String jpType;

    public SearchTermGenerator() {
        this.searchTerm = "R";
    }

    public SearchTermGenerator(HashMap<String, Object> properties) {
        
        jpTypeGenerator();
        jpNameGenerator();
        jpName = enToJp.get(properties.get("pokeName"));
        jpType = enToJpType.get(properties.get("cardType"));
        stCardProperties = new HashMap<>();
        stCardProperties.put("Name", jpName);
        stCardProperties.put("cardType", jpType);
        stCardProperties.put("setNo", properties.get("setNo"));
        stCardProperties.put("setLimit", properties.get("setLimit"));

        this.searchTerm = generateSearchTerm(stCardProperties);

    }

    // Generate a search term based off information
    private String generateSearchTerm(HashMap<String, Object> cardProperties) {
        StringBuilder sb = new StringBuilder();
        searchTerm = sb.append(cardProperties.get("Name")).append("+").append(cardProperties.get("cardType")).append(String.valueOf(cardProperties.get("setNo"))).append("/").append(String.valueOf(cardProperties.get("setLimit"))).toString();
        return searchTerm;
    }

    // Generate hashmap of jp names from csv (if it extists otherwise create it)
    private void jpNameGenerator() {
        this.enToJp = new HashMap<>();
        File csvFile = new File("pokemon_names.csv");
        if (!csvFile.exists()) {
            PokemonNameExtractor.buldTranslationTable();
        }
        this.enToJp = CSVProcessor.readCSVToMap("pokemon_names.csv");
        this.enToJp.put("","");
    }

    private void jpTypeGenerator() {
        this.enToJpType = new HashMap<>();
        this.enToJpType.put("v","v");
        this.enToJpType.put("vstar","vstar");
        this.enToJpType.put("vmax","vmax");
        this.enToJpType.put("radiant","\u304B\u304C\u3084\u304F");
        this.enToJpType.put("shining","\u3072\u304B\u308B");
        this.enToJpType.put("sar","sar");
        this.enToJpType.put("sr","sr");
        this.enToJpType.put("ex","ex");
        this.enToJpType.put("gx","gx");
        this.enToJpType.put("sa","sa");
        this.enToJpType.put("ar","ar");
        this.enToJpType.put("chr","chr");
        this.enToJpType.put("csr","csr");
        this.enToJpType.put("","");
    }
    
    public String getSearchTerm() {
        return this.searchTerm;
    }

    public String getJpCardType() {
        return this.jpType;
    }

    public String getJpCardName() {
        return this.jpName;
    }

}
