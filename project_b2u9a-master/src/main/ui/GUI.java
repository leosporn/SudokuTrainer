package ui;

import io.BoardReader;
import model.Game;
import model.square.Square;
import model.square.SquareOperation;
import model.square.UserSquare;
import ui.button.Button;
import ui.button.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.*;

public class GUI extends JFrame implements KeyListener {

    static final int[] SIZE = BoardReader.SIZE;

    static final Color BG_COLOR = new Color(191, 191, 191);

    static final Dimension MIN_SIZE;

    boolean[] selected = new boolean[SIZE[3]];

    boolean shiftPressed = false;
    boolean controlPressed = false;

    Game game;
    Board board;
    Set<UserSquare> hint = new TreeSet<>();
    List<Button> buttons = new ArrayList<>();


    /*
     * EFFECTS:  constructor
     */
    GUI() {
        super("Sudoku");
        newGame();
    }

    /*
     * REQUIRES: difficulty level and puzzle number are within the specified range
     * MODIFIES: this
     * EFFECTS:  gets difficulty level and puzle number from user input and initializes a new game
     *           crashes if improper user input is given
     */
    public void newGame() {
        int difficultyLevel = Integer.parseInt(JOptionPane.showInputDialog("Choose difficulty level (1 - 16)"));
        int puzzleNumber = Integer.parseInt(JOptionPane.showInputDialog("Choose puzzle number (1, 2, or 3)"));
        initFields(difficultyLevel, puzzleNumber);
        initInteraction();
        initGUI();
    }

