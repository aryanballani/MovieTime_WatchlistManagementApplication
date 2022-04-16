package ui;

import model.Event;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

// Entertainment Management System application
public class MovieTimeApp extends JFrame implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;

    //buttons
    private JButton saveButton;
    private JButton loadButton;
    private JButton addToWatchlist;
    private JButton removeFromWatchlist;
    private JButton viewWatchlist;
    private JButton addToWatched;
    private JButton viewWatched;
    private JButton addToFavourites;
    private JButton removeFromFavourites;
    private JButton viewFavourites;
    private JButton movieMakerButton;

    //TextAreas and fields
    private JTextArea rightSplitTextArea;
    JTextField movieName = new JTextField("<Replace With Movie Name>");
    JTextField movieNumHours = new JTextField("<Replace with Number of Hours>");
    JTextField movieRating = new JTextField("<Replace with Movie rating>");
    JTextField movieGenre = new JTextField("<Replace with Movie Genre>");

    // storage
    private static final String JSON_STORE_WL = "./data/WatchListData.json";
    private static final String JSON_STORE_W = "./data/WatchedData.json";
    private static final String JSON_STORE_F = "./data/FavouritesData.json";
    private Folder watchlist;
    private Folder watched;
    private Folder favourites;
    private JsonWriter jsonWriterWL;
    private JsonReader jsonReaderWL;
    private JsonWriter jsonWriterW;
    private JsonReader jsonReaderW;
    private JsonWriter jsonWriterF;
    private JsonReader jsonReaderF;

    //frames and labels and JList
    private JLabel topRight;
    private final JLabel topLeftLabel = new JLabel("WatchList");
    JFrame movieMakerFrame = new JFrame();
    JList<String> movieList = new JList<>();
    DefaultListModel<String> model = new DefaultListModel<>();
    JSplitPane splitPane = new JSplitPane();

    // EFFECTS: runs the whole GUI application
    public MovieTimeApp() {
        super("MovieTime");
        initializeFields();
        initializeGraphics();
        makeMovieFrame();
        setBackground(Color.RED);
        initializeEntertainmentSystem();
    }

    // MODIFIES: this
    // EFFECTS: makes a movieMaker frame which is not visible until addToWatchlist button is pressed
    private void makeMovieFrame() {
        movieMakerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        movieMakerFrame.setVisible(false);
        movieMakerFrame.setLayout(new GridLayout(5, 1));
        movieMakerFrame.setSize(new Dimension(300, 250));
        movieMakerFrame.add(movieName);
        movieMakerFrame.add(movieNumHours);
        movieMakerFrame.add(movieGenre);
        movieMakerFrame.add(movieRating);
        movieMakerButton = new JButton("Submit");
        movieMakerFrame.add(movieMakerButton);
        movieMakerButton.setEnabled(false);
        movieMakerButton.addActionListener(this);
    }

    // EFFECTS: instantiates all the fields required for basic functioning of application
    private void initializeEntertainmentSystem() {
        watchlist = new Folder();
        watched = new Folder();
        favourites = new Folder();
        jsonWriterWL = new JsonWriter(JSON_STORE_WL);
        jsonReaderWL = new JsonReader(JSON_STORE_WL);
        jsonWriterW = new JsonWriter(JSON_STORE_W);
        jsonReaderW = new JsonReader(JSON_STORE_W);
        jsonWriterF = new JsonWriter(JSON_STORE_F);
        jsonReaderF = new JsonReader(JSON_STORE_F);
    }

    // MODIFIES: this
    // EFFECTS: sets up all the graphics related to JavaSwing in the main frame
    private void initializeGraphics() {
        makeBackground();
        ImageIcon image = new ImageIcon("MovieTime.jpg");
        setLayout(null);
        setTopLeft();
        setTopRight(image);
        setBottomLeft();
        setBottomRight();
    }

    // MODIFIES: this
    // EFFECTS: sets up all the graphics related to JavaSwing in the top left area of the main frame
    private void setTopLeft() {
        add(splitPane);
        splitPane.setBounds(0, (HEIGHT / 10), (WIDTH / 2), (2 * HEIGHT / 5));
        splitPane.setLeftComponent(new JScrollPane(movieList));
        movieList.setModel(model);
        splitPane.setRightComponent(rightSplitTextArea);
        splitPane.setDividerLocation((WIDTH / 4));
        movieList.getSelectionModel().addListSelectionListener(e -> {
            String s = movieList.getSelectedValue();
            rightSplitTextArea.setText(s);
        });
        add(topLeftLabel);
        topLeftLabel.setBounds(0, 0, (WIDTH / 2), (HEIGHT / 10));
        topLeftLabel.setBackground(Color.DARK_GRAY);
        topLeftLabel.setVisible(true);
        topLeftLabel.setOpaque(true);
        topLeftLabel.setHorizontalAlignment(JLabel.CENTER);
        topLeftLabel.setFont(new Font("MV Boli", Font.BOLD, 30));
        topLeftLabel.setForeground(Color.WHITE);
    }

    // MODIFIES: this
    // EFFECTS: sets up all the graphics related to JavaSwing in the bottom right area of the main frame
    private void setBottomRight() {
        saveButton.setBounds((3 * WIDTH / 4), (HEIGHT / 2), (WIDTH / 4), (41 * HEIGHT / 180));
        saveButton.setText("Save all data");
        add(saveButton);
        saveButton.addActionListener(this);
        saveButton.setFont(new Font("MV Boli", Font.BOLD, 20));

        loadButton.setBounds((3 * WIDTH / 4), (131 * HEIGHT / 180), (WIDTH / 4), (41 * HEIGHT / 180));
        loadButton.setText("Load all data");
        add(loadButton);
        loadButton.addActionListener(this);
        loadButton.setFont(new Font("MV Boli", Font.BOLD, 20));
    }

    // MODIFIES: this
    // EFFECTS: sets up all the graphics related to JavaSwing in the bottom left area of the main frame
    private void setBottomLeft() {
        addWatchListButtons();
        addWatchedButtons();
        favouritesButtons();
    }

    // MODIFIES: this
    // EFFECTS: sets up all buttons related to favourites functions
    private void favouritesButtons() {
        addToFavourites.setBounds((WIDTH / 4), (HEIGHT / 2), (WIDTH / 4), (9 * HEIGHT / 60));
        addToFavourites.setText("Add to Favourites");
        add(addToFavourites);
        addToFavourites.addActionListener(this);
        addToFavourites.setFont(new Font("MV Boli", Font.BOLD, 20));

        removeFromFavourites.setBounds((WIDTH / 4), (39 * HEIGHT / 60), (WIDTH / 4), (9 * HEIGHT / 60));
        removeFromFavourites.setText("Remove from Favourites");
        add(removeFromFavourites);
        removeFromFavourites.addActionListener(this);
        removeFromFavourites.setFont(new Font("MV Boli", Font.BOLD, 20));

        viewFavourites.setBounds((WIDTH / 4), (24 * HEIGHT / 30), (WIDTH / 4), (9 * HEIGHT / 60));
        viewFavourites.setText("View favourite movies");
        add(viewFavourites);
        viewFavourites.addActionListener(this);
        viewFavourites.setFont(new Font("MV Boli", Font.BOLD, 20));
    }

    // MODIFIES: this
    // EFFECTS: sets up all buttons related to watched functions
    private void addWatchedButtons() {
        addToWatched.setBounds((2 * WIDTH / 4), (HEIGHT / 2), (WIDTH / 4), (41 * HEIGHT / 180));
        addToWatched.setText("Mark as Watched");
        add(addToWatched);
        addToWatched.addActionListener(this);
        addToWatched.setFont(new Font("MV Boli", Font.BOLD, 20));

        viewWatched.setBounds((2 * WIDTH / 4), (131 * HEIGHT / 180), (WIDTH / 4), (HEIGHT / 4));
        viewWatched.setText("View Watched Movies");
        add(viewWatched);
        viewWatched.addActionListener(this);
        viewWatched.setFont(new Font("MV Boli", Font.BOLD, 20));
    }

    // MODIFIES: this
    // EFFECTS: sets up all buttons related to watchlist functions
    private void addWatchListButtons() {
        addToWatchlist.setBounds(0, (HEIGHT / 2), (WIDTH / 4), (9 * HEIGHT / 60));
        addToWatchlist.setText("Add to Watchlist");
        add(addToWatchlist);
        addToWatchlist.addActionListener(this);
        addToWatchlist.setFont(new Font("MV Boli", Font.BOLD, 20));

        removeFromWatchlist.setBounds(0, (39 * HEIGHT / 60), (WIDTH / 4), (9 * HEIGHT / 60));
        removeFromWatchlist.setText("Remove From WatchList");
        add(removeFromWatchlist);
        removeFromWatchlist.addActionListener(this);
        removeFromWatchlist.setFont(new Font("MV Boli", Font.BOLD, 20));

        viewWatchlist.setBounds(0, (24 * HEIGHT / 30), (WIDTH / 4), (9 * HEIGHT / 60));
        viewWatchlist.setText("View Watchlist");
        add(viewWatchlist);
        viewWatchlist.addActionListener(this);
        viewWatchlist.setFont(new Font("MV Boli", Font.BOLD, 20));
    }


    // MODIFIES: this
    // EFFECTS: sets up all the graphics related to JavaSwing in the top right area of the main frame
    private void setTopRight(ImageIcon image) {
        add(topRight);
        topRight.setIcon(image);
        topRight.setBounds((27 * WIDTH / 50), 0, (WIDTH / 2), (HEIGHT / 2));

    }

    // MODIFIES: this
    // EFFECTS: sets up the attributes of the main frame
    private void makeBackground() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
        setResizable(false);
        setVisible(true);
        getContentPane().setBackground(new Color(255, 165, 0));
    }

    // EFFECTS: instantiates all the remaining fields in the application
    private void initializeFields() {
        rightSplitTextArea = new JTextArea("");
        topRight = new JLabel();
        addToWatchlist = new JButton();
        removeFromWatchlist = new JButton();
        viewWatchlist = new JButton();
        addToWatched = new JButton();
        viewWatched = new JButton();
        addToFavourites = new JButton();
        removeFromFavourites = new JButton();
        viewFavourites = new JButton();
        saveButton = new JButton();
        loadButton = new JButton();
    }


    // MODIFIES: this
    // EFFECTS: adds the given Movie to the watched list and removes the same movie from WatchList
    public void addToWatched() {
        String input = rightSplitTextArea.getText();
        if (Objects.equals(input, "")) {
            JOptionPane.showMessageDialog(null, "Select a Movie First",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        } else {
            Movie movieToBeRemoved = watchListToWatched(input);
            if (movieToBeRemoved != null) {
                watchlist.removeFromList(movieToBeRemoved);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the given Movie to the watched list and returns the name of the same movie
    private Movie watchListToWatched(String input) {
        Movie movie1 = null;
        for (Movie movie : watchlist.getListOfItems()) {
            if (movie.getName().compareTo(input) == 0) {
                try {
                    watched.addToList(movie);
                    watched.addToWatchedLogPrint(movie);
                    JOptionPane.showMessageDialog(null, "Movie marked as watched",
                            "MovieMaker", JOptionPane.INFORMATION_MESSAGE);
                    movie1 = movie;
                } catch (AlreadyInListException e) {
                    JOptionPane.showMessageDialog(null, "Movie already marked as watched",
                            "MovieMaker", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        return movie1;
    }


    // EFFECTS: prints out the Names of all movies in favourites folder
    public void viewFavourites() {
        model.clear();
        topLeftLabel.setText("Favourites");
        favourites.viewFavouritesLogPrint();
        for (Movie movie : favourites.getListOfItems()) {
            model.addElement(movie.getName());
        }
    }

    // EFFECTS: prints out the Names of all movies in watched folder
    public void viewWatched() {
        model.clear();
        topLeftLabel.setText("Watched Movies");
        watched.viewWatchedLogPrint();
        for (Movie movie : watched.getListOfItems()) {
            model.addElement(movie.getName());
        }
    }

    // EFFECTS: prints out the Names of all movies in Watchlist
    public void viewWatchList() {
        model.clear();
        topLeftLabel.setText("WatchList");
        watchlist.viewWatchListLogPrint();
        for (Movie movie : watchlist.getListOfItems()) {
            model.addElement(movie.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the given movie to the favourites' folder,
    //          if it isn't already there and exists in the watchlist
    public void addToFavourites() {
        String input = rightSplitTextArea.getText();
        if (Objects.equals(input, "")) {
            JOptionPane.showMessageDialog(null, "Select a Movie First",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        } else {
            int length = favourites.getSize();
            watchListToFav(input);
            watchedToFav(input);
            if (favourites.getSize() == (length + 1)) {
                JOptionPane.showMessageDialog(null, "Added to Favourites'",
                        "MovieMaker", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds movie from watchlist to favourites folder
    //          if the movie exists in watchlist
    private void watchListToFav(String input) {
        for (Movie movie : watchlist.getListOfItems()) {
            if (movie.getName().compareTo(input) == 0) {
                try {
                    favourites.addToList(movie);
                    favourites.addToFavouritesLogPrint(movie);
                } catch (AlreadyInListException e) {
                    JOptionPane.showMessageDialog(null, "Movie already in Favourites' list",
                            "MovieMaker", JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds movie from watched to favourites folder
    //          if the movie exists in watchlist
    private void watchedToFav(String input) {
        for (Movie movie : watched.getListOfItems()) {
            if (movie.getName().compareTo(input) == 0) {
                try {
                    favourites.addToList(movie);
                    favourites.addToFavouritesLogPrint(movie);
                } catch (AlreadyInListException e) {
                    JOptionPane.showMessageDialog(null, "Movie already in Favourites' list",
                            "MovieMaker", JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the given movie from the favourites folder
    public void removeFromFavourites() {
        String input = rightSplitTextArea.getText();
        if (Objects.equals(input, "")) {
            JOptionPane.showMessageDialog(null, "Select a Movie First",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        } else {
            Movie movieToBeRemoved = null;
            for (Movie movie : favourites.getListOfItems()) {
                if (movie.getName().compareTo(input) == 0) {
                    movieToBeRemoved = movie;
                }
            }
            if (movieToBeRemoved != null) {
                favourites.removeFromList(movieToBeRemoved);
                favourites.removeFromFavouritesLogPrint(movieToBeRemoved);
                JOptionPane.showMessageDialog(null, "Movie Successfully removed",
                        "MovieMaker", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Movie not in list",
                        "MovieMaker", JOptionPane.WARNING_MESSAGE);

            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the given movie to the watchlist
    private void addToWatchList() {
        String name = movieName.getText();
        double numHours = Double.parseDouble(movieNumHours.getText());
        String genre = movieGenre.getText();
        double rating = Double.parseDouble(movieRating.getText());
        Movie movie1 = new Movie(name, numHours, rating, genre);
        movieMakerFrame.setVisible(false);
        try {
            watchlist.addToList(movie1);
            JOptionPane.showMessageDialog(null, "Successfully Added",
                    "MovieMaker", JOptionPane.INFORMATION_MESSAGE);
            watchlist.addToWatchListLogPrint(movie1);
        } catch (AlreadyInListException e) {
            JOptionPane.showMessageDialog(null, "Movie already in List",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        }
    }


    // MODIFIES: this
    // EFFECTS: removes the given movie from the watchlist
    public void removeFromWatchList() {
        String input = rightSplitTextArea.getText();
        if (Objects.equals(input, "")) {
            JOptionPane.showMessageDialog(null, "Select a Movie First",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        } else {
            Movie movieToBeRemoved = null;
            for (Movie movie : watchlist.getListOfItems()) {
                if (movie.getName().compareTo(input) == 0) {
                    movieToBeRemoved = movie;
                }
            }
            if (movieToBeRemoved != null) {
                watchlist.removeFromList(movieToBeRemoved);
                JOptionPane.showMessageDialog(null, "Successfully removed",
                        "MovieMaker", JOptionPane.INFORMATION_MESSAGE);
                watchlist.removeFromWatchListLogPrint(movieToBeRemoved);
            } else {
                JOptionPane.showMessageDialog(null, "Movie not in List",
                        "MovieMaker", JOptionPane.WARNING_MESSAGE);

            }
        }
    }

    //EFFECTS: saves the watchlist to file
    public void saveWatchList() {
        try {
            jsonWriterWL.open();
            jsonWriterWL.write(watchlist);
            jsonWriterWL.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE_WL,
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);

        }
    }

    // MODIFIES: this
    // EFFECTS: loads watchlist from file
    public void loadWatchList() {
        try {
            watchlist = jsonReaderWL.read();
            //System.out.println("Loaded WatchList from " + JSON_STORE_WL);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE_WL);
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE_WL,
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        } catch (AlreadyInListException e) {
            //System.out.println("A movie is being duplicated if we load the data");
            JOptionPane.showMessageDialog(null, "A movie is being duplicated if we load the data",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        }
    }

    // EFFECTS: saves the watched to file
    public void saveWatched() {
        try {
            jsonWriterW.open();
            jsonWriterW.write(watched);
            jsonWriterW.close();
            //System.out.println("Saved Watchlist to " + JSON_STORE_W);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE_W);
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE_W,
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads watched from file
    public void loadWatched() {
        try {
            watched = jsonReaderW.read();
            //System.out.println("Loaded Watched from " + JSON_STORE_W);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE_W);
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE_W,
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        } catch (AlreadyInListException e) {
            //System.out.println("A movie is being duplicated if we load the data");
            JOptionPane.showMessageDialog(null, "A movie is being duplicated if we load the data",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);

        }
    }

    // EFFECTS: saves the favourites to file
    public void saveFavourites() {
        try {
            jsonWriterF.open();
            jsonWriterF.write(favourites);
            jsonWriterF.close();
            //System.out.println("Saved Watchlist to " + JSON_STORE_F);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE_F);
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE_F,
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);

        }
    }

    // MODIFIES: this
    // EFFECTS: loads favourites from file
    public void loadFavourites() {
        try {
            favourites = jsonReaderF.read();
            //System.out.println("Loaded WatchList from " + JSON_STORE_F);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE_F);
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE_F,
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);
        } catch (AlreadyInListException e) {
            //System.out.println("A movie is being duplicated if we load the data");
            JOptionPane.showMessageDialog(null, "A movie is being duplicated if we load the data",
                    "MovieMaker", JOptionPane.WARNING_MESSAGE);

        }
    }

    // MODIFIES: this
    // EFFECTS: calls the functions when certain buttons are pressed
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addToWatchlist) {
            movieMakerFrame.setVisible(true);
            movieMakerButton.setEnabled(true);
        } else if (event.getSource() == movieMakerButton) {
            addToWatchList();
        } else if (event.getSource() == viewWatchlist) {
            viewWatchList();
        } else if (event.getSource() == removeFromWatchlist) {
            removeFromWatchList();
        } else if (event.getSource() == viewFavourites) {
            viewFavourites();
        } else if (event.getSource() == viewWatched) {
            viewWatched();
        } else if (event.getSource() == addToWatched) {
            addToWatched();
        } else {
            handleRest(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: calls the functions when certain buttons are pressed part 2
    private void handleRest(ActionEvent event) {
        if (event.getSource() == saveButton) {
            saveWatchList();
            saveWatched();
            saveFavourites();
            JOptionPane.showMessageDialog(null, "Saved all Data ",
                    "MovieMaker", JOptionPane.INFORMATION_MESSAGE);
        } else if (event.getSource() == loadButton) {
            loadWatchList();
            loadWatched();
            loadFavourites();
            JOptionPane.showMessageDialog(null, "Loaded all Data ",
                    "MovieMaker", JOptionPane.INFORMATION_MESSAGE);
        } else if (event.getSource() == addToFavourites) {
            addToFavourites();
        } else if (event.getSource() == removeFromFavourites) {
            removeFromFavourites();
        }
    }

    // EFFECTS: prints the logs of all the events that took place on the console.
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }

        repaint();
    }

}
