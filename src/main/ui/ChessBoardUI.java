package ui;

import model.Chess;
import model.Pos;
import model.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Panel of an 8 x 8 chess board
public class ChessBoardUI extends JPanel implements ActionListener {
    private Chess game;
    private final ChessSquareUI[][] squareGrid;
    private Pos selectedPos;

    // EFFECTS: constructs chess board panel with given game and no selected position
    public ChessBoardUI(Chess game) {
        this.game = game;
        this.squareGrid = new ChessSquareUI[8][8];
        this.selectedPos = null;
        this.setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up the panel of the overall chess board
    public void setupPanel() {
        this.setLayout(new GridLayout(8, 8));
        this.setupSquares();
    }

    // MODIFIES: this
    // EFFECTS: sets up the panels for each individual square adds this as
    //          an action listener that receives the pos to each
    public void setupSquares() {
        for (int rank = 7; rank > -1; rank--) {
            for (int file = 0; file < 8; file++) {
                String shade = "light";
                if ((rank + file) % 2 == 0) {
                    shade = "dark";
                }
                Square square = game.getSquareAt(new Pos(rank, file));
                ChessSquareUI squareUI = new ChessSquareUI(square, shade);
                squareUI.addActionListener(this);
                squareUI.setActionCommand(rank + "" + file);

                squareGrid[rank][file] = squareUI;
                this.add(squareUI);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resets every square to be the squares on the board
    public void resetSquares() {
        for (int rank = 7; rank > -1; rank--) {
            for (int file = 0; file < 8; file++) {
                squareGrid[rank][file].setSquare(game.getSquareAt(new Pos(rank, file)));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the color and images of all squares
    public void updateSquares() {
        for (int rank = 7; rank > -1; rank--) {
            for (int file = 0; file < 8; file++) {
                squareGrid[rank][file].update();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets this.game to given game, deselected current position
    //          and resets and updates the panel of squares
    public void setGame(Chess game) {
        this.game = game;
        this.selectedPos = null;
        this.resetSquares();
        this.updateSquares();
    }

    // MODIFIES: this
    // EFFECTS: selects square at pos if no selected square and unselects it
    //          if that square is already selected, otherwise move piece
    //          from selectedPos to pos and unselect square
    public void clickSquare(Pos pos) {
        if (this.selectedPos == null) {
            this.selectedPos = pos;
            this.getSquareUiAt(this.selectedPos).select();

        } else if (pos.getRank() == this.selectedPos.getRank()
                && pos.getFile() == this.selectedPos.getFile()) {
            this.getSquareUiAt(this.selectedPos).resetColor();
            this.selectedPos = null;

        } else {
            this.game.move(this.selectedPos, pos);
            this.updateSquares();
            this.getSquareUiAt(this.selectedPos).resetColor();
            this.selectedPos = null;
        }
    }

    // EFFECTS: returns the ChessSquareUI in squareGrid at the position represented by pos
    public ChessSquareUI getSquareUiAt(Pos pos) {
        return this.squareGrid[pos.getRank()][pos.getFile()];
    }

    // MODIFIES: this
    // EFFECTS: respond to clicking events of button squares
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        int rank = Integer.parseInt(action.substring(0, 1));
        int file = Integer.parseInt(action.substring(1, 2));

        this.clickSquare(new Pos(rank, file));
    }
}
