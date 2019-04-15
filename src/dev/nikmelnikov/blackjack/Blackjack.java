package dev.nikmelnikov.blackjack;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class containing the rules and functionality for the simple blackjack
 * casino game.
 */
public class Blackjack {

    // Array that stores the information of the available cards in the deck
    private static final String cards[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final String suits[] = {"H", "D", "S", "C"};
    private static final String deck[] = {
            "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH",
            "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD",
            "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS",
            "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC"
    };
    private Random rand;

    // Dealer variables
    private ArrayList<String> dealer_hand = new ArrayList<>();
    private int dealer_value;

    // Player variables
    private ArrayList<ArrayList<String>> player_cards = new ArrayList<>();
    private ArrayList<Integer> player_values = new ArrayList<>();
    private int number_of_players;

    /**
     * Class constructor to create a Blackjack game instance with given number of players.
     * Players value must be more than 0 (ie greater than or equal to 1).
     *
     * @param players = integer number of players.
     */
    public Blackjack(int players) throws IllegalArgumentException {
        if (players < 1) throw new IllegalArgumentException("Players argument must be >= 1");

        this.rand = new Random();
        this.dealer_value = 0;

        // Initialise the players
        this.number_of_players = players;
        for (int i = 0; i < players; i++) {
            // Create new player value
            this.player_values.add(0);
            // Create new player hand
            ArrayList<String> hand = new ArrayList<>();
            this.player_cards.add(hand);
        }
    }

    /**
     * Method to start a game of blackjack.
     */
    public void play() {
        dealDealerCard();
        System.out.println();

        for (int i = 0; i < number_of_players; i++) {
            dealCard(i);
        }
    }

    /**
     * Method to get the integer value of a given card.
     *
     * @param card = String representation of the selected card.
     * @return integer value of the card.
     */
    private int getCardValue(String card) {
        if (card.contains("A")) return 11;
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
     * Method to deal a card to a player.
     *
     * @param player = Integer ID of a player.
     */
    public void dealCard(int player) {
        String card = deck[rand.nextInt(52)];
        player_cards.get(player).add(card);
        player_values.set(player, player_values.get(player) + getCardValue(card));

        System.out.println("Player " + player + " dealt a " + card);
        System.out.println("Total: " + player_values.get(player));
    }

    /**
     * Method to deal a card to the dealer.
     */
    public void dealDealerCard() {
        if (dealer_value >= 17) return;
        String card = deck[rand.nextInt(52)];
        dealer_hand.add(card);
        dealer_value += getCardValue(card);

        System.out.println("Dealer dealt a " + card);
        System.out.println("Total: " + dealer_value);
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
     * Method to get the given players cards (hand).
     *
     * @param player = integer ID of the player (starting at 0).
     * @return ArrayList of type string of player's hand.
     */
    public ArrayList<String> getPlayerHand(int player) {
        return player_cards.get(player);
    }

    /**
     * Method to get the given players hand value.
     *
     * @param player = integer ID of the player (starting at 0).
     * @return integer value of the players hand.
     */
    public int getPlayerValue(int player) {
        return player_values.get(player);
    }

    public int getNumberOfPlayers() {
        return number_of_players;
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
            value += getCardValue(card);
        }
        return value;
    }
}
