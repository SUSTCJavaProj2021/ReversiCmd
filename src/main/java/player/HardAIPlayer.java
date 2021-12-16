package main.java.player;

import main.java.game.Game;

public class HardAIPlayer extends AIPlayer {
    public HardAIPlayer() {
        super("_AI_HARD");

        this.mode = Mode.HARD;
        --playerCnt;
        pid = AI_PID_OFFSET + 2;
    }

    @Override
    public int[] nextStep(Game game) {
        return new int[0];
    }
}
