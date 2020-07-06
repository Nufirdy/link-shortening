package org.example.model;

import javax.persistence.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.Objects;

import static org.example.converter.Base62Encoder.fromBase10;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @SequenceGenerator(initialValue = 15018571,
            allocationSize = 1,
            name = "link_sequence",
            sequenceName="link_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_sequence")
    @Column(nullable = false, unique = true)
    private long id;

    @Transient
    private String shortLink = fromBase10(id);

    @Column(nullable = false, length = 512)
    private URI original;

    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "link", orphanRemoval = true)
    @JoinColumn(name = "link_stats_id")
    private LinkStats linkStats;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        shortLink = fromBase10(id);
    }

    public String getShortLink() {
        return shortLink;
    }

    public URI getOriginal() {
        return original;
    }

    public void setOriginal(URI original) {
        this.original = original;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LinkStats getLinkStats() {
        return linkStats;
    }

    public void setLinkStats(LinkStats linkStats) {
        this.linkStats = linkStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link1 = (Link) o;
        return shortLink.equals(link1.shortLink) &&
                original.equals(link1.original);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortLink, original);
    }

    @Override
    public String toString() {
        return "Link{" +
                "id='" + id + '\'' +
                ", link='" + shortLink + '\'' +
                ", original=" + original +
                ", creationDate=" + creationDate +
                '}';
    }

    @PostPersist
    @PostLoad
    public void onEntityPersistOrLoad() {
        shortLink = fromBase10(id);
    }
}
