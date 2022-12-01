package com.example.radify_be.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "songs")
@AllArgsConstructor
@NoArgsConstructor
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer seconds;


    @Column(nullable = false)
    public String genre;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(name="song_artists",
            joinColumns = @JoinColumn(
                    name = "song_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "artist_id",
                    referencedColumnName = "id"
            ))
    private List<ArtistEntity> artists;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "songs")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private List<PlaylistEntity> playlists;

}
