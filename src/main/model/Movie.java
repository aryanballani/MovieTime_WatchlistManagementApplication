package model;

import org.json.JSONObject;
import persistence.Writable;

//makes an object called movie which contains certain attributes
public class Movie implements Writable {
    private final String name;
    private final double numHours;
    private double rating;
    private final String genre;


    //EFFECTS: makes an object named movie and sets all parameters as the given by the user
    public Movie(String name, double numHours, double rating, String genre) {
        this.name = name;
        this.numHours = numHours;
        this.rating = rating;
        this.genre = genre;
    }

    // getter to get name
    public String getName() {
        return name;
    }

    //getter to get Genre
    public String getGenre() {
        return genre;
    }

    //getter to get number of hours of movie
    public double getNumHours() {
        return numHours;
    }

    //getter to get the rating of the movie
    public double getRating() {
        return rating;
    }

    //MODIFIES: this
    //EFFECTS: sets the rating of the movie as the given rating.
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("numHours", numHours);
        json.put("rating", rating);
        json.put("genre", genre);
        return json;
    }
}
