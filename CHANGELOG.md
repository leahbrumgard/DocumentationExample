# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Started this file on November 3rd because we didn't realize we needed a changelog before.
### However, we did work before Nov. 3rd! Here is everything we did before then:
### Set up
- Got IntelliJ set up (took most of our time)

### Added
- Author class
- Genre class
- Position class
- Book class
- RowShelf class
- BookShelf class
- HibBook class

## 2017-11-03
### Added
- AllShelves interface
- Book interface
- BookShelf interface
- CreatedList interface
- Publisher class
- ReadingList class
- RowShelf interface
- ShelvedBook interface
- SimpleShelvedBook class
- SortType class
- Title class
- WishList class
- BookTests class
- HibBook class
- ItemOneFragment class
- ItemTwoFragment class
- ItemThree Fragment class
- MainActivity class

### renamed
- Book class to SimpleBook class
- BookShelf class to  SimpleBookShelf class
- Row class to SimpleRowShelf class
- HibernateTest class to HibTest
- HibernateUtil class to HibUtil
- renamed Title, Author, Genre, Publisher to SimpleTitle, SimpleAuthor, SimpleGenre, SimplePublisher
- renamed ItemOneFragment class to ShelfFragment
- renamed ItemTwoFragment class to CameraFragment
- renamed ItemThreeFragment class to ProfileFragment


### Removed
- Position class

### Current classes as of 2017-11-3, 9pm
- AllShelves interface
- Author interface
- Book interface
- BookShelf interface
- ContactList class
- CreatedList interface
- Genre class
- Main class (contents are temporary)
- Position class
- Publisher class
- ReadingList class
- RowShelf interface
- ShelvedBook interface
- SimpleAuthor class
- SimpleBook class
- SimpleBookShelf class
- SimpleGenre class
- SimplePublisher class
- SimpleRowshelf class
- SimpleShelvedBook class
- SortType class
- Title class
- User class
- WishList class

  Hibernate:
- BookTests class
- HibAuthor class
- HibBook class
- HibTest class
- HibUtil class
- HibRowShelf
- HibShelvedBook

  Android:
- ShelfFragment
- CameraFragment
- ProfileFragment
- MainActivity class

### Heavily Edited
- All Hibernate files

### Created on 2017-11-4
- Login class
- Goodreads class
- OAuthExample class
- GoodreadsTests
- HashPasswordTests: tests hashing passwords

  Hibernate/spark:
- Server class

  Android:
- fragment_search.xml
- fragment_shelf.xml
- SearchFragment class
- ConnectFragment class

### Created on 2017-11-5
- ServerExample

### Updated on 2017-11-5
- HibTest class: tests hibernate classes
- HibBook class
- HibTitle class
- HipAuthor class
- Server class: essentially the code Zach walked through during class. For reference.
- ServerExample class: attempts to use spark to post a visual representation of
  the database on a webpage.

  Updated for Facebook GUI login:
- Strings.xml
- AndroidManifest.xml
- build.gradle
- MainActivity class
- activity_main.xml
- activity_shelved_login_5.xml
- content_shelved_login_5.xml

### Deleted on 2017-11-5
- HibUtil

### General status update as of 2017-11-5
Database is fully working. Server tries to send representation of database to webpage, but has issues (error code 100 - coming to office hours).
Facebook login button appears but does not function. However, we have a prototype which allows login, so this behavior is concerning, and
we will also be coming to office hours for this. Five buttons at bottom of the screen are present, and clicking on them bring up blank pages for each.
We created almost all of the classes (including hibernate versions). We implemented some Goodreads API queries, and also used the API to scrape their page
when their API was being belligerent. We also used JBCrypt hashing password API to hash passwords.

### Created on 2017-11-10
- LoginActivity class
- activity_login.xml
- SignupActivity class
- activity_signup.xml

### 2017-11-12
- Created SimpleMessageBody, which converts an object to a json. The first field of this json is a header which stores the name of the class for reference by the server

### Status update 2017-11-12
Successfully linked LoginActivity to MainActivity. Login page is the first to appear upon opening the app, and a successful login (Facebook or Shelved) will bring the user to the Main page.

### 2017-11-18
Created list view of book objects that appears when a user taps the Shelf icon in the bottom navigation bar. The items have a Title and Author text field. Working UI layout of the Profile page with name, bio, picture, and settings.

### 2017-11-19
Added an add book button on the shelf page that popups a dialog to manually input a book by entering a Title and Author.

### Created (default) or heavily modified (noted) as of 2017-11-19
- ServerRouteAddBook
- HibBookService
- BookAddedResponse
- SimpleObjectsTest
- GsonUtils
- CreateUserResponse
- FailureResponse
- InvalidLoginUserResponse
- ResponseMessage
- ValidLoginUserResponse
- HibUserService
- HibBookShelf
- HibAllShelves
- DisplayTestRoute
- ExtendedStringBuilder
- HibTest
- JsonUtil
- PersistenceUtils
- Server
- ServerExample
- ServerRoute
- ServerRouteLogin
- ServerRouteSearchBook
- ServerRouteSignup
- Goodreads, heavily modified
In general, these classes describe how the server interacts with hibernate/mysql and gives information back to the model. We have also created new tests (SimpleObjectsTest) and heavily modified the goodreads class (it was refactored, and methods were added)


