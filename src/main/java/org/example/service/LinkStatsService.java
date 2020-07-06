package org.example.service;

import org.example.model.LinkStats;
import org.example.persistence.access.LinkStatsDAO;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import java.util.List;

import static org.example.converter.Base62Encoder.toBase10;

public class LinkStatsService {

    private LinkStatsDAO linkStatsDAO;

    @Inject
    public LinkStatsService(LinkStatsDAO linkStatsDAO) {
        this.linkStatsDAO = linkStatsDAO;
    }

    public LinkStats getLinkStats(@NotNull String shortLink) {
        return linkStatsDAO.getByLinkId(toBase10(shortLink));
    }

    public List<LinkStats> getAll() {
        return linkStatsDAO.getAll();
    }

    public List<LinkStats> getAll(int page, int count) {
        int firstResult = (page - 1) * count;
        int maxResults = Math.min(count, 100);
        return linkStatsDAO.getAll(firstResult, maxResults);
    }
}
