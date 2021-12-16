package main.java.player;

import main.java.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public abstract class Player {
    public static class GameCounter {
        private int toHuman, toAI;
        private Map<Integer, Integer> times;

        public GameCounter() {
            times = new HashMap<>();
        }

        protected void addGame(Player player) {
            if (player.isHuman) {
                ++ toHuman;
            } else {
                ++ toAI;
            }

            times.merge(player.pid, 1, (key, value) -> value + 1);
        }

        public int humanCount() {
            return toHuman;
        }

        public int AICount() {
            return toAI;
        }

        public int playerCount(int pid) {
            return times.getOrDefault(pid, 0);
        }

        @Override
        public String toString() {
            return "[ToHuman = " + toHuman + " ToAI = " + toAI + "Times = " + times.toString().replace("=", " = ") + "]";
        }

        public String print() {
            StringBuilder string = new StringBuilder();

            string.append(String.format("%d %d\n", toHuman, toAI)).append(times.size()).append('\n');

            for (Map.Entry<Integer, Integer> entry: times.entrySet()) {
                string.append(String.format("%d %d\n", entry.getKey(), entry.getValue()));
            }

            return string.toString();
        }

        public void load(Scanner scanner) {
            toHuman = scanner.nextInt();
            toAI = scanner.nextInt();

            int size = scanner.nextInt();

            for (int i = 0; i < size; ++i) {
                int key = scanner.nextInt(), value = scanner.nextInt();

                times.put(key, value);
            }
        }
    }

    protected static int playerCnt;
    protected int pid;
    protected boolean isHuman;
    protected String name;
    protected GameCounter win;
    protected GameCounter lose;
    protected GameCounter total;

    public Player(String name) {
        pid = ++playerCnt;
        this.name = name;
        win = new GameCounter();
        lose = new GameCounter();
        total = new GameCounter();
    }

    public static int getPlayerCnt() {
        return playerCnt;
    }

    public static void setPlayerCnt(int playerCnt) {
        Player.playerCnt = playerCnt;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public abstract void setName(String name);

    public abstract GameCounter winCounter();

    public abstract GameCounter loseCounter();

    public abstract GameCounter totalCounter();

    public abstract void addWinGame(Player player);

    public abstract void addLoseGame(Player player);

    public abstract void addDrawGame(Player player);

    public abstract double calculateWinRateToHuman();

    public abstract double calculateWinRateToAI();

    public abstract double calculateWinRate(int pid);

    public abstract String print();

    public abstract void load(Scanner scanner);

    public abstract int[] nextStep(Game game);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return isHuman && player.isHuman && pid == player.pid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }
}
