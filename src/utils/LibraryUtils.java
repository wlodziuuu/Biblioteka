package utils;


import data.*;


import java.util.*;

public class LibraryUtils {

    public static void printBooks(Library lib) {
        printPublications(lib, Book.class);
    }

    public static void printMagazines(Library lib) {
        printPublications(lib, Magazine.class);
    }

    public static void printPublications(Library lib,Class cl) {
        long countPublications = lib.getPublications().values().stream()
                .filter(cl::isInstance).sorted(new Library.AlphabeticalComparator())
                .peek(System.out::println).count();

        if(countPublications == 0) {
            System.out.println("W bibliotece nie znaleziono publikacji typu " + cl.getSimpleName());
        }
    }

    public static  void printUsers(Library lib){
        lib.getUsers().values().stream()
                .sorted((a, b) -> a.getLastName().compareTo(b.getLastName()))
                .forEach(System.out::println);
    }

    public static void printBorrowed(LibraryUser libUser){
        libUser.getBorrowedPublications().forEach(System.out::println);
    }

    public static void printHistory(LibraryUser libUser){
        libUser.getPublicationHistory().forEach(System.out::println);
    }
}