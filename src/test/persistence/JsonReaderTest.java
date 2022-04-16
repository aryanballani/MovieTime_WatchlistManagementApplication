package persistence;

import model.AlreadyInListException;
import model.Folder;
import model.Movie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Folder folder = reader.read();
            fail("IOException expected");
        } catch (IOException | AlreadyInListException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFolder() {
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
    void testReaderGeneralWorkRoom() {
        try {
            Folder folder = new Folder();
            Movie movie1 = new Movie("Aryan", 2.34, 5, "horror");
            Movie movie2 = new Movie("SpiderMan", 3, 4, "genre");
            try {
                folder.addToList(movie1);
            } catch (AlreadyInListException e) {
                System.out.println("Movie already in list");
            }
            try {
                folder.addToList(movie2);
            } catch (AlreadyInListException e) {
                System.out.println("Movie already in list");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(folder);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            folder = reader.read();
            ArrayList<Movie> movies = folder.getListOfItems();
            assertEquals(2, movies.size());

            Movie movie3 = movies.get(1);
            Movie movie4 = movies.get(0);

            assertEquals("SpiderMan", movie3.getName());
            assertEquals(3, movie3.getNumHours());
            assertEquals(4, movie3.getRating());
            assertEquals("genre", movie3.getGenre());

            assertEquals("Aryan", movie4.getName());
            assertEquals(2.34, movie4.getNumHours());
            assertEquals(5, movie4.getRating());
            assertEquals("horror", movie4.getGenre());


        } catch (IOException | AlreadyInListException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void catchAlreadyInListException() {
        try {
            Folder folder = new Folder();
            Movie movie1 = new Movie("Aryan", 2.34, 5, "horror");
            Movie movie2 = new Movie("SpiderMan", 3, 4, "genre");
            try {
                folder.addToList(movie1);
            } catch (AlreadyInListException e) {
                System.out.println("Movie already in list");
            }
            try {
                folder.addToList(movie2);
            } catch (AlreadyInListException e) {
                System.out.println("Movie already in list");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(folder);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            Folder folder1 = reader.read();
            folder1.addToList(movie1);
            fail("Exception should have been thrown");


        } catch (IOException | AlreadyInListException e) {
            //pass
        }
    }
}