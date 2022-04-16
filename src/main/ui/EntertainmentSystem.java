//package ui;
//
//import model.AlreadyInListException;
//import model.Folder;
//import model.Movie;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.Scanner;
//
//// Entertainment Management System application
//public class EntertainmentSystem extends Folder {
//    private static final String JSON_STORE_WL = "./data/WatchListData.json";
//    private static final String JSON_STORE_W = "./data/WatchedData.json";
//    private static final String JSON_STORE_F = "./data/FavouritesData.json";
//    private final Scanner input;
//    private Folder watchlist;
//    private Folder watched;
//    private Folder favourites;
//    private final JsonWriter jsonWriterWL;
//    private final JsonReader jsonReaderWL;
//    private final JsonWriter jsonWriterW;
//    private final JsonReader jsonReaderW;
//    private final JsonWriter jsonWriterF;
//    private final JsonReader jsonReaderF;
//
//
//    // EFFECTS: runs the management system application
//    public EntertainmentSystem() {
//        input = new Scanner(System.in);
//        watchlist = new Folder();
//        watched = new Folder();
//        favourites = new Folder();
//        jsonWriterWL = new JsonWriter(JSON_STORE_WL);
//        jsonReaderWL = new JsonReader(JSON_STORE_WL);
//        jsonWriterW = new JsonWriter(JSON_STORE_W);
//        jsonReaderW = new JsonReader(JSON_STORE_W);
//        jsonWriterF = new JsonWriter(JSON_STORE_F);
//        jsonReaderF = new JsonReader(JSON_STORE_F);
//        runApp();
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user input
//    private void runApp() {
//        boolean keepGoing = true;
//        String command;
//
//        while (keepGoing) {
//            displayMenu();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand1(command);
//            }
//        }
//    }
//
//
//    // MODIFIES: this
//    // EFFECTS: processes user command part 1
//    private void processCommand1(String command) {
//        if (command.equals("a")) {
//            addToWatchList();
//        } else if (command.equals("r")) {
//            removeFromWatchList();
//        } else if (command.equals("b")) {
//            addToFavourites();
//        } else if (command.equals("s")) {
//            removeFromFavourites();
//        } else if (command.equals("swl")) {
//            saveWatchList();
//        } else if (command.equals("sw")) {
//            saveWatched();
//        } else if (command.equals("sf")) {
//            saveFavourites();
//        } else if (command.equals("v")) {
//            viewWatchList();
//        } else if (command.equals("n")) {
//            numHoursWatched();
//        } else {
//            processCommand2(command);
//        }
//    }
//
//
//    // MODIFIES: this
//    // EFFECTS: processes user command part 2
//    private void processCommand2(String command) {
//        if (command.equals("5")) {
//            viewFiveStars();
//        } else if (command.equals("4")) {
//            viewFourStars();
//        } else if (command.equals("f")) {
//            viewFavourites();
//        } else if (command.equals("lf")) {
//            loadFavourites();
//        } else if (command.equals("lwl")) {
//            loadWatchList();
//        } else if (command.equals("lw")) {
//            loadWatched();
//        } else if (command.equals("m")) {
//            addToWatched();
//        } else if (command.equals("w")) {
//            viewWatched();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds the given Movie to the watchlist
//    public void addToWatched() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the name of movie you want to add");
//        String input = scanner.next();
//        Movie movieToBeRemoved = null;
//        for (Movie movie : watchlist.getListOfItems()) {
//            if (movie.getName().compareTo(input) == 0) {
//                try {
//                    watched.addToList(movie);
//                    movieToBeRemoved = movie;
//                } catch (AlreadyInListException e) {
//                    System.out.println("Movie already in List");
//                }
//            }
//        }
//        if (movieToBeRemoved != null) {
//            watchlist.removeFromList(movieToBeRemoved);
//        }
//    }
//
//    // EFFECTS: prints out the Names of all four-star movies
//    public void viewFourStars() {
//        for (Movie movie : watchlist.getListOfItems()) {
//            if (movie.getRating() >= 4 && movie.getRating() < 5) {
//                System.out.println(movie.getName());
//            }
//        }
//        for (Movie movie : watched.getListOfItems()) {
//            if (movie.getRating() >= 4 && movie.getRating() < 5) {
//                System.out.println(movie.getName());
//            }
//        }
//    }
//
//    // EFFECTS: prints out the Names of all five-star movies
//    public void viewFiveStars() {
//        for (Movie movie : watchlist.getListOfItems()) {
//            if (movie.getRating() == 5) {
//                System.out.println(movie.getName());
//            }
//        }
//        for (Movie movie : watched.getListOfItems()) {
//            if (movie.getRating() == 5) {
//                System.out.println(movie.getName());
//            }
//        }
//    }
//
//    // EFFECTS: prints out the Names of all movies in favourites folder
//    public void viewFavourites() {
//        for (Movie movie : favourites.getListOfItems()) {
//            System.out.println(movie.getName());
//        }
//    }
//
//    // EFFECTS: prints out the Names of all movies in watched folder
//    public void viewWatched() {
//        for (Movie movie : watched.getListOfItems()) {
//            System.out.println(movie.getName());
//        }
//    }
//
//
//    // EFFECTS: prints out the number of hours invested in watching all the movies until now
//    public void numHoursWatched() {
//        double total = 0;
//        for (Movie movie : watched.getListOfItems()) {
//            total += movie.getNumHours();
//        }
//        System.out.println("The number of hours streamed is " + total);
//    }
//
//    // EFFECTS: prints out the Names of all movies in Watchlist
//    public void viewWatchList() {
//        for (Movie movie : watchlist.getListOfItems()) {
//            System.out.println(movie.getName());
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds the given movie to the favourites' folder,
//    //          if it isn't already there and exists in the watchlist
//    public void addToFavourites() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the name of movie you want to add");
//        String input = scanner.next();
//        int length = favourites.getSize();
//        watchListToFav(input);
//        watchedToFav(input);
//        if (favourites.getSize() == (length + 1)) {
//            System.out.println("Successfully added");
//        } else {
//            System.out.println("Movie not watched/ in watchlist");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds movie from watchlist to favourites folder
//    //          if the movie exists in watchlist
//    private void watchListToFav(String input) {
//        for (Movie movie : watchlist.getListOfItems()) {
//            if (movie.getName().compareTo(input) == 0) {
//                try {
//                    favourites.addToList(movie);
//                } catch (AlreadyInListException e) {
//                    System.out.println("Movie already in List");
//                }
//            }
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds movie from watched to favourites folder
//    //          if the movie exists in watchlist
//    private void watchedToFav(String input) {
//        for (Movie movie : watched.getListOfItems()) {
//            if (movie.getName().compareTo(input) == 0) {
//                try {
//                    favourites.addToList(movie);
//                } catch (AlreadyInListException e) {
//                    System.out.println("Movie already in List");
//                }
//            }
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: removes the given movie from the favourites folder
//    public void removeFromFavourites() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the name of movie you want to remove");
//        String input = scanner.next();
//        Movie movieToBeRemoved = null;
//        for (Movie movie : favourites.getListOfItems()) {
//            if (movie.getName().compareTo(input) == 0) {
//                movieToBeRemoved = movie;
//            }
//        }
//        if (movieToBeRemoved != null) {
//            favourites.removeFromList(movieToBeRemoved);
//            System.out.println("Successfully removed");
//        } else {
//            System.out.println("Movie Not in list");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds the given movie to the watchlist
//    public void addToWatchList() {
//        Movie movie = makeMovie();
//        try {
//            watchlist.addToList(movie);
//            System.out.println("Successfully added");
//        } catch (AlreadyInListException e) {
//            System.out.println("Movie already in List");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: removes the given movie from the watchlist
//    public void removeFromWatchList() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the name of movie you want to remove");
//        String input = scanner.next();
//        Movie movieToBeRemoved = null;
//        for (Movie movie : watchlist.getListOfItems()) {
//            if (movie.getName().compareTo(input) == 0) {
//                movieToBeRemoved = movie;
//            }
//        }
//        if (movieToBeRemoved != null) {
//            watchlist.removeFromList(movieToBeRemoved);
//            System.out.println("Successfully removed");
//        } else {
//            System.out.println("Movie not in List");
//        }
//    }
//
//    // REQUIRES: rating should be 0<= rating <=5
//    // EFFECTS: Scans the user input and returns a movie object
//    private Movie makeMovie() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter the name of the movie");
//        String name = scanner.next();
//        System.out.println("Enter the Num of hours of the movie");
//        double numHours = scanner.nextDouble();
//        System.out.println("Enter the genre of the movie");
//        String genre = scanner.next();
//        System.out.println("Enter the rating of the movie");
//        double rating = scanner.nextDouble();
//        return new Movie(name, numHours, rating, genre);
//    }
//
//    // EFFECTS: saves the watchlist to file
//    public void saveWatchList() {
//        try {
//            jsonWriterWL.open();
//            jsonWriterWL.write(watchlist);
//            jsonWriterWL.close();
//            System.out.println("Saved Watchlist to " + JSON_STORE_WL);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE_WL);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads watchlist from file
//    public void loadWatchList() {
//        try {
//            watchlist = jsonReaderWL.read();
//            System.out.println("Loaded WatchList from " + JSON_STORE_WL);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE_WL);
//        } catch (AlreadyInListException e) {
//            System.out.println("A movie is being duplicated if we load the data");
//        }
//    }
//
//    // EFFECTS: saves the watched to file
//    public void saveWatched() {
//        try {
//            jsonWriterW.open();
//            jsonWriterW.write(watched);
//            jsonWriterW.close();
//            System.out.println("Saved Watchlist to " + JSON_STORE_W);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE_W);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads watched from file
//    public void loadWatched() {
//        try {
//            watched = jsonReaderW.read();
//            System.out.println("Loaded WatchList from " + JSON_STORE_W);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE_W);
//        } catch (AlreadyInListException e) {
//            System.out.println("A movie is being duplicated if we load the data");
//        }
//    }
//
//    // EFFECTS: saves the favourites to file
//    public void saveFavourites() {
//        try {
//            jsonWriterF.open();
//            jsonWriterF.write(favourites);
//            jsonWriterF.close();
//            System.out.println("Saved Watchlist to " + JSON_STORE_F);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE_F);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads favourites from file
//    public void loadFavourites() {
//        try {
//            favourites = jsonReaderF.read();
//            System.out.println("Loaded WatchList from " + JSON_STORE_F);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE_F);
//        } catch (AlreadyInListException e) {
//            System.out.println("A movie is being duplicated if we load the data");
//        }
//    }
//
//
//    // EFFECTS: displays menu of options to user
//    private void displayMenu() {
//        System.out.println("\nSelect from:");
//        System.out.println("\ta -> Add a movie to watchlist");
//        System.out.println("\tb -> Add a movie to favourites");
//        System.out.println("\tm -> Mark a movie as watched");
//        System.out.println("\tr -> Remove a movie from watchlist");
//        System.out.println("\ts -> Remove a movie from favourites");
//        System.out.println("\tv -> View your Watchlist");
//        System.out.println("\tn -> Number of Hours Streaming");
//        System.out.println("\t5 -> View 5 Star movie folder");
//        System.out.println("\t4 -> View 4 Star movie folder");
//        System.out.println("\tf -> View Favourites movie folder");
//        System.out.println("\tw -> View watched movie folder");
//        System.out.println("\tswl -> save watchlist to file");
//        System.out.println("\tlwl -> load watchlist from file");
//        System.out.println("\tsw -> save watched folder to file");
//        System.out.println("\tlw -> load watched folder from file");
//        System.out.println("\tsf -> save favourites to file");
//        System.out.println("\tlf -> load favourites from file");
//        System.out.println("\tq -> quit");
//        System.out.println("Press Button to continue");
//    }
//}
