package api.transpool.user.component.feedback;


import api.transpool.user.account.User;

import java.util.Collection;

/**
 * An interface of an object which can be feedbacked.
 */
public interface Feedbackable extends User  {

    void addFeedback(Feedback feedback);

    Feedback getFeedback(int riderID);

    Collection<Feedback> getAllFeedbacks();

    double getAverageRating();
}
