package edu.hofstra.cs.csc017.socialNetwork;

import java.util.List;

public class Post implements Displayable {
    String content;
    int authorID;
    String authorName;

    public Post(int authorID, String content){
        this.content = content;
        this.authorID = authorID;
    }

    public String getDisplay(){
        return this.authorName + " posted: " + this.content;
    }

    public int getAuthorID(){
        return authorID;
    }

    public void setAuthorName(List<User> listOfUsers){
        for (User user : listOfUsers) {
            if (this.authorID == user.getUserID()) {
                authorName = user.getName();
            }
        }
    }
}