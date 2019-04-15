package dev.nikmelnikov.blackjack;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

/**
 * Class containing the rules and functionality for the simple blackjack
 * casino game.
 */
public class Blackjack {

    // Array that stores the information of the available cards in the deck
    private static String cards[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static String suits[] = {"H", "D", "S", "C"};
    private static String deck[] = {
            "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH",
            "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD",
            "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS",
            "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC"
    };

    // Dealer variables
    private ArrayList<String> dealer_hand = new ArrayList<>();
    private int dealer_value;

    // Player variables
    private ArrayList<ArrayList<String>> player_cards = new ArrayList<>();
    private ArrayList<Integer> player_values = new ArrayList<>();

    /**
     * Class constructor to create a Blackjack game instance with given number of players.
     * Players value must be more than 0 (ie greater than or equal to 1).
     *
     * @param players = integer number of players.
     */
    public Blackjack(int players) throws IllegalArgumentException {
        if (players < 1) throw new IllegalArgumentException("Players argument must be >= 1");

        this.dealer_value = 0;
        // Initialise the players
        for (int i = 0; i < players; i++) {
            // Create new player value
            this.player_values.add(0);
            // Create new player hand
            ArrayList<String> hand = new ArrayList<>();
            this.player_cards.add(hand);
        }
    }
    /**
     * Method to get the integer value of a given card.
     *
     * @param card = String representation of the selected card.
     * @return integer value of the card.
     */
    private int cardValue(String card) {
        if (card.contains("A")) return 1;
        else if (card.contains("2")) return 2;
        else if (card.contains("3")) return 3;
        else if (card.contains("4")) return 4;
        else if (card.contains("5")) return 5;
        else if (card.contains("6")) return 6;
        else if (card.contains("7")) return 7;
        else if (card.contains("8")) return 8;
        else if (card.contains("9")) return 9;
        else if (card.contains("10") || card.contains("J") || card.contains("Q") || card.contains("K")) return 10;
        return -1;
    }

    /**
     * Method to start a new game
     */
    public void newGame() {
        throw new NotImplementedException();
    }

    /**
     * Method to get the value of the dealer's hand.
     *
     * @return integer value of the dealers hand.
     */
    public int getDealerValue() {
        return dealer_value;
    }

    /**
     * Method to get the value of a given hand.
     *
     * @param hand An ArrayList of type string.
     * @return integer value of the hand.
     */
    private int getHandValue(ArrayList<String> hand) {
        int value = 0;
        for (String card : hand) {
            value += cardValue(card);
        }
        return value;
    }

    public static void main(String[] args) {

    }
}
