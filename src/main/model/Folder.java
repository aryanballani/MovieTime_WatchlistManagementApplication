package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// makes a folder which will contain a movie
public class Folder implements Writable {
    private ArrayList<Movie> listOfItems;

    //EFFECTS: creates a folder with empty Array list
    public Folder() {
        listOfItems = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: adds the movie to the list if it isn't already there
    public void addToList(Movie movie) throws AlreadyInListException {
        boolean flag = true;
        for (Movie m : listOfItems) {
            if (Objects.equals(m.getName(), movie.getName())) {
                flag = false;
            }
        }
        if (flag) {
            listOfItems.add(movie);
        } else {
            throw new AlreadyInListException();
        }
    }

    //EFFECTS: adds an event of adding movie to watchlist to the main event log.
    public void addToWatchListLogPrint(Movie movie) {
        EventLog.getInstance().logEvent(new Event((movie.getName()) + " added to WatchList"));
    }

    //EFFECTS: adds an event of adding movie to watched list to the main event log.
    public void addToWatchedLogPrint(Movie movie) {
        EventLog.getInstance().logEvent(new Event((movie.getName()) + " added to Watched List"));
    }

    //EFFECTS: adds an event of adding movie to favourites' to the main event log.
    public void addToFavouritesLogPrint(Movie movie) {
        EventLog.getInstance().logEvent(new Event((movie.getName()) + " added to Favorites"));
    }

    //MODIFIES: this
    //EFFECTS: removes the movie to the list if it is in there
    public void removeFromList(Movie movie) {
        listOfItems.remove(movie);
    }

    //EFFECTS: adds an event of removing a movie from watchlist to the main event log.
    public void removeFromWatchListLogPrint(Movie movie) {
        EventLog.getInstance().logEvent(new Event((movie.getName()) + " removed from WatchList"));
    }

    //EFFECTS: adds an event of removing a movie from favourites' to the main event log.
    public void removeFromFavouritesLogPrint(Movie movie) {
        EventLog.getInstance().logEvent(new Event((movie.getName()) + " removed from Favourites"));
    }

    //EFFECTS: adds an event of viewing the watchlist to the main event log.
    public void viewWatchListLogPrint() {
        EventLog.getInstance().logEvent(new Event("Viewed WatchList"));
    }

    //EFFECTS: adds an event of viewing the watched list to the main event log.
    public void viewWatchedLogPrint() {
        EventLog.getInstance().logEvent(new Event("Viewed Watched movie List"));
    }

    //EFFECTS: adds an event of viewing the favourites' to the main event log.
    public void viewFavouritesLogPrint() {
        EventLog.getInstance().logEvent(new Event("Viewed Favourites"));
    }

    // getter function to get the list of items
    public ArrayList<Movie> getListOfItems() {
        return listOfItems;
    }

    //getter function to get the size of the list of items
    public int getSize() {
        return listOfItems.size();
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listOfItems", thingiesToJson());
        return json;
    }

    // EFFECTS: returns movies in this folder as a JSON array
    private JSONArray thingiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Movie movie : listOfItems) {
            jsonArray.put(movie.toJson());
        }

        return jsonArray;
    }
}
