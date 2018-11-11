package com.example.martyna.lab_2;

public class ModelPost {

    int id;
    int userId;
    String title;
    String bodyText;
    int size;

    public ModelPost(){}

    public ModelPost(int id, int userId, String title, String bodyText, int size){
        this.id = id;
        this.userId=userId;
        this.title=title;
        this.bodyText=bodyText;
        this.size=size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId=userId;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getBodyText(){
        return bodyText;
    }

    public void setBodyText(String bodyText){
        this.bodyText=bodyText;
    }

    public void setSize(int size){
        this.size=size;
    }

    public int getSize(){
        return size;
    }

}
