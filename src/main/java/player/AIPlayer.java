/*
todo: implement AI
*/

package main.java.player;

import main.java.game.Game;

import java.util.Random;
import java.util.Scanner;

public abstract class AIPlayer extends Player {
    protected static final int AI_PID_OFFSET = 1000000000;

    public AIPlayer(String mode) {
        super(mode);

        random = new Random();
        isHuman = false;
    }

    protected Mode mode;
    protected final Random random;

    public static int getOffset() {
        return AI_PID_OFFSET;
    }

    public Mode getMode() {
        return mode;
    }

    @Override
    public void setName(String name) {}

    @Override
    public GameCounter winCounter() {
        return null;
    }

    @Override
    public GameCounter loseCounter() {
        return null;
    }

    @Override
    public GameCounter totalCounter() {
        return null;
    }

    @Override
    public void addWinGame(Player player) {}

    @Override
    public void addLoseGame(Player player) {}

    @Override
    public void addDrawGame(Player player) {}

    @Override
    public double calculateWinRateToHuman() {
        return -1.0;
    }

    @Override
    public double calculateWinRateToAI() {
        return -1.0;
    }

    @Override
    public double calculateWinRate(int pid) {
        return -1.0;
    }

    @Override
    public String toString() {
        return "[AI: " + name + "]";
    }

    @Override
    public String print() {
        return "";
    }

    @Override
    public void load(Scanner scanner) {}
}
