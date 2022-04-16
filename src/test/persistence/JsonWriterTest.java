package persistence;

import model.AlreadyInListException;
import model.Folder;
import model.Movie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Folder folder = new Folder();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFolder() {
        try {
            Folder folder = new Folder();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFolder.json");
            writer.open();
            writer.write(folder);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFolder.json");
            folder = reader.read();
            assertEquals(0, folder.getSize());
        } catch (IOException | AlreadyInListException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFolder() {
        try {
            Folder folder = new Folder();
            try {
                folder.addToList(new Movie("SpiderMan", 3, 4, "genre"));
            } catch (AlreadyInListException e) {
                System.out.println("Movie already in there");
            }
            try {
                folder.addToList(new Movie("Aryan", 2.34, 5, "horror"));
            } catch (AlreadyInListException e) {
                System.out.println("Movie already in there");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralFolder.json");
            writer.open();
            writer.write(folder);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralFolder.json");
            folder = reader.read();
            ArrayList<Movie> list = folder.getListOfItems();
            assertEquals(2, list.size());

            Movie movie1 = list.get(0);
            Movie movie2 = list.get(1);

            assertEquals("SpiderMan", movie1.getName());
            assertEquals(3, movie1.getNumHours());
            assertEquals(4, movie1.getRating());
            assertEquals("genre", movie1.getGenre());

            assertEquals("Aryan", movie2.getName());
            assertEquals(2.34, movie2.getNumHours());
            assertEquals(5, movie2.getRating());
            assertEquals("horror", movie2.getGenre());

        } catch (IOException | AlreadyInListException e) {
            fail("Exception should not have been thrown");
        }
    }
}