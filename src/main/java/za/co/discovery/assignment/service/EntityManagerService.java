package za.co.discovery.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.assignment.dao.RouteDao;
import za.co.discovery.assignment.dao.TrafficDao;
import za.co.discovery.assignment.dao.PlanetDao;
import za.co.discovery.assignment.entity.Planet;
import za.co.discovery.assignment.entity.Route;
import za.co.discovery.assignment.entity.Traffic;
import za.co.discovery.assignment.helper.Graph;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
public class EntityManagerService {
    private static final String EXCEL_FILENAME = "/interstellar.xlsx";
    private PlanetDao planetDao;
    private RouteDao routeDao;
    private TrafficDao trafficDao;

    @Autowired
    public EntityManagerService(PlanetDao planetDao, RouteDao routeDao, TrafficDao trafficDao) {
        this.planetDao = planetDao;
        this.routeDao = routeDao;
        this.trafficDao = trafficDao;
    }

    public void persistGraph() {
        URL resource = getClass().getResource(EXCEL_FILENAME);
        File file1;
        try {
            file1 = new File(resource.toURI());
            persistGraph(file1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void persistGraph(File file) {
        XLSXHandler handler = new XLSXHandler(file);

        List<Planet> vertices = handler.readPlanets();
        if (vertices != null && !vertices.isEmpty()) {
            for (Planet v : vertices) {
                planetDao.save(v);
            }
        }
        List<Route> routes = handler.readRoutes();
        if (routes != null && !routes.isEmpty()) {
            for (Route e : routes) {
                routeDao.save(e);
            }
        }
        List<Traffic> traffic = handler.readTraffics();
        if (routes != null && !routes.isEmpty()) {
            for (Traffic t : traffic) {
                trafficDao.save(t);
            }
        }
    }

    public Graph selectGraph() {
        List<Planet> vertices = planetDao.selectAll();
        List<Route> routes = routeDao.selectAll();
        List<Traffic> traffics = trafficDao.selectAll();

        Graph graph = new Graph(vertices, routes, traffics);

        return graph;
    }


    public List<Planet> getAllPlanets() {
        return planetDao.selectAll();
    }

    public Planet getPlanetByName(String name) {
        return planetDao.selectUniqueByName(name);
    }

    public Planet getPlanetById(String planetId) {
        return planetDao.selectUnique(planetId);
    }


    public List<Route> getAllRoutes() {
        return routeDao.selectAll();
    }

    public Route getRouteById(long recordId) {
        return routeDao.selectUnique(recordId);
    }

    public long getRouteMaxRecordId() {
        return routeDao.selectMaxRecordId();
    }

    public boolean routeExists(Route route) {
        List<Route> routes = routeDao.routeExists(route);
        return !routes.isEmpty();
    }

    public Traffic saveTraffic(Traffic traffic) {
        trafficDao.save(traffic);
        return traffic;
    }

    public Traffic updateTraffic(Traffic traffic) {
        trafficDao.update(traffic);
        return traffic;
    }

    public boolean deleteTraffic(String routeId) {
        trafficDao.delete(routeId);
        return true;
    }

    public List<Traffic> getAllTraffics() {
        return trafficDao.selectAll();
    }

    public Traffic getTrafficById(String routeId) {
        return trafficDao.selectUnique(routeId);
    }

    public long getTrafficMaxRecordId() {
        return trafficDao.selectMaxRecordId();
    }

    public boolean trafficExists(Traffic traffic) {
        List<Traffic> traffics = trafficDao.trafficExists(traffic);
        return !traffics.isEmpty();
    }
}
