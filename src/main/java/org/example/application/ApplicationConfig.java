package org.example.application;

import org.example.persistence.access.LinkDAO;
import org.example.persistence.access.LinkStatsDAO;
import org.example.service.LinkService;
import org.example.service.LinkStatsService;
import org.glassfish.hk2.api.Immediate;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;

public class ApplicationConfig extends ResourceConfig {

    @Inject
    public ApplicationConfig(ServiceLocator serviceLocator) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(LinkDAO.class)
                        .to(LinkDAO.class)
                        .in(Immediate.class);
                bind(LinkStatsDAO.class)
                        .to(LinkStatsDAO.class)
                        .in(Immediate.class);
                bind(LinkService.class)
                        .to(LinkService.class);
                bind(LinkStatsService.class)
                        .to(LinkStatsService.class);
            }
        });
        ServiceLocatorUtilities.enableImmediateScope(serviceLocator);
        packages(true, "org.example.resources");
    }
}
