package model;

import model.exceptions.InvalidPosException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PosTest {

    @Test
    void testConstructor() {
        try {
            Pos pos = new Pos(0, 7);
            assertEquals(0, pos.getRank());
            assertEquals(7, pos.getFile());

            pos = new Pos(1, 4);
            assertEquals(1, pos.getRank());
            assertEquals(4, pos.getFile());
        } catch (InvalidPosException e) {
            fail("InvalidPosException thrown when none expected");
        }
    }

    @Test
    void testInvalidRank() {
        try {
            new Pos(-1, 4);
            fail("No exception thrown when InvalidPosException expected");
        } catch (InvalidPosException e) {
            // pass
        }
        try {
            new Pos(8, 3);
            fail("No exception thrown when InvalidPosException expected");
        } catch (InvalidPosException e) {
            // pass
        }
    }

    @Test
    void testInvalidFile() {
        try {
            new Pos(2, -1);
            fail("No exception thrown when InvalidPosException expected");
        } catch (InvalidPosException e) {
            // pass
        }
        try {
            new Pos(5, 8);
            fail("No exception thrown when InvalidPosException expected");
        } catch (InvalidPosException e) {
            // pass
        }
    }
}
