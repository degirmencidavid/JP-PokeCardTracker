// Generate a default pokecard

public class CardGenerator {

    public static void main(String[] args) {
        PokeCard charizardCard = new PokeCard("Charizard", "Japanese", "リザードン", "SR", 212, 172, "Charizard Vstar",
                "リザードン Vstar", 7980.0, 50.0);

        System.out.println("Card Name: " + charizardCard.getProperty("gbpPrice"));
        System.out.println("JPY Price: " + charizardCard.getProperty("jpyPrice"));
    }
    
}
