package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// The panel that selects between different games
public class GameSelectorUI extends JPanel implements ActionListener {
    private final ChessAnalyzerApp app;
    private final JComboBox<String> gamesDropDown;
    private List<String> gameNames;

    // EFFECTS: constructs a new selector panel with given app
    public GameSelectorUI(ChessAnalyzerApp app) {
        this.app = app;
        this.gamesDropDown = new JComboBox<>();

        this.setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up the drop down list of all games
    public void setupPanel() {
        this.setLayout(new BorderLayout());

        this.resetNames(0);
        this.gamesDropDown.addActionListener(this);

        this.add(this.gamesDropDown);
    }

    // MODIFIES: this
    // EFFECTS: removes and re-adds all game names to drop down menu,
    //          then resets selected game to the previously selected one
    public void resetNames(int selected) {
        this.gamesDropDown.removeAllItems();
        this.gameNames = this.app.getGameNames();
        for (String name : this.gameNames) {
            this.gamesDropDown.addItem(name);
        }
        this.gamesDropDown.setSelectedIndex(selected);
    }

    // MODIFIES: this
    // EFFECTS: responds to clicking events of buttons for selecting items in drop down menu
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
        String gameName = (String) comboBox.getSelectedItem();

        if (gameName != null) {
            int selected = this.gameNames.indexOf(gameName);
            this.app.changeGame(selected);
        }
    }
}
