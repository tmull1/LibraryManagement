package org.example;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();


        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 218, "Fiction"));
        library.addBook(new Book("A Brief History of Time", "Stephen Hawking", 1988, 212, "Science"));
        library.addBook(new Book("The Hobbit", "J.R.R. Tolkien", 1937, 310, "Fiction"));


        Predicate<Book> isFiction = book -> book.getCategory().equalsIgnoreCase("Fiction");
        List<Book> fictionBooks = library.findBooksByPredicate(isFiction);
        fictionBooks.forEach(book -> System.out.println(book.getTitle()));


        Function<Book, String> bookTitleMapper = Book::getTitle;
        List<String> bookTitles = library.mapBooks(bookTitleMapper);
        bookTitles.forEach(System.out::println);


        library.loanBook("The Hobbit");
        System.out.println("Books on loan: " + library.getBooksOnLoan().size());


        library.returnBook("The Hobbit");
        System.out.println("Books on loan after return: " + library.getBooksOnLoan().size());


        User user = new User("John Doe", "12345");
        Book book = library.findBooksByPredicate(b -> b.getTitle().equals("The Great Gatsby")).get(0);
        user.loanBook(book);
        System.out.println("User's books on loan: " + user.getBooksOnLoan().size());


        book.setOnLoan(true);
        book.setOnLoan(true);
        System.out.println("Late fees: " + user.calculateLateFees());
    }
}
