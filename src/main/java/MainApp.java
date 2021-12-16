package main.java;

import main.java.game.Game;
import main.java.player.HumanPlayer;
import main.java.player.Mode;
import main.java.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        test3();
    }

    public static void test1() {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new HumanPlayer("DengQingwen"), player2 = new HumanPlayer("DaShen");

        Game game = GameSystem.newGame(new Player[] {player1, player2}, new boolean[] {false, false}, "Game");

        while (true) {
            if (process(scanner, game)) break;
        }

        GameSystem.saveGame(getFile("src/main/resources/" + game.getName() + ".sav"), game);
        GameSystem.saveSettings(getFile("src/main/resources/settings.sav"));
    }

    public static void test2() {
        Scanner scanner = new Scanner(System.in);

        GameSystem.loadSettings(getFile("src/main/resources/settings.sav"));
        Game game = GameSystem.loadGame(getFile("src/main/resources/Game.sav"));

        while (true) {
            assert game != null;
            if (process(scanner, game)) break;
        }

        GameSystem.saveGame(getFile("src/main/resources/" + game.getName() + ".sav"), game);
        GameSystem.saveSettings(getFile("src/main/resources/settings.sav"));
    }

    public static void test3() {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new HumanPlayer("DengQingwen"), player2 = Mode.EASY.getPlayer();

        Game game = GameSystem.newGame(new Player[] {player1, player2}, new boolean[] {false, false}, "Game2");

        while (true) {
            if (process(scanner, game)) break;
        }

        GameSystem.saveGame(getFile("src/main/resources/" + game.getName() + ".sav"), game);
        GameSystem.saveSettings(getFile("src/main/resources/settings.sav"));
    }

    private static boolean process(Scanner scanner, Game game) {
        game.getBoard().printToCmd();

        if (game.getCurrentPlayer().isHuman()) {
            int x = scanner.nextInt(), y = scanner.nextInt();

            if (x == 0) {
                return true;
            } else {
                --x;
                --y;
                game.move(x, y);
            }
            return false;
        } else {
            int[] move = game.getCurrentPlayer().nextStep(game);

            game.move(move[0], move[1]);
            System.out.printf("MOVE: %d %d\n", move[0], move[1]);

            return false;
        }
    }

    public static File getFile(String pathname) {
        File file = new File(pathname);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
