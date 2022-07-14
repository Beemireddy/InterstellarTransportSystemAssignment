package za.co.discovery.assignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "route")
@Table
public class Route implements Serializable {

    @Id
    @Column
    private long recordId;
    @Column
    private String routeId;
    @Column
    private String source;
    @Column
    private String destination;
    @Column
    private float distance;
    @Column
    private float timeDelay;

    public Route() {
    }

    public Route(long recordId, String routeId, String source, String destination, float distance) {
        this.recordId = recordId;
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Route(long recordId, String routeId, String source, String destination, float distance, float timeDelay) {
        this.recordId = recordId;
        this.routeId = routeId;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.timeDelay = timeDelay;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String edgeId) {
        this.routeId = edgeId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(float timeDelay) {
        this.timeDelay = timeDelay;
    }
}

