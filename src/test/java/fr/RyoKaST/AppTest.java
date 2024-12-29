package fr.RyoKaST;

import fr.RyoKaST.Gomoku.Board;
import fr.RyoKaST.Gomoku.Gomoku;
import fr.RyoKaST.Stable.IJeu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    IJeu game = new Gomoku();

    @Test
    void initialise(){
        game.setBoardSize("3");
        String vide = "  A B C\n" + "3 . . . 3\n" + "2 . . . 2\n" + "1 . . . 1\n" + "  A B C\n";
        assertEquals(vide, game.showBoard());
    }

    @Test
    void playTest() {
        game.setBoardSize("3");
        game.play("white", "A1");
        String A1 = "  A B C\n" + "3 . . . 3\n" + "2 . . . 2\n" + "1 w . . 1\n" + "  A B C\n";
        assertEquals(A1, game.showBoard());

        game.play("black", "A2");
        String A2 = "  A B C\n" + "3 . . . 3\n" + "2 b . . 2\n" + "1 w . . 1\n" + "  A B C\n";
        assertEquals(A2, game.showBoard());

        game.play("black", "A3");
        String A3 = "  A B C\n" + "3 b . . 3\n" + "2 b . . 2\n" + "1 w . . 1\n" + "  A B C\n";
        assertEquals(A3, game.showBoard());
    }

    @Test
    void clearBoard() {
        game.setBoardSize("3");
        String vide = "  A B C\n" + "3 . . . 3\n" + "2 . . . 2\n" + "1 . . . 1\n" + "  A B C\n";
        assertEquals(vide, game.showBoard());
        game.play("white", "A1");
        game.clearBoard();
        assertEquals(vide, game.showBoard());
    }

    @Test
    void genmove() {
        game.setBoardSize("3");
        String move = game.genMove("white");
        assertNotNull(move, "La méthode genmove() doit retourner un coup valide");
        assertTrue(move.matches("[A-G][1-7]"), "Le coup généré doit être dans le format attendu, comme A1");

    }



}

