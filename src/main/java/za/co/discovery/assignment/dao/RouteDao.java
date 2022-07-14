package za.co.discovery.assignment.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import za.co.discovery.assignment.entity.Route;
import java.util.List;

@Repository
@Transactional
public class RouteDao {

    private SessionFactory sessionFactory;

    @Autowired
    public RouteDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(Route route) {
        Session session = sessionFactory.getCurrentSession();
        session.save(route);
    }

    public void update(Route route) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(route);
    }

    public int delete(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM route AS E WHERE E.recordId = :recordIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("recordIdParameter", recordId);

        return query.executeUpdate();
    }

    public Route selectUnique(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Route.class);
        criteria.add(Restrictions.eq("recordId", recordId));

        return (Route) criteria.uniqueResult();
    }

    public long selectMaxRecordId() {
        long maxId = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Route.class)
                .setProjection(Projections.max("recordId")).uniqueResult();

        return maxId;
    }

    public List<Route> routeExists(Route route) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Route.class);
        criteria.add(Restrictions.ne("recordId", route.getRecordId()));
        criteria.add(Restrictions.eq("source", route.getSource()));
        criteria.add(Restrictions.eq("destination", route.getDestination()));
        List<Route> routes = (List<Route>) criteria.list();

        return routes;
    }

    public List<Route> selectAllByRecordId(long recordId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Route.class);
        criteria.add(Restrictions.eq("recordId", recordId));
        List<Route> routes = (List<Route>) criteria.list();

        return routes;
    }

    public List<Route> selectAllByrouteId(String routeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Route.class);
        criteria.add(Restrictions.eq("routeId", routeId));
        List<Route> routes = (List<Route>) criteria.list();

        return routes;
    }

    public List<Route> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Route.class);
        List<Route> routes = (List<Route>) criteria.list();
        return routes;
    }
}

