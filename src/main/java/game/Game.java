package main.java.game;

import main.java.chess.Chess;
import main.java.chess.ChessColor;
import main.java.player.AIPlayer;
import main.java.player.Mode;
import main.java.player.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static int gameCnt;
    private int gid;
    private int curPlayer;
    private int pauseCnt;
    private boolean isEnded;
    private boolean[] cheatMode;
    private String name;
    private Board board;
    private Board startBoard;
    private Player winner;
    private Player[] player;
    private ChessColor[] color;
    private List<Step> stepList;
    private LocalDateTime createTime;
    private LocalDateTime lastModifiedTime;

    public Game(Player[] player, ChessColor[] color, boolean[] cheatMode, String name, Board board) {
        gid = ++gameCnt;
        this.player = player.clone();
        this.color = color.clone();
        this.cheatMode = cheatMode.clone();
        this.name = name;
        this.board = new Board(board);
        startBoard = new Board(board);
        stepList = new ArrayList<>();
        lastModifiedTime = createTime = LocalDateTime.now();
    }

    public Game(Scanner scanner, List<Player> playerList) {
        gid = ++gameCnt;
        stepList = new ArrayList<>();
        cheatMode = new boolean[2];
        player = new Player[2];
        color = new ChessColor[2];
        load(scanner, playerList);
    }

    public int getGid() {
        return gid;
    }

    public static int getGameCnt() {
        return gameCnt;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getBlackPlayer() {
        return player[0];
    }

    public void setBlackPlayer(Player player) {
        this.player[0] = player;
        setStepPlayer();
    }

    public Player getWhitePlayer() {
        return player[1];
    }

    public void setWhitePlayer(Player player) {
        this.player[1] = player;
        setStepPlayer();
    }

    public Player getCurrentPlayer() {
        return player[curPlayer];
    }

    public void setCurrentPlayer(Player player) {
        this.player[curPlayer] = player;
        setStepPlayer();
    }

    public ChessColor getColor() {
        return color[curPlayer];
    }

    public void setColor(ChessColor color) {
        this.color[curPlayer] = color;
    }

    public boolean isCheatMode() {
        return cheatMode[curPlayer];
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode[curPlayer] = cheatMode;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    private void setStepPlayer() {
        int index = 0;

        for (Step step: stepList) {
            step.setPlayer(player[index]);
            index ^= 1;
        }
    }

    public boolean pause() {
        lastModifiedTime = LocalDateTime.now();

        ++pauseCnt;
        stepList.add(new Step(player[curPlayer]));
        curPlayer ^= 1;

        return pauseCnt >= 2;
    }

    public boolean move(int i, int j) {
        lastModifiedTime = LocalDateTime.now();

        Chess chess = new Chess(color[curPlayer], i, j);
        List<Chess[]> list = board.addChess(chess, cheatMode[curPlayer]);

        stepList.add(new Step(player[curPlayer], chess, list));
        curPlayer ^= 1;
        pauseCnt = 0;

        return isMovable();
    }

    public void backToStart() {
        startBoard.setSize(board.getRowSize(), board.getColumnSize());

        board = new Board(startBoard);
    }

    public void performStep(int index) {
        board.changeInto(stepList.get(index).getModifiedChessList());
    }

    public boolean undo() {
        if (stepList.isEmpty()) {
            return false;
        }

        board.changeBack(stepList.remove(stepList.size() - 1).getModifiedChessList());

        return true;
    }

    public List<int[]> getPossibleMoves() {
        return board.showAllPossibleMoves(color[curPlayer], cheatMode[curPlayer]);
    }

    public boolean isMovable() {
        return board.checkMovable(color[curPlayer], cheatMode[curPlayer]);
    }

    public Player endGame() {
        ChessColor winnerColor = board.calculateWinner();

        if (winnerColor == color[0]) {
            winner = player[0];
            player[0].addWinGame(player[1]);
            player[1].addLoseGame(player[0]);
        } else if (winnerColor == color[1]) {
            winner = player[1];
            player[0].addLoseGame(player[1]);
            player[1].addWinGame(player[0]);
        } else {
            player[0].addDrawGame(player[1]);
            player[1].addDrawGame(player[0]);
        }

        isEnded = true;

        return winner;
    }

    public String print() {
        StringBuilder string = new StringBuilder();

        string.append(curPlayer).append('\n').append(pauseCnt).append('\n').append(isEnded).append('\n');

        for (int i = 0; i < 2; i++) {
            string.append(cheatMode[i]).append(' ');
        }

        string.append('\n');
        string.append(name).append('\n');
        string.append(board.print());
        string.append(winner == null ? -1 : winner.getPid()).append('\n');

        for (int i = 0; i < 2; i++) {
            string.append(player[i].getPid()).append(' ');
        }

        string.append('\n');

        for (int i = 0; i < 2; i++) {
            string.append(color[i].name()).append(' ');
        }

        string.append('\n');
        string.append(stepList.size()).append('\n');

        for (Step step: stepList) {
            string.append(step.print());
        }

        string.append(createTime).append('\n');
        string.append(lastModifiedTime).append('\n');

        return string.toString();
    }

    public void load(Scanner scanner, List<Player> playerList) {
        curPlayer = scanner.nextInt();
        pauseCnt = scanner.nextInt();
        isEnded = scanner.nextBoolean();

        for (int i = 0; i < 2; i++) {
            cheatMode[i] = scanner.nextBoolean();
        }

        name = scanner.next();
        board = new Board(scanner);

        int winnerPid = scanner.nextInt();

        for (int i = 0; i < 2; i++) {
            int pid = scanner.nextInt();

            if (pid >= AIPlayer.getOffset()) {
                player[i] = Mode.getPlayer(pid);
            } else {
                player[i] = playerList.stream().filter((value) -> value.getPid() == pid).findFirst().get();
            }
        }

        if (winnerPid != -1) {
            winner = winnerPid == player[0].getPid() ? player[0] : player[1];
        }

        for (int i = 0; i < 2; i++) {
            color[i] = ChessColor.valueOf(scanner.next());
        }

        int size = scanner.nextInt();

        for (int i = 0, current = 0; i < size; i++, current ^= 1) {
            Step step = new Step(scanner);
            step.setPlayer(player[current]);

            stepList.add(step);
        }

        createTime = LocalDateTime.parse(scanner.next());
        lastModifiedTime = LocalDateTime.parse(scanner.next());
    }
}
