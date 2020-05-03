package data.transpool.structures;

import data.transpool.TransPoolTripRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransPoolTripRequests {
    private List<TransPoolTripRequest> transpoolTripRequests;

    public TransPoolTripRequests() {
        transpoolTripRequests = new ArrayList<>();
    }

    public void addTransPoolTripRequest(TransPoolTripRequest transpoolTripRequest) {
        transpoolTripRequests.add(transpoolTripRequest);
    }

    public List<TransPoolTripRequest> getTranspoolTripRequests() {
        return transpoolTripRequests;
    }

    public TransPoolTripRequest getTripRequestByID(int ID) throws NullPointerException {
        return transpoolTripRequests
                .stream()
                .filter(t -> t.getID() == ID)
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public String toString() {
        StringBuilder allRequests = new StringBuilder();
        for (TransPoolTripRequest request : transpoolTripRequests) {
            allRequests.append(request.toString());
            allRequests.append("\n");
        }
        return allRequests.toString();
    }
    public String getDryInfoAsString() {
        StringBuilder transpoolTripsString = new StringBuilder();
        for (TransPoolTripRequest trip : transpoolTripRequests) {
            transpoolTripsString.append(trip.getDryInfoAsString());
            transpoolTripsString.append("\n");
        }
        return transpoolTripsString.toString();
    }
}