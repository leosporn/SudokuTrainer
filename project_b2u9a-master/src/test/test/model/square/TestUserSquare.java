package test.model.square;

import exceptions.InvalidValueException;
import model.square.UserSquare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.TestWithExceptions;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TestUserSquare extends TestWithExceptions {

    UserSquare us;

    @Override
    @BeforeEach
    protected void runBefore() {
        us = new UserSquare(0);
    }

    @Test
    void testInvalidIdxLow() {
        try {
            new UserSquare(-1);
            fail();
        } catch (InvalidValueException e) {
            printTestMessage(e);
        }
    }

    @Test
    void testInvalidIdxHigh() {
        try {
            new UserSquare(SIZE[3]);
            fail();
        } catch (InvalidValueException e) {
            printTestMessage(e);
        }
    }

    @Test
    void testGetIdx() {
        for (int idx = 0; idx < SIZE[3]; idx++) {
            us = new UserSquare(idx);
            assertEquals(idx, us.getIdx());
        }
    }

    @Test
    void testGetValue() {
        for (int value = 1; value <= SIZE[1]; value++) {
            us = new UserSquare(0, value);
            assertEquals(value, us.getValue());
        }
    }

    @Test
    void testGetValues() {
        int expected;
        for (int i = 1; i <= SIZE[1]; i++) {
            for (int j = 1; j <= SIZE[1]; j++) {
                for (int k = 1; k <= SIZE[1]; k++) {
                    expected = us.toInt(i, j, k);
                    us = new UserSquare(0, 0, i, j, k);
                    assertEquals(expected, us.getValues());
                }
            }
        }
    }

    @Test
    void testSetValue() {
        int diff1;
        int diff2;
        for (int i = 0; i <= SIZE[1]; i++) {
            for (int j = 0; j <= SIZE[1]; j++) {
                us.setValue(i);
                assertEquals(i, us.getValue());
                diff1 = us.setValue(j);
                assertEquals(j, us.getValue());
                assertEquals(i ^ j, diff1);
                diff2 = us.setValue(i);
                assertEquals(diff1, diff2);
            }
        }
    }

    @Test
    void testContains() {
        for (int i = 1; i <= SIZE[1]; i++) {
            for (int j = 1; j <= SIZE[1]; j++) {
                for (int k = 1; k <= SIZE[1]; k++) {
                    us = new UserSquare(0, 0, i, j);
                    assertTrue(us.contains(k) ^ (i != k && j != k));
                }
            }
        }
    }

    @Test
    void testAdd() {
        int added;
        int expected;
        for (int i = 1; i <= SIZE[1]; i++) {
            for (int j = 1; j <= SIZE[1]; j++) {
                for (int k = 1; k <= SIZE[1]; k++) {
                    for (int l = 1; l <= SIZE[1]; l++) {
                        us = new UserSquare(0, 0, i, j);
                        added = us.add(k, l);
                        expected = ~us.toInt(i, j) & us.toInt(k, l);
                        assertTrue(us.contains(i, j, k, l));
                        assertEquals(expected, added);
                    }
                }
            }
        }
    }

    @Test
    void testRemove() {
        int removed;
        int expected;
        for (int i = 1; i <= SIZE[1]; i++) {
            for (int j = 1; j <= SIZE[1]; j++) {
                for (int k = 1; k <= SIZE[1]; k++) {
                    for (int l = 1; l <= SIZE[1]; l++) {
                        us = new UserSquare(0, 0, i, j);
                        removed = us.remove(k, l);
                        expected = us.toInt(i, j) & us.toInt(k, l);
                        assertFalse(us.contains(i, j, k, l));
                        assertEquals(expected, removed);
                    }
                }
            }
        }
    }

    @Test
    void testEmpty() {

    }

    @Test
    void testToInt() {
        assertEquals(0, us.toInt());
        assertEquals(341, us.toInt(1, 3, 5, 7, 9, 7, 5, 3, 1));
        assertEquals(170, us.toInt(8, 6, 4, 2, 4, 6, 8));
    }

    @Test
    void testToSet() {
        boolean b;
        Set<Integer> s = new TreeSet<>();
        Set<Integer> e = new TreeSet<>();
        Set<Integer> o = new TreeSet<>();
        for (int value = 1; value <= SIZE[1]; value++) {
            b = value % 2 == 0 ? e.add(value) : o.add(value);
        }
        assertEquals(s, us.toSet(0));
        assertEquals(e, us.toSet(170));
        assertEquals(o, us.toSet(341));

    }

    @Test
    void testCompare() {
        for (int idx = 0; idx < SIZE[3]; idx++) {
            us = new UserSquare(idx);
            for (int jdx = 0; jdx < SIZE[3]; jdx++) {
                assertEquals(idx - jdx, us.compareTo(new UserSquare(jdx)));
            }
        }
    }
}
