package app;


import data.*;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class LibraryControl {
    // zmienna do komunikacji z użytkownikiem
    private DataReader dataReader;
    private FileManager fileManager;

    // "biblioteka" przechowująca dane
    private Library library;

    public LibraryControl() {
        dataReader = new DataReader();
        fileManager = new FileManager();
        try {
            library = fileManager.readLibraryFromFile();
            System.out.println("Wczytano dane biblioteki z pliku ");
        } catch (ClassNotFoundException | IOException e) {
            library = new Library();
            System.out.println("Utworzono nową bazę biblioteki.");
        }
    }

    /*
     * Główna pętla programu, która pozwala na wybór opcji i interakcję
     */
    public void controlLoop() {
        Option option = null;
        while (option != Option.EXIT) {
            try {
                printOptions();
                option = Option.createFromInt(dataReader.getInt());
                switch (option) {
                    case ADD_BOOK:
                        addBook();
                        break;
                    case ADD_MAGAZINE:
                        addMagazine();
                        break;
                    case PRINT_BOOKS:
                        printBooks();
                        break;
                    case PRINT_MAGAZINES:
                        printMagazines();
                        break;
                    case ADD_USER:
                        addUser();
                        break;
                    case PRINT_USERS:
                        printUsers();
                        break;
                    case BORROW_PUBLICATION:
                        borrowPublication();
                        break;
                    case PRINT_BORROWED:
                        printBorrowed();
                        break;
                    case RETURN_BORROW:
                        returnBorrowed();
                        break;
                    case PRINT_HISTORY:
                        printHistory();
                        break;
                    case EXIT:
                        exit();
                }
            } catch (InputMismatchException e) {
                System.out.println("Wprowadzono niepoprawne dane, publikacji nie dodano");
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("Wybrana opcja nie istnieje, wybierz ponownie:");
            }
        }
        // zamykamy strumień wejścia
        dataReader.close();
    }

    private void printOptions() {
        System.out.println("Wybierz opcję: ");
        for (Option o : Option.values()) {
            System.out.println(o);
        }
    }

    private void addBook() {
        Book book = dataReader.readAndCreateBook();
        library.addBook(book);
    }

    private void printBooks() {
        LibraryUtils.printBooks(library);
    }

    private void addMagazine() {
        Magazine magazine = dataReader.readAndCreateMagazine();
        library.addMagazine(magazine);
    }

    private void printMagazines() {
        LibraryUtils.printMagazines(library);
    }

    private void addUser() {
        LibraryUser user = dataReader.readAndCreateLibraryUser();
        library.addUser(user);
    }

    private void printUsers() {
        LibraryUtils.printUsers(library);
    }

    private void borrowPublication(){
        String pesel = dataReader.readPesel();
        String title = dataReader.readTitle();
        LibraryUser user = library.getUser(pesel);
        Publication pub = library.getPublication(title);
        user.borrowPublication(pub);
    }

    private void printBorrowed(){
        LibraryUser user = library.getUser(dataReader.readPesel());
        LibraryUtils.printBorrowed(user);
    }

    private void returnBorrowed(){
        LibraryUser user = library.getUser(dataReader.readPesel());
        Publication pub = library.getPublication(dataReader.readTitle());
        user.returnPublication(pub);
    }

    private void printHistory(){
        LibraryUser user = library.getUser(dataReader.readPesel());
        LibraryUtils.printHistory(user);
    }

    private void exit() {
        fileManager.writeLibraryToFile(library);
    }

    private enum Option {
        EXIT(0, "Wyjście z programu"),
        ADD_BOOK(1, "Dodanie książki"),
        ADD_MAGAZINE(2, "Dodanie magazynu/gazety"),
        PRINT_BOOKS(3, "Wyświetlenie dostępnych książek"),
        PRINT_MAGAZINES(4, "Wyświetlenie dostępnych magazynów/gazet"),
        ADD_USER(5, "Dodanie nowego użytkownika"),
        PRINT_USERS(6, "Wyświetlenie listy użytkowników"),
        BORROW_PUBLICATION(7, "Wypożyczanie publikacji"),
        PRINT_BORROWED(8, "Wyświetl wypożyczone publikacje"),
        RETURN_BORROW(9, "Zwrot wypożyczonej publikacji"),
        PRINT_HISTORY(10, "Wyświetl historie wypozyczanych publikacji");
        private int value;
        private String description;

        Option(int value, String desc) {
            this.value = value;
            this.description = desc;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        public static Option createFromInt(int option) throws NoSuchElementException {
            Option result = null;
            try {
                result = Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException("Brak elementu o wskazanym ID");
            }

            return result;
        }
    }
}
