package org.example.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "link_stats")
public class LinkStats {

    @Id
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "link_id", unique = true, nullable = false)
    private Link link;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int count;

    @Formula("({rank_sql_query} = link_id)")
    private int rank;

    public LinkStats() {

    }

    public LinkStats(Link link) {
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkStats linkStats = (LinkStats) o;
        return link.equals(linkStats.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public String toString() {
        return "LinkStats{" +
                "id=" + id +
                ", link=" + link +
                ", count=" + count +
                ", rank=" + rank +
                '}';
    }

    public void incrementCount() {
        count++;
    }
}
