package dev.nikmelnikov;

import dev.nikmelnikov.blackjack.Blackjack;

public class Main {

    public static void main(String[] args) {
        int[] array = {5, 10};
        Blackjack blackjack = new Blackjack(2, array);
        blackjack.play();
    }
}
