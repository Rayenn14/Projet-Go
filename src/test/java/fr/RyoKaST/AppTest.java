package fr.RyoKaST;

import fr.RyoKaST.Gomoku.Board;
import fr.RyoKaST.Gomoku.CommandFailedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private Board board;

    @Test
    void initialise(){
        board = new Board(7);
        assertNotNull(board);
    }

    @Test
    void clearBoard() {
        board = new Board(7);
        board.play("white", "A1");
        board.clearBoard();
        assert(board.isEmpty());
    }

    @Test
    void play() {
        board = new Board(7);
        board.play("white", "A1"); // Le joueur "X" joue à la position "A1"
        String cellValue = board.getCellValue("A1"); // Supposons une méthode pour récupérer la valeur d'une cellule
        assertEquals("white", cellValue, "La cellule A1 doit contenir 'X' après le coup");
    }

    @Test
    void genmove() {
        board = new Board(7);
        String move = board.genmove("black");
        assertNotNull(move, "La méthode genmove() doit retourner un coup valide");
        assertTrue(move.matches("[A-G][1-7]"), "Le coup généré doit être dans le format attendu, comme A1");
    }

    @Test
    void showBoard() {
        board = new Board(7);
        board.play("white", "A1");
        board.play("black", "B2");
        board.showBoard(); //Vérifier manuellementl'état du plateau ???
    }

    @Test
    void testPlay() {
        board = new Board(7);
        board.play("black", "A1");
        board.play("white", "A2");
        board.showBoard();
        assertEquals("black", board.getCellValue("A1"), "La cellule A1 doit contenir 'b'");
        assertEquals("white", board.getCellValue("A2"), "La cellule A2 doit contenir 'w'");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.play("white", "A1");
        });
        assertEquals("Position already occupied", exception.getMessage(), "Un message d'erreur doit être retourné si la position est déjà occupée");
    }
}

