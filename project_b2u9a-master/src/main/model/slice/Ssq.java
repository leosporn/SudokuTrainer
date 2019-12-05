package model.slice;

import model.Game;

public class Ssq extends Slice {

    /*
     * EFFECTS:  constructor
     */
    public Ssq(int sliceNumber, Game game) {
        super(sliceNumber, game);
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * EFFECTS:  returns the ssq number of a square at position idx
     */
    @Override
    public int sliceNumber(int idx) {
        return idx / SIZE[2] * SIZE[0] + idx / SIZE[0] % SIZE[0];
    }
}
