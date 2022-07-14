package za.co.discovery.assignment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "planet")
@Table
public class Planet implements Serializable {

    @Id
    @Column
    private String planetId;
    @Column
    private String name;

    public Planet() {
    }

    public Planet(String planetId, String name) {
        this.planetId = planetId;
        this.name = name;
    }

    public String getPlanetId() {
        return planetId;
    }

    public void setPlanetId(String vertexId) {
        this.planetId = vertexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((planetId == null) ? 0 : planetId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Planet other = (Planet) obj;
        if (planetId == null) {
            if (other.planetId != null)
                return false;
        } else if (!planetId.equals(other.planetId))
            return false;
        return true;
    }
}