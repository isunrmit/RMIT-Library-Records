package au.edu.rmit.ct;

import com.wmw.examples.mockito.library.Book;

import java.util.Date;
import java.util.List;

public class RMITLibraryItem extends Book {

    protected RMITLibraryItem( Long theId, String theISBN, String theItemName, List<String> theAuthors, String thePublisher,
                               Date thePublicationDate){
        setId(theId);
        setISBN(theISBN);
        setName(theItemName); // title of the publication or item
        setAuthors(theAuthors);
        setPublisher(thePublisher);
        setPublicationDate(thePublicationDate);
    }

    // Removes instantiation of object with all null fields
    private RMITLibraryItem(){
    }

    // Quick constructor for testing
    protected RMITLibraryItem(Long theID, String theISBN){
        setId(theID);
        setISBN(theISBN);
    }

}
