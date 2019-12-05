package model.slice;

import io.BoardReader;
import model.Game;
import model.square.Square;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public abstract class Slice implements Iterable<Square> {

    static final int[] SIZE = BoardReader.SIZE;

    private int sliceNumber;

    private Collection<Square> squares = new Vector<>(SIZE[1]);

    /*
     * EFFECTS:  constructor
     */
    public Slice(int sliceNumber, Game game) {
        this.sliceNumber = sliceNumber;
        getSquares(game);
    }

    /*
     * EFFECTS:  returns the slice number of this
     */
    public int getSliceNumber() {
        return sliceNumber;
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * EFFECTS:  returns the slice number of a square at position idx
     */
    protected abstract int sliceNumber(int idx);

    /*
     * MODIFIES: this
     * EFFECTS:  adds all squares in the same slice to squares
     */
    private void getSquares(Game game) {
        for (Square square : game) {
            if (sliceNumber(square.getIdx()) == sliceNumber) {
                squares.add(square);
            }
        }
    }

    /*
     * EFFECTS:  returns an iterator over the squares in this
     */
    @Override
    public Iterator<Square> iterator() {
        return squares.iterator();
    }
}
