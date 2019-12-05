package test.model;

import exceptions.TestEnumException;
import io.BoardReader;
import model.Game;
import model.square.Square;
import model.square.SquareOperation;
import model.square.UserSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TestWithExceptions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGame extends TestWithExceptions {

    static final int N_BOARDS = BoardReader.BOARDS.length;
    List<Game> games = new Vector<>(N_BOARDS);

    @Override
    @BeforeEach
    protected void runBefore() {
        int difficultyLevel;
        int puzzleNumber;
        for (int boardNumber = 0; boardNumber < N_BOARDS + 2; boardNumber++) {
            difficultyLevel = (boardNumber / 3) + 1;
            puzzleNumber = (boardNumber % 3) + 1;
            games.add(new Game(difficultyLevel, puzzleNumber));
        }
    }

    @Test
    void testUpdate() {
        Set<Square> squares;
        for (Game game : games) {
            squares = new TreeSet<>();
            for (Square square : game) {
                if (square.getIdx() == 69) {
                    squares.add(square);
                }
            }
            tryUpdate(game, squares);
            tryUndo(game);
        }
    }

    @Test
    void testFill() {
        Set<UserSquare> move;
        for (Game game : games) {
            move = game.fill();
            assertFalse(move.isEmpty());
            move = game.fill();
            assertTrue(move.isEmpty());
        }
    }

    @Test
    void testSetSingles() {
        Set<UserSquare> move = new TreeSet<>();
        for (Game game : games) {
            for (int idx = 0; idx < SIZE[3]; idx++) {
                move = game.setSingles();
            }
            assertTrue(move.isEmpty());
        }
    }

    private void tryUpdate(Game game, Set<Square> squares) {
        for (SquareOperation op : SquareOperation.values()) {
            try {
                game.update(op, squares, 1);
            } catch (AssertionError e) {
                System.out.println();
            } catch (TestEnumException e) {
                printTestMessage(e);
            }
        }
    }

    private void tryUndo(Game game) {
        while (true) {
            try {
                game.undo();
            } catch (EmptyStackException e) {
                printTestMessage(e);
                break;
            }
        }
    }
}
