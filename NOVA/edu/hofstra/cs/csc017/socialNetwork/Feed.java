package edu.hofstra.cs.csc017.socialNetwork;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Feed {
    
    public void display(Displayable source){
        System.out.println(source.getDisplay());
    }

    public void displayFollowingFeed(User loggedInUser, DataManager dataManager, int numberOfPostsToDisplay, boolean isThisTheFinalOutput){
        System.out.println("Displaying Main Feed for: " + loggedInUser.getName());
        dataManager.getContentList().stream().filter(post -> getFollowedUsersIDs(loggedInUser, dataManager).contains(post.getAuthorID())).limit(numberOfPostsToDisplay).forEach(this::display);
        if (isThisTheFinalOutput == true){
            System.out.println("");
        }
    }
    
    public void displayRecommendedFeed(User loggedInUser, DataManager dataManager , int numberOfPostsToDisplay, boolean isThisTheFinalOutput){
        System.out.println("Displaying Reccomended Feed for: " + loggedInUser.getName());
        dataManager.getContentList().stream().filter(post -> getReccommendedUsersIDs(loggedInUser, dataManager).contains(post.getAuthorID())).limit(numberOfPostsToDisplay).forEach(this::display);
        if (isThisTheFinalOutput == false){
            System.out.println("");
        }
    }

    public Set<Integer> getFollowedUsersIDs(User loggedInUser, DataManager dataManager){
        Set<Integer> followedUserIDs = dataManager.getUserList().stream().filter(user -> !user.equals(loggedInUser)).filter(user -> loggedInUser.getFollowing().contains(user)).map(user -> user.getUserID()).collect(Collectors.toSet());
        return followedUserIDs;
    }

    public List<Post> getOnlyFollowedUsers(List<Post> listOfPosts, User loggedInUser, DataManager dataManager){
        List<Post> listOfFollowedPosts = listOfPosts.stream().filter(post -> getFollowedUsersIDs(loggedInUser, dataManager).contains(post.getAuthorID())).collect(Collectors.toList());
        return listOfFollowedPosts;
    }

    public Set<Integer> getReccommendedUsersIDs(User loggedInUser, DataManager dataManager){
        Set<Integer> reccommendedUserIDs = dataManager.getUserList().stream().filter(user -> !user.equals(loggedInUser)).filter(user -> !loggedInUser.getFollowing().contains(user)).map(user -> user.getUserID()).collect(Collectors.toSet());
        return reccommendedUserIDs;
    }
    
}