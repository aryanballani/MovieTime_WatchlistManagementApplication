package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FolderTest {
    private Movie testMovie1;
    private Movie testMovie2;
    private Folder watchList;

    @BeforeEach
    void runBefore() {
        testMovie1 = new Movie("Spider Man", 2.8, 4.1, "Superhero");
        testMovie2 = new Movie("Harry Potter", 2.5, 4.4, "Action");
        watchList = new Folder();
    }

    @Test
    void testConstructor() {
        assertEquals(0, watchList.getSize());
    }

    @Test
    void testAddToList() {
        try {
            watchList.addToList(testMovie1);
        } catch (AlreadyInListException e) {
            System.out.println("Exception caught");
        }
        assertEquals(1, watchList.getSize());
        ArrayList<Movie> list = watchList.getListOfItems();
        assertEquals("Spider Man", list.get(0).getName());
        assertEquals(2.8, list.get(0).getNumHours());
        assertEquals(4.1, list.get(0).getRating());
        assertEquals("Superhero", list.get(0).getGenre());

        try {
            watchList.addToList(testMovie2);
        } catch (AlreadyInListException e) {
            System.out.println("Exception caught");
        }
        assertEquals(2, watchList.getSize());
        ArrayList<Movie> list1 = watchList.getListOfItems();
        assertEquals("Spider Man", list1.get(0).getName());
        assertEquals(2.8, list1.get(0).getNumHours());
        assertEquals(4.1, list1.get(0).getRating());
        assertEquals("Superhero", list1.get(0).getGenre());

        assertEquals("Harry Potter", list1.get(1).getName());
        assertEquals(2.5, list1.get(1).getNumHours());
        assertEquals(4.4, list1.get(1).getRating());
        assertEquals("Action", list1.get(1).getGenre());

        try {
            watchList.addToList(testMovie1);
            fail("Expected to catch AlreadyInListException but caught nothing");
        } catch (AlreadyInListException e) {
            //pass
        }

    }

    @Test
    void testRemoveFromList() throws AlreadyInListException {
        watchList.addToList(testMovie2);
        watchList.addToList(testMovie1);
        assertEquals(2, watchList.getSize());
        ArrayList<Movie> list = watchList.getListOfItems();
        assertEquals("Harry Potter", list.get(0).getName());
        assertEquals(2.5, list.get(0).getNumHours());
        assertEquals(4.4, list.get(0).getRating());
        assertEquals("Action", list.get(0).getGenre());

        assertEquals("Spider Man", list.get(1).getName());
        assertEquals(2.8, list.get(1).getNumHours());
        assertEquals(4.1, list.get(1).getRating());
        assertEquals("Superhero", list.get(1).getGenre());

        watchList.removeFromList(testMovie1);
        assertEquals(1, watchList.getSize());
        ArrayList<Movie> list1 = watchList.getListOfItems();
        assertEquals("Harry Potter", list1.get(0).getName());
        assertEquals(2.5, list1.get(0).getNumHours());
        assertEquals(4.4, list1.get(0).getRating());
        assertEquals("Action", list1.get(0).getGenre());

        watchList.removeFromList(testMovie2);
        assertEquals(0, watchList.getSize());
    }
}
