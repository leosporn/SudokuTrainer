package test.model.slice;

import model.Game;
import model.slice.Slice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TestWithExceptions;

import java.util.List;
import java.util.Vector;

public abstract class TestSlice extends TestWithExceptions {

    protected Game game;
    protected List<Slice> slices = new Vector<>(SIZE[1]);

    @Override
    @BeforeEach
    protected void runBefore() {
        game = new Game(1, 1);
    }

    @Test
    abstract void testGetSliceNumber();

    @Test
    abstract void testSliceNumber();

    @Test
    abstract void testIterator();
}
