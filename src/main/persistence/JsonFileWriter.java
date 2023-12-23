package persistence;

import model.Chess;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class JsonFileWriter {
    PrintWriter writer;
    String path;

    // EFFECTS: constructs writer that will write to the file found at path
    public JsonFileWriter(String path) {
        this.path = path;
    }

    // EFFECTS: writes a list of games into a json file
    public void writeFile(List<Chess> games) throws FileNotFoundException {
        JSONObject gamesJson = this.gamesAsJsonObject(games);
        this.writer = new PrintWriter(this.path);
        this.writer.write(gamesJson.toString(4));
        this.writer.close();
    }

    // EFFECTS: returns a list of chess games as a JSON object
    public JSONObject gamesAsJsonObject(List<Chess> games) {
        JSONObject gamesJson = new JSONObject();

        JSONArray gamesJsonArray = new JSONArray();
        for (Chess game : games) {
            gamesJsonArray.put(game.asJsonObject());
        }
        gamesJson.put("games", gamesJsonArray);
        return gamesJson;
    }
}