    /*
     * REQUIRES: difficulty level and puzzle number are valid
     * MODIFIES: this
     * EFFECTS:  initializes the game and deselects all squares
     */
    private void initFields(int difficultyLevel, int puzzleNumber) {
        game = new Game(difficultyLevel, puzzleNumber);
        deselect();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  adds key listener and makes focusable
     */
    private void initInteraction() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  initializes visual components of GUI
     */
    private void initGUI() {
        setBackground(BG_COLOR);
        setLayout(new BorderLayout());
        setMinimumSize(MIN_SIZE);
        setResizable(false);
        setUndecorated(true);
        addBoard();
        addButtons();
        centreUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  initializes board panel
     */
    private void addBoard() {
        board = new Board(this);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  creates a button panel and adds buttons to it
     */
    private void addButtons() {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new GridLayout(0, 1));
        buttonArea.setSize(Button.WIDTH, 0);
        add(buttonArea, BorderLayout.EAST);
        buttons.add(new UndoButton(this, buttonArea));
        buttons.add(new HintButton(this, buttonArea));
        buttons.add(new FillButton(this, buttonArea));
        buttons.add(new SetSinglesButton(this, buttonArea));
        buttons.add(new NewGameButton(this, buttonArea));
        buttons.add(new ExitButton(this, buttonArea));
    }

    /*
     * MODIFIES: this
     * EFFECTS:  centres window on display
     */
    private void centreUI() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        setLocationRelativeTo(null);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  exits the program
     */
    public void exit() {
        setVisible(false);
        dispose();
    }

    /*
     * REQUIRES: idx is valid
     * MODIFIES: this
     * EFFECTS:  square with idx is now selected
     */
    void select(int idx) {
        selected[idx] = true;
    }

    /*
     * REQUIRES: idx is valid
     * MODIFIES: this
     * EFFECTS:  square with idx is now deselected
     */
    void deselect(int idx) {
        selected[idx] = false;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  deselects all squares
     */
    void deselect() {
        for (int idx = 0; idx < SIZE[3]; idx++) {
            deselect(idx);
        }
    }

    /*
     * REQUIRES: idx is valid
     * MODIFIES: this
     * EFFECTS:  square with idx is now toggled
     */
    void toggle(int idx) {
        selected[idx] = !selected[idx];
    }

    /*
     * MODIFIES: this
     * EFFECTS:  tries to fill the board with possible values
     *           if nothing happens, tries to set all squares with only one possible value
     */
    public void hint() {
        if (tryFill()) {
            return;
        } else if (trySetSingleValue()) {
            return;
        }
        repaint();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  undoes last move if there is one
     */
    public void undo() {
        try {
            game.undo();
            repaint();
        } catch (EmptyStackException e) {
            deselect();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  fills possible values of each square with all values except those that are ruled out by its neighbours
     */
    public void fill() {
        game.fill();
        repaint();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  sets value of all squares with only one possible value
     */
    public void setSingles() {
        game.setSingles();
        repaint();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  tries to fill squares and returns whether anything changed
     */
    private boolean tryFill() {
        hint = game.fill();
        if (!hint.isEmpty()) {
            repaint();
            return true;
        }
        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  tries to set value of squares and returns whether anything changed
     */
    private boolean trySetSingleValue() {
        hint = game.setSingles();
        if (!hint.isEmpty()) {
            repaint();
            return true;
        }
        return false;
    }

    /*
     * REQUIRES: value is between 1 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  updates the game
     */
    private void update(int value) {
        update(getOp(value), value);
    }

    /*
     * REQUIRES: values are between 1 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  updates the game
     */
    private void update(SquareOperation op, int... values) {
        game.update(op, getSelectedSquares(), values);
    }

    /*
     * REQUIRES: value is between 1 and SIZE[1]
     * EFFECTS:  returns whether to remove, add, or set value of selected squares
     */
    private SquareOperation getOp(int value) {
        if (shiftPressed) {
            return allSelectedSquaresContain(value) ? SquareOperation.REMOVE : SquareOperation.ADD;
        } else {
            return SquareOperation.SET;
        }
    }

    /*
     * EFFECTS:  returns set of all selected squares
     */
    private Collection<Square> getSelectedSquares() {
        Collection<Square> squares = new TreeSet<>();
        for (Square square : game) {
            if (selected[square.getIdx()] && !square.isGiven()) {
                squares.add(square);
            }
        }
        return squares;
    }

    /*
     * EFFECTS:  returns whether all squares are correctly filled in
     */
    boolean gameOver() {
        for (Square square : game) {
            if (square.getValue() != square.getTrueValue()) {
                return false;
            }
        }
        return true;
    }

    /*
     * EFFECTS:  nothing
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /*
     * MODIFIES: this
     * EFFECTS:  records which keys are pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        updatePressedKeys(e);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  records which keys are pressed and decides on what to do based on which key was released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        updatePressedKeys(e);
        int keyCode = e.getKeyCode();
        if (isValueKeyCode(keyCode)) {
            handleValuePressed(keyCode);
        } else if (isDirectionKeyCode(keyCode)) {
            handleDirectionPressed(keyCode);
        } else if (isDeleteKeyCode(keyCode)) {
            handleDeletePressed();
        } else if (isExitCode(keyCode)) {
            exit();
        } else if (isHintCode(keyCode)) {
            hint();
        } else if (isUndoCode(keyCode)) {
            hint();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  updates whether shift and control keys are pressed
     */
    private void updatePressedKeys(KeyEvent e) {
        shiftPressed = e.isShiftDown();
        controlPressed = e.isControlDown();
    }

    /*
     * EFFECTS:  returns whether the key pressed corresponds to a value between 1 and SIZE[1]
     */
    private boolean isValueKeyCode(int keyCode) {
        int value = keyCode - KeyEvent.VK_0;
        return 1 <= value && value <= SIZE[1];
    }

    /*
     * EFFECTS:  returns whether the key pressed corresponds to an arrow key
     */
    private boolean isDirectionKeyCode(int keyCode) {
        return keyCode == KeyEvent.VK_DOWN
                || keyCode == KeyEvent.VK_UP
                || keyCode == KeyEvent.VK_LEFT
                || keyCode == KeyEvent.VK_RIGHT;
    }

    /*
     * EFFECTS:  returns whether delete key is pressed
     */
    private boolean isDeleteKeyCode(int keyCode) {
        return keyCode == 8;
    }

    /*
     * EFFECTS:  returns whether ctrl-Q is pressed
     */
    private boolean isExitCode(int keyCode) {
        return keyCode == KeyEvent.VK_Q && controlPressed;
    }

    /*
     * EFFECTS:  returns whether ctrl-H is pressed
     */
    private boolean isHintCode(int keyCode) {
        return keyCode == KeyEvent.VK_H && controlPressed;
    }

    /*
     * EFFECTS:  returns whether ctrl-Z is pressed
     */
    private boolean isUndoCode(int keyCode) {
        return keyCode == KeyEvent.VK_Z && controlPressed;
    }

    /*
     * REQUIRES: keyCode corresponds to a value between 1 and SIZE[1]
     * MODIFIES: this
     * EFFECTS:  updates game based on value of key pressed
     */
    private void handleValuePressed(int keyCode) {
        update(keyCode - KeyEvent.VK_0);
        repaint();
    }

    /*
     * REQUIRES: value is between 1 and SIZE[1]
     * EFFECTS:  returns whether all selected squares have value as a possible value
     */
    private boolean allSelectedSquaresContain(int value) {
        for (Square square : getSelectedSquares()) {
            if (!square.contains(value)) {
                return false;
            }
        }
        return true;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  handles direction pressed based on direction
     */
    private void handleDirectionPressed(int keyCode) {
        int idx = getPressedIdx();
        if (idx == -1) {
            return;
        }
        handleDirectionPressedHelper(keyCode, idx);
        repaint();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  handles direction pressed based on direction
     */
    private void handleDirectionPressedHelper(int keyCode, int idx) {
        switch (keyCode) {
            case KeyEvent.VK_DOWN:
                handleDownPressed(idx);
                break;
            case KeyEvent.VK_UP:
                handleUpPressed(idx);
                break;
            case KeyEvent.VK_LEFT:
                handleLeftPressed(idx);
                break;
            case KeyEvent.VK_RIGHT:
                handleRightPressed(idx);
                break;
            default:
                deselect(idx);
        }
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * MODIFIES: this
     * EFFECTS:  deselects current key and selects lower key if there is one
     */
    private void handleDownPressed(int idx) {
        deselect(idx);
        select((idx + 1) % SIZE[1] == 0 ? idx : idx + 1);
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * MODIFIES: this
     * EFFECTS:  deselects current key and selects upper key if there is one
     */
    private void handleUpPressed(int idx) {
        deselect(idx);
        select(idx % SIZE[1] == 0 ? idx : idx - 1);
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * MODIFIES: this
     * EFFECTS:  deselects current key and selects left key if there is one
     */
    private void handleLeftPressed(int idx) {
        deselect(idx);
        select(idx / SIZE[1] == 0 ? idx : idx - SIZE[1]);
    }

    /*
     * REQUIRES: idx is between 0 and SIZE[3] - 1
     * MODIFIES: this
     * EFFECTS:  deselects current key and selects right key if there is one
     */
    private void handleRightPressed(int idx) {
        deselect(idx);
        select(idx + SIZE[1] >= SIZE[3] ? idx : idx + SIZE[1]);
    }

    /*
     * EFFECTS:  returns the idx of the selected idx, or -1 if there is no single pressed idx
     */
    private int getPressedIdx() {
        int pressed = -1;
        boolean anyPressed = false;
        for (int idx = 0; idx < SIZE[3]; idx++) {
            if (selected[idx]) {
                if (anyPressed) {
                    return -1;
                } else {
                    pressed = idx;
                    anyPressed = true;
                }
            }
        }
        return pressed;
    }

    /*
     * MODIFIES: this
     * EFFECTS:  sets value in selected squares to 0 and removes possible values
     */
    private void handleDeletePressed() {
        update(SquareOperation.SET, 0);
        repaint();
    }

    static {
        MIN_SIZE = new Dimension(Board.PANEL_SIZE + Button.WIDTH, Board.PANEL_SIZE);
    }
}
