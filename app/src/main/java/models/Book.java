package models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")

public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String clientName;
    private String clientPhone;
    private String service;
    private String date;
    private String time;

    public Book(String clientName, String clientPhone, String service, String date, String time) {
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.service = service;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }
    public void setId(int newid){
        this.id=newid;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
