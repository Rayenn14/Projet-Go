package fr.RyoKaST;

import fr.RyoKaST.Gomoku.Gomoku;
import fr.RyoKaST.Stable.IJeu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


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

    @Test
    void WinHorizontale(){
        game.setBoardSize("3");
        game.play("white", "A1");
        game.play("white", "B1");
        game.play("white", "C1");
        String GagnantHorizontale = "  A B C\n" + "3 . . . 3\n" + "2 . . . 2\n" + "1 w w w 1\n" + "  A B C\n";
        assertEquals(GagnantHorizontale, game.showBoard());
        assertTrue(game.gameFinish());
    }

    @Test
    void WinHorizontalBlack(){
        game.setBoardSize("3");
        game.play("black", "A1");
        game.play("black", "B1");
        game.play("black", "C1");
        String GagnantHorizontale = "  A B C\n" + "3 . . . 3\n" + "2 . . . 2\n" + "1 b b b 1\n" + "  A B C\n";
        assertEquals(GagnantHorizontale, game.showBoard());
        assertTrue(game.gameFinish());
    }

    @Test
    void WinHorizontalWhite(){
        game.setBoardSize("3");
        game.play("white", "A1");
        game.play("white", "B1");
        game.play("white", "C1");
        String GagnantHorizontale = "  A B C\n" + "3 . . . 3\n" + "2 . . . 2\n" + "1 w w w 1\n" + "  A B C\n";
        assertEquals(GagnantHorizontale, game.showBoard());
        assertTrue(game.gameFinish());
    }

    @Test
    void WinVerticalWhite(){
        game.setBoardSize("3");
        game.play("white", "A1");
        game.play("white", "A2");
        game.play("white", "A3");
        String GagnantHorizontale = "  A B C\n" + "3 w . . 3\n" + "2 w . . 2\n" + "1 w . . 1\n" + "  A B C\n";
        assertEquals(GagnantHorizontale, game.showBoard());
        assertTrue(game.gameFinish());
    }

    @Test
    void WinVerticalBlack(){
        game.setBoardSize("3");
        game.play("black", "C1");
        game.play("black", "C2");
        game.play("black", "C3");
        String GagnantHorizontale = "  A B C\n" + "3 . . b 3\n" + "2 . . b 2\n" + "1 . . b 1\n" + "  A B C\n";
        assertEquals(GagnantHorizontale, game.showBoard());
        assertTrue(game.gameFinish());
    }

    @Test
    void WinDiagoWhite(){
        game.setBoardSize("3");
        game.play("white", "A1");
        game.play("white", "B2");
        game.play("white", "C3");
        String GagnantHorizontale = "  A B C\n" + "3 . . w 3\n" + "2 . w . 2\n" + "1 w . . 1\n" + "  A B C\n";
        assertEquals(GagnantHorizontale, game.showBoard());
        assertTrue(game.gameFinish());
    }

    @Test
    void WinDiagoBlack(){
        game.setBoardSize("3");
        game.play("black", "A3");
        game.play("black", "B2");
        game.play("black", "C1");
        String GagnantHorizontale = "  A B C\n" + "3 b . . 3\n" + "2 . b . 2\n" + "1 . . b 1\n" + "  A B C\n";
        assertEquals(GagnantHorizontale, game.showBoard());
        assertTrue(game.gameFinish());
    }







}

