package com.example.radify_be.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "artists")
@AllArgsConstructor
@NoArgsConstructor
public class ArtistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "f_name", nullable = false)
    private String fName;

    @Column(name = "l_name")
    private String lname;

    @ManyToMany(mappedBy = "artists")
    List<SongEntity> songs;

}
