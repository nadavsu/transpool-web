package data.transpool.map;

import data.jaxb.MapDescriptor;
import exceptions.data.TransPoolDataException;
import exceptions.data.MapDimensionsException;
import exceptions.data.StopDuplicationException;
import exceptions.data.StopOutOfBoundsException;

import java.util.*;

public class TransPoolMap {
    private static final int MAX_MAP_SIZE = 100;
    private static final int MIN_MAP_SIZE = 6;

    private int width;
    private int length;

    private List<Stop> stopList = new ArrayList<>();
    private List<Path> pathList = new ArrayList<>();

    private Set<String> stopNameSet = new HashSet<>();
    private Set<Coordinates> stopCoordinates = new HashSet<>();

    public TransPoolMap() {
        width = MIN_MAP_SIZE;
        length = MIN_MAP_SIZE;
    }

    public TransPoolMap(MapDescriptor JAXBMap) throws TransPoolDataException {
        setWidth(JAXBMap.getMapBoundries().getWidth());
        setLength(JAXBMap.getMapBoundries().getLength());

        List<data.jaxb.Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for (data.jaxb.Stop JAXBStop : JAXBStopsList) {
            addStop(new Stop(JAXBStop));
        }

        List<data.jaxb.Path> JAXBPathList = JAXBMap.getPaths().getPath();
        for (data.jaxb.Path JAXBPath : JAXBPathList) {
            addPath(new Path(JAXBPath, this));
        }

    }

    public int getWidth() {
        return width;
    }

    private void setWidth(int width) throws MapDimensionsException {
        if (width < MIN_MAP_SIZE || width > MAX_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    private void setLength(int length) throws MapDimensionsException {
        if (length < MIN_MAP_SIZE || length > MAX_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.length = length;
    }

    public Set<Coordinates> getStopCoordinatesSet() {
        return stopCoordinates;
    }

    public List<Path> getPathList() {
        return pathList;
    }

    public Stop getStop(String stopName) throws NullPointerException {
        return stopList
                .stream()
                .filter(s -> s.getName().equals(stopName))
                .findAny()
                .orElseThrow(NullPointerException::new);
    }

    private void addStop(Stop stop) throws StopOutOfBoundsException, StopDuplicationException {
        if (!stopNameSet.add(stop.getName())) {
            throw new StopDuplicationException();
        }
        if (!stopCoordinates.add(stop.getCoordinates())) {
            throw new StopDuplicationException();
        }
        if (!stop.isInBoundsOf(width, length)) {
            throw new StopOutOfBoundsException();
        }
        stopList.add(stop);
    }

    private void addPath(Path path) {
        pathList.add(path);
        if (!path.isOneWay()) {
            Path swappedPath = new Path(path);
            swappedPath.setSource(path.getDestination());
            swappedPath.setDestination(path.getSource());
            pathList.add(swappedPath);
        }
    }

    public Path getPathByStopNames(String source, String destination) throws NullPointerException {
        return pathList
                .stream()
                .filter(path -> path.getSource().getName().equals(source) && path.getDestination().getName().equals(destination))
                .findAny()
                .orElseThrow(NullPointerException::new);
    }
}