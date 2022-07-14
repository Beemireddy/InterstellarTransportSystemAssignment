package za.co.discovery.assignment.helper;

import za.co.discovery.assignment.entity.Planet;
import za.co.discovery.assignment.entity.Route;
import za.co.discovery.assignment.entity.Traffic;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Planet> planets;
    private List<Route> routes;
    private List<Traffic> traffics;
    private boolean undirectedGraph;
    private boolean trafficAllowed;

    public Graph(List<Planet> planets, List<Route> routes, List<Traffic> traffics) {
        this.planets = planets;
        this.routes = routes;
        this.traffics = traffics;
    }

    public List<Traffic> getTraffics() {
        return traffics;
    }

    public List<Planet> getVertexes() {
        return planets;
    }

    public List<Route> getEdges() {
        return routes;
    }

    public boolean isUndirectedGraph() {
        return undirectedGraph;
    }

    public void setUndirectedGraph(boolean undirectedGraph) {
        this.undirectedGraph = undirectedGraph;
    }

    public boolean isTrafficAllowed() {
        return trafficAllowed;
    }

    public void setTrafficAllowed(boolean trafficAllowed) {
        this.trafficAllowed = trafficAllowed;
    }

    public void processTraffics() {
        if (traffics != null && !traffics.isEmpty()) {
            for (Traffic traffic : traffics) {
                for (Route route : routes) {
                    if (checkObjectsEqual(route.getRouteId(), traffic.getRouteId())) {
                        if (checkObjectsEqual(route.getSource(), traffic.getSource()) && checkObjectsEqual(route.getDestination(), traffic.getDestination())) {
                            route.setTimeDelay(traffic.getDelay());
                        }
                    }
                }
            }
        }
    }

    public List<Route> getUndirectedEdges() {
        List<Route> undirectedRoutes = new ArrayList();
        for (Route fromRoute : routes) {
            Route toRoute = copyAdjacentEdge(fromRoute);
            undirectedRoutes.add(fromRoute);
            undirectedRoutes.add(toRoute);
        }
        return undirectedRoutes;
    }

    public Route copyAdjacentEdge(Route fromRoute) {
        Route toRoute = new Route();
        toRoute.setRouteId(fromRoute.getRouteId());
        toRoute.setSource(fromRoute.getDestination());
        toRoute.setDestination(fromRoute.getSource());
        toRoute.setDistance(fromRoute.getDistance());
        toRoute.setTimeDelay(fromRoute.getTimeDelay());
        return toRoute;
    }

    public boolean checkObjectsEqual(Object object, Object otherObject) {
        if (object == null && otherObject == null) {
            //Both objects are null
            return true;
        } else if (object == null || otherObject == null) {
            //One of the objects is null
            return false;
        } else if (object instanceof String && otherObject instanceof String) {
            return ((String) object).equalsIgnoreCase((String) otherObject);
        } else {
            //Both objects are not null
            return object.equals(otherObject);
        }

    }
}
