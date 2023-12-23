package persistence;

import model.exceptions.ChessGameException;
import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonFileReader {
    String path;

    // EFFECTS: constructs reader that will read the file found at path
    public JsonFileReader(String path) {
        this.path = path;
    }

    // EFFECTS: reads a JSON file and returns it as a list of chess games
    public List<Chess> readFile() throws IOException, ChessGameException {
        JSONObject gamesJson = new JSONObject(this.readFileAsString(this.path));
        return this.parseJson(gamesJson);
    }

    // EFFECTS: returns a list of games parsed from a JSON object
    public List<Chess> parseJson(JSONObject gamesJson) throws ChessGameException {
        List<Chess> games = new ArrayList<>();

        JSONArray gamesJsonArray = (JSONArray) gamesJson.get("games");
        for (Object gameJson : gamesJsonArray) {
            games.add(parseGameJson((JSONObject) gameJson));
        }
        return games;
    }

    // EFFECTS: returns a chess game parsed from a JSON object
    public Chess parseGameJson(JSONObject gameJson) throws ChessGameException {
        String name = (String) gameJson.get("name");
        Chess game = new Chess(name);

        List<Move> gameRecord = new ArrayList<>();
        JSONArray movesJsonArray = (JSONArray) gameJson.get("gameRecord");
        for (Object moveJsonArray : movesJsonArray) {
            gameRecord.add(parseMoveJson((JSONArray) moveJsonArray));
        }
        game.setGameRecord(gameRecord);

        return game;
    }

    // EFFECTS: returns a move parsed from a JSON array
    public Move parseMoveJson(JSONArray moveJsonArray) throws ChessGameException {
        Move move = new Move();
        for (Object actionJson : moveJsonArray) {
            move.addAction(parseActionJson((JSONObject) actionJson));
        }
        return move;
    }

    // EFFECTS: returns an action parsed from a JSON object
    public Action parseActionJson(JSONObject actionJson) throws ChessGameException {
        Square square = parseSquareJson((JSONObject) actionJson.get("square"));
        Pos pos = parsePosJson((JSONObject) actionJson.get("pos"));
        String type = (String) actionJson.get("type");
        return new Action(square, pos, type);
    }

    // EFFECTS: returns a square parsed from a JSON object
    public Square parseSquareJson(JSONObject squareJson) throws ChessGameException {
        char piece = ((String) squareJson.get("piece")).charAt(0);
        return new Square(piece);
    }

    // EFFECTS: returns a pos parsed from a JSON object
    public Pos parsePosJson(JSONObject posJson) throws ChessGameException {
        int rank = (int) posJson.get("rank");
        int file = (int) posJson.get("file");
        return new Pos(rank, file);
    }

    // Taken from the JSONReader class in JsonSerializationDemo from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFileAsString(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }
}
