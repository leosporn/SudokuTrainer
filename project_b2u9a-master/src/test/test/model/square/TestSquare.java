package test.model.square;

import exceptions.TestEnumException;
import model.square.Square;
import model.square.SquareOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSquare extends TestUserSquare {

    private Square s;

    @Override
    @BeforeEach
    public void runBefore() {
        super.runBefore();
        s = new Square(0, 1, true);
    }

    @Test
    void testGetTrueValue() {
        for (int value = 1; value <= SIZE[1]; value++) {
            s = new Square(0, value, true);
            assertEquals(value, s.getTrueValue());
            s = new Square(0, value, false);
            assertEquals(value, s.getTrueValue());
        }
    }

    @Test
    void testIsGiven() {
        assertTrue(s.isGiven());
        s = new Square(0, 1, false);
        assertFalse(s.isGiven());
    }

    @Test
    void testUpdateSet() {
        for (int oldValue = 0; oldValue <= SIZE[1]; oldValue++) {
            for (int newValue = 0; newValue <= SIZE[1]; newValue++) {
                for (int i = 1; i <= SIZE[1]; i++) {
                    for (int j = 1; j <= SIZE[1]; j++) {
                        s = new Square(0, 1, true);
                        s.setValue(oldValue);
                        s.add(i, j);
                        us = s.update(SquareOperation.SET, newValue);
                        assertEquals(newValue, s.getValue());
                        assertEquals(oldValue ^ newValue, us.getValue());
                        assertEquals(0, s.getValues());
                        assertEquals(us.toInt(i, j), us.getValues());
                    }
                }
            }
        }
    }

    @Test
    void testUpdateAdd() {
        for (int i = 1; i <= SIZE[1]; i++) {
            for (int j = 1; j <= SIZE[1]; j++) {
                s = new Square(0, 1, true);
                s.add(i);
                us = s.update(SquareOperation.ADD, j);
                assertEquals(0, s.getValue());
                assertEquals(0, us.getValue());
                assertEquals(us.toInt(i, j), s.getValues());
                assertEquals(~us.toInt(i) & us.toInt(j), us.getValues());
            }
        }
    }

    @Test
    void testUpdateRemove() {
        for (int i = 1; i <= SIZE[1]; i++) {
            for (int j = 1; j <= SIZE[1]; j++) {
                s = new Square(0, 1, true);
                s.add(i);
                us = s.update(SquareOperation.REMOVE, j);
                assertEquals(0, s.getValue());
                assertEquals(0, us.getValue());
                assertEquals(us.toInt(i) & ~us.toInt(j), s.getValues());
                assertEquals(us.toInt(i) & us.toInt(j), us.getValues());
            }
        }
    }

    @Test
    void testUpdateTestException() {
        try {
            s.update(SquareOperation.TEST);
            fail();
        } catch (TestEnumException e) {
            printTestMessage(e);
        }
    }

    @Test
    void testUpdate() {
        for (SquareOperation op : SquareOperation.values()) {
            if (op == SquareOperation.TEST) {
                continue;
            }
            for (int i = 1; i <= SIZE[1]; i++) {
                for (int j = 1; j <= SIZE[1]; j++) {
                    for (int k = 1; k <= SIZE[1]; k++) {
                        s = new Square(0, 1, true);
                        s.setValue(i);
                        s.add(j);
                        try {
                            us = s.update(op, k);
                        } catch (AssertionError e) {
                            continue;
                        }
                        s.update(us);
                        assertEquals(i, s.getValue());
                        assertEquals(us.toInt(j), s.getValues());
                    }
                }
            }
        }
    }
}
