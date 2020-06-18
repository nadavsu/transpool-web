package data.transpool.time;

import data.transpool.TransPoolData;
import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class Scheduling {
    private ObjectProperty<TimeDay> departureTime;
    private ObjectProperty<TimeDay> arrivalTime;
    private ObjectProperty<Recurrence> recurrences;

    public Scheduling(int dayStart, LocalTime departureTime, int tripDuration, Recurrence recurrences) throws TransPoolDataException {
        this.departureTime = new SimpleObjectProperty<>(new TimeDay(departureTime, dayStart));
        this.recurrences = new SimpleObjectProperty<>(recurrences);
        this.arrivalTime = new SimpleObjectProperty<>();
        setArrivalTime(tripDuration);
    }

    public Scheduling(TimeDay timeStart, TimeDay timeEnd, Recurrence recurrences) {
        this.departureTime = new SimpleObjectProperty<>(timeStart);
        this.arrivalTime = new SimpleObjectProperty<>(timeEnd);
        this.recurrences = new SimpleObjectProperty<>(recurrences);
    }

    public Scheduling(data.jaxb.Scheduling JAXBScheduling, int tripDuration) throws TransPoolDataException {
        this.recurrences = new SimpleObjectProperty<>();
        LocalTime time = LocalTime.of(JAXBScheduling.getHourStart(), 0);
        this.departureTime = new SimpleObjectProperty<>(new TimeDay(time, getDayStart(JAXBScheduling.getDayStart())));
        this.arrivalTime = new SimpleObjectProperty<>();
        setArrivalTime(tripDuration);
        setRecurrences(JAXBScheduling.getRecurrences());
    }

    public Scheduling(Scheduling other) {
        recurrences = new SimpleObjectProperty<>(other.getRecurrences());
        departureTime = new SimpleObjectProperty<>(new TimeDay(other.departureTime.get()));
        arrivalTime = new SimpleObjectProperty<>(new TimeDay(other.arrivalTime.get()));
    }

    private int getDayStart(Integer dayStart) {
        if (dayStart == null) {
            return 1;
        } else {
            return dayStart;
        }
    }

    private void setArrivalTime(int tripDuration) {
        TimeDay arrivalTime = new TimeDay(this.departureTime.get());
        arrivalTime.plus(tripDuration);
        this.arrivalTime.set(arrivalTime);
    }

    private void setRecurrences(String recurrences) {
        switch (recurrences) {
            case "Daily":
                this.recurrences.set(Recurrence.DAILY);
                break;
            case "Bi-daily":
                this.recurrences.set(Recurrence.BI_DAILY);
                break;
            case "Weekly":
                this.recurrences.set(Recurrence.WEEKLY);
                break;
            case "Monthly":
                this.recurrences.set(Recurrence.MONTHLY);
                break;
            default:
                this.recurrences.set(Recurrence.ONE_TIME);
                break;
        }
    }

    public boolean isCurrentlyDeparting() {
        return TransPoolData.currentTime.getTime().equals(departureTime.get().getTime())
                && isHappeningOnDay(TransPoolData.currentTime.getDay());
    }

    public boolean isCurrentlyHappening() {
        return !TransPoolData.currentTime.getTime().isBefore(departureTime.get().getTime())
                && !TransPoolData.currentTime.getTime().isAfter(arrivalTime.get().getTime())
                && isHappeningOnDay(TransPoolData.currentTime.getDay());
    }

    public boolean isHappeningOnDay(int day) {
        return getRecurrences().isOnDay(day, getDayStart());
    }

    public boolean isBefore(TimeDay timeDay) {
        return this.getDepartureTime().isBefore(timeDay);
    }

    public static Scheduling getFirstRecurrenceAfter(Scheduling scheduling, TimeDay timeDay) {
        if (timeDay.isBefore(scheduling.getDepartureTime())) {
            return scheduling;
        } else if (!scheduling.getRecurrences().equals(Recurrence.ONE_TIME)){
            Scheduling nextOccurrence = new Scheduling(scheduling);
            while (nextOccurrence.isBefore(timeDay)) {
                nextOccurrence.setNextRecurrence();
            }
            return nextOccurrence;
        } else {
            return null;
        }
    }

    private void setNextRecurrence() {
        departureTime.get().setNextRecurrence(this.getRecurrences());
        arrivalTime.get().setNextRecurrence(this.getRecurrences());
    }

    public int getDayStart() {
        return departureTime.get().getDay();
    }

    public Recurrence getRecurrences() {
        return recurrences.get();
    }

    public ObjectProperty<Recurrence> recurrencesProperty() {
        return recurrences;
    }

    public TimeDay getDepartureTime() {
        return departureTime.get();
    }

    public ObjectProperty<TimeDay> departureTimeProperty() {
        return departureTime;
    }

    public void setDepartureTime(TimeDay departureTime) {
        this.departureTime.set(departureTime);
    }

    public TimeDay getArrivalTime() {
        return arrivalTime.get();
    }

    public ObjectProperty<TimeDay> arrivalTimeProperty() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return recurrences + " " + departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scheduling)) return false;
        Scheduling that = (Scheduling) o;
        return Objects.equals(recurrences, that.recurrences) &&
                Objects.equals(departureTime, that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recurrences, departureTime);
    }
}
