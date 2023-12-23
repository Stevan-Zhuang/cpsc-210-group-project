package model;

import model.exceptions.ChessGameException;
import model.exceptions.InvalidTurnException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

// Represents a game of chess with a record of all moves played,
// current board, and current turn
public class Chess {
    private List<Move> gameRecord;
    private String name;
    private int turn;
    private Square[][] board;

    public static final String PIECES = " PNBRQKpnbrqk";

    public static final char EMPTY = ' ';

    public static final char W_PAWN = 'P';
    public static final char W_KNIGHT = 'N';
    public static final char W_BISHOP = 'B';
    public static final char W_ROOK = 'R';
    public static final char W_QUEEN = 'Q';
    public static final char W_KING = 'K';

    public static final char B_PAWN = 'p';
    public static final char B_KNIGHT = 'n';
    public static final char B_BISHOP = 'b';
    public static final char B_ROOK = 'r';
    public static final char B_QUEEN = 'q';
    public static final char B_KING = 'k';

    public static final String FILES = "abcdefgh";
    public static final String RANKS = "12345678";

    // EFFECTS: constructs a chess game at turn 0 with zero moves played
    //          with given name and a regular starting board
    public Chess(String name) {
        this.gameRecord = new ArrayList<>();
        this.name = name;
        this.turn = 0;
        this.board = this.getNewBoard();
    }

    // MODIFIES: this
    // EFFECTS: moves a piece from start to end on board, appends the move
    //          to the move record and increment the turn counter
    //          if not at the latest move, remove all future moves
    public void move(Pos start, Pos end) throws ChessGameException {
        Move move = new Move();

        Square startSquare = this.getSquareAt(start);
        Square endSquare = this.getSquareAt(end);

        if (endSquare.getPiece() != Chess.EMPTY) {
            move.addAction(new Action(new Square(endSquare.getPiece()),
                    end, "delete"));
        }
        move.addAction(new Action(new Square(startSquare.getPiece()),
                start, "delete"));
        move.addAction(new Action(new Square(startSquare.getPiece()),
                end, "insert"));

        while (this.turn < this.gameRecord.size()) {
            this.gameRecord.remove(this.turn);
        }
        this.gameRecord.add(move);

        endSquare.setPiece(startSquare.getPiece());
        startSquare.setPiece(Chess.EMPTY);

        this.turn++;
    }

    // MODIFIES: this
    // EFFECTS: undoes the previous move made, undoing inserting before deleting,
    //          and decrement turn
    //          throws InvalidTurnException if turn <= 0
    public void undo() throws ChessGameException, InvalidTurnException {
        if (this.turn <= 0) {
            throw new InvalidTurnException();
        }
        this.turn--;

        Move move = this.gameRecord.get(this.turn);
        for (Action action : move.getActions()) {
            Pos pos = action.getPos();
            if (action.getType().equals("insert")) {
                this.getSquareAt(pos).setPiece(Chess.EMPTY);
            }
        }
        for (Action action : move.getActions()) {
            Pos pos = action.getPos();
            char piece = action.getSquare().getPiece();
            if (action.getType().equals("delete")) {
                this.getSquareAt(pos).setPiece(piece);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the next record move, deleting before inserting, and increment turn
    //          throws InvalidTurnException if turn >= gameRecord.size()
    public void play() throws ChessGameException, InvalidTurnException {
        if (this.turn >= this.gameRecord.size()) {
            throw new InvalidTurnException();
        }
        Move move = this.gameRecord.get(this.turn);
        for (Action action : move.getActions()) {
            Pos pos = action.getPos();
            if (action.getType().equals("delete")) {
                this.getSquareAt(pos).setPiece(Chess.EMPTY);
            }
        }
        for (Action action : move.getActions()) {
            Pos pos = action.getPos();
            char piece = action.getSquare().getPiece();
            if (action.getType().equals("insert")) {
                this.getSquareAt(pos).setPiece(piece);
            }
        }
        this.turn++;
    }

    // EFFECTS: gets a new board with the default starting position
    public Square[][] getNewBoard() {
        Square[][] board = {
                // White back rank
                {new Square(W_ROOK), new Square(W_KNIGHT), new Square(W_BISHOP),
                        new Square(W_QUEEN), new Square(W_KING),
                        new Square(W_BISHOP), new Square(W_KNIGHT), new Square(W_ROOK)},
                new Square[8], new Square[8], new Square[8],
                new Square[8], new Square[8], new Square[8],
                // Black back rank
                {new Square(B_ROOK), new Square(B_KNIGHT), new Square(B_BISHOP),
                        new Square(B_QUEEN), new Square(B_KING),
                        new Square(B_BISHOP), new Square(B_KNIGHT), new Square(B_ROOK)}
        };
        for (int file = 0; file < 8; file++) {
            // White pawn rank
            board[1][file] = new Square(W_PAWN);
            for (int rank = 2; rank < 6; rank++) {
                board[rank][file] = new Square(EMPTY);
            }
            // Black pawn rank
            board[6][file] = new Square(B_PAWN);
        }
        return board;
    }

    // EFFECTS: converts and returns chess game as JSON object
    public JSONObject asJsonObject() {
        JSONObject gameJson = new JSONObject();

        JSONArray movesJson = new JSONArray();
        for (Move move : this.gameRecord) {
            movesJson.put(move.asJsonArray());
        }

        gameJson.put("gameRecord", movesJson);
        gameJson.put("name", this.name);
        return gameJson;
    }

    // EFFECTS: returns the square in board at the position represented by pos
    public Square getSquareAt(Pos pos) {
        return this.board[pos.getRank()][pos.getFile()];
    }

    public List<Move> getGameRecord() {
        return this.gameRecord;
    }

    public void setGameRecord(List<Move> gameRecord) {
        this.gameRecord = gameRecord;
    }

    public int getTurn() {
        return this.turn;
    }

    public Square[][] getBoard() {
        return this.board;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
