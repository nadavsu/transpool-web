package data.transpool.trip;

import data.transpool.user.TransPoolRider;

import java.time.LocalTime;
import java.util.Objects;

public class TransPoolTripRequest {
    private static int IDGenerator = 20000;
    private int ID;
    private TransPoolRider transpoolRider;
    private String source;
    private String destination;
    private LocalTime time;
    private boolean isArrivalTime;
    private boolean isContinuous;

    public TransPoolTripRequest(String transpoolRider, String source, String destination, LocalTime time, boolean isArrivalTime, boolean isContinuous) {
        this.ID = IDGenerator++;
        this.transpoolRider = new TransPoolRider(transpoolRider);
        this.source = source;
        this.destination = destination;
        this.time = time;
        this.isArrivalTime = isArrivalTime;
        this.isContinuous = isContinuous;
    }

    public TransPoolRider getTranspoolRider() {
        return (TransPoolRider) transpoolRider;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getTimeOfDeparture() {
        return time;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        String requestString = "";
        requestString += "Request ID:        " + ID + "\n";
        requestString += "Name of requester: " + transpoolRider + "\n";
        requestString += "Stop source:       " + source + "\n";
        requestString += "Stop destination:  " + destination + "\n";
        requestString += "Time:              " + time + "\n";
        requestString += "Time of arrival?   " + isArrivalTime;

        return requestString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransPoolTripRequest)) return false;
        TransPoolTripRequest that = (TransPoolTripRequest) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
