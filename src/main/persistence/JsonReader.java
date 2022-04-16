package persistence;

import model.AlreadyInListException;
import model.Folder;
import model.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads folder from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads folder from file and returns it;
    // throws IOException if an error occurs reading data from file or
    // throws AlreadyInListException if even one movie object is being duplicated
    public Folder read() throws IOException, AlreadyInListException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFolder(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses folder from JSON object and returns it
    private Folder parseFolder(JSONObject jsonObject) throws AlreadyInListException {
        Folder folder = new Folder();
        addMovies(folder, jsonObject);
        return folder;
    }

    // MODIFIES: folder
    // EFFECTS: parses movies from JSON object and adds them to folder
    private void addMovies(Folder folder, JSONObject jsonObject) throws AlreadyInListException {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfItems");
        for (Object json : jsonArray) {
            JSONObject nextMovie = (JSONObject) json;
            addMovie(folder, nextMovie);
        }
    }

    // MODIFIES: folder
    // EFFECTS: parses movie from JSON object and adds it to folder
    private void addMovie(Folder folder, JSONObject jsonObject) throws AlreadyInListException {
        String name = jsonObject.getString("name");
        double numHours = jsonObject.getDouble("numHours");
        double rating = jsonObject.getDouble("rating");
        String genre = jsonObject.getString("genre");
        Movie movie = new Movie(name, numHours, rating, genre);
        folder.addToList(movie);
    }
}
