package model;

import io.BoardReader;
import model.ai.FillAI;
import model.square.Square;
import model.square.SquareOperation;
import model.square.UserSquare;

import java.util.*;

public class Game implements Iterable<Square> {

    private static final int[] SIZE = BoardReader.SIZE;

    private List<Square> board = new Vector<>(SIZE[3]);
    private Stack<Set<UserSquare>> moves = new Stack<>();

    /*
     * REQUIRES: difficultyLevel and puzzleNumber correspond to a valid puzzle
     * EFFECTS:  constructor
     */
    public Game(int difficultyLevel, int puzzleNumber) {
        if (difficultyLevel == 16) {
            puzzleNumber = 1;
        }
        int boardNumber = 3 * difficultyLevel + puzzleNumber - 4;
        Square square;
        for (int idx = 0; idx < SIZE[3]; idx++) {
            square = new Square(idx, BoardReader.BOARDS[boardNumber][idx], BoardReader.GIVEN[boardNumber][idx]);
            if (square.isGiven()) {
                square.setValue(square.getTrueValue());
            }
            board.add(square);
        }
    }

    /*
     * REQUIRES: all values are between 1 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  applies a move to the board and records the move in moves if it isn't empty
     */
    public void update(SquareOperation op, Collection<Square> squares, int... values) {
        UserSquare diff;
        Set<UserSquare> move = new TreeSet<>();
        for (Square square : squares) {
            diff = square.update(op, values);
            if (isDifferent(diff)) {
                move.add(diff);
            }
        }
        if (!move.isEmpty()) {
            moves.add(move);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  updates the corresponding square according to diff
     */
    private void update(UserSquare diff) {
        board.get(diff.getIdx()).update(diff);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  undoes last move or throws an EmptyStackException if there is no last move
     */
    public void undo() throws EmptyStackException {
        moves.pop().forEach(this::update);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  fills all possible values of all squares except for what is ruled out by their neighbours
     */
    public Set<UserSquare> fill() {
        FillAI fillAI = new FillAI(this);
        Set<UserSquare> move = fillAI.fill();
        if (!move.isEmpty()) {
            moves.add(move);
        }
        return move;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  sets value of all squares that only have one possible value
     */
    public Set<UserSquare> setSingles() {
        UserSquare diff;
        fill();
        Set<UserSquare> move = new TreeSet<>();
        for (Square square : board) {
            diff = square.update(SquareOperation.SETSINGLES);
            if (isDifferent(diff)) {
                move.add(diff);
            }
        }
        if (!move.isEmpty()) {
            moves.add(move);
        }
        return move;
    }

    /*
     * EFFECTS:  returns an iterator over squares in the board
     */
    @Override
    public Iterator<Square> iterator() {
        return board.iterator();
    }

    /*
     * EFFECTS:  returns whether diff represents a change made to a square
     */
    private boolean isDifferent(UserSquare diff) {
        return (diff.getValue() | diff.getValues()) > 0;
    }
}