## Deleted as of 2017-11-19
- Deleted SimpleMessageBody
- Deleted Server class and renamed working ExampleServer.java to Server.java

## Added as of 2017-11-29
- Added BookListFragment
- Set ScannerActivity to open upon click of Camera icon in nav bar and can scan large ISBNs

## Updated as of 2017-11-30
- Changed BookListFragment from a ListView to a GridView
- Updated AddBookDialog and AddListDialog constructors to the behavior in newInstance() and made the data structure a List of SimpleBook objects rather than using a SimpleAdapter and String and Integer arrays.
- Cleaned up and finished defining all methods to get book information from sites like Google Books, isbndb, and Goodreads.
- Wrote server/networking classes that add a book to the database after adding a book through the AddBookDialog on shelf fragment

## Created as of 2017-11-30
- Wrote server/networking classes that add a book to the database after retrieving the ISBN from the barcode scanner in the camera fragment

## Update as of 2017-12-01
- Turned on the wifi on Android phone. This was an accomplishment.
- Implemented more of the functionality regarding getting book information from the server to the phone

## Created as of 2017-12-02
- Created a SearchView element in SearchFragment with a Listener for when a user changes text and when a user clicks submit, to be linked with the Goodreads/Google Books API calls

## Updated as of 2017-12-02
- Added code to the individual book view dialog so that it displays the book title and author

## Created as of 2017-12-3
- Wrote hibernate classes for lists (HibList and HibListService)
- Wrote server/networking classes that add a list to the database after adding a list in the profile fragment
- Created tests for HibShelvedBook, HibList, HibRowShelf

## Updated as of 2017-12-4
- SearchView Listener requires the user to click a button corresponding with ISBN, Title, or Author. Currently only have ISBN search implemented. When a user submits a search query, the bookInfo class from the server is called, namely getAuthorfromISBN and getTitleFromISBN. These results are placed in a book item, fed into a BookListAdapter, and set to the ListView of SearchResultsFragment

## New as of 2017-12-4
- Created a model package inside of the android package.
- Created a ShelvedModel inside of this model package.
- Created String request classes in the model package for addBook and getBookList. Created corresponding listener classes.
- Created ShelvedUrls class that generically creates the url needed for a Post, given an enum value for each type needed.

## Updated as of 2017-12-4 evening
- Moved a lot of the application logic from inside of ShelfFragment to inside the corresponding ShelvedModel classes.
- Moved a lot of the application logic from inside of AddBookDialog to inside the corresponding ShelvedModel classes.
- Added a method to get a ShelvedModel inside of AppSingleton.
## As of 2017-12-7
- Scanning an isbn and searching for an isbn returns the correct SimpleBook result
- Refactored BookInfo class to no longer allow injection attacks, to remove dependency on ISBNdb, and to use a key to access Google Books' API

## As of 2017-12-9
- Refactored BookInfo class to query Google Books a smaller number of times to increase performance and more importantly keep us from going over our quota by running the tests in bulk

## Created on 2017-12-12
- BookInfoFragment uses the fields from the SimpleBook object. We added the book cover URL as a field of the SimpleBook object, and then on the BookInfoFragment we use the Ion library to load a url image into an ImageView android object.
- Completed writing server/networking code for adding a list and adding a book to a list
- Created basic fragment for individual list view
- Updated BookInfo

## Created on 2017-12-13
- Added progress dialog that spins after a user clicks logout and before the LoginActivity is started
- Updated BookInfo

## Created on 2017-12-14
- EditProfileDialog allows user to edit profile fields like name, bio, location
- SearchFragment now functions with Title, Author, or ISBN
- Updated BookInfo

## Created on 2017-12/17
- Created progress dialog to appear when a user submits a search query
- Added book cover images to the Camera Fragment (after scanning an ISBN and showing info for book)
- Added book cover images to the list items that appear in search and in list view of shelves
- Added book cover images to BookInfoFragment
- Plus button on search_result_item is now linked to add the book to your shelf
- Plus button on BookInfoFragment is now linked to add the book to your shelf
- Recommended books now appear on the BookInfoFragment tab, and clicking on each recommended book redirects you to that book's BookInfoFragment

## As of 2017-12-19
- Refactored BookInfo to better follow the open-closed principle by updating populate method to not rely on so many try/catch statements
- Got onClickListeners working for search result fragment
- Automatically initialize a reading list named "wishlist"
- Created addBookToList dialog
  - This addBookToList dialog only adds to the default wishlist we've created.
- Added a button to add a book to a list onto the search result items
- Wrote more listeners for recommended books

## Thanks for an amazing class :)
