package model.square;

import exceptions.InvalidValueException;
import io.BoardReader;

import java.util.Set;
import java.util.TreeSet;

public class UserSquare implements Comparable<UserSquare> {

    static final int MIN = 1;
    static final int MAX = BoardReader.SIZE[1];

    private final int idx;

    private int value = 0;
    private int values = 0;

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * EFFECTS:  constructor
     */
    public UserSquare(int idx) {
        checkValid(idx, 0, MAX * MAX - 1);
        this.idx = idx;
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     *           value is between 0 and SIZE[1]
     *           all values are between 1 and SIZE[1]
     * EFFECTS:  constructor
     */
    public UserSquare(int idx, int value, int... values) {
        checkValid(idx, 0, MAX * MAX - 1);
        this.idx = idx;
        setValue(value);
        add(values);
    }

    /*
     * EFFECTS:  returns idx of this
     */
    public int getIdx() {
        return idx;
    }

    /*
     * EFFECTS:  retuns value of this
     */
    public int getValue() {
        return value;
    }

    /*
     * EFFECTS:  returns an int corresponding to the values of this
     */
    public int getValues() {
        return values;
    }

    public int setValue(int value) {
        int diff = this.value ^ value;
        this.value = value;
        return diff;
    }

    /*
     * REQUIRES: values represents a set of integers between 1 and SIZE[1]
     * EFFECTS:  sets values of this to values
     */
    void setValues(int values) {
        this.values = values;
    }

    /*
     * REQUIRES: values represents a set of integers between 1 and SIZE[1]
     * EFFECTS:  returns whether this contains every value in values
     */
    public boolean contains(int... values) {
        for (int value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    /*
     * EFFECTS:  returns whether this contains value
     *           throws InvalidValueException if value isn't between 1 and SIZE[1]
     */
    private boolean contains(int value) {
        checkValid(value, MIN, MAX);
        return (values & toInt(value)) > 0;
    }

    /*
     * REQUIRES: all values are between 1 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  adds all values to this
     *           returns an int containing how this has changed
     */
    public int add(int... values) {
        int diff = ~this.values & toInt(values);
        this.values ^= diff;
        return diff;
    }

    /*
     * REQUIRES: all values are between 1 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  removes all values from this
     *           returns an int containing how this has changed
     */
    public int remove(int... values) {
        int diff = this.values & toInt(values);
        this.values ^= diff;
        return diff;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  removes all possible values from this
     *           returns an int containing how this has changed
     */
    int empty() {
        int removed = values;
        values = 0;
        return removed;
    }

    /*
     * REQUIRES: all values are between 1 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  adds all values except exceptions to possible values
     */
    int fill(int exceptions) {
        int diff = (this.values ^ ~exceptions) & ((1 << MAX) - 1);
        this.values ^= diff;
        return diff;
    }

    /*
     * REQUIRES: all values are between 1 and SIZE[1]
     * EFFECTS:  returns an int that can be interpreted as the set of possible values in this
     */
    public int toInt(int... values) {
        int i = 0;
        for (int value : values) {
            i |= toInt(value);
        }
        return i;
    }

    /*
     * REQUIRES: value is between 1 and SIZE[1]
     * EFFECTS:  returns 2^(value - 1)
     */
    private int toInt(int value) {
        checkValid(value, MIN, MAX);
        return 1 << (value - 1);
    }

    /*
     * EFFECTS:  returns the only value in values, or 0 if there are 0 or more than one
     */
    int toSingleValue() {
        int values = this.values;
        for (int value = MIN; value <= MAX; value++) {
            if (values == 1) {
                return value;
            } else if (values % 2 == 1) {
                return 0;
            } else {
                values >>= 1;
            }
        }
        return 0;
    }

    /*
     * REQUIRES: all values are between 1 and SIZE[1]
     * EFFECTS:  returns a set of all integers in values
     */
    public Set<Integer> toSet(int values) {
        Set<Integer> set = new TreeSet<>();
        for (int value = MIN; value <= MAX; value++) {
            if ((values & 1) == 1) {
                set.add(value);
            }
            values >>= 1;
        }
        return set;
    }

    /*
     * EFFECTS:  throws InvalidValueException if i isn't between min and max
     */
    void checkValid(int i, int min, int max) {
        if (i < min || max < i) {
            throw new InvalidValueException();
        }
    }

    /*
     * EFFECTS:  comparator based on idx
     */
    @Override
    public int compareTo(UserSquare other) {
        return idx - other.idx;
    }
}
