package model.ai;

import io.BoardReader;
import model.Game;
import model.square.Square;

import java.util.Set;
import java.util.TreeSet;

public abstract class AI {

    protected static final int[] SIZE = BoardReader.SIZE;

    protected Game game;

    /*
     * EFFECTS:  constructor
     */
    public AI(Game game) {
        this.game = game;
    }

    /*
     * EFFECTS:  returns an int interpretable as the set of values present in all neighbours
     */
    protected int getNeighbourValues(Square square) {
        int values = 0;
        int value;
        for (Square neighbour : getNeighbours(square)) {
            value = neighbour.getValue();
            if (value == 0) {
                continue;
            }
            values |= square.toInt(value);
        }
        return values;
    }

    /*
     * EFFECTS:  returns a set of all neighbouring squares to square
     */
    private Set<Square> getNeighbours(Square square) {
        Set<Square> neighbours = new TreeSet<>();
        neighbours.addAll(getRowNeighbours(square));
        neighbours.addAll(getColNeighbours(square));
        neighbours.addAll(getSsqNeighbours(square));
        return neighbours;
    }

    /*
     * EFFECTS:  returns a set of all squares in the same row as square
     */
    private Set<Square> getRowNeighbours(Square square) {
        int row = getRow(square);
        Set<Square> neighbours = new TreeSet<>();
        for (Square other : game) {
            if (row == getRow(other)) {
                neighbours.add(other);
            }
        }
        neighbours.remove(square);
        return neighbours;
    }

    /*
     * EFFECTS:  returns a set of all squares in the same col as square
     */
    private Set<Square> getColNeighbours(Square square) {
        int col = getCol(square);
        Set<Square> neighbours = new TreeSet<>();
        for (Square other : game) {
            if (col == getCol(other)) {
                neighbours.add(other);
            }
        }
        neighbours.remove(square);
        return neighbours;
    }

    /*
     * EFFECTS:  returns a set of all squares in the same ssq as square
     */
    private Set<Square> getSsqNeighbours(Square square) {
        int ssq = getSsq(square);
        Set<Square> neighbours = new TreeSet<>();
        for (Square other : game) {
            if (ssq == getSsq(other)) {
                neighbours.add(other);
            }
        }
        neighbours.remove(square);
        return neighbours;
    }

    /*
     * EFFECTS:  returns the row square is in
     */
    private int getRow(Square square) {
        return square.getIdx() % SIZE[1];
    }

    /*
     * EFFECTS:  returns the col square is in
     */
    private int getCol(Square square) {
        return square.getIdx() / SIZE[1];
    }

    /*
     * EFFECTS:  returns the ssq square is in
     */
    private int getSsq(Square square) {
        return (square.getIdx() / SIZE[2]) * SIZE[0] + (square.getIdx() / SIZE[0]) % SIZE[0];
    }
}
