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
@Table(name = "playlists")
@AllArgsConstructor
@NoArgsConstructor

public class PlaylistEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isPublic;


    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @OneToOne
    private UserEntity creator;

    @Column(nullable = false)
    private String dateOfCreation;

    @ManyToMany(fetch = FetchType.LAZY)
//    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(name="user_playlists",
    joinColumns = @JoinColumn(
            name = "playlist_id",
            referencedColumnName = "id"
    ),
    inverseJoinColumns = @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    ))
    private List<UserEntity> users;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinTable(name="playlist_songs",
            joinColumns = @JoinColumn(
                    name = "playlist_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "song_id",
                    referencedColumnName = "id"
            ))
    private List<SongEntity> songs;
}
