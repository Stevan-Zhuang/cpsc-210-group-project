package model;

import model.exceptions.InvalidPosException;
import org.json.JSONObject;

import java.util.Objects;

// An x y coordinate point on the chess board
public class Pos {
    private final int rank;
    private final int file;

    // EFFECTS: constructs a position with rank (y) and file (x)
    //          throws InvalidPosException if rank or file or not between 0 and 7 inclusive
    public Pos(int rank, int file) throws InvalidPosException {
        if (0 > rank || rank > 7 || 0 > file || file > 7) {
            throw new InvalidPosException();
        }
        this.rank = rank;
        this.file = file;
    }

    // EFFECTS: converts and returns pos as a JSON object
    public JSONObject asJsonObject() {
        JSONObject posJson = new JSONObject();
        posJson.put("rank", this.rank);
        posJson.put("file", this.file);
        return posJson;
    }

    public int getRank() {
        return this.rank;
    }

    public int getFile() {
        return this.file;
    }
}
