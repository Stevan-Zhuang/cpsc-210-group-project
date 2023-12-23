package ui;

import model.Chess;
import model.ChessAnalyzer;
import model.Event;
import model.EventLog;
import model.exceptions.InvalidTurnException;
import persistence.JsonFileReader;
import persistence.JsonFileWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// The main window of the chess analyzer application
public class ChessAnalyzerApp extends JFrame {
    private final ChessAnalyzer analyzer;
    private final ChessBoardUI chessBoard;
    private final OptionsUI options;
    private final JsonFileWriter writer;
    private final JsonFileReader reader;

    // EFFECTS: constructs the window frame for chess analyzer graphical ui
    public ChessAnalyzerApp() {
        super("Chess Analyzer");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
                System.exit(0);
            }
        });
        setVisible(true);

        this.analyzer = new ChessAnalyzer();
        this.chessBoard = new ChessBoardUI(this.analyzer.getSelectedGame());
        this.options = new OptionsUI(this);

        this.reader = new JsonFileReader("data/games.json");
        this.writer = new JsonFileWriter("data/games.json");

        this.setupFrame();
    }

    // MODIFIES: this
    // EFFECTS: sets up the chess board panel and the options panel
    public void setupFrame() {
        this.add(this.chessBoard, BorderLayout.WEST);
        this.add(this.options, BorderLayout.EAST);
        this.pack();
    }

    // MODIFIES: this
    // EFFECTS: changes the current selected game to the one found at given index
    //          and updates the chess board
    public void changeGame(int selected) {
        this.analyzer.setSelected(selected);
        this.chessBoard.setGame(this.analyzer.getSelectedGame());
    }

    // MODIFIES: this
    // EFFECTS: undoes the previous move and resets square images,
    //          does nothing if no move to undo
    public void undo() {
        try {
            this.analyzer.getSelectedGame().undo();
            this.chessBoard.updateSquares();
        } catch (InvalidTurnException e) {
            // pass
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the previous move and resets square images
    //          does nothing if no move to play
    public void play() {
        try {
            this.analyzer.getSelectedGame().play();
            this.chessBoard.updateSquares();
        } catch (InvalidTurnException e) {
            // pass
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new game and updates selector
    public void addNewGame() {
        this.analyzer.addNewGame();
        this.resetSelector();
    }

    // MODIFIES: this
    // EFFECTS: removes the selected game and updates selector
    public void removeGame() {
        this.analyzer.removeGame();
        this.resetSelector();
    }

    // MODIFIES: this
    // EFFECTS: renames the selected game and updates selector
    public void renameGame(String name) {
        this.analyzer.getSelectedGame().setName(name);
        this.resetSelector();
    }

    // MODIFIES: this
    // EFFECTS: saves all games in collection to file and updates selector
    public void saveGames() {
        try {
            this.writer.writeFile(this.analyzer.getGames());
        } catch (FileNotFoundException e) {
            //
        }
        this.resetSelector();
    }

    // MODIFIES: this
    // EFFECTS: deletes all current games and load all saved games from file
    //          and updates selector
    public void loadGames() {
        try {
            List<Chess> games = this.reader.readFile();
            while (this.analyzer.getGames().size() > 1) {
                this.analyzer.removeGame();
            }
            for (Chess game : games) {
                this.analyzer.addGame(game);
            }
            this.analyzer.removeGame();
        } catch (IOException e) {
            //
        }
        this.resetSelector();
    }

    // MODIFIES: this
    // EFFECTS: updates the selector to have all the current games in analyzer
    public void resetSelector() {
        this.options.getSelector().resetNames(this.analyzer.getSelected());
    }

    // EFFECTS: returns the list of names of every chess game
    public List<String> getGameNames() {
        List<String> names = new ArrayList<>();
        for (Chess game : this.analyzer.getGames()) {
            names.add(game.getName());
        }
        return names;
    }

    // EFFECTS: prints out all entries in the event log separated by newlines
    private void printLog() {
        EventLog log = EventLog.getInstance();
        for (Event event : log) {
            System.out.println(event.toString() + "\n");
        }
    }
}
