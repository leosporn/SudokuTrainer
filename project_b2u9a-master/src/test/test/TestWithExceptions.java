package test;

import io.BoardReader;
import org.junit.jupiter.api.BeforeEach;

public abstract class TestWithExceptions {

    protected static final int[] SIZE = BoardReader.SIZE;

    private static final String TEST_MESSAGE = "The following Stack trace should only be printed during testing:\n";

    @BeforeEach
    protected abstract void runBefore();

    protected void printTestMessage(Exception e) {
        System.out.println(TEST_MESSAGE);
        e.printStackTrace();
    }
}
