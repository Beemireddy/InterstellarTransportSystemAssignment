package za.co.discovery.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import za.co.discovery.assignment.entity.Planet;
import za.co.discovery.assignment.helper.Graph;
import za.co.discovery.assignment.model.ShortestPathModel;
import za.co.discovery.assignment.service.EntityManagerService;
import za.co.discovery.assignment.service.InterstellarService;

import java.util.LinkedList;
import java.util.List;

@Controller
public class InterstellarController {

    private static final String PATH_NOT_AVAILABLE = "Unavailable.";
    private static final String PATH_NOT_NEEDED = "Not needed. You are already on planet ";
    private static final String NO_PLANET_FOUND = "No planet found.";
    private EntityManagerService entityManagerService;
    private InterstellarService interstellarService;



    @RequestMapping(value = "/interstellar", method = RequestMethod.GET)
    public String shortest(Model model) {
        ShortestPathModel pathModel = new ShortestPathModel();
        List<Planet> allPlanets = entityManagerService.getAllPlanets();
        if (allPlanets == null || allPlanets.isEmpty()) {
            model.addAttribute("validationMessage", NO_PLANET_FOUND);
            return "validation";
        }
        Planet origin = allPlanets.get(0);
        pathModel.setPlanetName(origin.getName());
        model.addAttribute("interstellar", pathModel);
        model.addAttribute("pathList", allPlanets);
        return "interstellar";
    }

    @RequestMapping(value = "/interstellar", method = RequestMethod.POST)
    public String shortestSubmit(@ModelAttribute ShortestPathModel pathModel, Model model) {

        StringBuilder path = new StringBuilder();
        Graph graph = entityManagerService.selectGraph();
        if (pathModel.isTrafficAllowed()) {
            graph.setTrafficAllowed(true);
        }
        if (pathModel.isUndirectedGraph()) {
            graph.setUndirectedGraph(true);
        }
        interstellarService.initializePlanets(graph);
        Planet source = entityManagerService.getPlanetByName(pathModel.getPlanetName());
        Planet destination = entityManagerService.getPlanetById(pathModel.getSelectedPlanet());
        //
        interstellarService.run(source);
        LinkedList<Planet> paths = interstellarService.getPath(destination);
        if (paths != null) {
            for (Planet v : paths) {
                path.append(v.getName() + " (" + v.getPlanetId() + ")");
                path.append("\t");
            }
        } else if (source != null && destination != null && source.getPlanetId().equals(destination.getPlanetId())) {
            path.append(PATH_NOT_NEEDED + source.getName());
        } else {
            path.append(PATH_NOT_AVAILABLE);
        }
        pathModel.setThePath(path.toString());
        pathModel.setSelectedPlanetName(destination.getName());
        model.addAttribute("interstellar", pathModel);
        return "result";
    }


    @Autowired
    public InterstellarController(EntityManagerService entityManagerService, InterstellarService interstellarService) {
        this.entityManagerService = entityManagerService;
        this.interstellarService = interstellarService;
    }

    @RequestMapping(value = "/planets", method = RequestMethod.GET)
    public String listVertices(Model model) {
        List allPlanets = entityManagerService.getAllPlanets();
        model.addAttribute("planets", allPlanets);
        return "planets";
    }

    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public String listRoutes(Model model) {
        List allRoutes = entityManagerService.getAllRoutes();
        model.addAttribute("routes", allRoutes);
        return "routes";
    }

}

