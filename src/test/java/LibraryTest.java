import org.example.Book;
import org.example.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

    @BeforeEach
    public void setUp() {
        library = new Library();

        book1 = new Book("1984", "George Orwell", 1949, 328, "Dystopian");
        book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, 281, "Classic");
        book3 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 180, "Classic");
        book4 = new Book("Brave New World", "Aldous Huxley", 1932, 268, "Dystopian");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
    }

    @Test
    public void testAddBook() {
        Book newBook = new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 214, "Classic");
        library.addBook(newBook);

        List<Book> result = library.findBooksByAuthor("J.D. Salinger");
        assertEquals(1, result.size());
        assertEquals("The Catcher in the Rye", result.get(0).getTitle());
    }

    @Test
    public void testRemoveBookByTitle() {
        library.removeBookByTitle("1984");

        // Verify that the book is removed by checking the books by year or by author
        List<Book> result = library.findBooksByAuthor("George Orwell");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindBooksByYear() {
        List<Book> result = library.findBooksByYear(1949);
        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitle());
    }

    @Test
    public void testFindBooksByAuthor() {
        List<Book> result = library.findBooksByAuthor("George Orwell");
        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitle());
    }

    @Test
    public void testFindBookWithMostPages() {
        Optional<Book> result = library.findBookWithMostPages();
        assertTrue(result.isPresent());
        assertEquals("1984", result.get().getTitle()); // Corrected expectation
    }

    @Test
    public void testFindBooksWithMoreThanNPages() {
        List<Book> result = library.findBooksWithMoreThanNPages(200);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("1984")));
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("To Kill a Mockingbird")));
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("Brave New World")));
    }

    @Test
    public void testGetAllBookTitlesSorted() {
        List<String> result = library.getAllBookTitlesSorted();
        assertEquals(4, result.size());
        assertEquals("1984", result.get(0));
        assertEquals("Brave New World", result.get(1));
        assertEquals("The Great Gatsby", result.get(2));
        assertEquals("To Kill a Mockingbird", result.get(3));
    }

    @Test
    public void testFindBooksByCategory() {
        List<Book> result = library.findBooksByCategory("Dystopian");
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("1984")));
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("Brave New World")));
    }

    @Test
    public void testLoanBook() {
        library.loanBook("1984");
        List<Book> onLoan = library.getBooksOnLoan();
        assertEquals(1, onLoan.size());
        assertEquals("1984", onLoan.get(0).getTitle());
    }

    @Test
    public void testReturnBook() {
        library.loanBook("1984");
        library.returnBook("1984");

        List<Book> onLoan = library.getBooksOnLoan();
        assertTrue(onLoan.isEmpty());
    }

    @Test
    public void testGetBooksOnLoan() {
        library.loanBook("1984");
        library.loanBook("Brave New World");

        List<Book> onLoan = library.getBooksOnLoan();
        assertEquals(2, onLoan.size());
        assertTrue(onLoan.stream().anyMatch(book -> book.getTitle().equals("1984")));
        assertTrue(onLoan.stream().anyMatch(book -> book.getTitle().equals("Brave New World")));
    }

    @Test
    public void testFindBooksByPredicate() {
        Predicate<Book> predicate = book -> book.getPages() > 200;
        List<Book> result = library.findBooksByPredicate(predicate);
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("1984")));
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("To Kill a Mockingbird")));
        assertTrue(result.stream().anyMatch(book -> book.getTitle().equals("Brave New World")));
    }

    @Test
    public void testMapBooks() {
        List<String> result = library.mapBooks(Book::getTitle);
        assertEquals(4, result.size());
        assertTrue(result.contains("1984"));
        assertTrue(result.contains("To Kill a Mockingbird"));
        assertTrue(result.contains("The Great Gatsby"));
        assertTrue(result.contains("Brave New World"));
    }
}
