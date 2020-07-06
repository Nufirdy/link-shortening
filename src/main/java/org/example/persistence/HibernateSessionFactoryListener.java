package org.example.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(HibernateSessionFactoryListener.class);

    private static SessionFactory sessionFactory;

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent
                .getServletContext()
                .getAttribute("SessionFactory");
        if(sessionFactory != null && !sessionFactory.isClosed()){
            logger.info("Closing sessionFactory");
            sessionFactory.close();
        }
        logger.info("Released Hibernate sessionFactory resource");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.session_factory.statement_inspector",
                "org.example.persistence.SqlStatementInspector");
        logger.info("Hibernate Configuration created successfully");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .configure()
                .build();
        logger.info("ServiceRegistry created successfully");
        MetadataSources sources = new MetadataSources(serviceRegistry);
        Metadata metadata = sources.getMetadataBuilder()
                .build();
        logger.info("Metadata created successfully");
        sessionFactory = metadata.buildSessionFactory();
        logger.info("SessionFactory created successfully");

        servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
        logger.info("Hibernate SessionFactory Configured successfully");
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}