package model;

import java.util.List;
import java.util.ArrayList;

// A collection of chess games, with a specific game selected
public class ChessAnalyzer {
    private List<Chess> games;
    private int selected;

    // EFFECTS: constructs a chess analyzer with a single game with
    //          no moves made and with that game selected
    public ChessAnalyzer() {
        this.games = new ArrayList<>();
        this.addNewGame();
        this.selected = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds a game to list of games
    public void addGame(Chess game) {
        this.games.add(game);
        EventLog.getInstance().logEvent(new Event(
                "Added game " + game.getName() + "."));
    }

    // MODIFIES: this
    // EFFECTS: adds a new game with no moves made to list of games and
    //          name it based on its position in the list
    public void addNewGame() {
        Chess game = new Chess("Untitled-" + (this.games.size() + 1));
        this.addGame(game);
    }

    // REQUIRES: 0 <= selected and selected < games.size()
    // MODIFIES: this
    // EFFECTS: removes the selected game from list of games, decrements
    //          selected by 1 if it is greater than 0, and add a new
    //          game with no moves made to list of games if games is empty
    public void removeGame() {
        Chess game = this.games.get(this.selected);
        this.games.remove(this.selected);
        if (this.selected > 0) {
            this.selected--;
        }
        if (this.games.isEmpty()) {
            this.addNewGame();
        }
        EventLog.getInstance().logEvent(new Event(
                "Removed game " + game.getName() + "."));
    }

    // REQUIRES: 0 <= selected and selected < games.size()
    // EFFECTS: gets the game at selected index in list of games
    public Chess getSelectedGame() {
        return this.games.get(this.selected);
    }

    // REQUIRES: 0 <= selected and selected < games.size()
    public void setSelected(int selected) {
        this.selected = selected;
    }

    public List<Chess> getGames() {
        return this.games;
    }

    public int getSelected() {
        return this.selected;
    }
}
