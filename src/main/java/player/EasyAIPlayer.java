package main.java.player;

import main.java.game.Game;

import java.util.List;

public class EasyAIPlayer extends AIPlayer {
    public EasyAIPlayer() {
        super("_AI_EASY");

        this.mode = Mode.EASY;
        --playerCnt;
        pid = AI_PID_OFFSET + 0;
    }

    @Override
    public int[] nextStep(Game game) {
        List<int[]> moves = game.getPossibleMoves();

        return moves.get(random.nextInt(moves.size()));
    }
}
