package model.slice;

import model.Game;

public class Col extends Slice {

    /*
     * EFFECTS:  constructor
     */
    public Col(int sliceNumber, Game game) {
        super(sliceNumber, game);
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * EFFECTS:  returns the col number of a square at position idx
     */
    @Override
    public int sliceNumber(int idx) {
        return idx / SIZE[1];
    }
}
