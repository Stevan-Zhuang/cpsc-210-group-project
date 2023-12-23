package ui;

import javax.swing.*;
import java.awt.*;

// The panel containing all the options for the chess analyzer app
public class OptionsUI extends JPanel {
    private final ChessAnalyzerApp app;
    private final ReplayUI replay;
    private final GameSelectorUI selector;
    private final GameManagerUI gameManager;
    private final SaveManagerUI saveManager;

    // EFFECTS: constructs the options panel with given chess analyzer app
    public OptionsUI(ChessAnalyzerApp app) {
        this.app = app;
        this.replay = new ReplayUI(this.app);
        this.selector = new GameSelectorUI(this.app);
        this.gameManager = new GameManagerUI(this.app);
        this.saveManager = new SaveManagerUI(this.app);

        this.setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up all contained panels in the options panel
    public void setupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        c.gridy = 0;
        panel.add(this.saveManager, c);

        c.gridy = 1;
        panel.add(this.replay, c);

        c.gridy = 2;
        panel.add(this.gameManager, c);

        c.gridy = 3;
        panel.add(this.selector, c);

        this.add(panel);
    }

    public GameSelectorUI getSelector() {
        return selector;
    }
}
