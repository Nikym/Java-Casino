package dev.nikmelnikov;

import dev.nikmelnikov.blackjack.Blackjack;

public class Main {

    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack(2);
        blackjack.play();
    }
}
