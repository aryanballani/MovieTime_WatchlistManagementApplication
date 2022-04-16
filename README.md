 # Personalised Entertainment Management System

## What will the project application do?
This is a very comprehensive project which implements the ideologies of a personification of the user. The project application is designed for one single user to keep track of all the movies he/she watches 
over the years. The user can add/remove movies from his/her watchlist, rate each movie out of 5 stars and 
can even add their favourite movies to the favourite movies' folder.

## Who will use it?
The application can be used by anyone who likes to keep tab of all the media they consume, to later reflect 
on all of them and re-watch one if they wish to.

## Why is the project of interest to you?
When in the summer I used to binge-watch all my favourite movies and TV series, I used to go on IMDb and rate them and 
that I wished that I had one such personal application just to keep track of my progress, wherein, I could add/ remove
movies from my watchlist, rate them according to myself without judgement on the internet, etc. That is why I decided 
to go with this idea.
***
## User Stories:
- As a user, I want to be able to add a movie to my watchlist.
- As a user, I want to be able to add a movie to my favourites' folder.
- As a user, I want to be able to view all the movies which I have rated more than 4 stars.
- As a user, I want to be able to view all the movies which I have rated 5 stars.
- As a user, I want to be able to view the list of movies on my watchlist.
- As a user, I want to be able to view the number of hours invested win watching all movies until now.
- As a user, I want to be able to mark a movie as watched on my watchlist.
- As a user, I want to be able to remove a movie from my watchlist.
- As a user, I want to be able to remove a movie from my favourites' folder.

***
- As a user, I want to be able to save my watchlist to file.
- As a user, I want to be able to be able to load my watchlist from file.
- As a user, I want to be able to save my watched folder to file.
- As a user, I want to be able to be able to load my watched folder from file.
- As a user, I want to be able to save my favourites folder to file.
- As a user, I want to be able to be able to load my favourites folder from file.
***
## Phase 4: Task 2
Fri Apr 01 14:33:54 PDT 2022
Viewed WatchList


Fri Apr 01 14:33:55 PDT 2022
Viewed Favourites


Fri Apr 01 14:33:57 PDT 2022
Viewed WatchList


Fri Apr 01 14:34:04 PDT 2022
Spartians removed from WatchList


Fri Apr 01 14:34:08 PDT 2022
Viewed WatchList


Fri Apr 01 14:34:17 PDT 2022
Pirates of the Caribbean 3 added to Watched List


Fri Apr 01 14:34:19 PDT 2022
Pirates of the Caribbean 3 added to Favorites


Fri Apr 01 14:34:21 PDT 2022
Viewed Watched movie List


Fri Apr 01 14:34:23 PDT 2022
Shrek removed from Favourites


Fri Apr 01 14:34:25 PDT 2022
Shrek added to Favorites


Fri Apr 01 14:34:28 PDT 2022
Viewed Watched movie List


Fri Apr 01 14:34:29 PDT 2022
Viewed Favourites


Fri Apr 01 14:34:29 PDT 2022
Viewed WatchList


Fri Apr 01 14:35:12 PDT 2022
Spider Man NWH added to WatchList


Fri Apr 01 14:35:13 PDT 2022
Viewed WatchList


Fri Apr 01 14:35:16 PDT 2022
Spider Man NWH added to Favorites


Fri Apr 01 14:35:18 PDT 2022
Spider Man NWH added to Watched List


Fri Apr 01 14:35:20 PDT 2022
Viewed Watched movie List


Fri Apr 01 14:35:20 PDT 2022
Viewed Favourites



Process finished with exit code 0
***
## Phase 4: Task 3
### *Reflection on UML Diagram*

- Event class has a uni-directional relationship with EventLog class. EventLog class uses a list of Events.
- Main class is used to run the MovieTimeApp by calling a new MovieTimeApp in its constructor.
- Folder class uses a list of Movies, hence, the multiplicity here is 0..* 
- JsonReader and JsonWriter are 2 classes that are called by the MovieTimeApp and each of them are called 3 times.

### *Refactoring*
If I had some more time, I would focus more on 

- Making abstract functions because I have 3 lists and the methods for adding, removing, etc. are similar.
- Making Exceptions for different common behaviour which shouldn't happen.
- Making the buttons/some method to call the additional functions that I made in Phase 2.
***

 Sources : 
 - JsonSerializationDemo : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
 - Java Swing Basics : https://www.youtube.com/watch?v=Kmgo00avvEw&t=3235s
 - JList and JScrollPane Basics : https://www.youtube.com/watch?v=KOI1WbkKUpQ&t=408s
 - AlarmSystem : https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 