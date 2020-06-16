package data.transpool.user;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.util.List;

public interface Feedbacker {
    String getFeedbackerName();
    int getFeedbackerID();
    void leaveFeedback(Feedbackable feedbackee, Feedback feedback);
    ObservableList<Feedbackable> getAllFeedbackables();
}