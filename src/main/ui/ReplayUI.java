package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The panel that undoes previous moves and plays next moves
public class ReplayUI extends JPanel implements ActionListener {
    private final ChessAnalyzerApp app;
    private final JButton undoButton;
    private final JButton playButton;

    // EFFECTS: constructs a new replay panel with given chess analyzer app
    public ReplayUI(ChessAnalyzerApp app) {
        this.app = app;
        this.undoButton = new JButton();
        this.playButton = new JButton();

        this.setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up the panel and adds undo and play buttons
    public void setupPanel() {
        this.setLayout(new GridLayout(1, 2));

        this.undoButton.addActionListener(this);
        this.undoButton.setActionCommand("undo");
        this.undoButton.setText("◀");

        this.playButton.addActionListener(this);
        this.playButton.setActionCommand("play");
        this.playButton.setText("▶");

        this.add(this.undoButton);
        this.add(this.playButton);
    }

    // MODIFIES: this
    // EFFECTS: respond to clicking events of buttons for undo and play
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("undo")) {
            app.undo();
        } else if (action.equals("play")) {
            app.play();
        }
    }
}
