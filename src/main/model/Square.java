package model;

import model.exceptions.InvalidSquareException;
import org.json.JSONObject;

// A single tile on the chess board
public class Square {
    private char piece;

    // EFFECTS: constructs a tile that is empty or with a chess piece
    //          throws InvalidSquareException if piece is not in Chess.PIECES
    public Square(char piece) throws InvalidSquareException {
        if (Chess.PIECES.indexOf(piece) == -1) {
            throw new InvalidSquareException();
        }
        this.piece = piece;
    }

    // EFFECTS: converts and returns square as a JSON object
    public JSONObject asJsonObject() {
        JSONObject squareJson = new JSONObject();
        squareJson.put("piece", String.valueOf(this.piece));
        return squareJson;
    }

    public char getPiece() {
        return this.piece;
    }

    public void setPiece(char piece) {
        this.piece = piece;
    }

}
