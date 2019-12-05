package ui;

import model.square.Square;
import model.square.UserSquare;

import java.awt.*;

public class SquareRenderer {

    private GUI gui;
    private Graphics2D g2;
    private Square square;

    private final int x0;
    private final int y0;
    private final int w0 = Board.CELL_SIZE;
    private final int h0 = Board.CELL_SIZE;

    static final String FONT_NAME = "Helvetica";
    static final int BIG_FONT_SIZE;
    static final int SMALL_FONT_SIZE;

    static final Font BIG_FONT;
    static final Font BIG_FONT_HINT;
    static final Font SMALL_FONT;
    static final Font SMALL_FONT_HINT;

    static final Color GIVEN_TXT_COLOR = new Color(0, 0, 0);
    static final Color USER_TXT_COLOR = new Color(0, 0, 127);

    static final Color BLANK_CELL_COLOR = new Color(255, 255, 255);
    static final Color SELECTED_CELL_COLOR = new Color(255, 255, 191);
    static final Color HINT_CELL_COLOR = new Color(191, 255, 191);
    static final Color ERROR_CELL_COLOR = new Color(255, 191, 191);
    static final Color GAME_OVER_CELL_COLOR = new Color(127, 255, 127);

    /*
     * MODIFIES: this, parent
     * EFFECTS:  constructor
     */
    SquareRenderer(GUI parent, Graphics2D g2, Square square) {
        gui = parent;
        this.g2 = g2;
        this.square = square;
        x0 = Board.BORDER_SIZE + w0 * (square.getIdx() / GUI.SIZE[1]);
        y0 = Board.BORDER_SIZE + h0 * (square.getIdx() % GUI.SIZE[1]);
        drawBackground();
        drawValues();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  draws square background based on whether a hint is shown and whether the game is over
     */
    private void drawBackground() {
        g2.setColor(gui.selected[square.getIdx()] ? SELECTED_CELL_COLOR : BLANK_CELL_COLOR);
        for (UserSquare us : gui.hint) {
            if (us.getIdx() == square.getIdx()) {
                g2.setColor(HINT_CELL_COLOR);
            }
        }
        if (gui.gameOver()) {
            g2.setColor(GAME_OVER_CELL_COLOR);
        }
        g2.fillRect(x0, y0, w0, h0);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  fills in the values on the board
     */
    private void drawValues() {
        if (square.getValue() > 0) {
            drawBigValue();
        } else {
            drawSmallValues();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  fills in the values on the board
     */
    private void drawBigValue() {
        g2.setFont(BIG_FONT);
        g2.setColor(square.isGiven() ? GIVEN_TXT_COLOR : USER_TXT_COLOR);
        int value = square.getValue();
        Point p = getBigValueLocation(value);
        g2.drawString(String.valueOf(value), p.x, p.y);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  fills in the possible values on the board
     */
    private void drawSmallValues() {
        g2.setFont(SMALL_FONT);
        g2.setColor(USER_TXT_COLOR);
        for (int value : square.toSet(square.getValues())) {
            Point p = getSmallValueLocation(value);
            g2.drawString(String.valueOf(value), p.x, p.y);
        }
    }

    /*
     * REQUIRES: value is between 1 and SIZE[1]
     * EFFECTS:  returns a point with the coordinate to daw a big value
     */
    private Point getBigValueLocation(int value) {
        FontMetrics fm = g2.getFontMetrics();
        int x = x0 + w0 / 2 - fm.stringWidth(String.valueOf(value)) / 2;
        int y = y0 + h0 / 2 - fm.getHeight() / 2 + fm.getAscent();
        return new Point(x, y);
    }

    /*
     * REQUIRES: value is between 1 and SIZE[1]
     * EFFECTS:  returns a point with the coordinate to daw a small value
     */
    private Point getSmallValueLocation(int value) {
        FontMetrics fm = g2.getFontMetrics();
        int z = GUI.SIZE[0];
        int x = x0 + (w0 * ((value - 1) % z + 1)) / (z + 1) - fm.stringWidth(String.valueOf(value)) / 2;
        int y = y0 + (h0 * ((value - 1) / z + 1)) / (z + 1) - fm.getHeight() / 2 + fm.getAscent();
        return new Point(x, y);
    }

    static {
        BIG_FONT_SIZE = Board.CELL_SIZE * 7 / 10;
        BIG_FONT = new Font(FONT_NAME, Font.PLAIN, BIG_FONT_SIZE);
        BIG_FONT_HINT = new Font(FONT_NAME, Font.BOLD, BIG_FONT_SIZE);
        SMALL_FONT_SIZE = Board.CELL_SIZE * 2 / 10;
        SMALL_FONT = new Font(FONT_NAME, Font.PLAIN, SMALL_FONT_SIZE);
        SMALL_FONT_HINT = new Font(FONT_NAME, Font.BOLD, SMALL_FONT_SIZE);
    }
}
