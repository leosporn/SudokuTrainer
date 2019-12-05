package ui.button;

import ui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FillButton extends Button {

    /*
     * MODIFIES: this, parent
     * EFFECTS:  initializes this and adds it to parent
     */
    public FillButton(GUI gui, JComponent parent) {
        super(gui, parent);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  customizes button
     */
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Fill");
        customize(button);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  adds FillListener
     */
    @Override
    protected void addListener() {
        button.addActionListener(new FillListener());
    }

    private class FillListener implements ActionListener {

        /*
         * MODIFIES: this, parent
         * EFFECTS:  fills board
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.fill();
        }
    }
}
