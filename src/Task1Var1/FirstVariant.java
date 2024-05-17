package Task1Var1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class Human implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;

    public Human() {
    }

    public Human(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

class Author extends Human {
    public Author() {
    }

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }
}

class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private List<Author> authors;
    private int year;
    private int edition;

    public Book() {
    }

    public Book(String title, List<Author> authors, int year, int edition) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.edition = edition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    @Override
    public String toString() {
        StringBuilder authorsStr = new StringBuilder();
        for (Author author : authors) {
            authorsStr.append(author.toString()).append(", ");
        }
        if (authorsStr.length() > 0) {
            authorsStr.setLength(authorsStr.length() - 2); // Remove last comma
        }
        return String.format("%s, Authors: [%s], Year: %d, Edition: %d",
                title, authorsStr, year, edition);
    }
}

class BookStore implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Book> books;

    public BookStore() {
        books = new ArrayList<>();
    }

    public BookStore(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        StringBuilder booksStr = new StringBuilder();
        for (Book book : books) {
            booksStr.append(book.toString()).append("\n    ");
        }
        if (booksStr.length() > 0) {
            booksStr.setLength(booksStr.length() - 5); // Remove last newline and spaces
        }
        return String.format("%s Books: %s", name, booksStr.toString());
    }
}

class BookReader extends Human {
    @Serial
    private static final long serialVersionUID = 1L;
    private int registrationNumber;
    private List<Book> borrowedBooks;

    public BookReader() {
        borrowedBooks = new ArrayList<>();
    }

    public BookReader(String firstName, String lastName, int registrationNumber) {
        super(firstName, lastName);
        this.registrationNumber = registrationNumber;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    @Override
    public String toString() {
        StringBuilder borrowedBooksStr = new StringBuilder();
        for (Book book : borrowedBooks) {
            borrowedBooksStr.append(book.toString()).append("\n    ");
        }
        if (borrowedBooksStr.length() > 0) {
            borrowedBooksStr.setLength(borrowedBooksStr.length() - 5); // Remove last newline and spaces
        }
        return String.format("%s RegistrationNumber: %d BorrowedBooks: %s",
                super.toString(), registrationNumber, borrowedBooksStr.toString());
    }
}

class Bookshelf implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<Book> books;

    public Bookshelf() {
        books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        StringBuilder booksStr = new StringBuilder();
        for (Book book : books) {
            booksStr.append(book.toString()).append("\n    ");
        }
        if (booksStr.length() > 0) {
            booksStr.setLength(booksStr.length() - 5); // Remove last newline and spaces
        }
        return String.format("Bookshelf: %s", booksStr.toString());
    }
}

class Rental implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Book book;
    private BookReader reader;

    public Rental() {
    }

    public Rental(Book book, BookReader reader) {
        this.book = book;
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookReader getReader() {
        return reader;
    }

    public void setReader(BookReader reader) {
        this.reader = reader;
    }

    @Override
    public String toString() {
        return String.format("Rental: Book: %s Borrowed by: %s", book.toString(), reader.toString());
    }
}

