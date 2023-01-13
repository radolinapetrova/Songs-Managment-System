package com.example.radify_be.persistence.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "listeners")
@AllArgsConstructor
@NoArgsConstructor
public class ListenersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="song_id", nullable=false)
//    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private SongEntity song;

    @ManyToOne
    @JoinColumn(name="user_id")
//    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private UserEntity listener;

    private String date;
}
