package org.example.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.model.Link;
import org.example.model.LinkStats;
import org.example.service.LinkService;
import org.example.service.LinkStatsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path(value = "")
public class LinksShortenerResource {

    private LinkService linkService;
    private LinkStatsService linkStatsService;
    private ObjectMapper mapper;

    @Inject
    public LinksShortenerResource(LinkService linkService,
                                  LinkStatsService linkStatsService) {
        this.linkService = linkService;
        this.linkStatsService = linkStatsService;
        mapper = new ObjectMapper();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("generate")
    public JsonNode generateShortLink(Link link) {
        linkService.create(link);
        ObjectNode shortLink = mapper.createObjectNode();
        shortLink.put("link", "/l/" + link.getShortLink());
        return shortLink;
    }

    @GET
    @Path("l/{short-link}")
    public Response redirectTo(@PathParam("short-link") String shortLink) {
        Link link = linkService.get(shortLink);
        return Response.seeOther(link.getOriginal()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("stats{p:/?}{short-link:([a-zA-Z0-9]*)}")
    public JsonNode getLinkStats(@PathParam("short-link") String shortLink,
                                 @QueryParam("page") Integer page,
                                 @QueryParam("count") Integer count) {
        if (!shortLink.isEmpty()) {
            LinkStats linkStats = linkStatsService.getLinkStats(shortLink);
            ObjectNode linkStatsJson = mapper.createObjectNode();

            linkStatsJson.put("link", "/l/" + linkStats.getLink().getShortLink());
            linkStatsJson.put("original", linkStats.getLink().getOriginal().toString());
            linkStatsJson.put("rank", linkStats.getRank());
            linkStatsJson.put("count", linkStats.getCount());
            return linkStatsJson;
        }

        if (page != null && count != null) {
            List<LinkStats> linkStatsList = linkStatsService.getAll(page, count);
            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addAll(linkStatsList.stream()
                    .map(l -> mapper.createObjectNode()
                            .put("link", "/l/" + l.getLink().getShortLink())
                            .put("original", l.getLink().getOriginal().toString())
                            .put("rank", l.getRank())
                            .put("count", l.getCount()))
                    .collect(Collectors.toList()));

            return arrayNode;
        }

        List<LinkStats> linkStatsList = linkStatsService.getAll();
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.addAll(linkStatsList.stream()
                .map(l -> mapper.createObjectNode()
                        .put("link", "/l/" + l.getLink().getShortLink())
                        .put("original", l.getLink().getOriginal().toString())
                        .put("rank", l.getRank())
                        .put("count", l.getCount()))
                .collect(Collectors.toList()));

        return arrayNode;
    }
}
