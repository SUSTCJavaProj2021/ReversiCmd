package main.java;

import main.java.chess.Chess;
import main.java.chess.ChessColor;
import main.java.game.Board;
import main.java.game.Game;
import main.java.player.HumanPlayer;
import main.java.player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class GameSystem {
    private static List<Player> playerList;
    private static Board initialBoard;

    static {
        playerList = new ArrayList<>();
        Player.setPlayerCnt(0);
        backToDefaultBoard();
    }

    public static void saveSettings(File file) {
        try {
            Formatter formatter = new Formatter(file);

            formatter.format("%d\n", playerList.size());

            for (Player player: playerList) {
                formatter.format(player.print());
            }

            formatter.format(initialBoard.print());
            formatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings(File file) {
        try {
            Scanner scanner = new Scanner(file);
            int playerCnt = scanner.nextInt();

            Player.setPlayerCnt(0);

            for (int i = 0; i < playerCnt; ++i) {
                playerList.add(new HumanPlayer(scanner));
            }

            initialBoard = new Board(scanner);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Player> getPlayerList() {
        return playerList;
    }

    public static void setPlayerList(List<Player> playerList) {
        GameSystem.playerList = new ArrayList<>(playerList);
        Player.setPlayerCnt(playerList.size());
    }

    public static void setInitialBoard(Board initialBoard) {
        GameSystem.initialBoard = initialBoard;
    }

    public static Game newGame(Player[] player, boolean[] cheatMode, String name) {
        return newGame(player, cheatMode, name, initialBoard);
    }

    public static Game newGame(Player[] player, boolean[] cheatMode, String name, Board board) {
        for (int i = 0; i < 2; i++) {
            if (player[i].isHuman() && !playerList.contains(player[i])) {
                playerList.add(player[i]);
            }
        }

        return new Game(player, new ChessColor[] {ChessColor.BLACK, ChessColor.WHITE}, cheatMode, name, board);
    }

    public static void saveGame(File file, Game game) {
        try {
            Formatter formatter = new Formatter(file);

            formatter.format(game.print());
            formatter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Game loadGame(File file) {
        try {
            Scanner scanner = new Scanner(file);
            Game game = new Game(scanner, playerList);

            scanner.close();

            return game;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    public static void backToDefaultBoard() {
        initialBoard = new Board(
            8,
            8,
            new ArrayList<>(List.of(
                new Chess(ChessColor.BLACK, 3, 3),
                new Chess(ChessColor.WHITE, 3, 4),
                new Chess(ChessColor.WHITE, 4, 3),
                new Chess(ChessColor.BLACK, 4, 4))),
            new ArrayList<>());
    }
}
