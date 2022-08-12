package au.edu.rmit.ct;

import com.wmw.examples.mockito.library.Book;
import com.wmw.examples.mockito.library.LibraryRecord;
import com.wmw.examples.mockito.library.LibraryRecordDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.MAX_VALUE;

public class RMITLibraryRecordsDAO implements LibraryRecordDAO {

    List<LibraryRecord> myLibraryRecords = new ArrayList<>();
    int recordLimit = Integer.MAX_VALUE;

    @Override
    public List<LibraryRecord> findByBook(Book book) {
        if (book.getClass() != RMITLibraryItem.class){
            throw new IllegalArgumentException("Book class usage suspended. Expecting RMITLibraryItem.class");
        }
        List<LibraryRecord> results = new ArrayList<>();
        for(  LibraryRecord rec : myLibraryRecords){
            if(Objects.equals(rec.getBook(), book))
                results.add(rec);
        }
        return results;
    }

    @Override
    public boolean save(LibraryRecord record) {
        if(myLibraryRecords.size() <= recordLimit){
            if(myLibraryRecords.add(record))
                return true;
            else return false;
        }
        else return false;
    }

    // These are just assortment of work-in-progress methods that dont need to be tested but feel free to use in your testing

    protected void setRecordLimit(int newLimit){
        recordLimit = newLimit;
    }

    protected int getLibraryRecordsSize() {
        return myLibraryRecords.size();
    }

    protected List<LibraryRecord> replaceLibraryRecord( List<LibraryRecord> seedRecords) {
        if(seedRecords.isEmpty()) {
                throw new RuntimeException("Trying to replace database with empty source");
        }
        myLibraryRecords = seedRecords;
        return new ArrayList<>(seedRecords);

    }
    protected List<LibraryRecord> appendLibraryRecord( List<LibraryRecord> seedRecords) {
        if(seedRecords.isEmpty()) {
            throw new RuntimeException("Trying to append database with empty source");
        }
        myLibraryRecords.addAll(seedRecords);
        return myLibraryRecords;
    }
    protected List<LibraryRecord> export( ) {
        if(myLibraryRecords.isEmpty()) {
            throw new RuntimeException("Trying to export empty database");
        }
        return new ArrayList<>(myLibraryRecords);
    }

    protected void printLRList(){
        for (LibraryRecord lr : myLibraryRecords) {
            System.out.println(lr.getBook().toString());
        }
    }
}
