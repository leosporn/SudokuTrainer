package ui.button;

import ui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoButton extends Button {

    /*
     * MODIFIES: this, parent
     * EFFECTS:  initializes this and adds it to parent
     */
    public UndoButton(GUI gui, JComponent parent) {
        super(gui, parent);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  customizes button
     */
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Undo");
        customize(button);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  adds UndoListener
     */
    @Override
    protected void addListener() {
        button.addActionListener(new UndoListener());
    }

    private class UndoListener implements ActionListener {

        /*
         * MODIFIES: this, parent
         * EFFECTS:  undoes the last user move
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.undo();
        }
    }
}
