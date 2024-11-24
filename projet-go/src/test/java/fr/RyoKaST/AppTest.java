package fr.RyoKaST;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Basic test to ensure the application runs correctly.
     */
    @Test
    void testApp() {
        assertTrue(true, "This test should always pass!");
    }

    /**
     * Example of a more meaningful test.
     */
    @Test
    void testAddition() {
        int a = 5;
        int b = 3;
        int result = a + b;
        assertEquals(8, result, "The addition should result in 8");
    }
}
