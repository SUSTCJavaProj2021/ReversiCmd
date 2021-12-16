package main.java.player;

import main.java.game.Game;

public class NormalAIPlayer extends AIPlayer {
    public NormalAIPlayer() {
        super("_AI_NORMAL");

        this.mode = Mode.NORMAL;
        --playerCnt;
        pid = AI_PID_OFFSET + 1;
    }

    @Override
    public int[] nextStep(Game game) {
        return new int[0];
    }
}
