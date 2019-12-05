package model.slice;

import model.Game;

public class Row extends Slice {

    /*
     * EFFECTS:  constructor
     */
    public Row(int sliceNumber, Game game) {
        super(sliceNumber, game);
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * EFFECTS:  returns the row number of a square at position idx
     */
    @Override
    public int sliceNumber(int idx) {
        return idx % SIZE[1];
    }
}
