package za.co.discovery.assignment.service;

import org.springframework.stereotype.Service;
import za.co.discovery.assignment.entity.Planet;
import za.co.discovery.assignment.entity.Route;
import za.co.discovery.assignment.helper.Graph;

import java.util.*;


@Service
public class InterstellarService {

    private List<Planet> vertices;
    private List<Route> routes;
    private Set<Planet> visitedVertices;
    private Set<Planet> unvisitedVertices;
    private Map<Planet, Planet> previousPaths;
    private Map<Planet, Float> distance;

    public InterstellarService() {
    }

    public InterstellarService(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.routes = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.routes = new ArrayList<>(graph.getEdges());
        }
    }

    public void initializePlanets(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.routes = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.routes = new ArrayList<>(graph.getEdges());
        }
    }

    public void run(Planet source) {
        distance = new HashMap<>();
        previousPaths = new HashMap<>();
        visitedVertices = new HashSet<>();
        unvisitedVertices = new HashSet<>();
        distance.put(source, 0f);
        unvisitedVertices.add(source);
        while (unvisitedVertices.size() > 0) {
            Planet currentPlanet = getVertexWithLowestDistance(unvisitedVertices);
            visitedVertices.add(currentPlanet);
            unvisitedVertices.remove(currentPlanet);
            evaluateNeighborsWithMinimalDistances(currentPlanet);
        }
    }

    private Planet getVertexWithLowestDistance(Set<Planet> planets) {
        Planet lowestPlanet = null;
        for (Planet planet : planets) {
            if (lowestPlanet == null) {
                lowestPlanet = planet;
            } else if (getShortestDistance(planet) < getShortestDistance(lowestPlanet)) {
                lowestPlanet = planet;
            }
        }
        return lowestPlanet;
    }

    private void evaluateNeighborsWithMinimalDistances(Planet currentPlanet) {
        List<Planet> adjacentVertices = getNeighbors(currentPlanet);
        for (Planet target : adjacentVertices) {
            float alternateDistance = getShortestDistance(currentPlanet) + getDistance(currentPlanet, target);
            if (alternateDistance < getShortestDistance(target)) {
                distance.put(target, alternateDistance);
                previousPaths.put(target, currentPlanet);
                unvisitedVertices.add(target);
            }
        }
    }

    private List<Planet> getNeighbors(Planet currentPlanet) {
        List<Planet> neighbors = new ArrayList<>();
        for (Route route : routes) {
            Planet destination = fromId(route.getDestination());
            if (route.getSource().equals(currentPlanet.getPlanetId()) && !isVisited(destination)) {
                neighbors.add(destination);
            }
        }
        return neighbors;
    }

    public Planet fromId(final String str) {
        for (Planet v : vertices) {
            if (v.getPlanetId().equalsIgnoreCase(str)) {
                return v;
            }
        }
        Planet islandPlanet = new Planet();
        islandPlanet.setPlanetId(str);
        islandPlanet.setName("Island " + str);
        return islandPlanet;
    }

    private boolean isVisited(Planet planet) {
        return visitedVertices.contains(planet);
    }

    private Float getShortestDistance(Planet destination) {
        Float d = distance.get(destination);
        if (d == null) {
            return Float.POSITIVE_INFINITY;
        } else {
            return d;
        }
    }

    private float getDistance(Planet source, Planet target) {
        for (Route route : routes) {
            if (route.getSource().equals(source.getPlanetId()) && route.getDestination().equals(target.getPlanetId())) {
                return route.getDistance() + route.getTimeDelay();
            }
        }
        throw new RuntimeException("Error: Something went wrong!");
    }

    public LinkedList<Planet> getPath(Planet target) {
        LinkedList<Planet> path = new LinkedList<>();
        Planet step = target;

        if (previousPaths.get(step) == null) {
            return null;
        }
        path.add(step);
        while (previousPaths.get(step) != null) {
            step = previousPaths.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }

}
