package ui.button;

import ui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HintButton extends Button {

    /*
     * MODIFIES: this, parent
     * EFFECTS:  initializes this and adds it to parent
     */
    public HintButton(GUI gui, JComponent parent) {
        super(gui, parent);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  customizes button
     */
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Hint");
        customize(button);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  adds HintListener
     */
    @Override
    protected void addListener() {
        button.addActionListener(new HintListener());
    }

    private class HintListener implements ActionListener {

        /*
         * MODIFIES: this, parent
         * EFFECTS:  gives hint
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.hint();
        }
    }
}
