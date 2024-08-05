package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String libraryCardNumber;
    private List<Book> booksOnLoan;
    private static final double LATE_FEE_PER_DAY = 0.5;

    public User(String name, String libraryCardNumber) {
        this.name = name;
        this.libraryCardNumber = libraryCardNumber;
        this.booksOnLoan = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public List<Book> getBooksOnLoan() {
        return booksOnLoan;
    }

    public void loanBook(Book book) {
        if (!book.isOnLoan()) {
            book.setOnLoan(true);
            booksOnLoan.add(book);
        }
    }

    public void returnBook(Book book) {
        if (book.isOnLoan()) {
            book.setOnLoan(false);
            booksOnLoan.remove(book);
        }
    }

    public double calculateLateFees() {
        return booksOnLoan.stream()
                .filter(book -> book.getLoanDate().isBefore(LocalDate.now().minusWeeks(2)))
                .mapToDouble(book -> {
                    long daysLate = LocalDate.now().toEpochDay() - book.getLoanDate().toEpochDay() - 14;
                    return daysLate * LATE_FEE_PER_DAY;
                })
                .sum();
    }
}
