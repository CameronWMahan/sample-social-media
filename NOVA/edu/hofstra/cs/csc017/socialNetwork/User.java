package edu.hofstra.cs.csc017.socialNetwork;

import java.util.ArrayList;
import java.util.List;

public class User implements Displayable {
    String firstName;
    String lastName;
    String name = firstName + " " + lastName;
    int ageInYears;
    List<User> following = new ArrayList<User>();
    int userID;

    public User(String firstName, String lastName, int ageInYears){
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
        this.ageInYears = ageInYears;
    }

    public String getName(){
        return this.name;
    }

    public String getDisplay(){
        String print = this.getName() + " is following:  ";
        for (User user : following) {
            print = print + user.getName() + "  ";
        }
        return print;
    }

    public void followUser(User diffPerson){
        this.following.add(diffPerson);
    }

    public List<User> getFollowing(){
        return this.following;
    }

    public void setUserID(int id){
        this.userID = id;
    }

    public int getUserID(){
        return this.userID;
    }
}