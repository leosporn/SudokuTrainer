package ui.button;

import ui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetSinglesButton extends Button {

    /*
     * MODIFIES: this, parent
     * EFFECTS:  initializes this and adds it to parent
     */
    public SetSinglesButton(GUI gui, JComponent parent) {
        super(gui, parent);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  customizes button
     */
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Set Single Values");
        customize(button);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  adds SetSinglesListener
     */
    @Override
    protected void addListener() {
        button.addActionListener(new SetSinglesListener());
    }

    private class SetSinglesListener implements ActionListener {

        /*
         * MODIFIES: this, parent
         * EFFECTS:  sets all single values in game
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.setSingles();
        }
    }
}
