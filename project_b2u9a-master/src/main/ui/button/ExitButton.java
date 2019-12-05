package ui.button;

import ui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitButton extends Button {

    /*
     * MODIFIES: this, parent
     * EFFECTS:  initializes this and adds it to parent
     */
    public ExitButton(GUI gui, JComponent parent) {
        super(gui, parent);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  customizes button
     */
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Exit");
        customize(button);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  adds ExitListener
     */
    @Override
    protected void addListener() {
        button.addActionListener(new ExitListener());
    }

    private class ExitListener implements ActionListener {

        /*
         * MODIFIES: this, parent
         * EFFECTS:  exits
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.exit();
        }
    }
}
