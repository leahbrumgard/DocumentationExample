package edu.swarthmore.cs.cs71.shelved.model.bookData;

import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookInfo {
    private final String GOODREADS_KEY = "VCtvMQ3iSjQaSHPXlhGZQA";
    private final String GOOGLE_KEY = "AIzaSyAhOYmtyu0DYSipcoZzoeYjomVqYBQjHJQ";
//    private final String GOOGLE_KEY_old = "AIzaSyAjhcw1HxBoBsw19IE4CGN8NxMxiaxZ-Po";
    //private final String GOOGLE_KEY = "AIzaSyAowLKnhi1-ovIJoeKvuNrXyxcZ0noMJzY";


    private final int MAX_BOOKS = 3;
    private final int MAX_NUM_RECS = 3;

    public BookInfo() {}

    //COMBINED section
    public SimpleBook populateSimpleBookFromISBN(String isbn) throws SAXException, EmptyQueryException, ParserConfigurationException, XPathExpressionException, IOException, NotFoundException {
        SimpleBook simpleBook = new SimpleBook();
        JSONObject jObj = getJsonObjectFromISBN(isbn);
        String html = getGoodreadsHTMLFromISBN(isbn);

        simpleBook.setTitle(getTitleFromISBN(jObj, html, isbn));
        //Purposefully throws a not found exception if there isn't a title

        simpleBook.setAuthor(getAuthorFromISBN(jObj, html, isbn));
        //Purposefully throws a not found exception if there isn't an author

        simpleBook.setISBN(isbn);

        try {
            simpleBook.setGenre(getGenreFromISBN(jObj, html, isbn));
        } catch (NotFoundException e) {
            simpleBook.setGenre("");
        }

        try {
            simpleBook.setPublisher(getPublisherFromISBN(jObj, html, isbn));
        } catch (NotFoundException e) {
            simpleBook.setPublisher("");
        }

        try {
            simpleBook.setPages(getNumPagesFromISBN(jObj, html, isbn));
        } catch (NotFoundException e) {
            simpleBook.setPages(-1);
        }

        try {
            simpleBook.setImageUrl(getUrlBookCoverFromISBN(jObj, isbn));
        } catch (NotFoundException e){
            simpleBook.setImageUrl(null);
        }

        return simpleBook;
    }

    private String getGoodreadsHTMLFromISBN(String isbn) throws IOException {
        String html;
        try {
            String goodreadsId = getGoodreadsId(isbn);
            URL url = new URL("https://www.goodreads.com/book/show/" + goodreadsId);
            StringBuffer content = getHTMLContent(url);
            html = content.toString();
        } catch (NotFoundException e){
            html = null;
        }
        return html;
    }

    private JSONObject getJsonObjectFromISBN(String isbn) throws IOException, EmptyQueryException {
        JSONObject jObj;
        try {
            jObj = getJsonFromQueryGoogle("","",isbn);
        } catch (NotFoundException e){
            jObj = null;
        }
        return jObj;
    }

    public SimpleBook populateSimpleBookFromTitleAndOrAuthor(String title, String author) throws SAXException, EmptyQueryException, ParserConfigurationException, XPathExpressionException, IOException, NotFoundException {
        try {
            String isbn = getISBNFromTitleAndOrAuthor(title,author);
            return populateSimpleBookFromISBN(isbn);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public List<SimpleBook> populateSimpleBookListFromTitleAndOrAuthor(String title, String author) throws SAXException, EmptyQueryException, ParserConfigurationException, XPathExpressionException, IOException, NotFoundException {
        List<SimpleBook> listOfBooks = new ArrayList<>();
        try {
            List<String> isbnList = getISBNListFromTitleAndOrAuthor(title,author);
            for (String isbn:isbnList){
                try {
                    listOfBooks.add(populateSimpleBookFromISBN(isbn));
                } catch (NotFoundException e){}
            }
        } catch (NotFoundException e) {
            return null;
        }
        return listOfBooks;
    }


    //ISBN-based Methods


    public String getTitleFromISBN(JSONObject jObj, String html, String ISBN) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, EmptyQueryException, NotFoundException {
        if (jObj != null) {
            return getTitleGoogleJson(jObj);
        } else if (html != null){ //try Goodreads. Will still throw NotFoundException if not found
            return getTitleFromISBNGoodreads(ISBN, html);
        } else {
            throw new NotFoundException("Title not found", jObj);
        }
    }


    public String getAuthorFromISBN(JSONObject jObj, String html, String ISBN) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, EmptyQueryException, NotFoundException {
        if (jObj != null) {
            return getAuthorGoogleJson(jObj);
        } else if (html != null){ //try Goodreads. Will still throw NotFoundException if not found
            return getAuthorFromISBNGoodreads(ISBN, html);
        } else {
            throw new NotFoundException("Author not found", jObj);
        }
    }

    public String getGenreFromISBN(JSONObject jObj, String html, String ISBN) throws EmptyQueryException, IOException, NotFoundException, XPathExpressionException, SAXException, ParserConfigurationException {
        if (jObj != null) {
            return getGenreGoogleJson(jObj);
        } else if (html != null){ //try Goodreads. Will still throw NotFoundException if not found
            return getGenreFromISBNGoodreads(ISBN, html);
        } else {
            throw new NotFoundException("Genre not found", jObj);
        }
    }

    public String getPublisherFromISBN(JSONObject jObj, String html, String ISBN) throws IOException, NotFoundException, EmptyQueryException {
        if (jObj != null) {
            return getPublisherGoogleJson(jObj);
        } else { //try Goodreads. Will still throw NotFoundException if not found
            throw new NotFoundException("Publisher not found", null);
        }
    }


    public int getNumPagesFromISBN(JSONObject jObj, String html, String ISBN) throws IOException, NotFoundException, EmptyQueryException, XPathExpressionException, SAXException, ParserConfigurationException {
        if (jObj != null) {
            return getNumPagesGoogleJson(jObj);
        } else if (html != null){ //try Goodreads. Will still throw NotFoundException if not found
            return getNumPagesFromISBNGoodreads(ISBN, html);
        } else {
            throw new NotFoundException("Num pages not found", jObj);
        }
    }


    public List<SimpleBook> getRecommendedBooksFromISBN(String ISBN) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, NotFoundException, EmptyQueryException {
        String workId = getWorkId(ISBN);
        URL url = new URL("https://www.goodreads.com/book/similar/"+workId);
        StringBuffer content = getHTMLContent(url);
        String html = content.toString();
        List<Integer> begIndexListTitle = getIndexBegList(html,"'name'>", MAX_NUM_RECS);
        List<Integer> begIndexListAuthor = getIndexBegList(html,"itemprop=\"name\">", MAX_NUM_RECS);
        List<Integer> endIndexListTitle = getIndecesEndList(html, "</span></a>      <br/>        <span class=\'by smallText\'>", MAX_NUM_RECS);
        List<Integer> endIndexListAuthor = new ArrayList<Integer>();
        endIndexListAuthor.addAll(getIndecesEndList(html, "</span></a></span>"));
        endIndexListAuthor.addAll(getIndecesEndList(html, "</span></a> <"));
        Collections.sort(endIndexListAuthor);
        List<SimpleBook> BookList = new ArrayList<>();
        int numRecs = begIndexListTitle.size();
        if (begIndexListTitle.size()>MAX_NUM_RECS){
            numRecs = MAX_NUM_RECS;
        }
        for (int i=1;i<numRecs;i++){
            String title = html.substring(begIndexListTitle.get(i), endIndexListTitle.get(i));
            String author = html.substring(begIndexListAuthor.get(i), endIndexListAuthor.get(i));
            SimpleBook simpleBook = populateSimpleBookFromTitleAndOrAuthor(title, author);
            if (simpleBook != null){
                BookList.add(simpleBook);
            }
        }
        return BookList;
    }


    //Title-based Methods
    public List<String> getISBNListFromTitleAndOrAuthor(String title, String author) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, EmptyQueryException, NotFoundException {
        JSONObject jObj = getJsonFromQueryGoogle(title, author, "");
        return getISBNListGoogleJson(jObj);
    }

    public String getISBNFromTitleAndOrAuthor(String title, String author) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, EmptyQueryException, NotFoundException {
        JSONObject jObj = getJsonFromQueryGoogle(title, author, "");
        return getISBNGoogleJson(jObj);
    }



    public String getUrlBookCoverFromISBN(JSONObject jObj, String isbn) throws EmptyQueryException, IOException, NotFoundException {
        try {
            return jObj.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("smallThumbnail");
        } catch (Exception e){
            throw new NotFoundException(e.toString(), jObj);
        }
    }
    public List<String> getAuthorListFromTitle(String title) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, EmptyQueryException, NotFoundException {
        List<String> isbnList = getISBNListFromTitleAndOrAuthor(title, "");
        List<String> author = new ArrayList<>();
        for (String isbn:isbnList){
            JSONObject jObj = getJsonFromQueryGoogle("","",isbn);
            String goodreadsId = getGoodreadsId(isbn);
            URL url = new URL("https://www.goodreads.com/book/show/" + goodreadsId);
            StringBuffer content = getHTMLContent(url);
            author.add(getAuthorFromISBN(jObj, content.toString(), isbn));
        }
        return author;
    }

    public List<String> getPublisherListFromTitleAndOrAuthor(String title, String author) throws IOException, NotFoundException, SAXException, EmptyQueryException, ParserConfigurationException, XPathExpressionException {
        List<String> isbnList = getISBNListFromTitleAndOrAuthor(title, author);
        List<String> publisher = new ArrayList<>();
        for (String isbn:isbnList){
            JSONObject jObj = getJsonFromQueryGoogle("","",isbn);
            String goodreadsId = getGoodreadsId(isbn);
            URL url = new URL("https://www.goodreads.com/book/show/" + goodreadsId);
            StringBuffer content = getHTMLContent(url);
            publisher.add(getPublisherFromISBN(jObj, content.toString(), isbn));
        }
        return publisher;
    }

    public List<Integer> getNumPagesListFromTitleAndOrAuthor(String title, String author) throws IOException, NotFoundException, SAXException, EmptyQueryException, ParserConfigurationException, XPathExpressionException {
        List<String> isbnList = getISBNListFromTitleAndOrAuthor(title, author);
        List<Integer> NumPages = new ArrayList<>();
        for (String isbn:isbnList){
            JSONObject jObj = getJsonFromQueryGoogle("","",isbn);
            String goodreadsId = getGoodreadsId(isbn);
            URL url = new URL("https://www.goodreads.com/book/show/" + goodreadsId);
            StringBuffer content = getHTMLContent(url);
            NumPages.add(getNumPagesFromISBN(jObj, content.toString(), isbn));
        }
        return NumPages;
    }

//    public List<String> getRecommendedBooksFromTitleAndOrAuthor(String title, String author) throws ParserConfigurationException, IOException, XPathExpressionException, NotFoundException, SAXException, EmptyQueryException {
//        int upperBound = 5;
//        List<String> isbnList = getISBNsFromTitleAndOrAuthor(title, author);
//        Set<String> recBooksList = new HashSet(); //FIX
//        for (int i=0;i<isbnList.size();i++) {
//            if (i<upperBound){
//                String isbn = isbnList.get(i);
//                recBooksList.addAll(getRecommendedBooksFromISBN(isbn));
//            }
//        }
//    }



    //GOODREADS section
    public String getGoodreadsId(String isbn) throws NotFoundException {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("www.goodreads.com")
                .setPath("/book/isbn_to_id/"+isbn);
        uriBuilder.addParameter("key", GOODREADS_KEY);
        try {
            URL url = new URL(uriBuilder.toString());
            StringBuffer content = getHTMLContent(url);
            return content.toString();
        } catch (IOException e){
            throw new NotFoundException("isbn not found", null);
        }
    }

    private String getWorkId(String isbn) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, NotFoundException {
        String id = getGoodreadsId(isbn);
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("www.goodreads.com")
                .setPath("/book/id_to_work_id/");
        uriBuilder.addParameter("key", GOODREADS_KEY);
        String urlString = uriBuilder.toString() + "&id="+id;
        URL url = new URL(urlString);
        StringBuffer content = getHTMLContent(url);

        String xml = content.toString();
        Element rootElement = getRootElement(xml);
        return getStringElement("item",rootElement);
    }



//    private List<String> getAllPossibleBookURLs(String query) throws IOException {
//        URIBuilder uriBuilder = new URIBuilder()
//                .setScheme("https")
//                .setHost("www.goodreads.com")
//                .setPath("/search");
//        uriBuilder.addParameter("q", query);
//        URL url = new URL(uriBuilder.toString());
//        String html = getHTMLContent(url).toString();
//        List<Integer> begIndexList = getIndexBegList(html,"<a title=");
//        List<Integer> extraEndIndexList = getIndecesEndList(html, "<img alt=\"");
//        List<Integer> endIndexList = new ArrayList<>();
//
//        for (Integer index:extraEndIndexList) {
//            String substr = html.substring(index+("<img alt=").length());
//            substr = substr.substring(1,("saving".length()+1));
//            if (!substr.equals("saving")){
//                endIndexList.add(index);
//            }
//        }
//        List<String> suggWebLinkList = new ArrayList<>();
//        for (int i=0;i<begIndexList.size();i++){
//            String longHTMLParse = html.substring(begIndexList.get(i), endIndexList.get(i));
//            int index = longHTMLParse.indexOf("href=\"");
//            String suggWebLink = "https://www.goodreads.com" + longHTMLParse.substring(index+("href=\"").length(),longHTMLParse.length()-12);
//            suggWebLinkList.add(suggWebLink);
//        }
//        return suggWebLinkList;
//
//    }



    private String getTitleFromISBNGoodreads(String ISBN, String html) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, NotFoundException {
        int begIndexList = getIndexBegInt(html, "<h1 id=\"bookTitle\" class=\"bookTitle\" itemprop=\"name\">");
        int endIndexList = getFirstIndexAfterBegIndex(html,begIndexList,"<a class=\"greyText\"");
        try {
            return html.substring(begIndexList, endIndexList).trim();
        } catch (Exception e){
            try {
                begIndexList = getIndexBegInt(html, "<title>");
                endIndexList = getFirstIndexAfterBegIndex(html,begIndexList,"</title>");
                return html.substring(begIndexList, endIndexList).trim();
            } catch (Exception f){
                throw new NotFoundException("Title not found.", null);
            }
        }
    }

    public String getAuthorFromISBNGoodreads(String ISBN, String html) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, NotFoundException {
        int begIndex = getIndexBegInt(html, "<span itemprop=\"name\">");
        int endIndex = getFirstIndexAfterBegIndex(html,begIndex,"</span>");
        try {
            return html.substring(begIndex, endIndex);
        } catch (Exception e){
            throw new NotFoundException("Author not found.", null);
        }
    }
    public String getGenreFromISBNGoodreads(String ISBN, String html) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, NotFoundException {
        int begIndex = getIndexBegInt(html, "\"shelf\":[\"");
        int endIndex = getFirstIndexAfterBegIndex(html,begIndex,"\"");
        try {
            return html.substring(begIndex, endIndex);
        } catch (Exception e){
            throw new NotFoundException("Author not found.", null);
        }
    }


    public int getNumPagesFromISBNGoodreads(String ISBN, String html) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, NotFoundException {
        int begIndexList = getIndexBegInt(html, "<span itemprop=\"numberOfPages\">");
        int endIndexList = getFirstIndexAfterBegIndex(html,begIndexList,"</span>");
        try {
            String numPages = html.substring(begIndexList, endIndexList);
            //https://stackoverflow.com/questions/4030928/extract-digits-from-a-string-in-java
            return Integer.parseInt(numPages.replaceAll("\\D+",""));
        } catch (Exception e){
            throw new NotFoundException("Author not found.", null);
        }
    }


    private int getFirstIndexAfterBegIndex(String html, int begIndexList, String toSearch) {
        int i=begIndexList;
        while (i<html.length()) {
            int index = html.indexOf(toSearch, i);
            if (index != -1){
                return index;
            }
            i++;
        }
        return 0;
    }


//    public List<String> getISBNFromTitleAuthorGoodreads(String title, String author) throws IOException {
//        List<String> urlList = getAllPossibleBookURLs(title + " " + author);
//        List<String> isbnList = new ArrayList<>();
//        for (String url:urlList){
//            String isbn = getISBN(url);
//            if (isbn.length()>0){
//                isbnList.add(isbn);
//            }
//        }
//        return isbnList;
//    }



    private String getISBN(String strUrl) throws IOException {
        String html = getHTMLContent(new URL(strUrl)).toString();
        String initialSearch = "<span itemprop='isbn'>";
        String finalSearch = "</span>)</span>";
        int begIndex = getIndexBegInt(html,initialSearch);
        int endIndex = getIndexEndInt(html,finalSearch);
        String longHTMLParse;
        if (begIndex != 0 && endIndex != 0) {
            initialSearch = "<span itemprop='isbn'>";
            begIndex = getIndexBegInt(html, initialSearch);
            longHTMLParse = html.substring(begIndex, endIndex);
        } else {
            initialSearch = "itemprop='isbn'>";
            begIndex = getIndexBegInt(html,initialSearch);
            longHTMLParse = html.substring(begIndex, begIndex+13);
        }
        if (longHTMLParse.contains("<")){
            longHTMLParse = longHTMLParse.substring(0,longHTMLParse.indexOf("<"));
        }
        return longHTMLParse;
    }

//    public String getCoverUrlFromISBN(String ISBN){
//        return "covers.openlibrary.org/b/isbn/"+ISBN+"-M.jpg";
//    }

    //    https://www.googleapis.com/books/v1/volumes?q=intitle:so%20you%20want%20to%20be%20a%20wizard
    //GOOGLEAPIS Section
    public JSONObject getJsonFromQueryGoogle(String title, String author, String ISBN) throws IOException, EmptyQueryException, NotFoundException {
        //takes a title, author, title and author, or isbn
        if (title.isEmpty() && author.isEmpty() && ISBN.isEmpty()){
            throw new EmptyQueryException("No search terms entered");
        }
        title = title.replace(" ","+");
        author = author.replace(" ","+");
        ISBN = ISBN.replace("-","");
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("www.googleapis.com")
                .setPath("/books/v1/volumes");
        if (!ISBN.isEmpty()){
            uriBuilder.addParameter("q", "isbn:"+ISBN);
        } else {
            if (!author.isEmpty() && !title.isEmpty()) {
                uriBuilder.addParameter("q", "intitle:" + title + "+inauthor:"+author);
            } else if (!author.isEmpty()){
                uriBuilder.addParameter("q", "inauthor:" + author);
            } else {
                uriBuilder.addParameter("q", "intitle:" + title);
            }
        }
        String urlString = uriBuilder.toString();
        urlString+="&key="+GOOGLE_KEY;
        System.out.println(urlString);
        URL url = new URL(urlString);
        StringBuffer content = getHTMLContent(url);
        JSONObject jObj = new JSONObject(content.toString());
        String numFound = jObj.get("totalItems").toString();
        if (Integer.parseInt(numFound) == 0){
            throw new NotFoundException("Not found.",jObj);
        }
        return jObj;
    }

    private String getTitleGoogleJson(JSONObject jObj) throws NotFoundException {
        //Based only on ISBN
        int totalItems = jObj.getInt("totalItems");
        for (int i=0;i<totalItems;i++){
            try {
                return jObj.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("title");
            } catch (JSONException e){}
        }
        throw new NotFoundException("Title not found", jObj);
    }

    private String getAuthorGoogleJson(JSONObject jObj) throws NotFoundException {
        //Based only on ISBN
        int totalItems = jObj.getInt("totalItems");
        for (int i=0;i<totalItems;i++){
            try {
                return jObj.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("authors").getString(0);
            } catch (JSONException e){}
        }
        throw new NotFoundException("Author not found",jObj);
    }

    private String getGenreGoogleJson(JSONObject jObj) throws NotFoundException {
        //Based only on ISBN
        int totalItems = jObj.getInt("totalItems");
        for (int i=0;i<totalItems;i++){
            try {
                return jObj.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("categories").getString(0);
            } catch (JSONException e){}
        }
        throw new NotFoundException("Genre not found",jObj);
    }


    private String getPublisherGoogleJson(JSONObject jObj) throws NotFoundException {
        //Based only on ISBN
        int totalItems = jObj.getInt("totalItems");
        for (int i=0;i<totalItems;i++){
            try {
                return jObj.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("publisher");
            } catch (JSONException e){}
        }
        throw new NotFoundException("Publisher not found",jObj);
    }

    private int getNumPagesGoogleJson(JSONObject jObj) throws NotFoundException {
        //Based only on ISBN
        int totalItems = jObj.getInt("totalItems");
        for (int i=0;i<totalItems;i++){
            try {
                return jObj.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getInt("pageCount");
            } catch (JSONException e){}
        }
        throw new NotFoundException("Number of pages not found",jObj);
    }


    private List<String> getISBNListGoogleJson(JSONObject jObj) throws NotFoundException {
        List<String> ISBNList= new ArrayList<>();
        JSONArray allContent = jObj.getJSONArray("items");
        int numISBNs = allContent.length();
        if (numISBNs>MAX_BOOKS){
            numISBNs=MAX_BOOKS;
        }
        for (int i=0;i<numISBNs;i++){
            try {
                ISBNList.add(allContent.getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier"));
            } catch (JSONException e){
                System.out.println(e);
                i+=1;
            }
        }
        return ISBNList;
    }

    private String getISBNGoogleJson(JSONObject jObj) {
        JSONArray allContent = jObj.getJSONArray("items");
        return allContent.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier");
    }


    //General helpers section


    // https://stackoverflow.com/questions/4076910/how-to-retrieve-element-value-of-xml-using-java
    private Element getRootElement(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        return document.getDocumentElement();
    }
    // https://stackoverflow.com/questions/4076910/how-to-retrieve-element-value-of-xml-using-java
    private String getStringElement(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }


    public StringBuffer getHTMLContent(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content;
    }


    private int getIndexBegInt(String html, String toSearch) {
        int i=1;
        while (i<html.length() && i>=1) {
            int index = html.indexOf(toSearch, i);
            if (index != -1){
                return index+toSearch.length();
            }
            i=index+1;
        }
        return 0;
    }
    private int getIndexEndInt(String html, String toSearch) {
        int i=1;
        while (i<html.length() && i>=1) {
            int index = html.indexOf(toSearch, i);
            if (index != -1){
                return index;
            }
            i=index+1;
        }
        return 0;
    }

    private List<Integer> getIndexBegList(String html, String toSearch, int maxSize) {
        List<Integer> indexList = new ArrayList<>();
        int i = 1;
        int bookCounter = 0;
        while (i < html.length() && i >= 1 && bookCounter < maxSize) {
            int index = html.indexOf(toSearch, i);
            if (index != -1) {
                indexList.add(index + toSearch.length());
            }
            i = index + 1;
            bookCounter++;
        }
        return indexList;
    }



    private List<Integer> getIndecesEndList(String html, String toSearch, int maxSize) {
        List<Integer> indexList = new ArrayList<>();
        int i=1;
        int bookCounter = 0;
        while (i<html.length() && i>=1 && bookCounter < maxSize) {
            int index = html.indexOf(toSearch, i);
            if (index != -1){
                indexList.add(index);
            }
            i=index+1;
            bookCounter++;
        }

        return indexList;
    }

    private List<Integer> getIndecesEndList(String html, String toSearch) {
        List<Integer> indexList = new ArrayList<>();
        int i=1;
        while (i<html.length() && i>=1) {
            int index = html.indexOf(toSearch, i);
            if (index != -1){
                indexList.add(index);
            }
            i=index+1;
        }

        return indexList;
    }

    public void printBookInfo(SimpleBook book) {
        System.out.println("Title: " + book.getTitle().getTitle());
        System.out.println("Author: " + book.getAuthor().getAuthorName());
        System.out.println("Genre: " + book.getGenre().getGenre());
        System.out.println("Number of pages: " + Integer.toString(book.getPages()));
        System.out.println("Publisher: " + book.getPublisher().getPublisher() + "\n");
    }
}
