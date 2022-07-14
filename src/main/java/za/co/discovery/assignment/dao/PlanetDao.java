package za.co.discovery.assignment.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.assignment.entity.Planet;
import java.util.List;

@Repository
@Transactional
public class PlanetDao {

    private SessionFactory sessionFactory;

    @Autowired
    public PlanetDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Planet planet) {
        Session session = sessionFactory.getCurrentSession();
        session.save(planet);
    }

    public void update(Planet planet) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(planet);
    }

    public int delete(String planetId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM planet AS V WHERE V.planetId = :planetIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("planetIdParameter", planetId);

        return query.executeUpdate();
    }

    public Planet selectUnique(String planetId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Planet.class);
        criteria.add(Restrictions.eq("planetId", planetId));

        return (Planet) criteria.uniqueResult();
    }

    public Planet selectUniqueByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Planet.class);
        criteria.add(Restrictions.eq("name", name));

        return (Planet) criteria.uniqueResult();
    }

    public List<Planet> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Planet.class);
        List<Planet> planets = (List<Planet>) criteria.list();

        return planets;
    }
}
