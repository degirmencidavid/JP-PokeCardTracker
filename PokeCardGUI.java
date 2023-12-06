import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.io.IOException;
import java.net.URL;


public class PokeCardGUI {

    // Frame Dimensions
    private final int FRAME_HEIGHT = 720;
    private final int FRAME_WIDTH = 720;

    // Prices
    private static String[] currencyNames = {"JPY", "GBP"};

    // GUI components
    private JFrame frame;
    private JTextField pokeNameField, pokeNameLangField, rarityField, setNoField, setLimitField, cardNameField, cardNameLangField, jpyPriceField, gbpPriceField;
    private JComboBox<String> languageComboBox, cardShopComboBox; // Add cardShopComboBox
    private JButton createButton, displayButton;
    private JTextArea displayArea;

    // JLabel to display the selected Card Shop
    private JLabel selectedCardShopLabel;

    public PokeCardGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("PokeCard Creator");
        frame.setLayout(new GridLayout(0, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a main panel with BoxLayout in Y_AXIS
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Components for PokeCard properties
        pokeNameField = new JTextField(15);
        languageComboBox = new JComboBox<>(new String[]{"English", "Japanese"});
        pokeNameLangField = new JTextField(15);
        rarityField = new JTextField(15);
        setNoField = new JTextField(15);
        setLimitField = new JTextField(15);
        cardNameField = new JTextField(15);
        cardNameLangField = new JTextField(15);
        jpyPriceField = new JTextField(15);
        gbpPriceField = new JTextField(15);

        // Initially hide pokeNameLangField and cardNameLangField
        pokeNameLangField.setVisible(false);
        cardNameLangField.setVisible(false);

        // Initialize the cardShopComboBox
        cardShopComboBox = new JComboBox<>(CardShop.cardShops);

        // Listener to keep track of the selected Card Shop and update the label
        cardShopComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCardShop = (String) cardShopComboBox.getSelectedItem();
                selectedCardShopLabel.setText("Selected Card Shop: " + selectedCardShop);
            }
        });

        // Language ComboBox ActionListener
        languageComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedLanguage = (String) languageComboBox.getSelectedItem();
                pokeNameLangField.setVisible(selectedLanguage.equals("Japanese"));
                cardNameLangField.setVisible(selectedLanguage.equals("Japanese"));
            }
        });

        // Card Shop ComboBox
        cardShopComboBox = new JComboBox<>(CardShop.cardShops);

        // Button to create PokeCard object
        // Button to create PokeCard object
        // Button to create PokeCard object
        createButton = new JButton("Create PokeCard");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Parse the input data from text fields and convert to lowercase
                    String pokeName = pokeNameField.getText().toLowerCase();
                    String language = ((String) languageComboBox.getSelectedItem()).toLowerCase();
                    String pokeNameLang = pokeNameLangField.getText().toLowerCase();
                    String rarity = rarityField.getText().toLowerCase();
                    
                    // Check for empty fields and set them to an empty string
                    if (pokeName.isEmpty()) pokeName = "";
                    if (language.isEmpty()) language = "";
                    if (pokeNameLang.isEmpty()) pokeNameLang = "";
                    if (rarity.isEmpty()) rarity = "";
                    
                    int setNo = Integer.parseInt(setNoField.getText());
                    int setLimit = Integer.parseInt(setLimitField.getText());
                    
                    String cardName = cardNameField.getText().toLowerCase();
                    String cardNameLang = cardNameLangField.getText().toLowerCase();
                    double jpyPrice = Double.parseDouble(jpyPriceField.getText());
                    double gbpPrice = Double.parseDouble(gbpPriceField.getText());

                    // Convert JPY price to GBP with 2 decimal places
                    double gbpPriceConverted = Currencies.convertCurrency(jpyPrice, "JPY", "GBP");
                    gbpPriceConverted = Double.parseDouble(new DecimalFormat("##.##").format(gbpPriceConverted));

                    // Create the PokeCard object
                    PokeCard pokeCard = new PokeCard(pokeName, language, pokeNameLang, rarity, setNo, setLimit,
                            cardName, cardNameLang, jpyPrice, gbpPriceConverted);

                    // Display the created PokeCard properties
                    displayProperties(pokeCard);
                } catch (NumberFormatException ex) {
                    // Handle the exception when the wrong data type is entered
                    String message = "Invalid data type for some fields.\n\n";
                    message += "Please ensure that the following fields have the correct data type:\n";
                    message += "Set No, Set Limit, JPY Price, GBP Price\n";
                    JOptionPane.showMessageDialog(frame, message, "Invalid Data Type", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        
        // Display area for showing PokeCard properties
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);

        // Add components to the frame
        frame.add(new JLabel("Card Shop:")); // Add Card Shop label
        frame.add(cardShopComboBox); // Add Card Shop combo box
        frame.add(new JLabel("Poke Name:"));
        frame.add(pokeNameField);
        frame.add(new JLabel("Language:"));
        frame.add(languageComboBox);
        frame.add(new JLabel("Poke Name (Japanese):"));
        frame.add(pokeNameLangField);
        frame.add(new JLabel("Rarity:"));
        frame.add(rarityField);
        frame.add(new JLabel("Set No:"));
        frame.add(setNoField);
        frame.add(new JLabel("Set Limit:"));
        frame.add(setLimitField);
        frame.add(new JLabel("Card Type:"));
        frame.add(cardNameField);
        frame.add(new JLabel("Card Type (Japanese):"));
        frame.add(cardNameLangField);
        frame.add(new JLabel("JPY Price:"));
        frame.add(jpyPriceField);
        frame.add(new JLabel("GBP Price:"));
        frame.add(gbpPriceField);
        frame.add(createButton);
        frame.add(new JScrollPane(displayArea));

        // Set the height of the frame to 720 pixels
        int frameHeight = FRAME_HEIGHT;
        int frameWidth = FRAME_WIDTH;
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));

        frame.pack();
        frame.setVisible(true);
    }

    private void displayProperties(PokeCard pokeCard) {

        StringBuilder propertiesBuilder = new StringBuilder();
        String selectedCardShop = (String) cardShopComboBox.getSelectedItem();
        propertiesBuilder.append("Card Shop: ").append(selectedCardShop).append("\n");
        propertiesBuilder.append("Pokemon Name: ").append(pokeCard.getProperty("pokeName")).append("\n");
        propertiesBuilder.append("Language: ").append(pokeCard.getProperty("language")).append("\n");
        propertiesBuilder.append("Pokemon Name (Japanese): ").append(pokeCard.getProperty("pokeNameLang")).append("\n");
        propertiesBuilder.append("Rarity: ").append(pokeCard.getProperty("rarity")).append("\n");
        propertiesBuilder.append("Set No: ").append(pokeCard.getProperty("setNo")).append("\n");
        propertiesBuilder.append("Set Limit: ").append(pokeCard.getProperty("setLimit")).append("\n");
        propertiesBuilder.append("Card Type: ").append(pokeCard.getProperty("cardType")).append("\n"); // Change "cardName" to "cardType"
        propertiesBuilder.append("Card Type (Japanese): ").append(pokeCard.getProperty("cardNameLang")).append("\n");
        propertiesBuilder.append("JPY Price: ").append(pokeCard.getProperty("jpyPrice")).append("\n");
        propertiesBuilder.append("GBP Price: ").append(pokeCard.getProperty("gbpPrice")).append("\n");
        propertiesBuilder.append("GBP Price Converted: ").append(pokeCard.getProperty("gbpPriceConverted")).append("\n");

        // Get the required properties for generating the search term
        String[] searchPropertyNames = {"pokeName", "cardType", "setNo", "setLimit"};

        // Create a HashMap to store the properties
        HashMap<String, Object> searchProperties = new HashMap<>();
        for (String propertyName : searchPropertyNames) {
            searchProperties.put(propertyName, pokeCard.getProperty(propertyName));
        }

        // Generate the search term based on the properties
        SearchTermGenerator searchTermGenerator = new SearchTermGenerator(searchProperties);
        String searchTerm = searchTermGenerator.getSearchTerm();

        // Find the index of selectedCardShop in the cardShops array
        int index = -1;
        for (int i = 0; i < CardShop.cardShops.length; i++) {
            if (selectedCardShop.equals(CardShop.cardShops[i])) {
                index = i;
                break;
            }
        }

        // Generate the searched URL based on the index and search term
        StringBuilder searchURL = new StringBuilder();
        String searchedURL = searchURL.append(CardShop.cardShopURL[index]).append(CardShop.cardShopSt[index]).append(searchTerm).toString();

        // Append the searched URL to the display
        propertiesBuilder.append("Searched URL: ").append(searchedURL).append("\n");

        // Append the search term to the display
        propertiesBuilder.append("Search Term: ").append(searchTerm).append("\n");

        // Set the display text
        displayArea.setText(propertiesBuilder.toString());

         // Create a CardListing object and fetch its properties
        try {
            CardListing cardListing = new CardListing(searchedURL);
            String cardImg = (String) cardListing.getProperty("cardImg");
            String cardName = (String) cardListing.getProperty("cardName");
            String setNumber = (String) cardListing.getProperty("setNumber");
            int cardPrice = (int) cardListing.getProperty("cardPrice");
            int cardStock = (int) cardListing.getProperty("cardStock");

            // Convert the Card Price to GBP
            double cardPriceGBP = Currencies.convertCurrency(cardPrice, "JPY", "GBP");
            cardPriceGBP = Double.parseDouble(new DecimalFormat("##.##").format(cardPriceGBP));

            // Check if the cardImg is not empty and load the image
            if (!cardImg.isEmpty()) {
                ImageIcon cardImageIcon = new ImageIcon(new URL(cardImg));
                JLabel cardImageLabel = new JLabel(cardImageIcon);

                // Custom JPanel to hold the card image and properties
                JPanel cardPanel = new JPanel(new BorderLayout());
                cardPanel.add(cardImageLabel, BorderLayout.NORTH);

                // Create a StringBuilder to hold the card properties
                StringBuilder cardInfoBuilder = new StringBuilder();
                cardInfoBuilder.append("Card Name: ").append(cardName).append("\n");
                cardInfoBuilder.append("Set Number: ").append(setNumber).append("\n");
                cardInfoBuilder.append("Card Price: ").append(cardPrice).append(" JPY").append("\n");
                cardInfoBuilder.append("Card Price GBP: ").append(cardPriceGBP).append(" GBP").append("\n");
                cardInfoBuilder.append("Card Stock: ").append(cardStock).append(" in stock").append("\n");

                // Create a JTextArea to display the card properties
                JTextArea cardInfoArea = new JTextArea(cardInfoBuilder.toString());
                cardInfoArea.setEditable(false);

                // Add the JTextArea to the JPanel
                cardPanel.add(cardInfoArea, BorderLayout.CENTER);

                // Show the custom JPanel in the pop-up box
                JOptionPane.showMessageDialog(frame, cardPanel, "Card Information", JOptionPane.PLAIN_MESSAGE);
            } else {
                // If cardImg is empty, show only the card properties without the image
                StringBuilder cardInfoBuilder = new StringBuilder();
                cardInfoBuilder.append("Card Name: ").append(cardName).append("\n");
                cardInfoBuilder.append("Set Number: ").append(setNumber).append("\n");
                cardInfoBuilder.append("Card Price: ").append(cardPrice).append(" JPY").append("\n");
                cardInfoBuilder.append("Card Price GBP: ").append(cardPriceGBP).append(" GBP").append("\n");
                cardInfoBuilder.append("Card Stock: ").append(cardStock).append(" in stock").append("\n");

                // Show the card properties in the pop-up box
                JOptionPane.showMessageDialog(frame, cardInfoBuilder.toString(), "Card Information", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException e) {
            // Handle the exception if CardListing object cannot be created
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching card information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to display the card image from the given URL
    private void displayCardImage(String imageURL) {
        try {
            // Check if the image URL is valid
            URL url = new URL(imageURL);
            Image image = ImageIO.read(url);

            if (image != null) {
                // Scale the image to a width of 200 pixels while maintaining aspect ratio
                int scaledWidth = 200;
                int scaledHeight = (int) ((double) image.getHeight(null) * scaledWidth / image.getWidth(null));
                Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                // Create and show the image label in a dialog
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                JOptionPane.showMessageDialog(frame, imageLabel, "Card Image", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Error loading the card image.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error loading the card image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PokeCardGUI();
            }
        });
    }
}