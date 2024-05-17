package Task1Var2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Reader {
    private String name;
    private ArrayList<Book> borrowedBooks;

    public Reader(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (Borrowed Books: ");
        for (Book book : borrowedBooks) {
            sb.append(book.getTitle()).append("; ");
        }
        if (!borrowedBooks.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove last semicolon and space
        }
        sb.append(")");
        return sb.toString();
    }
}

class Book {
    private String title;
    private ArrayList<Author> authors;

    public Book(String title) {
        this.title = title;
        this.authors = new ArrayList<>();
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return title + " by " + authors;
    }
}

class Library implements Serializable {
    private transient ArrayList<Book> books;
    private transient ArrayList<Reader> readers;

    public Library() {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(books.size());
        for (Book book : books) {
            out.writeObject(book.getTitle());
            out.writeInt(book.getAuthors().size());
            for (Author author : book.getAuthors()) {
                out.writeObject(author.toString());
            }
        }
        out.writeInt(readers.size());
        for (Reader reader : readers) {
            out.writeObject(reader.toString());
            out.writeInt(reader.getBorrowedBooks().size());
            for (Book book : reader.getBorrowedBooks()) {
                out.writeObject(book.getTitle());
                out.writeInt(book.getAuthors().size());
                for (Author author : book.getAuthors()) {
                    out.writeObject(author.toString());
                }
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        books = new ArrayList<>();
        int bookSize = in.readInt();
        for (int i = 0; i < bookSize; i++) {
            String title = (String) in.readObject();
            int authorSize = in.readInt();
            Book book = new Book(title);
            for (int j = 0; j < authorSize; j++) {
                book.addAuthor(new Author((String) in.readObject()));
            }
            books.add(book);
        }
        readers = new ArrayList<>();
        int readerSize = in.readInt();
        for (int i = 0; i < readerSize; i++) {
            String readerName = (String) in.readObject();
            Reader reader = new Reader(readerName);
            int borrowedBooksSize = in.readInt();
            for (int j = 0; j < borrowedBooksSize; j++) {
                String bookTitle = (String) in.readObject();
                int bookAuthorsSize = in.readInt();
                Book borrowedBook = new Book(bookTitle);
                for (int k = 0; k < bookAuthorsSize; k++) {
                    borrowedBook.addAuthor(new Author((String) in.readObject()));
                }
                reader.borrowBook(borrowedBook);
            }
            readers.add(reader);
        }
    }

    public void printTable() {
        System.out.println(String.join("", Collections.nCopies(210, "=")));
        String headerFormat = "| %-100s | %-100s |\n";
        String rowFormat = "| %-100s | %-100s |\n";
        System.out.printf(headerFormat, "Books (Title & Authors)", "Readers (Name & Borrowed Books)");
        System.out.println(String.join("", Collections.nCopies(210, "=")));
        int maxRows = Math.max(books.size(), readers.size());
        for (int i = 0; i < maxRows; i++) {
            String bookEntry = i < books.size() ? books.get(i).toString().replaceAll("\n", " ") : "";
            String readerEntry = i < readers.size() ? readers.get(i).toString() : "";
            System.out.printf(rowFormat, bookEntry, readerEntry);
        }
        System.out.println(String.join("", Collections.nCopies(210, "=")));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Library:\n");
        sb.append("Books:\n");
        for (Book book : books) {
            sb.append(book.toString()).append("\n");
        }
        sb.append("Readers:\n");
        for (Reader reader : readers) {
            sb.append(reader.toString()).append("\n");
        }
        return sb.toString();
    }
}

public class SecondVariant {
    public static void main(String[] args) {
        Library library = new Library();
        Book book1 = new Book("The Little Prince");
        book1.addAuthor(new Author("Antoine de Saint-Exupéry"));
        Book book2 = new Book("Pollyanna");
        book2.addAuthor(new Author("Eleanor H. Porter"));
        Book book3 = new Book("Harry Potter and the Sorcerer's Stone");
        book3.addAuthor(new Author("J.K. Rowling"));
        Book book4 = new Book("1984");
        book4.addAuthor(new Author("George Orwell"));
        Book book5 = new Book("The Hobbit");
        book5.addAuthor(new Author("J.R.R. Tolkien"));
        Book book6 = new Book("The Great Gatsby");
        book6.addAuthor(new Author("F. Scott Fitzgerald"));

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        library.addBook(book6);

        Reader reader1 = new Reader("Olesia Pushkina");
        Reader reader2 = new Reader("Viktor Barbinov");
        Reader reader3 = new Reader("Daniil Pavlusenko");
        Reader reader4 = new Reader("Iryna Karas");
        Reader reader5 = new Reader("Oleksandr Sporov");
        Reader reader6 = new Reader("Serhii Sevidov");

        reader1.borrowBook(book1);
        reader2.borrowBook(book2);

        library.addReader(reader1);
        library.addReader(reader2);
        library.addReader(reader3);
        library.addReader(reader4);
        library.addReader(reader5);
        library.addReader(reader6);

        // Initial state
        System.out.println("Initial state:");
        library.printTable();

        // Serialization
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library2.ser"))) {
            oos.writeObject(library);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialization
        Library deserializedLibrary = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("library2.ser"))) {
            deserializedLibrary = (Library) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (deserializedLibrary != null) {
            // Deserialized state
            System.out.println("Deserialized state:");
            deserializedLibrary.printTable();
        }
    }
}
