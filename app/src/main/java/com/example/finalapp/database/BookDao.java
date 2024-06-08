package com.example.finalapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import models.Book;

@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();

    @Query("SELECT * FROM books WHERE clientName = :name AND clientPhone = :phone")
    List<Book> getBooksByUser(String name, String phone);

    @Query("SELECT * FROM books WHERE date = :date AND time = :time")
    List<Book> getBooksByDateAndTime(String date, String time);

}
