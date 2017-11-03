package com.example.alexb.booksorter;

/**
 * Created by alexb on 24/06/2017.
 */

public class BookInformation {
    private String bookName;
    private int bookYear;
    private String bookAuthor;
    private int pages;
    private int currentPage;
    private String bookDescription;
    private byte[] bookImage;

    public BookInformation(String bookName,int bookYear,String bookAuthor, int pages,int currentPage,String bookDescription,byte[] bookImage){

        this.bookName=bookName;
        this.bookYear=bookYear;
        this.bookAuthor=bookAuthor;
        this.pages = pages;
        this.currentPage=currentPage;
        this.bookDescription=bookDescription;
        this.bookImage=bookImage;
    }

    public String getBookAuthor(){
        return bookAuthor;
    }

    public String setBookAuthor(){
        return this.bookAuthor=bookAuthor;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookYear() {
        return bookYear;
    }

    public void setBookYear(int bookYear) {
        this.bookYear = bookYear;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public void setBookImage(byte[] bookImage){
        this.bookImage=bookImage;
    }
    public byte[] getBookImage(){
        return bookImage;
    }
}
