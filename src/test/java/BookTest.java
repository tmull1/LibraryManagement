import org.example.Book;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class BookTest {

    @Test
    public void testBookCreation() {
        Book book = new Book("1984", "George Orwell", 1949, 328, "Dystopian");

        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals(1949, book.getPublicationYear());
        assertEquals(328, book.getPages());
        assertEquals("Dystopian", book.getCategory());
        assertFalse(book.isOnLoan());
        assertNull(book.getLoanDate());
    }

    @Test
    public void testSetOnLoan() {
        Book book = new Book("1984", "George Orwell", 1949, 328, "Dystopian");

        book.setOnLoan(true);
        assertTrue(book.isOnLoan());
        assertNotNull(book.getLoanDate());
        assertEquals(LocalDate.now(), book.getLoanDate());

        book.setOnLoan(false);
        assertFalse(book.isOnLoan());
        assertNull(book.getLoanDate());
    }

    @Test
    public void testSetOnLoanTwice() {
        Book book = new Book("1984", "George Orwell", 1949, 328, "Dystopian");

        // First loan
        book.setOnLoan(true);
        LocalDate firstLoanDate = book.getLoanDate();
        assertTrue(book.isOnLoan());
        assertNotNull(firstLoanDate);

        // Return the book
        book.setOnLoan(false);
        assertFalse(book.isOnLoan());
        assertNull(book.getLoanDate());

        // Loan the book again
        book.setOnLoan(true);
        assertTrue(book.isOnLoan());

        // Check that the book is on loan, and the loan date is not null
        LocalDate secondLoanDate = book.getLoanDate();
        assertNotNull(secondLoanDate);

        // To avoid timing issues, we check that the second loan date is either the same or a different day
        assertTrue(secondLoanDate.isAfter(firstLoanDate) || secondLoanDate.isEqual(firstLoanDate));
    }

    @Test
    public void testSetOnLoanAndReturnBook() {
        Book book = new Book("1984", "George Orwell", 1949, 328, "Dystopian");

        book.setOnLoan(true);
        assertTrue(book.isOnLoan());
        assertNotNull(book.getLoanDate());

        book.setOnLoan(false);
        assertFalse(book.isOnLoan());
        assertNull(book.getLoanDate());
    }
}

