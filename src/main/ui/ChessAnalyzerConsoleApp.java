package ui;

import model.Chess;
import model.ChessAnalyzer;
import model.Pos;
import model.Square;
import model.exceptions.InvalidPosException;
import model.exceptions.InvalidTurnException;
import persistence.JsonFileReader;
import persistence.JsonFileWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// The console application for the chess analyzer
public class ChessAnalyzerConsoleApp {
    private ChessAnalyzer chessAnalyzer;
    private final Scanner scanner;

    private final JsonFileReader reader;
    private final JsonFileWriter writer;

    // EFFECTS: constructs a new console application and runs the main loop
    public ChessAnalyzerConsoleApp() {
        this.chessAnalyzer = new ChessAnalyzer();
        this.scanner = new Scanner(System.in);

        this.reader = new JsonFileReader("data/games.json");
        this.writer = new JsonFileWriter("data/games.json");

        this.run();
    }

    // MODIFIES: this
    // EFFECTS: runs the main loop that takes in user input to
    //          perform actions in the application
    public void run() {
        String input;
        while (true) {
            this.printStatus();
            input = scanner.nextLine().toLowerCase();
            if (input.equals("q")) {
                System.out.println("closing application...");
                break;
            } else {
                handleInput(input);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Have application do something based on user command
    public void handleInput(String input) {
        if (input.equals("m")) {
            this.makeMove();
        } else if (input.equals("a")) {
            this.addNewGame();
        } else if (input.equals("d")) {
            this.removeGame();
        } else if (input.equals("g")) {
            this.selectGame();
        } else if (input.equals("r")) {
            this.renameGame();
        } else if (input.equals("u")) {
            this.makeUndo();
        } else if (input.equals("p")) {
            this.makePlay();
        } else if (input.equals("s")) {
            this.save();
        } else if (input.equals("l")) {
            this.load();
        } else {
            System.out.println("command not recognized!");
        }
    }

    // MODIFIES: this
    // EFFECTS: take user input for start pos and end pos, and move the piece
    //          at start to end if the input is valid
    public void makeMove() {
        try {
            System.out.println("select file of starting square (a-h)");
            String startFile = scanner.nextLine().toLowerCase();
            System.out.println("select rank of starting square (1-8)");
            String startRank = scanner.nextLine().toLowerCase();

            System.out.println("select file of ending square (a-h)");
            String endFile = scanner.nextLine().toLowerCase();
            System.out.println("select rank of ending square (1-8)");
            String endRank = scanner.nextLine().toLowerCase();

            Pos start = processMoveInput(startRank, startFile);
            Pos end = processMoveInput(endRank, endFile);
            this.chessAnalyzer.getSelectedGame().move(start, end);

            System.out.println("moving piece from " + startFile + startRank
                    + " to " + endFile + endRank + "...");

        } catch (InvalidPosException e) {
            System.out.println("move input not accepted!");
        }
    }

    // REQUIRES: fileCode is a single letter between a and h and
    //           rankCode is a single digit between 1 and 8
    // EFFECTS: returns a position created from the rank and file given
    //          throws InvalidPosException if fileCode is not a single letter between a and h or
    //          rankCode is not a single character
    public Pos processMoveInput(String rankCode, String fileCode) throws InvalidPosException {
        if (rankCode.length() != 1 || fileCode.length() != 1
                || !Chess.RANKS.contains(rankCode)
                || !Chess.FILES.contains(fileCode)) {
            throw new InvalidPosException();
        }
        int file = Chess.FILES.indexOf(fileCode);
        int rank = Integer.parseInt(rankCode) - 1;
        return new Pos(rank, file);
    }

    // MODIFIES: this
    // EFFECTS: adds a new game into list of games
    public void addNewGame() {
        this.chessAnalyzer.addNewGame();
        System.out.println("adding new game...");
    }

    // MODIFIES: this
    // EFFECTS: delete the current from list of games
    public void removeGame() {
        int select = this.chessAnalyzer.getSelected();
        this.chessAnalyzer.removeGame();
        System.out.println("deleting game " + (select + 1) + "...");
    }

    // MODIFIES: this
    // EFFECTS: changes selected game to input
    public void selectGame() {
        int maxSelect = this.chessAnalyzer.getGames().size();
        System.out.println("select a game (1-" + maxSelect + ")");
        String input = scanner.nextLine();
        int select = Integer.parseInt(input) - 1;
        if (0 <= select && select < maxSelect) {
            this.chessAnalyzer.setSelected(select);
            System.out.println("selecting game " + (select + 1) + "...");
        } else {
            System.out.println("selected game is out of range!");
        }
    }

    // MODIFIES: this
    // EFFECTS: renames the current selected game
    public void renameGame() {
        System.out.println("choose a name");
        String input = scanner.nextLine();
        this.chessAnalyzer.getSelectedGame().setName(input);
        System.out.println("renaming game to " + input + "...");
    }

    // MODIFIES: this
    // EFFECTS: undo the last recorded move
    public void makeUndo() {
        try {
            this.chessAnalyzer.getSelectedGame().undo();
        } catch (InvalidTurnException e) {
            System.out.println("no more moves to undo!");
        }
    }

    // MODIFIES: this
    // EFFECTS: play the next recorded move
    public void makePlay() {
        try {
            this.chessAnalyzer.getSelectedGame().play();
        } catch (InvalidTurnException e) {
            System.out.println("no more moves to play!");
        }
    }

    // EFFECTS: save all games in collection to file
    public void save() {
        try {
            this.writer.writeFile(this.chessAnalyzer.getGames());
            System.out.println("saving games...");
        } catch (FileNotFoundException e) {
            System.out.println("save file not found!");
        }
    }

    // MODIFIES: this
    // EFFECTS: delete all current games and load all saved games from file
    public void load() {
        try {
            List<Chess> games = this.reader.readFile();
            this.chessAnalyzer = new ChessAnalyzer();
            for (Chess game : games) {
                chessAnalyzer.addGame(game);
            }
            chessAnalyzer.removeGame();
            System.out.println("loading games...");
        } catch (IOException e) {
            System.out.println("save file not found!");
        }
    }

    // EFFECTS: prints the console menu for the application
    public void printStatus() {
        this.printBoard();
        System.out.println("m : move a piece to another square");
        System.out.println("a : add a new game to list of games");
        System.out.println("d : delete the current selected game");
        System.out.println("g : select another game");
        System.out.println("r : rename the current selected game");
        System.out.println("u : undo the last recorded move");
        System.out.println("p : play the next recorded move");
        System.out.println("s : save all current games");
        System.out.println("l : load all saved games");
        System.out.println("q : quit application");
    }

    // EFFECTS: prints the current chess board of the selected game to console
    public void printBoard() {
        Chess game = this.chessAnalyzer.getSelectedGame();
        Square[][] board = game.getBoard();

        System.out.println("\n" + game.getName());
        for (int r = 7; r > -1; r--) {
            System.out.print((r + 1) + "  ");
            for (int f = 0; f < 8; f++) {
                this.printSquare(board[r][f]);
                System.out.print(" ");
            }
            System.out.println();
        }
        // Manually formatted for inconsistent unicode spacing in console
        System.out.println("   a  b c  d  e f  g  h");
        System.out.println("Turn " + (game.getTurn() + 1 + "\n"));
    }

    // EFFECTS: print out a string symbol representing the piece on the square
    public void printSquare(Square square) {
        String pieceStrings = "♙♘♗♖♕♔♟♞♝♜♛♚―";

        ArrayList<Character> pieceCodes = new ArrayList<>();
        pieceCodes.add(Chess.W_PAWN);
        pieceCodes.add(Chess.W_KNIGHT);
        pieceCodes.add(Chess.W_BISHOP);
        pieceCodes.add(Chess.W_ROOK);
        pieceCodes.add(Chess.W_QUEEN);
        pieceCodes.add(Chess.W_KING);
        pieceCodes.add(Chess.B_PAWN);
        pieceCodes.add(Chess.B_KNIGHT);
        pieceCodes.add(Chess.B_BISHOP);
        pieceCodes.add(Chess.B_ROOK);
        pieceCodes.add(Chess.B_QUEEN);
        pieceCodes.add(Chess.B_KING);
        pieceCodes.add(Chess.EMPTY);

        int pieceIndex = pieceCodes.indexOf(square.getPiece());
        System.out.print(pieceStrings.charAt(pieceIndex));
    }
}
