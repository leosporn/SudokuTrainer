package model.square;

import exceptions.TestEnumException;

public class Square extends UserSquare {

    private final int trueValue;

    /*
     * EFFECTS:  constructor
     */
    public Square(int idx, int trueValue, boolean given) {
        super(idx);
        checkValid(trueValue, MIN, MAX);
        this.trueValue = given ? trueValue : -trueValue;
    }

    /*
     * EFFECTS:  returns the square's true value
     */
    public int getTrueValue() {
        return isGiven() ? trueValue : -trueValue;
    }

    /*
     * EFFECTS:  returns whether the square is given
     */
    public boolean isGiven() {
        return trueValue > 0;
    }

    /*
     * EFFECTS:  updates this according to the specified operation
     *           returns a UserSquare recording how this has changed
     *           throws a TestEnumException of op == TEST (required for CS221 checkstyle)
     */
    public UserSquare update(SquareOperation op, int... values) {
        switch (op) {
            case SET:
                return updateSet(singleValue(values));
            case ADD:
                return updateAdd(values);
            case REMOVE:
                return updateRemove(values);
            case FILL:
                return updateFill(singleValue(values));
            case SETSINGLES:
                return updateSetSingles();
            default:
                throw new TestEnumException();
        }
    }

    /*
     * REQUIRES: diff and this have the same idx
     * MODIFIES: this
     * EFFECTS:  updates this based on what was changed in diff
     */
    public void update(UserSquare diff) {
        assertSameIdx(diff);
        setValue(getValue() ^ diff.getValue());
        setValues(getValues() ^ diff.getValues());
    }

    /*
     * REQUIRES: value is between 0 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  sets value to value and empties possible values
     *           returns a UserSquare recording how this has changed
     */
    private UserSquare updateSet(int value) {
        UserSquare diff = new UserSquare(getIdx());
        diff.setValue(setValue(value));
        diff.setValues(empty());
        return diff;
    }

    /*
     * REQUIRES: all values are between 0 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  adds all values to possible values
     *           returns a UserSquare recording how this has changed
     */
    private UserSquare updateAdd(int... values) {
        assertValueNotSet();
        UserSquare diff = new UserSquare(getIdx());
        diff.setValues(add(values));
        return diff;
    }

    /*
     * REQUIRES: all values are between 0 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  removes all values from possible values
     *           returns a UserSquare recording how this has changed
     */
    private UserSquare updateRemove(int... values) {
        assertValueNotSet();
        UserSquare diff = new UserSquare(getIdx());
        diff.setValues(remove(values));
        return diff;
    }

    /*
     * REQUIRES: all values are between 0 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  fills this with all values except those represented by values
     *           returns a UserSquare recording how this has changed
     */
    private UserSquare updateFill(int values) {
        UserSquare diff = new UserSquare(getIdx());
        diff.setValues(fill(values));
        return diff;
    }

    /*
     * REQUIRES: all values are between 0 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  sets value if possible values only contains one value
     */
    private UserSquare updateSetSingles() {
        int value = toSingleValue();
        return value > 0 ? updateSet(value) : new UserSquare(getIdx());
    }

    /*
     * REQUIRES: values has length 1
     * EFFECTS:  interprets values as a single integer
     */
    private int singleValue(int... values) {
        assert values.length == 1;
        return values[0];
    }

    /*
     * EFFECTS:  asserts that this hasn't had its value set
     */
    private void assertValueNotSet() {
        assert getValue() == 0;
    }

    /*
     * EFFECTS:  asserts diff has same idx as this
     */
    private void assertSameIdx(UserSquare diff) {
        assert getIdx() == diff.getIdx();
    }
}