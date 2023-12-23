package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The panel that manages the user collection of games
public class GameManagerUI extends JPanel implements ActionListener {
    private final ChessAnalyzerApp app;
    private final JButton addNewGame;
    private final JButton removeGame;
    private final JTextField renameGame;

    // EFFECTS: constructs a new game manager panel with given app
    public GameManagerUI(ChessAnalyzerApp app) {
        this.app = app;

        this.addNewGame = new JButton();
        this.removeGame = new JButton();
        this.renameGame = new JTextField();

        this.setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons for adding new games, removing games, and renaming games
    public void setupPanel() {
        this.setLayout(new GridLayout(3, 1));

        this.addNewGame.addActionListener(this);
        this.addNewGame.setActionCommand("add_new_game");
        this.addNewGame.setText("Add New Game");

        this.removeGame.addActionListener(this);
        this.removeGame.setActionCommand("remove_game");
        this.removeGame.setText("Remove Game");

        this.renameGame.addActionListener(this);
        this.renameGame.setActionCommand("rename_game");

        this.add(this.addNewGame);
        this.add(this.removeGame);
        this.add(this.renameGame);
    }

    // MODIFIES: this
    // EFFECTS: responds to clicking events of buttons for selecting items in drop down menu
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("add_new_game")) {
            this.app.addNewGame();
        } else if (action.equals("remove_game")) {
            this.app.removeGame();
        } else if (action.equals("rename_game")) {
            this.app.renameGame(this.renameGame.getText());
        }
    }
}
