import java.io.IOException;

import edu.hofstra.cs.csc017.socialNetwork.DataManager;
import edu.hofstra.cs.csc017.socialNetwork.User;
import edu.hofstra.cs.csc017.socialNetwork.Feed;

public class Social{
    public static void main(String[] args) throws IOException {
        DataManager dataManager = new DataManager("people.txt", "relationships.txt", "content.txt");
        Feed outputFeed = new Feed();
        int numberOfPostsToDisplay = 10;
        User firstUser = null;
        boolean notFinalOutput = true;
        boolean finalOutput = false;

        dataManager.createLists();
        firstUser = dataManager.getUserList().get(0);
        outputFeed.displayFollowingFeed(firstUser, dataManager, numberOfPostsToDisplay, notFinalOutput);
        outputFeed.displayRecommendedFeed(firstUser, dataManager, numberOfPostsToDisplay, finalOutput);
    }
}
