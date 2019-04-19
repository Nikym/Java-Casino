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
    private ArrayList<Integer> player_bets = new ArrayList<>();
    private int number_of_players;

    /**
     * Class constructor to create a Blackjack game instance with given number of players.
     * Players value must be more than 0 (ie greater than or equal to 1) and 7 or less.
     *
     * @param players = integer number of players.
     * @param bets = integer array containing bets of players
     */
    public Blackjack(int players, int[] bets) throws IllegalArgumentException {
        if (players < 1 || players > 7)
            throw new IllegalArgumentException("Players argument must be >= 1 and < 8");
        else if (bets.length != players)
            throw new IllegalArgumentException("Number of bets must match number of players");

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
            // Add players bet
            this.player_bets.add(bets[i]);
        }
    }

    /**
     * Method to start a game of blackjack.
     */
    public void play() {
        dealDealerCard();
        System.out.println();

        // 0: Playing, 1: Stay, 2: Bust, 3: Blackjack, 4: Above dealer, 5: Equal to dealer
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
                System.out.print("[Player " + i + ": " + player_values.get(i) + "] " +
                        player_cards.get(i) + "\nBuy or Stay? [b/s]: ");
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
        // Dealer dealer cards
        dealDealerCard();
        if (dealer_value > 21) {
            getReturns(player_status, true);
            System.out.println("Returns: " + player_bets);
        } else {
            // Check for highest values from the players
            ArrayList<Integer> winners = new ArrayList<>();
            for (int i = 0; i < number_of_players; i++) {
                if (player_values.get(i) > dealer_value && player_values.get(i) < 22) {
                    winners.add(i);
                    if (player_status[i] != 3)
                        player_status[i] = 4;
                } else if (player_values.get(i) == dealer_value) {
                    player_status[i] = 5;
                }
            }
            // If there are no winners
            if (winners.size() == 0) {
                System.out.println("Dealer wins.");
                getReturns(player_status);
                System.out.println("Returns: " + player_bets);
            }
            // If there are winners
            else {
                System.out.println("\nWinners are: " + winners);
                getReturns(player_status);
                System.out.println("Returns: " + player_bets);
            }
        }
    }

    /**
     * Function to get the returns on the player bets.
     *
     * @param players = integer array representing player bets.
     */
    private void getReturns(int[] players) {
        getReturns(players,false);
    }

    /**
     * Function to get the returns on the player bets.
     *
     * @param players = integer array representing player bets.
     * @param bust = boolean representing if dealer is bust or not.
     */
    private void getReturns(int[] players, boolean bust) {
        // If dealer is bust then award all non-bust players
        if (bust) {
            for (int i = 0; i < player_bets.size(); i++) {
                if (players[i] != 2)
                    player_bets.set(i, player_bets.get(i) * 2);
                else
                    player_bets.set(i, 0);
            }
        }
        // Award all non-bust players who beat dealer
        for (int i = 0; i < player_bets.size(); i++) {
            if (players[i] == 4) {
                player_bets.set(i, player_bets.get(i) * 2);
            } else if (players[i] == 3)
                player_bets.set(i, (int) (player_bets.get(i) * 2.5));
            else if (players[i] != 5)
                player_bets.set(i, 0);
        }
    }

    /**
     * Method to get the integer value of a given card.
     *
     * @param card = String representation of the selected card.
     * @return integer value of the card.
     */
    private int getCardValue(String card) {
        // if (card.contains("A")) return 11;
        if (card.contains("2")) return 2;
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
    private void dealCard(int player) {
        String card = cards[rand.nextInt(13)] + suits[rand.nextInt(4)];
        player_cards.get(player).add(card);
        player_values.set(player, getHandValue(player_cards.get(player)));

        System.out.println("Player " + player + " dealt a " + card);
        System.out.println("Total: " + player_values.get(player));
    }

    /**
     * Method to deal a card to the dealer.
     */
    private void dealDealerCard() {
        if (dealer_value == 0) {
            String card = cards[rand.nextInt(13)] + suits[rand.nextInt(4)];
            dealer_hand.add(card);
            dealer_value = getHandValue(dealer_hand);

            System.out.println("Dealer dealt a " + card);
            System.out.println(dealer_hand + " Total: " + dealer_value);
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
        dealer_value = getHandValue(dealer_hand);

        System.out.println("Dealer dealt a " + card);
        System.out.println(dealer_hand + " Total: " + dealer_value);
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
        int aces = 0;
        // Count all card excluding aces
        for (String card : hand) {
            if (card.contains("A")) {
                ++aces;
                continue;
            }
            value += getCardValue(card);
        }
        // Count the ace values
        for (int i = 0; i < aces; i++) {
            if (value < 11) value += 11;
            else value += 1;
        }
        return value;
    }
}
