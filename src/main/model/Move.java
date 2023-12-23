package model;

import org.json.JSONArray;

import java.util.List;
import java.util.ArrayList;

// All the actions made during a single turn
public class Move {
    private List<Action> actions;

    // EFFECTS: constructs move with no actions
    public Move() {
        this.actions = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds action to list of actions
    public void addAction(Action action) {
        this.actions.add(action);
    }

    // EFFECTS: converts and returns move as a JSON array
    public JSONArray asJsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Action action : this.actions) {
            jsonArray.put(action.asJsonObject());
        }
        return jsonArray;
    }

    public List<Action> getActions() {
        return this.actions;
    }
}
