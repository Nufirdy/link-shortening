package org.example.persistence.access;

import org.example.model.LinkStats;
import org.example.persistence.HibernateSessionFactoryListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class LinkStatsDAO {

    private SessionFactory sessionFactory = HibernateSessionFactoryListener.getSessionFactory();

    public LinkStats getByLinkId(long linkId) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        LinkStats linkStats = session.get(LinkStats.class, linkId);
        session.getTransaction().commit();
        session.close();

        return linkStats;
    }

    public List<LinkStats> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<LinkStats> criteria = builder.createQuery(LinkStats.class);
        Root<LinkStats> root = criteria.from(LinkStats.class);
        criteria.select(root);
        criteria.orderBy(builder.desc(root.get("count")));

        Query<LinkStats> query = session.createQuery(criteria);
        List<LinkStats> resultList = query.getResultList();

        session.getTransaction().commit();
        session.close();

        return resultList;
    }

    public List<LinkStats> getAll(int firstResult, int maxResults) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<LinkStats> criteria = builder.createQuery(LinkStats.class);
        Root<LinkStats> root = criteria.from(LinkStats.class);
        criteria.select(root);
        criteria.orderBy(builder.desc(root.get("count")));
        TypedQuery<LinkStats> typedQuery = session.createQuery(criteria);

        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(maxResults);

        List<LinkStats> resultList = typedQuery.getResultList();

        session.getTransaction().commit();
        session.close();

        return resultList;
    }
}
