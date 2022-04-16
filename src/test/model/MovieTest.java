package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTest {
    private Movie testMovie;

    @BeforeEach
    void runBefore() {
        testMovie = new Movie("abc", 2, 4, "action");
    }

    @Test
    void testConstructor() {
        assertEquals("abc", testMovie.getName());
        assertEquals(2, testMovie.getNumHours());
        assertEquals(4, testMovie.getRating());
        assertEquals("action", testMovie.getGenre());
    }

    @Test
    void modifyRating() {
        testMovie.setRating(4.2);
        assertEquals(4.2, testMovie.getRating());

        testMovie.setRating(3.4);
        assertEquals(3.4, testMovie.getRating());
    }


}