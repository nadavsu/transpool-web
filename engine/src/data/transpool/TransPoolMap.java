package data.transpool;

import data.transpool.map.BasicMap;
import data.transpool.time.TimeEngine;
import data.transpool.trip.offer.TripOffersEngine;
import data.transpool.trip.request.TripRequestEngine;

public interface TransPoolMap extends Updatable, BasicMap, TripOffersEngine, TripRequestEngine, TimeEngine {
    String getMapName();
    String getUploaderName();
    MapDetailsDTO getDetails();
    BasicMap getMap();

/*
    BasicMap getMap();
    TripRequestEngine getTripRequestEngine();
    TripOfferEngine getTripOfferEngine();
    TimeEngine getTimeEngine();
*/

}