class Library implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private List<BookStore> bookStores;
    private List<BookReader> readers;

    public Library() {
        bookStores = new ArrayList<>();
        readers = new ArrayList<>();
    }

    public Library(String name, List<BookStore> bookStores, List<BookReader> readers) {
        this.name = name;
        this.bookStores = bookStores;
        this.readers = readers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookStore> getBookStores() {
        return bookStores;
    }

    public void setBookStores(List<BookStore> bookStores) {
        this.bookStores = bookStores;
    }

    public List<BookReader> getReaders() {
        return readers;
    }

    public void setReaders(List<BookReader> readers) {
        this.readers = readers;
    }

    public void addBookStore(BookStore bookStore) {
        bookStores.add(bookStore);
    }

    public void addReader(BookReader reader) {
        readers.add(reader);
    }

    public void rentBook(Book book, BookReader reader) {
        reader.borrowBook(book);
    }

    @Override
    public String toString() {
        StringBuilder bookStoresStr = new StringBuilder();
        for (BookStore bookStore : bookStores) {
            bookStoresStr.append(bookStore.toString()).append("\n");
        }
        StringBuilder readersStr = new StringBuilder();
        for (BookReader reader : readers) {
            readersStr.append(reader.toString()).append("\n");
        }
        return String.format("Library: %s\nBookStores:\n%s\nReaders:\n%s",
                name, bookStoresStr.toString(), readersStr.toString());
    }

    public void saveState(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static Library loadState(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Library) in.readObject();
        }
    }

    public void printLibrary() {
        System.out.println(this);
    }

    public void printTable() {
        System.out.println(String.join("", Collections.nCopies(237, "-")));
        String headerFormat = "| %-130s | %-200s |\n";
        String rowFormat = "| %-100.100s | %-130.200s |\n";
        System.out.printf(headerFormat, "BookStores (Name & Books)", "Readers (Name & BorrowedBooks)");
        System.out.println(String.join("", Collections.nCopies(237, "-")));
        int maxRows = Math.max(bookStores.size(), readers.size());
        for (int i = 0; i < maxRows; i++) {
            String bookStoreEntry = i < bookStores.size() ? bookStores.get(i).toString().replaceAll("\n", " ") : "";
            String readerEntry = i < readers.size() ? readers.get(i).toString().replaceAll("\n", " ") : "";
            System.out.printf(rowFormat, bookStoreEntry, readerEntry);
        }
        System.out.println(String.join("", Collections.nCopies(237, "-")));
    }

    public static void main(String[] args) {
        Library library = new Library("City Library", new ArrayList<>(), new ArrayList<>());

        Author author1 = new Author("Antoine", "de Saint-Exupéry");
        Author author2 = new Author("Eleanor", "H. Porter");
        Author author3 = new Author("J.K.", "Rowling");
        Author author4 = new Author("George", "Orwell");
        Author author5 = new Author("J.R.R.", "Tolkien");
        Author author6 = new Author("F. Scott", "Fitzgerald");

        List<Author> authors1 = new ArrayList<>();
        authors1.add(author1);

        List<Author> authors2 = new ArrayList<>();
        authors2.add(author2);

        List<Author> authors3 = new ArrayList<>();
        authors3.add(author3);

        List<Author> authors4 = new ArrayList<>();
        authors4.add(author4);

        List<Author> authors5 = new ArrayList<>();
        authors5.add(author5);

        List<Author> authors6 = new ArrayList<>();
        authors6.add(author6);

        Book book1 = new Book("The Little Prince", authors1, 1943, 1);
        Book book2 = new Book("Pollyanna", authors2, 1913, 1);
        Book book3 = new Book("Harry Potter and the Sorcerer's Stone", authors3, 1997, 1);
        Book book4 = new Book("1984", authors4, 1949, 1);
        Book book5 = new Book("The Hobbit", authors5, 1937, 1);
        Book book6 = new Book("The Great Gatsby", authors6, 1925, 1);

        BookStore bookStore1 = new BookStore("Classics", new ArrayList<>());
        bookStore1.addBook(book1);
        bookStore1.addBook(book2);

        BookStore bookStore2 = new BookStore("Modern", new ArrayList<>());
        bookStore2.addBook(book3);
        bookStore2.addBook(book4);
        bookStore2.addBook(book5);
        bookStore2.addBook(book6);

        library.addBookStore(bookStore1);
        library.addBookStore(bookStore2);

        BookReader reader1 = new BookReader("Olesia", "Pushkina", 1);
        BookReader reader2 = new BookReader("Viktor", "Barbinov", 2);
        BookReader reader3 = new BookReader("Daniil", "Pavlusenko", 3);
        BookReader reader4 = new BookReader("Iryna", "Karas", 4);
        BookReader reader5 = new BookReader("Oleksandr", "Sporov", 5);
        BookReader reader6 = new BookReader("Serhii", "Sevidov", 6);

        library.addReader(reader1);
        library.addReader(reader2);
        library.addReader(reader3);
        library.addReader(reader4);
        library.addReader(reader5);
        library.addReader(reader6);

        library.rentBook(book1, reader1);
        library.rentBook(book2, reader2);

        System.out.println("Initial state:");
        library.printTable();

        try {
            library.saveState("library_state.ser");
            Library loadedLibrary = Library.loadState("library_state.ser");
            System.out.println("\nLoaded Library:");
            loadedLibrary.printTable();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
