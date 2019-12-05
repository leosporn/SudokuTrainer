package ui;

import model.square.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeSet;

public class Board extends JPanel {

    static final int CELL_SIZE = 64;
    static final int BORDER_SIZE = 10;
    static final int BOARD_SIZE;
    static final int PANEL_SIZE;

    static final Color LINE_COLOR = new Color(0, 0, 0);

    GUI gui;
    Graphics2D g2;


    /*
     * MODIFIES: this, parent
     * EFFECTS:  creates this and adds it to parent
     */
    Board(GUI parent) {
        super(new GridBagLayout());
        gui = parent;
        parent.add(this, BorderLayout.WEST);
        addMouseListener(new BoardMouseListener());
        setFocusable(true);
        setRequestFocusEnabled(true);
    }

    /*
     * MODIFIES: this, parent
     * EFFECTS:  paints board and turns of GUI hint
     */
    @Override
    public void paint(Graphics g) {
        g2 = (Graphics2D) g;
        drawBackground();
        drawSquares();
        drawLines();
        gui.hint = new TreeSet<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  draws background of board
     */
    private void drawBackground() {
        g2.setColor(SquareRenderer.BLANK_CELL_COLOR);
        g2.fillRect(BORDER_SIZE, BORDER_SIZE, BOARD_SIZE, BOARD_SIZE);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  draws squares on board
     */
    private void drawSquares() {
        for (Square square : gui.game) {
            new SquareRenderer(gui, g2, square);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  draws lines on board
     */
    private void drawLines() {
        int min = BORDER_SIZE;
        int max = PANEL_SIZE - min;
        int pos = min;
        g2.setColor(LINE_COLOR);
        for (int i = 0; i <= GUI.SIZE[1]; i++) {
            g2.setStroke(new BasicStroke(i % GUI.SIZE[0] == 0 ? 5 : 3));
            g2.drawLine(min, pos, max, pos);
            g2.drawLine(pos, min, pos, max);
            pos += CELL_SIZE;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS:  sets size of board
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_SIZE, PANEL_SIZE);
    }

    class BoardMouseListener extends MouseAdapter {

        private int idx = -1;

        /*
         * MODIFIES: this
         * EFFECTS:  records idx of clicked square
         */
        @Override
        public void mousePressed(MouseEvent e) {
            idx = getIdx(e);
        }

        /*
         * MODIFIES: this
         * EFFECTS:  if mouse is released in same square it was clicked, handles click and repaints
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (idx < 0) {
                return;
            }
            if (idx == getIdx(e)) {
                boolean b = gui.selected[idx];
                if (!gui.shiftPressed) {
                    gui.deselect();
                    if (b) {
                        gui.select(idx);
                    }
                }
                gui.toggle(idx);
                repaint();
            }
        }

        /*
         * EFFECTS:  returns the idx of clicked square, or -1 if the click was somewhere else
         */
        private int getIdx(MouseEvent e) {
            Point p = e.getPoint();
            p.translate(-BORDER_SIZE, -BORDER_SIZE);
            int x = p.x / CELL_SIZE;
            int y = p.y / CELL_SIZE;
            if (0 <= x && x < GUI.SIZE[1] && 0 <= y && y < GUI.SIZE[1]) {
                return GUI.SIZE[1] * x + y;
            } else {
                return -1;
            }
        }
    }

    static {
        BOARD_SIZE = GUI.SIZE[1] * CELL_SIZE;
        PANEL_SIZE = 2 * BORDER_SIZE + BOARD_SIZE;
    }
}
