package test.model.slice;

import model.slice.Slice;
import model.slice.Ssq;
import model.square.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSsq extends TestSlice {

    @Override
    @BeforeEach
    protected void runBefore() {
        super.runBefore();
        for (int i = 0; i < SIZE[1]; i++) {
            slices.add(new Ssq(i, game));
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
        for (Slice ssq : slices) {
            for (int idx = 0; idx < SIZE[3]; idx++) {
                assertEquals(idx / SIZE[2] * SIZE[0] + idx / SIZE[0] % SIZE[0], ((Ssq) ssq).sliceNumber(idx));
            }
        }
    }

    @Override
    @Test
    void testIterator() {
        int count;
        for (Slice ssq : slices) {
            count = 0;
            for (Square square : ssq) {
                count++;
            }
            assertEquals(SIZE[1], count);
        }
    }
}
