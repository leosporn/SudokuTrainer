package model.ai;

import model.Game;
import model.square.Square;
import model.square.SquareOperation;
import model.square.UserSquare;

import java.util.Set;
import java.util.TreeSet;

public class FillAI extends AI {

    /*
     * EFFECTS:  constructor
     */
    public FillAI(Game game) {
        super(game);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  fills possible values for every square, except for those ruled out by its neighbours
     */
    public Set<UserSquare> fill() {
        UserSquare diff;
        Set<UserSquare> move = new TreeSet<>();
        for (Square square : game) {
            if (square.getValue() > 0) {
                continue;
            }
            diff = square.update(SquareOperation.FILL, getNeighbourValues(square));
            if (diff.getValues() > 0) {
                move.add(diff);
            }
        }
        return move;
    }
}
