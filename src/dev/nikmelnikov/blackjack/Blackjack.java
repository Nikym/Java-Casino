package dev.nikmelnikov.blackjack;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class containing the rules and functionality for the simple blackjack
 * casino game.
 */
public class Blackjack {

    // Array that stores the information of the available cards in the deck
    private static final String cards[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    private static final String suits[] = {"♥", "♦", "♠", "♣"};
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
        if (players < 1 || players > 7) throw new IllegalArgumentException("Players argument must be >= 1 and < 8");

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

        // 0: Playing, 1: Stay, 2: Bust, 3: Blackjack
        int[] player_status = new int[number_of_players];
        for (int i = 0; i < number_of_players; i++) {
            dealCard(i);
            // Set status to playing
            player_status[i] = 0;
        }

        Scanner input = new Scanner(System.in);
        // Go through all the players with their choices
        for (int i = 0; i < number_of_players; i++) {
            // If player is still playing
            while (player_status[i] == 0) {
                System.out.print("[Player " + i + ": " + player_values.get(i) + "] Buy or Stay? [b/s]: ");
                String choice = input.next();
                // If chosen to buy
                if (choice.equalsIgnoreCase("b")) {
                    dealCard(i);
                    if (player_values.get(i) > 21) {
                        player_status[i] = 2;
                        System.out.println("Player " + i + " is bust!\n");
                    } else if (player_values.get(i) == 21) {
                        player_status[i] = 3;
                        System.out.println("Player " + i + " has a blackjack!\n");
                    }
                } else if (choice.equalsIgnoreCase("s")) {
                    player_status[i] = 1;
                    System.out.println("Player " + i + " chose to stay at " +
                            player_values.get(i) + "!\n");
                }
            }
        }
        // Check for highest values from the players
        ArrayList<Integer> winners = new ArrayList<>();
        int highest = 0;
        for (int i = 0; i < number_of_players; i++) {
            if (player_values.get(i) > highest && player_values.get(i) < 22) {
                highest = player_values.get(i);
                winners.clear();
                winners.add(i);
            }
            else if (player_values.get(i) == highest) {
                winners.add(i);
            }
        }
        // Dealer dealer cards
        dealDealerCard();
        if (dealer_value > highest && dealer_value < 22) {
            System.out.println("Dealer wins.");
        }
        else if (dealer_value == highest) {
            System.out.println("Draw, no one wins.");
        }
        else
            System.out.println("\nWinners are: " + winners);
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
        String card = cards[rand.nextInt(13)] + suits[rand.nextInt(4)];
        player_cards.get(player).add(card);
        player_values.set(player, player_values.get(player) + getCardValue(card));

        System.out.println("Player " + player + " dealt a " + card);
        System.out.println("Total: " + player_values.get(player));
    }

    /**
     * Method to deal a card to the dealer.
     */
    public void dealDealerCard() {
        if (dealer_value == 0) {
            String card = cards[rand.nextInt(13)] + suits[rand.nextInt(4)];
            dealer_hand.add(card);
            dealer_value += getCardValue(card);

            System.out.println("Dealer dealt a " + card);
            System.out.println("Total: " + dealer_value);
            return;
        }
        else if (dealer_value > 21) {
            System.out.println("Dealer is bust!");
            return;
        }
        else if (dealer_value == 21) {
            System.out.println("Dealer has a blackjack!");
            return;
        }
        else if (dealer_value >= 17) {
            System.out.println("Dealer stays at " + dealer_value + "!");
            return;
        }
        String card = cards[rand.nextInt(13)] + suits[rand.nextInt(4)];
        dealer_hand.add(card);
        dealer_value += getCardValue(card);

        System.out.println("Dealer dealt a " + card);
        System.out.println("Total: " + dealer_value);
        dealDealerCard();
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
