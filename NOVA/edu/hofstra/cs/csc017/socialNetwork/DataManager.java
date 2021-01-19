package edu.hofstra.cs.csc017.socialNetwork;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    FileSystem defaultFS = FileSystems.getDefault();
    Path peopleFile;
    Path relationshipFile;
    Path postFile;
    List<User> listOfUsers;
    List<Post> listOfPosts;

    public DataManager(String usersFileName, String relationshipsFileName, String contentFileName) {
        getFilePaths(usersFileName, relationshipsFileName, contentFileName);
        initializeLists();
    }

    private void getFilePaths(String usersFileName, String relationshipsFileName, String contentFileName){
        peopleFile = defaultFS.getPath(usersFileName);
        relationshipFile = defaultFS.getPath(relationshipsFileName);
        postFile = defaultFS.getPath(contentFileName);
    }

    private void initializeLists(){
        listOfUsers = new ArrayList<User>();
        listOfPosts = new ArrayList<Post>();
    }

    private static List<String> readAllLinesFromFile(Path filePath) throws IOException {
        List<String> list = null;
        try {
            list = Files.readAllLines(filePath);
        } catch (IOException exception) {
            System.out.println("Error: Could not read file: " + filePath.toFile());
            System.exit(1);
        }
        return list;
    }

    public void createLists() throws IOException {
        createUsersFromList();
        createRelationsFromList();
        createPostsFromList();
    }
    
    private void createUsersFromList() throws IOException {
        List<String> listOfPeople = readAllLinesFromFile(peopleFile);
        int idCounter = 1;
        for (String person : listOfPeople) {
            String [] personValuesArray = person.split("\\|");
            List<String> personValues = new ArrayList<String>(Arrays.asList(personValuesArray));
            try {
                String userFirstName = personValues.get(0);
                String userLastName = personValues.get(1);
                int userAgeInYears = findUserAge(personValues, userFirstName, userLastName);
                User user = new User(userFirstName, userLastName, userAgeInYears);
                user.setUserID(idCounter);
                listOfUsers.add(user);
                idCounter++;
            } catch (IndexOutOfBoundsException exception) {
                System.out.println("Not enough information was provided to create a User, please check the Users file");
            }
        }
    }

    private int findUserAge(List<String> personValues ,String userFirstName, String userLastName){
        int userAgeInYears = 0;
        try {
            userAgeInYears = Integer.parseInt(personValues.get(2));
        } catch (NumberFormatException exception){
            System.out.println("The age for " + userFirstName + " " + userLastName + " is not a number - please check the Users file");
        } catch (IndexOutOfBoundsException exception){
            System.out.print("No age provided for " + userFirstName + " " + userLastName);
        }
        return userAgeInYears;
    }

    private void createRelationsFromList() throws IOException {
        List<String> listOfRelationships = readAllLinesFromFile(relationshipFile);
        for (User user : listOfUsers) {
            for (String relationship : listOfRelationships) {
                String [] relationshipValuesArray = relationship.split("\\|");
                List<String> relationshipValues = new ArrayList<String>(Arrays.asList(relationshipValuesArray));
                try {
                    String userFirstName = relationshipValues.get(0);
                    String userLastName = relationshipValues.get(1);
                    if (user.getName().equals(userFirstName + " " + userLastName)){
                        for (User user2 : listOfUsers) {
                            String firstNameOfUserToFollow = relationshipValues.get(2);
                            String lastNameOfUserToFollow = relationshipValues.get(3);
                            if (user2.getName().equals(firstNameOfUserToFollow + " " + lastNameOfUserToFollow)){
                                user.followUser(user2);
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException exception) {
                    System.out.println("Not enough information to assign a follower");
                }
            }
        }
    }

    private void createPostsFromList() throws IOException {
        List<String> listOfContent = readAllLinesFromFile(postFile);
        for (String line : listOfContent) {
            String [] postValuesArray = line.split(("\\|"));
            List<String> postValues = new ArrayList<String>(Arrays.asList(postValuesArray));
            try {
                int postAuthorID = Integer.parseInt(postValues.get(1));
                String postContent = postValues.get(0);
                Post post = new Post(postAuthorID, postContent);
                post.setAuthorName(listOfUsers);
                listOfPosts.add(post);
            } catch (IndexOutOfBoundsException exception) {
                System.out.println("Not enough information was provided to create a post");
            }
        }

    }

    public List<User> getUserList(){
        return listOfUsers;
    }

    public List<Post> getContentList(){
        return listOfPosts;
    }

}