package data.transpool.time;

import data.transpool.Updatable;
import data.transpool.time.component.TimeDay;
import data.transpool.time.component.TimeInterval;

public interface TimeEngine {
    void incrementTime(TimeInterval interval, Updatable updatable);
    void decrementTime(TimeInterval interval, Updatable updatable);
    void incrementTime(TimeInterval interval);
    void decrementTime(TimeInterval interval);
    TimeDay getCurrentTime();
}
