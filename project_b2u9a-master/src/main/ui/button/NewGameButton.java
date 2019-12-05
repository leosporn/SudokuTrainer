package ui.button;

import ui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameButton extends Button {

    /*
     * MODIFIES: this, parent
     * EFFECTS:  initializes this and adds it to parent
     */
    public NewGameButton(GUI gui, JComponent parent) {
        super(gui, parent);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  customizes button
     */
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("New Game");
        customize(button);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  adds NewGameListener
     */
    @Override
    protected void addListener() {
        button.addActionListener(new NewGameListener());
    }

    private class NewGameListener implements ActionListener {

        /*
         * MODIFIES: this, parent
         * EFFECTS:  calls a new game
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.newGame();
        }
    }
}
