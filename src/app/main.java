package app;

import data.Book;
import data.Library;
import data.LibraryUser;
import data.Publication;

public class main {
    public static void main(String[] args) {
        Library lib = new Library();
        LibraryUser krzys = new LibraryUser("Krzys","Brawa","94042302998");
        LibraryUser krzys1 = new LibraryUser("Krzys","Brawa","89920324049");
        lib.addUser(krzys);
        lib.addUser(krzys1);
        System.out.println(lib.getUsers());
        Publication pub1 = new Book("JAVA","Jan Nowak",2018,512,"PWN","1234567891011");
        krzys.borrowPublication(pub1);
    }
}
