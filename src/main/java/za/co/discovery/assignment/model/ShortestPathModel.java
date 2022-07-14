package za.co.discovery.assignment.model;

import java.io.Serializable;

public class ShortestPathModel implements Serializable {

    private String selectedPlanet;
    private String selectedPlanetName;
    private String planetId;
    private String planetName;
    private String thePath;
    private String sourcePlanet;
    private String destinationPlanet;
    private boolean undirectedGraph;
    private boolean trafficAllowed;

    public String getSelectedPlanet() {
        return selectedPlanet;
    }

    public void setSelectedPlanet(String selectedPlanet) {
        this.selectedPlanet = selectedPlanet;
    }

    public String getPlanetId() {
        return planetId;
    }

    public void setPlanetId(String planetId) {
        this.planetId = planetId;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public String getThePath() {
        return thePath;
    }

    public void setThePath(String thePath) {
        this.thePath = thePath;
    }

    public String getSelectedPlanetName() {
        return selectedPlanetName;
    }

    public void setSelectedPlanetName(String selectedPlanetName) {
        this.selectedPlanetName = selectedPlanetName;
    }

    public String getSourcePlanet() {
        return sourcePlanet;
    }

    public void setSourcePlanet(String sourcePlanet) {
        this.sourcePlanet = sourcePlanet;
    }

    public String getDestinationPlanet() {
        return destinationPlanet;
    }

    public void setDestinationPlanet(String destinationPlanet) {
        this.destinationPlanet = destinationPlanet;
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
}
