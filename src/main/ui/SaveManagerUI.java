package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The panel that manages the saved chess games
public class SaveManagerUI extends JPanel implements ActionListener {
    private final ChessAnalyzerApp app;
    private final JButton saveGames;
    private final JButton loadGames;

    // EFFECTS: constructs a new save manager panel with given app
    public SaveManagerUI(ChessAnalyzerApp app) {
        this.app = app;

        this.saveGames = new JButton();
        this.loadGames = new JButton();

        this.setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons for saving and loading games
    public void setupPanel() {
        this.setLayout(new GridLayout(2, 1));

        this.saveGames.addActionListener(this);
        this.saveGames.setActionCommand("save_games");
        this.saveGames.setText("Save Games");

        this.loadGames.addActionListener(this);
        this.loadGames.setActionCommand("load_games");
        this.loadGames.setText("Load Games");

        this.add(this.saveGames);
        this.add(this.loadGames);
    }

    // MODIFIES: this
    // EFFECTS: responds to clicking events of buttons for selecting items in drop down menu
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("save_games")) {
            this.app.saveGames();
        } else if (action.equals("load_games")) {
            this.app.loadGames();
        }
    }
}
