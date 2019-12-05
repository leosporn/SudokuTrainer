package test.model.slice;

import model.slice.Row;
import model.slice.Slice;
import model.square.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRow extends TestSlice {

    @Override
    @BeforeEach
    protected void runBefore() {
        super.runBefore();
        for (int i = 0; i < SIZE[1]; i++) {
            slices.add(new Row(i, game));
        }
    }

    @Override
    @Test
    void testGetSliceNumber() {
        for (int i = 0; i < SIZE[1]; i++) {
            assertEquals(i, slices.get(i).getSliceNumber());
        }
    }

    @Override
    @Test
    void testSliceNumber() {
        for (Slice row : slices) {
            for (int idx = 0; idx < SIZE[3]; idx++) {
                assertEquals(idx % SIZE[1], ((Row) row).sliceNumber(idx));
            }
        }
    }

    @Override
    @Test
    void testIterator() {
        int count;
        for (Slice row : slices) {
            count = 0;
            for (Square square : row) {
                count++;
            }
            assertEquals(SIZE[1], count);
        }
    }
}
