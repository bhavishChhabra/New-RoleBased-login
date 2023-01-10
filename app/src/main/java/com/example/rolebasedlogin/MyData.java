package com.example.rolebasedlogin;


/**
 * Created by filipp on 9/16/2016.
 */
public class MyData {

   private int Id;
    private String description,image_link;

    public MyData(int Id, String description, String image_link) {
        this.Id=Id;
        this.description = description;
        this.image_link = image_link;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
