package org.example.persistence.access;

import org.example.model.Link;
import org.example.persistence.HibernateSessionFactoryListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;

@Transactional
public class LinkDAO {

    private SessionFactory sessionFactory = HibernateSessionFactoryListener.getSessionFactory();

    public Link getById(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Link link = session.get(Link.class, id);
        link.getLinkStats().incrementCount();
        session.getTransaction().commit();
        session.close();

        return link;
    }

    public void create(Link link) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(link);
        session.getTransaction().commit();
        session.close();
    }
}
