package com.fastnote.app.models;

public class NotesModel {
    String title,text, id, date;
    public NotesModel(String id,String text, String title,String data) {
        this.title = title;
        this.id = id;
        this.text = text;
        this.date = data;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }
}
