package edu.swarthmore.cs.cs71.shelved.common.junit.tests;

import edu.swarthmore.cs.cs71.shelved.model.bookData.BookInfo;
import edu.swarthmore.cs.cs71.shelved.model.bookData.EmptyQueryException;
import edu.swarthmore.cs.cs71.shelved.model.bookData.NotFoundException;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfoTests {
    @Test
    public void testNonExceptionGetters() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException, NotFoundException, EmptyQueryException {
        BookInfo bookInfo = new BookInfo();
        String isbn1 = "0552547344";
        String isbn2 = "1439171882";
        JSONObject jObj1 = bookInfo.getJsonFromQueryGoogle("","",isbn1);
        JSONObject jObj2 = bookInfo.getJsonFromQueryGoogle("","",isbn2);

        String goodreadsId = bookInfo.getGoodreadsId(isbn1);
        URL url = new URL("https://www.goodreads.com/book/show/" + goodreadsId);
        StringBuffer content = bookInfo.getHTMLContent(url);
        String html1 = content.toString();

        goodreadsId = bookInfo.getGoodreadsId(isbn2);
        url = new URL("https://www.goodreads.com/book/show/" + goodreadsId);
        content = bookInfo.getHTMLContent(url);
        String html2 = content.toString();

        //TESTING TITLE
        String title1 = bookInfo.getTitleFromISBN(jObj1,html1,isbn1);
        String title2 = bookInfo.getTitleFromISBN(jObj2,html2,isbn2);
        Assert.assertEquals("(Un)arranged Marriage", title1);
        Assert.assertEquals("Breakfast with Socrates", title2);

        //TESTING AUTHOR
        String author1 = bookInfo.getAuthorFromISBN(jObj1,html1,isbn1);
        String author2 = bookInfo.getAuthorFromISBN(jObj2,html2,isbn2);
        Assert.assertEquals("Bali Rai", author1);
        Assert.assertEquals("Robert Rowland Smith", author2);

        //TESTING GENRE
        String genre1 = bookInfo.getGenreFromISBN(jObj1,html1,isbn1);
        String genre2 = bookInfo.getGenreFromISBN(jObj2,html2,isbn2);
        Assert.assertEquals("Arranged marriage", genre1);
        Assert.assertEquals("Philosophy", genre2);

        //TESTING PUBLISHER
        String publisher1 = bookInfo.getPublisherFromISBN(jObj1,html1,isbn1);
        String publisher2 = bookInfo.getPublisherFromISBN(jObj2,html2,isbn2);
        Assert.assertEquals("Transworld Publishers", publisher1);
        Assert.assertEquals("Simon and Schuster", publisher2);

        //TESTING PAGES
        int pages1 = bookInfo.getNumPagesFromISBN(jObj1,html1,isbn1);
        int pages2 = bookInfo.getNumPagesFromISBN(jObj2,html2,isbn2);
        Assert.assertEquals(271, pages1);
        Assert.assertEquals(256, pages2);
    }

    @Test
    public void testGetISBNsFromTitleAndOrAuthorNoException() throws ParserConfigurationException, IOException, XPathExpressionException, NotFoundException, SAXException, EmptyQueryException {
        BookInfo bookInfo = new BookInfo();
        List<String> listOfISBNs = bookInfo.getISBNListFromTitleAndOrAuthor("So you want to be a wizard", "Diane Duane");
        List<String> trueListOfISBNs = new ArrayList<>();
        trueListOfISBNs.add("0547545118");
        trueListOfISBNs.add("9780152162504");
        Assert.assertEquals(trueListOfISBNs, listOfISBNs);
    }

    @Test(expected = NotFoundException.class)
    public void testGetISBNsFromTitleAndOrAuthorException() throws ParserConfigurationException, IOException, XPathExpressionException, NotFoundException, SAXException, EmptyQueryException {
        BookInfo bookInfo = new BookInfo();
        bookInfo.getISBNListFromTitleAndOrAuthor("So you want to be a wizard", "Rowling");
    }

    @Test(expected = NotFoundException.class)
    public void testExceptionCover() throws EmptyQueryException, IOException, NotFoundException {
        BookInfo bookInfo = new BookInfo();
        JSONObject jObj = bookInfo.getJsonFromQueryGoogle("","","143sdsaf9171882");
        bookInfo.getUrlBookCoverFromISBN(jObj,"143sdsaf9171882");
    }

    @Test(expected = NotFoundException.class)
    public void getTitleFromISBNTest() throws IOException, EmptyQueryException, NotFoundException {
        BookInfo bookInfo = new BookInfo();
        JSONObject jObj = bookInfo.getJsonFromQueryGoogle("", "", "05450s10225");
        jObj.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo").getString("title");
    }

    @Test
    public void testJsonFromQueryGoogleBooks() throws IOException, EmptyQueryException, NotFoundException {
        BookInfo bookInfo = new BookInfo();
        JSONObject json2 = bookInfo.getJsonFromQueryGoogle("So you want to be a wizard", "Diane Duane", "");
        Assert.assertEquals("{\"totalItems\":2,\"kind\":\"books#volumes\",\"items\":[{\"saleInfo\":{\"offers\":[{\"finskyOfferType\":1,\"retailPrice\":{\"amountInMicros\":3990000,\"currencyCode\":\"USD\"},\"listPrice\":{\"amountInMicros\":3990000,\"currencyCode\":\"USD\"},\"giftable\":true}],\"country\":\"US\",\"isEbook\":true,\"saleability\":\"FOR_SALE\",\"buyLink\":\"https://play.google.com/store/books/details?id=dfXYy0rj0S0C&rdid=book-dfXYy0rj0S0C&rdot=1&source=gbs_api\",\"retailPrice\":{\"amount\":3.99,\"currencyCode\":\"USD\"},\"listPrice\":{\"amount\":3.99,\"currencyCode\":\"USD\"}},\"searchInfo\":{\"textSnippet\":\"But this one said, So You Want to Be a Wizard. I don&#39;t belive this, Nina thought. She shut the book and stood there holding it in her hand, confused, amazed, suspicious\\u2014and delighted. If it was a joke, it was a great one. If it wasn&#39;t .\"},\"kind\":\"books#volume\",\"volumeInfo\":{\"industryIdentifiers\":[{\"identifier\":\"0547545118\",\"type\":\"ISBN_10\"},{\"identifier\":\"9780547545110\",\"type\":\"ISBN_13\"}],\"pageCount\":400,\"printType\":\"BOOK\",\"readingModes\":{\"image\":true,\"text\":true},\"previewLink\":\"http://books.google.com/books?id=dfXYy0rj0S0C&printsec=frontcover&dq=intitle:So%2Byou%2Bwant%2Bto%2Bbe%2Ba%2Bwizard%2Binauthor:Diane%2BDuane&hl=&cd=1&source=gbs_api\",\"canonicalVolumeLink\":\"https://market.android.com/details?id=book-dfXYy0rj0S0C\",\"description\":\"Something stopped Nita's hand as it ran along the bookshelf. She looked and found that one of the books had a loose thread at the top of its spine. It was one of those So You Want to Be a . . . books, a series on careers. So You Want to Be a Pilot, and a Scientist . . . a Writer. But this one said, So You Want to Be a Wizard. I don't belive this, Nina thought. She shut the book and stood there holding it in her hand, confused, amazed, suspicious\\u2014and delighted. If it was a joke, it was a great one. If it wasn't . . . ?\",\"language\":\"en\",\"title\":\"So You Want to Be a Wizard\",\"imageLinks\":{\"thumbnail\":\"http://books.google.com/books/content?id=dfXYy0rj0S0C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=dfXYy0rj0S0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"},\"averageRating\":4.5,\"panelizationSummary\":{\"containsImageBubbles\":false,\"containsEpubBubbles\":false},\"publisher\":\"Houghton Mifflin Harcourt\",\"ratingsCount\":11,\"publishedDate\":\"2003-10-01\",\"categories\":[\"Juvenile Fiction\"],\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":true,\"contentVersion\":\"0.11.8.0.preview.3\",\"authors\":[\"Diane Duane\"],\"infoLink\":\"https://play.google.com/store/books/details?id=dfXYy0rj0S0C&source=gbs_api\"},\"etag\":\"QkVIstK1oiM\",\"id\":\"dfXYy0rj0S0C\",\"accessInfo\":{\"accessViewStatus\":\"SAMPLE\",\"country\":\"US\",\"viewability\":\"PARTIAL\",\"pdf\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/So_You_Want_to_Be_a_Wizard-sample-pdf.acsm?id=dfXYy0rj0S0C&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"webReaderLink\":\"http://play.google.com/books/reader?id=dfXYy0rj0S0C&hl=&printsec=frontcover&source=gbs_api\",\"epub\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/So_You_Want_to_Be_a_Wizard-sample-epub.acsm?id=dfXYy0rj0S0C&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"publicDomain\":false,\"quoteSharingAllowed\":false,\"embeddable\":true,\"textToSpeechPermission\":\"ALLOWED\"},\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/dfXYy0rj0S0C\"},{\"saleInfo\":{\"country\":\"US\",\"isEbook\":false,\"saleability\":\"NOT_FOR_SALE\"},\"searchInfo\":{\"textSnippet\":\"During a summer vacation at the beach, thirteen-year-old wizard Nita and her friend Kit assist the whale-wizard S&#39;reee in combating an evil power.\"},\"kind\":\"books#volume\",\"volumeInfo\":{\"industryIdentifiers\":[{\"identifier\":\"9780152162504\",\"type\":\"ISBN_13\"},{\"identifier\":\"015216250X\",\"type\":\"ISBN_10\"}],\"pageCount\":371,\"printType\":\"BOOK\",\"readingModes\":{\"image\":false,\"text\":true},\"previewLink\":\"http://books.google.com/books?id=DHE1ngEACAAJ&dq=intitle:So%2Byou%2Bwant%2Bto%2Bbe%2Ba%2Bwizard%2Binauthor:Diane%2BDuane&hl=&cd=2&source=gbs_api\",\"canonicalVolumeLink\":\"https://books.google.com/books/about/Deep_Wizardry.html?hl=&id=DHE1ngEACAAJ\",\"description\":\"During a summer vacation at the beach, thirteen-year-old wizard Nita and her friend Kit assist the whale-wizard S'reee in combating an evil power.\",\"language\":\"en\",\"title\":\"Deep Wizardry\",\"imageLinks\":{\"thumbnail\":\"http://books.google.com/books/content?id=DHE1ngEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=DHE1ngEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"},\"averageRating\":3,\"publisher\":\"Houghton Mifflin Harcourt\",\"ratingsCount\":2,\"publishedDate\":\"1985\",\"categories\":[\"Fantasy\"],\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":true,\"contentVersion\":\"0.6.5.0.preview.2\",\"infoLink\":\"http://books.google.com/books?id=DHE1ngEACAAJ&dq=intitle:So%2Byou%2Bwant%2Bto%2Bbe%2Ba%2Bwizard%2Binauthor:Diane%2BDuane&hl=&source=gbs_api\"},\"etag\":\"FNJB6nJDw0Y\",\"id\":\"DHE1ngEACAAJ\",\"accessInfo\":{\"accessViewStatus\":\"NONE\",\"country\":\"US\",\"viewability\":\"NO_PAGES\",\"pdf\":{\"isAvailable\":true},\"webReaderLink\":\"http://play.google.com/books/reader?id=DHE1ngEACAAJ&hl=&printsec=frontcover&source=gbs_api\",\"epub\":{\"isAvailable\":true},\"publicDomain\":false,\"quoteSharingAllowed\":false,\"embeddable\":false,\"textToSpeechPermission\":\"ALLOWED\"},\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/DHE1ngEACAAJ\"}]}", json2.toString());
        JSONObject json3 = bookInfo.getJsonFromQueryGoogle("", "", "9780152162504");
        Assert.assertEquals("{\"totalItems\":3,\"kind\":\"books#volumes\",\"items\":[{\"saleInfo\":{\"country\":\"US\",\"isEbook\":false,\"saleability\":\"NOT_FOR_SALE\"},\"searchInfo\":{\"textSnippet\":\"During a summer vacation at the beach, thirteen-year-old wizard Nita and her friend Kit assist the whale-wizard S&#39;reee in combating an evil power.\"},\"kind\":\"books#volume\",\"volumeInfo\":{\"industryIdentifiers\":[{\"identifier\":\"9780152162504\",\"type\":\"ISBN_13\"},{\"identifier\":\"015216250X\",\"type\":\"ISBN_10\"}],\"pageCount\":371,\"printType\":\"BOOK\",\"readingModes\":{\"image\":false,\"text\":true},\"previewLink\":\"http://books.google.com/books?id=DHE1ngEACAAJ&dq=isbn:9780152162504&hl=&cd=1&source=gbs_api\",\"canonicalVolumeLink\":\"https://books.google.com/books/about/Deep_Wizardry.html?hl=&id=DHE1ngEACAAJ\",\"description\":\"During a summer vacation at the beach, thirteen-year-old wizard Nita and her friend Kit assist the whale-wizard S'reee in combating an evil power.\",\"language\":\"en\",\"title\":\"Deep Wizardry\",\"imageLinks\":{\"thumbnail\":\"http://books.google.com/books/content?id=DHE1ngEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=DHE1ngEACAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api\"},\"averageRating\":3,\"publisher\":\"Houghton Mifflin Harcourt\",\"ratingsCount\":2,\"publishedDate\":\"1985\",\"categories\":[\"Fantasy\"],\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":true,\"contentVersion\":\"0.6.5.0.preview.2\",\"infoLink\":\"http://books.google.com/books?id=DHE1ngEACAAJ&dq=isbn:9780152162504&hl=&source=gbs_api\"},\"etag\":\"3W7NjSOpN0s\",\"id\":\"DHE1ngEACAAJ\",\"accessInfo\":{\"accessViewStatus\":\"NONE\",\"country\":\"US\",\"viewability\":\"NO_PAGES\",\"pdf\":{\"isAvailable\":true},\"webReaderLink\":\"http://play.google.com/books/reader?id=DHE1ngEACAAJ&hl=&printsec=frontcover&source=gbs_api\",\"epub\":{\"isAvailable\":true},\"publicDomain\":false,\"quoteSharingAllowed\":false,\"embeddable\":false,\"textToSpeechPermission\":\"ALLOWED\"},\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/DHE1ngEACAAJ\"},{\"saleInfo\":{\"country\":\"US\",\"isEbook\":false,\"saleability\":\"NOT_FOR_SALE\"},\"searchInfo\":{\"textSnippet\":\"During a summer vacation at the beach, thirteen-year-old wizard Nita and her friend Kit assist the whale-wizard S&#39;reee in combating an evil power.\"},\"kind\":\"books#volume\",\"volumeInfo\":{\"industryIdentifiers\":[{\"identifier\":\"9780152162504\",\"type\":\"ISBN_13\"},{\"identifier\":\"015216250X\",\"type\":\"ISBN_10\"}],\"pageCount\":371,\"printType\":\"BOOK\",\"readingModes\":{\"image\":true,\"text\":true},\"previewLink\":\"http://books.google.com/books?id=cEI3wr0fvs0C&printsec=frontcover&dq=isbn:9780152162504&hl=&cd=2&source=gbs_api\",\"canonicalVolumeLink\":\"https://books.google.com/books/about/Deep_Wizardry.html?hl=&id=cEI3wr0fvs0C\",\"description\":\"During a summer vacation at the beach, thirteen-year-old wizard Nita and her friend Kit assist the whale-wizard S'reee in combating an evil power.\",\"language\":\"en\",\"title\":\"Deep Wizardry\",\"imageLinks\":{\"thumbnail\":\"http://books.google.com/books/content?id=cEI3wr0fvs0C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=cEI3wr0fvs0C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\"},\"averageRating\":3,\"publisher\":\"Houghton Mifflin Harcourt\",\"ratingsCount\":2,\"publishedDate\":\"1985\",\"categories\":[\"Fantasy\"],\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":true,\"contentVersion\":\"0.6.5.0.preview.3\",\"infoLink\":\"http://books.google.com/books?id=cEI3wr0fvs0C&dq=isbn:9780152162504&hl=&source=gbs_api\"},\"etag\":\"xsB8OCyL68U\",\"id\":\"cEI3wr0fvs0C\",\"accessInfo\":{\"accessViewStatus\":\"SAMPLE\",\"country\":\"US\",\"viewability\":\"PARTIAL\",\"pdf\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/Deep_Wizardry-sample-pdf.acsm?id=cEI3wr0fvs0C&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"webReaderLink\":\"http://play.google.com/books/reader?id=cEI3wr0fvs0C&hl=&printsec=frontcover&source=gbs_api\",\"epub\":{\"isAvailable\":true,\"acsTokenLink\":\"http://books.google.com/books/download/Deep_Wizardry-sample-epub.acsm?id=cEI3wr0fvs0C&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"publicDomain\":false,\"quoteSharingAllowed\":false,\"embeddable\":true,\"textToSpeechPermission\":\"ALLOWED\"},\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/cEI3wr0fvs0C\"},{\"saleInfo\":{\"country\":\"US\",\"isEbook\":false,\"saleability\":\"NOT_FOR_SALE\"},\"kind\":\"books#volume\",\"volumeInfo\":{\"industryIdentifiers\":[{\"identifier\":\"015216250X\",\"type\":\"ISBN_10\"},{\"identifier\":\"9780152162504\",\"type\":\"ISBN_13\"}],\"pageCount\":385,\"printType\":\"BOOK\",\"readingModes\":{\"image\":false,\"text\":false},\"previewLink\":\"http://books.google.com/books?id=sbgn_U3h77AC&pg=PP1&dq=isbn:9780152162504&hl=&cd=3&source=gbs_api\",\"canonicalVolumeLink\":\"https://books.google.com/books/about/So_You_Want_to_be_a_Wizard.html?hl=&id=sbgn_U3h77AC\",\"description\":\"Thirteen-year-old Nita, tormented by a gang of bullies because she won't fight back, finds the help she needs in a library book on wizardry which guides her into another dimension.\",\"language\":\"en\",\"title\":\"So You Want to be a Wizard\",\"imageLinks\":{\"thumbnail\":\"http://books.google.com/books/content?id=sbgn_U3h77AC&printsec=frontcover&img=1&zoom=1&source=gbs_api\",\"smallThumbnail\":\"http://books.google.com/books/content?id=sbgn_U3h77AC&printsec=frontcover&img=1&zoom=5&source=gbs_api\"},\"averageRating\":3.5,\"panelizationSummary\":{\"containsImageBubbles\":false,\"containsEpubBubbles\":false},\"publisher\":\"Graphia\",\"ratingsCount\":41,\"publishedDate\":\"2001-06\",\"categories\":[\"Juvenile Fiction\"],\"maturityRating\":\"NOT_MATURE\",\"allowAnonLogging\":false,\"contentVersion\":\"0.3.2.0.preview.0\",\"authors\":[\"Diane Duane\"],\"infoLink\":\"http://books.google.com/books?id=sbgn_U3h77AC&dq=isbn:9780152162504&hl=&source=gbs_api\"},\"etag\":\"l80S7LwjPoQ\",\"id\":\"sbgn_U3h77AC\",\"accessInfo\":{\"accessViewStatus\":\"NONE\",\"country\":\"US\",\"viewability\":\"NO_PAGES\",\"pdf\":{\"isAvailable\":false},\"webReaderLink\":\"http://play.google.com/books/reader?id=sbgn_U3h77AC&hl=&printsec=frontcover&source=gbs_api\",\"epub\":{\"isAvailable\":false},\"publicDomain\":false,\"quoteSharingAllowed\":false,\"embeddable\":false,\"textToSpeechPermission\":\"ALLOWED\"},\"selfLink\":\"https://www.googleapis.com/books/v1/volumes/sbgn_U3h77AC\"}]}", json3.toString());
    }

    @Test(expected = EmptyQueryException.class)
    public void testJsonFromQueryGoogleBooksException() throws IOException, EmptyQueryException, NotFoundException {
        BookInfo bookInfo = new BookInfo();
        bookInfo.getJsonFromQueryGoogle("", "", "");
    }

    @Test
    public void testURL() throws EmptyQueryException, IOException, NotFoundException {
        BookInfo bookInfo = new BookInfo();
        JSONObject jObj = bookInfo.getJsonFromQueryGoogle("", "","1439171882");
        String url = bookInfo.getUrlBookCoverFromISBN(jObj, "1439171882");
        Assert.assertEquals("http://books.google.com/books/content?id=Gpnk-K_yBj4C&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", url);
    }

    @Test
    public void testScrapeRecs() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException, NotFoundException, EmptyQueryException {
        BookInfo bookInfo = new BookInfo();
        List<SimpleBook> listOfRecs = bookInfo.getRecommendedBooksFromISBN("0152047387");
        for (SimpleBook book:listOfRecs){
            bookInfo.printBookInfo(book);
        }
    }

    public void compareBooks(SimpleBook one, SimpleBook two){
        Assert.assertEquals(one.getTitle().getTitle(), two.getTitle().getTitle());
        Assert.assertEquals(one.getAuthor().getAuthorName(), two.getAuthor().getAuthorName());
        Assert.assertEquals(one.getGenre().getGenre(), two.getGenre().getGenre());
        Assert.assertEquals(one.getPublisher().getPublisher(), two.getPublisher().getPublisher());
        Assert.assertEquals(one.getPages(), two.getPages());
    }

    @Test
    public void testGoodreadsFieldsFromISBN() throws IOException, NotFoundException, XPathExpressionException, ParserConfigurationException, SAXException, EmptyQueryException {
        BookInfo bookInfo = new BookInfo();
        String isbn = "0786838655";
        SimpleBook percyJackson = bookInfo.populateSimpleBookFromISBN(isbn);
        bookInfo.printBookInfo(percyJackson);
    }

    @Test
    public void testPopulateSimpleBook() throws NotFoundException, ParserConfigurationException, IOException, XPathExpressionException, SAXException, EmptyQueryException {
        BookInfo bookInfo = new BookInfo();
        SimpleBook simpleBook = bookInfo.populateSimpleBookFromISBN("1888983302");
        Assert.assertEquals("The Mystery Shopper's Manual", simpleBook.getTitle().getTitle());
        Assert.assertEquals("Cathy Stucker", simpleBook.getAuthor().getAuthorName());
        Assert.assertEquals("Customer services", simpleBook.getGenre().getGenre());
        Assert.assertEquals("Special Interests Publishing", simpleBook.getPublisher().getPublisher());
        Assert.assertEquals(256, simpleBook.getPages());

        SimpleBook simpleBook2 = bookInfo.populateSimpleBookFromISBN("9780590353427");
        Assert.assertEquals("Harry Potter and the Sorcerer's Stone", simpleBook2.getTitle().getTitle());
        Assert.assertEquals("J. K. Rowling", simpleBook2.getAuthor().getAuthorName());
        Assert.assertEquals("Juvenile Fiction", simpleBook2.getGenre().getGenre());
        Assert.assertEquals("Scholastic Paperbacks", simpleBook2.getPublisher().getPublisher());
        Assert.assertEquals(309, simpleBook2.getPages());
    }

    @Test
    public void testPopulateSimpleBookListFromTitleAndOrAuthor() throws SAXException, ParserConfigurationException, XPathExpressionException, EmptyQueryException, IOException, NotFoundException {
        BookInfo bookInfo = new BookInfo();
        List<SimpleBook> simpleBookList = bookInfo.populateSimpleBookListFromTitleAndOrAuthor("The Mystery Shopper's Manual","Cathy Stucker");
        for (SimpleBook book:simpleBookList){
            bookInfo.printBookInfo(book);
        }
    }

}
