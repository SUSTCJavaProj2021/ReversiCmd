package main.java.player;

import main.java.chess.Chess;
import main.java.chess.ChessColor;
import main.java.game.Board;
import main.java.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestAIPlayer extends AIPlayer {
    public TestAIPlayer() {
        super("_AI_TEST");

        this.mode = Mode.TEST;
        --playerCnt;
        pid = AI_PID_OFFSET + 114514;
    }

    private static final int RANDOM_TIMES = 100;

    private boolean MonteCarlo(Chess chess, Board initialBoard) {
        Board board = new Board(initialBoard);

        board.addChess(chess, false);

        ChessColor color = ChessColor.dual(chess.getColor());

        for (int pauseCnt = 0; pauseCnt < 2; color = ChessColor.dual(color)) {
            List<int[]> list = board.showAllPossibleMoves(color, false);

            if (list.isEmpty()) {
                ++pauseCnt;
                continue;
            } else {
                pauseCnt = 0;
            }

            int[] move = list.get(random.nextInt(list.size()));

            board.addChess(new Chess(color, move[0], move[1]), false);
        }

        return board.calculateWinner() == chess.getColor();
    }

    @Override
    public int[] nextStep(Game game) {
        Board board = game.getBoard();
        List<int[]> moves = game.getPossibleMoves();
        int[] result = new int[2];
        int maxWinTimes = -1;

        for (int[] move: moves) {
            int winTimes = 0;

            for (int i = 0; i < RANDOM_TIMES; i++) {
                if (MonteCarlo(new Chess(game.getColor(), move[0], move[1]), board)) {
                    ++winTimes;
                }
            }

            if (winTimes > maxWinTimes) {
                maxWinTimes = winTimes;
                result = move;
            }
        }

        return result;
    }
}
