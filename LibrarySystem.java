import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean available;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    public Book(Scanner scanner) {
        System.out.println("Enter book title: ");
        this.title = scanner.nextLine();

        System.out.println("Enter author name: ");
        this.author = scanner.nextLine();

        System.out.println("Enter ISBN: ");
        this.isbn = scanner.nextLine();

        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        // return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Available: " + available;
        return String.format("%-40s%-30s%-30s", title, author, isbn);
        
    }
}

class Library {
    private List<Book> fictionBooks;
    private List<Book> nonFictionBooks;

    public Library() {
        this.fictionBooks = new ArrayList<>();
        this.nonFictionBooks = new ArrayList<>();
    }

    public void addBook(Book book, boolean isFiction) {
        if (isFiction) {
            fictionBooks.add(book);
        } else {
            nonFictionBooks.add(book);
        }
    }

    public void displayAvailableBooks() {
        System.out.println("Available Books:");

        System.out.println("\nFiction Books:");
        System.out.println("-".repeat(100) + "\n");
        System.out.println(String.format("%-40s%-30s%-30s", "Title", "Author", "ISBN"));
        System.out.println("-".repeat(100));
        displayBooksByCategory(fictionBooks);

        System.out.println("\nNonfiction Books:");
        System.out.println("-".repeat(100) + "\n");
        System.out.println(String.format("%-40s%-30s%-30s", "Title", "Author", "ISBN"));
        System.out.println("-".repeat(100));
        displayBooksByCategory(nonFictionBooks);
    }

    private void displayBooksByCategory(List<Book> bookList) {
        for (Book book : bookList) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }

    public Book borrowBook(String title, boolean isFiction) {
        List<Book> bookList = isFiction ? fictionBooks : nonFictionBooks;

        for (Book book : bookList) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                book.setAvailable(false);
                return book;
            }
        }
        return null;
    }

    public void returnBook(Book book) {
        book.setAvailable(true);
    }
}

class User {
    private static int userCount = 0;
    private int userId;
    private List<Book> borrowedBooks;

    public User() {
        this.userId = ++userCount;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Adding predefined books
        addPredefinedBooks(library);

        User user = new User();

        while (true) {
            displayMenu();

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    library.displayAvailableBooks();
                    break;
                case 2:
                    borrowBook(scanner, library, user);
                    break;
                case 3:
                    returnBook(scanner, library, user);
                    break;
                case 4:
                    addNewBook(scanner, library);
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void addPredefinedBooks(Library library) {
        // Add predefined fiction books
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "FB001"), true);
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "FB002"), true);
        library.addBook(new Book("One Hundred Years of Solitude", "Gabriel Garcia Marquez", "FB003"), true);
        library.addBook(new Book("A Passage to India", "E.M. Forster", "FB004"), true);
        // Add predefined non-fiction books
        library.addBook(new Book("A Brief History of Humankind", "Yuval Noah Harari", "NF012"), false);
        library.addBook(new Book("The Immortal Life of Henrietta Lacks", "Rebecca Skloot", "NF013"), false);
        library.addBook(new Book("An Unnatural History", "Elizabeth Kolbert", "NF014"), false);
        library.addBook(new Book("The Power of Habit", "Charles Duhigg", "NF015"), false);
        library.addBook(new Book("The Power of Introverts", "Susan Cain", "NF016"), false);
        library.addBook(new Book("Becoming", "Michelle Obama", "NF017"), false);
        // Add more predefined books...
    }

    private static void displayMenu() {
        System.out.println("=".repeat(60));
        System.out.println("Welcome to the Library Management System!");
        System.out.println("=".repeat(60));
        System.out.println("\n1. Display Available Books");
        System.out.println("2. Borrow a Book");
        System.out.println("3. Return a Book");
        System.out.println("4. Add a New Book");
        System.out.println("5. Exit");
    }

    private static void borrowBook(Scanner scanner, Library library, User user) {
        System.out.print("Enter the book title to borrow: ");
        String titleToBorrow = scanner.nextLine();        
        System.out.print("Is the book fiction? (true/false): ");
        boolean isFictionToBorrow = scanner.nextBoolean();   
        Book borrowedBook = library.borrowBook(titleToBorrow, isFictionToBorrow);

        if (borrowedBook != null) {
            user.borrowBook(borrowedBook);
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book not available or not found.");
        }
    }

    private static void returnBook(Scanner scanner, Library library, User user) {
        System.out.print("Enter Title of the book to return: ");
        String titleToReturn = scanner.nextLine();
        Book bookToReturn = null;

        for (Book borrowed : user.getBorrowedBooks()) {
            if (borrowed.getTitle().equalsIgnoreCase(titleToReturn)) {
                bookToReturn = borrowed;
                break;
            }
        }

        if (bookToReturn != null) {
            library.returnBook(bookToReturn);
            user.returnBook(bookToReturn);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not found in your borrowed list.");
        }
    }

    private static void addNewBook(Scanner scanner, Library library) {
        System.out.print("Is the book fiction? (true/false): ");
        boolean isFictionToAdd = scanner.nextBoolean();
        Book newBook = new Book(scanner);
        library.addBook(newBook, isFictionToAdd);
        System.out.println("New book added to the library.");
    }
}
