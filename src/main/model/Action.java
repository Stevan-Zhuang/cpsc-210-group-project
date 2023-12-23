package model;

import model.exceptions.InvalidActionException;
import org.json.JSONObject;

// A single insertion or deletion of a chess piece at pos
public class Action {
    private final Square square;
    private final Pos pos;
    private final String type;

    // EFFECTS: constructs an action occurring at pos to piece of
    //          type insert or delete
    //          throws InvalidActionException if type is not "delete" or "insert"
    public Action(Square square, Pos pos, String type) throws InvalidActionException {
        if (!type.equals("delete") && !type.equals("insert")) {
            throw new InvalidActionException();
        }
        this.square = square;
        this.pos = pos;
        this.type = type;
    }

    // EFFECTS: converts and returns action as a JSON object
    public JSONObject asJsonObject() {
        JSONObject actionJson = new JSONObject();
        actionJson.put("square", this.square.asJsonObject());
        actionJson.put("pos", this.pos.asJsonObject());
        actionJson.put("type", this.type);
        return actionJson;
    }

    public Square getSquare() {
        return this.square;
    }

    public Pos getPos() {
        return this.pos;
    }

    public String getType() {
        return this.type;
    }
}
