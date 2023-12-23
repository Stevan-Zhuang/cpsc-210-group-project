package ui;

import model.Square;

import javax.swing.*;
import java.awt.*;

// Panel of a single square in the chess board
public class ChessSquareUI extends JButton {
    private Square square;
    private final JLabel image;
    private final String shade;

    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;

    public static final Color LIGHT_SQUARE = new Color(240, 218, 181);
    public static final Color DARK_SQUARE = new Color(181, 136, 99);
    public static final Color LIGHT_SQUARE_SEL = new Color(130, 151, 105);
    public static final Color DARK_SQUARE_SEL = new Color(100, 111, 64);

    // EFFECTS: Constructs a square panel with given background color and square
    public ChessSquareUI(Square square, String shade) {
        this.square = square;
        this.image = new JLabel();
        this.shade = shade;

        this.setupPanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up the panel of square and adds image label
    public void setupPanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.resetColor();
        this.updateImage();
        this.add(this.image);
    }

    // MODIFIES: this
    // EFFECTS: changes to color of the square to the selected color
    public void select() {
        this.setColor(LIGHT_SQUARE_SEL, DARK_SQUARE_SEL);
    }

    // MODIFIES: this
    // EFFECTS: resets the color of the square back to the default
    public void resetColor() {
        this.setColor(LIGHT_SQUARE, DARK_SQUARE);
    }

    // MODIFIES: this
    // EFFECTS: sets the color of the button background and border depending on shade
    public void setColor(Color lightCol, Color darkCol) {
        Color col = lightCol;
        if (this.shade.equals("dark"))  {
            col = darkCol;
        }
        this.setBackground(col);
        this.setBorder(BorderFactory.createLineBorder(col));
    }

    // MODIFIES: this
    // EFFECTS: updates the color and image for the square
    public void update() {
        this.resetColor();
        this.updateImage();
    }

    // MODIFIES: this
    // EFFECTS: updates the image of the button to the current piece of square
    public void updateImage() {
        String team = "";
        String piece = String.valueOf(this.square.getPiece());
        if (this.square.getPiece() == ' ') {
            piece = "blank";
        } else if (Character.isUpperCase(this.square.getPiece())) {
            team = "white/";
        } else {
            team = "black/";
        }
        ImageIcon icon = new ImageIcon("data/images/" + team + piece + ".png");
        icon = new ImageIcon(icon.getImage().getScaledInstance(
                WIDTH, HEIGHT, Image.SCALE_SMOOTH));
        image.setIcon(icon);
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
