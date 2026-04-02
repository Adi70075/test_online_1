import java.util.*;


class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean available;

    // Constructor
    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    // Getters & Setters
    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Display
    public void display() {
        System.out.println(bookId + " | " + title + " | " + author + " | " + (available ? "Available" : "Issued"));
    }
}


class Member {
    protected int memberId;
    protected String name;

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public int getMemberId() { return memberId; }
    public String getName() { return name; }
}


class StudentMember extends Member {
    public StudentMember(int memberId, String name) {
        super(memberId, name);
    }
}


class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private HashMap<Integer, List<Book>> issuedBooks = new HashMap<>();

    // Add Book
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully.");
    }

    // Display Books
    public void displayBooks() {
        for (Book b : books) {
            b.display();
        }
    }

    // Issue Book
    public void issueBook(int memberId, int bookId) {
        for (Book b : books) {
            if (b.getBookId() == bookId && b.isAvailable()) {
                b.setAvailable(false);

                issuedBooks.putIfAbsent(memberId, new ArrayList<>());
                issuedBooks.get(memberId).add(b);

                System.out.println("Book issued successfully.");
                return;
            }
        }
        System.out.println("Book not available.");
    }

    // Return Book
    public void returnBook(int memberId, int bookId) {
        List<Book> memberBooks = issuedBooks.get(memberId);

        if (memberBooks != null) {
            for (Book b : memberBooks) {
                if (b.getBookId() == bookId) {
                    b.setAvailable(true);
                    memberBooks.remove(b);
                    System.out.println("Book returned successfully.");
                    return;
                }
            }
        }
        System.out.println("Book not found for this member.");
    }

    // Search (Polymorphism - Overloading)
    public void search(String title) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                b.display();
            }
        }
    }

    public void searchByAuthor(String author) {
        for (Book b : books) {
            if (b.getAuthor().equalsIgnoreCase(author)) {
                b.display();
            }
        }
    }
}


public class Main {
    public static void main(String[] args) {
        Library lib = new Library();

        // Adding Books
        lib.addBook(new Book(1, "Java Basics", "James Gosling"));
        lib.addBook(new Book(2, "OOP Concepts", "Bjarne Stroustrup"));
        lib.addBook(new Book(3, "Data Structures", "Mark Allen"));

        // Display
        System.out.println("\nAll Books:");
        lib.displayBooks();

        // Members
        Member m1 = new StudentMember(101, "Aditya");

        // Issue Book
        lib.issueBook(m1.getMemberId(), 1);

        // Display again
        System.out.println("\nAfter Issuing:");
        lib.displayBooks();

        // Return Book
        lib.returnBook(m1.getMemberId(), 1);

        // Search
        System.out.println("\nSearch by Title:");
        lib.search("Java Basics");

        System.out.println("\nSearch by Author:");
        lib.searchByAuthor("Mark Allen");
    }
}