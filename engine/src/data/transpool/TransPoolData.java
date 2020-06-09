package data.transpool;

import api.components.TripRequestEngine;
import data.jaxb.TransPool;
import data.transpool.map.BasicMap;
import data.transpool.map.BasicMapData;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.map.trip.offer.TripOfferMap;
import data.transpool.map.trip.offer.TripOfferMapData;
import data.transpool.trip.offer.TripOffer;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class TransPoolData implements TripRequestEngine, BasicMap, TripOfferMap {

    private BasicMap map;
    private TripOfferMap tripOffers;
    private ObservableList<TripRequest> allTripRequests;
    private ObservableList<MatchedTripRequest> allMatchedTripRequests;

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        this.allTripRequests = FXCollections.observableArrayList();
        this.allMatchedTripRequests = FXCollections.observableArrayList();
        this.map = new BasicMapData(JAXBData.getMapDescriptor());
        this.tripOffers = new TripOfferMapData(map, JAXBData.getPlannedTrips().getTransPoolTrip());
    }

    @Override
    public TripOffer getTripOffer(int ID) {
        return tripOffers.getTripOffer(ID);
    }

    @Override
    public void addTripOffer(TripOffer tripOffer) {
        tripOffers.addTripOffer(tripOffer);
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() {
        return tripOffers.getAllTripOffers();
    }

    @Override
    public TripRequest getTripRequest(int ID) {
        return allTripRequests
                .stream()
                .filter(t -> t.getRequestID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteTripRequest(TripRequest requestToDelete) {
        allTripRequests.remove(requestToDelete);
    }

    @Override
    public void addTripRequest(TripRequest tripRequest) {
        allTripRequests.add(tripRequest);
    }

    @Override
    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        allMatchedTripRequests.add(matchedTripRequest);
        TripOffer trip = getTripOffer(matchedTripRequest.getTripOfferID());
        trip.updateAfterMatch(matchedTripRequest);
        deleteTripRequest(getTripRequest(matchedTripRequest.getRequestID()));
    }

    @Override
    public ObservableList<TripRequest> getAllTripRequests() {
        return allTripRequests;
    }

    @Override
    public ObservableList<MatchedTripRequest> getAllMatchedTripRequests() {
        return allMatchedTripRequests;
    }

    @Override
    public int getMapWidth() {
        return map.getMapWidth();
    }

    @Override
    public int getMapLength() {
        return map.getMapLength();
    }

    @Override
    public boolean containsStop(String stopName) {
        return map.containsStop(stopName);
    }

    @Override
    public Map<String, Stop> getAllStops() {
        return map.getAllStops();
    }

    @Override
    public List<Stop> getAllStopsAsList() {
        return map.getAllStopsAsList();
    }

    @Override
    public Stop getStop(String stopName) {
        return map.getStop(stopName);
    }

    @Override
    public int getNumberOfStops() {
        return map.getNumberOfStops();
    }

    @Override
    public List<Path> getAllPaths() {
        return map.getAllPaths();
    }

    @Override
    public boolean containsPath(String source, String destination) {
        return map.containsPath(source, destination);
    }

    @Override
    public Path getPath(Stop source, Stop destination) {
        return map.getPath(source, destination);
    }

    @Override
    public Path getPath(String source, String destination) {
        return map.getPath(source, destination);
    }

    @Override
    public List<List<Pair<Stop, TripOffer>>> getAllPossibleRoutes(Stop source, Stop destination) {
        return tripOffers.getAllPossibleRoutes(source, destination);
    }

    @Override
    public List<List<Pair<Stop, TripOffer>>> getGraph() {
        return tripOffers.getGraph();
    }

    @Override
    public void newConnection(Path E, TripOffer tripOffer) {

    }

    public BasicMap getMap() {
        return map;
    }
}
