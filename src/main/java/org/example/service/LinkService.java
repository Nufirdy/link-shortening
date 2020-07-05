package org.example.service;

import org.example.model.Link;
import org.example.model.LinkStats;
import org.example.persistence.access.LinkDAO;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import static org.example.utils.Base62ConvertUtil.toBase10;

public class LinkService {

    private LinkDAO linkDAO;

    @Inject
    public LinkService(LinkDAO linkDAO) {
        this.linkDAO = linkDAO;
    }

    public Link get(@NotNull String shortLink) {
        return linkDAO.getById(toBase10(shortLink));
    }

    public void create(@NotNull Link link) {
        link.setLinkStats(new LinkStats(link));
        linkDAO.create(link);
    }
}
