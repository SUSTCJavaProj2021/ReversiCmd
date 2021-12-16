package main.java.player;

import main.java.game.Game;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);

        isHuman = true;
    }

    public HumanPlayer(Scanner scanner) {
        super("");

        isHuman = true;
        load(scanner);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public GameCounter winCounter() {
        return win;
    }

    @Override
    public GameCounter loseCounter() {
        return lose;
    }

    @Override
    public GameCounter totalCounter() {
        return total;
    }

    @Override
    public void addWinGame(Player player) {
        win.addGame(player);
        total.addGame(player);
    }

    @Override
    public void addLoseGame(Player player) {
        lose.addGame(player);
        total.addGame(player);
    }

    @Override
    public void addDrawGame(Player player) {
        total.addGame(player);
    }

    @Override
    public double calculateWinRateToHuman() {
        if (total.humanCount() == 0) {
            return -1.0;
        }

        return 1.0 * win.humanCount() / total.humanCount();
    }

    @Override
    public double calculateWinRateToAI() {
        if (total.AICount() == 0) {
            return -1.0;
        }

        return 1.0 * lose.AICount() / total.AICount();
    }

    @Override
    public double calculateWinRate(int pid) {
        if (total.playerCount(pid) == 0) {
            return -1.0;
        }

        return 1.0 * win.playerCount(pid) / total.playerCount(pid);
    }

    @Override
    public String toString() {
        return "[ID = " + pid + " Name = " + name + " WinMap = " + win + " LoseMap = " + lose + " TotalMap = " + total + "]";
    }

    @Override
    public String print() {
        return pid + "\n" + name + "\n" + win.print() + lose.print() + total.print();
    }

    @Override
    public void load(Scanner scanner) {
        pid = scanner.nextInt();
        name = scanner.next();
        win.load(scanner);
        lose.load(scanner);
        total.load(scanner);
    }

    @Override
    public int[] nextStep(Game game) {
        assert isHuman;

        return new int[] {-1, -1};
    }
}
