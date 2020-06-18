package api.components;

import data.transpool.TransPoolData;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import exception.NoResultsFoundException;
import exception.data.RideFullException;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

/**
 * The engine used to find a match for a trip request. Contains the list of all possible matches, the request to match
 * and the chosen TP trip to match.
 */
public class MatchingEngine {
    private TripRequest tripRequestToMatch;
    private ObservableList<PossibleRoute> possibleRoutes;
    private BooleanProperty foundMatches;

    public MatchingEngine() {
        possibleRoutes = FXCollections.observableArrayList();
        foundMatches = new SimpleBooleanProperty(false);
    }

    public void findPossibleMatches(TransPoolData data, TripRequest tripRequestToMatch, int maximumMatches) throws NoResultsFoundException {
        this.tripRequestToMatch = tripRequestToMatch;
        possibleRoutes.addAll(data.getAllPossibleRoutes(tripRequestToMatch, maximumMatches));

        if (!possibleRoutes.isEmpty()) {
            foundMatches.set(true);
        }
    }


    //Create a new matchedTripRequest
    //Add the possibleRoute to the list of matched routes inside tripOfferMap?
    public void addNewMatch(TransPoolData data, int chosenPossibleRouteIndex) throws TransPoolDataException {
        PossibleRoute chosenPossibleRoute = possibleRoutes.get(chosenPossibleRouteIndex);
        MatchedTripRequest matchedTripRequest = new MatchedTripRequest(tripRequestToMatch, chosenPossibleRoute);
        data.addMatchedRequest(matchedTripRequest);
    }


    /**
     * @return the list of all possible matches.
     */
    public ObservableList<PossibleRoute> getPossibleRoutes() {
        return possibleRoutes;
    }

    public void clearPossibleMatches() {
        possibleRoutes.clear();
        foundMatches.set(false);
    }

    public BooleanProperty foundMatchesProperty() {
        return foundMatches;
    }

    public ObservableList<String> getPossibleRoutesAsString() {
        return possibleRoutes
                .stream()
                .map(PossibleRoute::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
