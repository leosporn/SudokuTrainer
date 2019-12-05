package ui.button;

import ui.GUI;

import javax.swing.*;
import java.awt.*;

public abstract class Button {

    public static final int WIDTH = 150;

    protected JButton button;
    protected GUI gui;

    /*
     * MODIFIES: this, parent
     * EFFECTS:  initializes this and adds it to parent
     */
    public Button(GUI gui, JComponent parent) {
        this.gui = gui;
        createButton(parent);
        addToParent(parent);
        addListener();
    }

    /*
     * MODIFIES: this
     * EFFECTS:  customizes button
     */
    protected void customize(JButton button) {
        button.setBorderPainted(true);
        button.setFocusable(false);
        button.setContentAreaFilled(true);
        button.setPreferredSize(new Dimension(WIDTH, 0));
    }

    /*
     * MODIFIES: this
     * EFFECTS:  creates button
     */
    protected abstract void createButton(JComponent parent);

    /*
     * MODIFIES: this
     * EFFECTS:  adds listener
     */
    protected abstract void addListener();

    /*
     * MODIFIES: this, parent
     * EFFECTS:  adds this to parent
     */
    private void addToParent(JComponent parent) {
        parent.add(button);
    }
}
