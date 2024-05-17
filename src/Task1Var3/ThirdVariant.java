package Task1Var3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Author implements Externalizable {
    private String name;

    public Author() {
        // Потрібен публічний конструктор без аргументів для Externalizable
    }

    public Author(String name) {
        this.name = name;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
    }

    @Override
    public String toString() {
        return name;
    }
}

class Reader implements Externalizable {
    private String name;
    private List<Book> borrowedBooks;

    public Reader() {
        // Потрібен публічний конструктор без аргументів для Externalizable
        borrowedBooks = new ArrayList<>();
    }

    public Reader(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(borrowedBooks.size());
        for (Book book : borrowedBooks) {
            book.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        int borrowedBooksSize = in.readInt();
        borrowedBooks = new ArrayList<>(borrowedBooksSize);
        for (int i = 0; i < borrowedBooksSize; i++) {
            Book book = new Book();
            book.readExternal(in);
            borrowedBooks.add(book);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" (Borrowed Books: ");
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

class Book implements Externalizable {
    private String title;
    private List<Author> authors;

    public Book() {
        // Потрібен публічний конструктор без аргументів для Externalizable
        authors = new ArrayList<>();
    }

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

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(title);
        out.writeInt(authors.size());
        for (Author author : authors) {
            author.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        title = (String) in.readObject();
        int authorSize = in.readInt();
        authors = new ArrayList<>(authorSize);
        for (int i = 0; i < authorSize; i++) {
            Author author = new Author();
            author.readExternal(in);
            authors.add(author);
        }
    }

    @Override
    public String toString() {
        return title + " by " + authors;
    }
}

class Library implements Externalizable {
    private String name;
    private List<Book> books;
    private List<Reader> readers;

    public Library() {
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public Library(String name) {
        this.name = name;
        this.books = new ArrayList<>();
        this.readers = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(books.size());
        for (Book book : books) {
            book.writeExternal(out);
        }
        out.writeInt(readers.size());
        for (Reader reader : readers) {
            reader.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        int bookSize = in.readInt();
        books = new ArrayList<>(bookSize);
        for (int i = 0; i < bookSize; i++) {
            Book book = new Book();
            book.readExternal(in);
            books.add(book);
        }
        int readerSize = in.readInt();
        readers = new ArrayList<>(readerSize);
        for (int i = 0; i < readerSize; i++) {
            Reader reader = new Reader();
            reader.readExternal(in);
            readers.add(reader);
        }
    }

    public void printTable() {
        System.out.println(String.join("", Collections.nCopies(150, "-")));
        String headerFormat = "| %-70s | %-70s |\n";
        String rowFormat = "| %-70.70s | %-70.70s |\n";
        System.out.printf(headerFormat, "Books (Title & Authors)", "Readers (Name & Borrowed Books)");
        System.out.println(String.join("", Collections.nCopies(150, "-")));
        int maxRows = Math.max(books.size(), readers.size());
        for (int i = 0; i < maxRows; i++) {
            String bookEntry = i < books.size() ? books.get(i).toString().replaceAll("\n", " ") : "";
            String readerEntry = i < readers.size() ? readers.get(i).toString() : "";
            System.out.printf(rowFormat, bookEntry, readerEntry);
        }
        System.out.println(String.join("", Collections.nCopies(150, "-")));
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

public class ThirdVariant {
    public static void main(String[] args) {
        Library library = new Library("City Library");

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
