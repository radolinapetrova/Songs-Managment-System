package com.example.radify_be.persistence.impl;

import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.DBRepositories.PlaylistDBRepository;
import com.example.radify_be.persistence.DBRepositories.SongDBRepository;
import com.example.radify_be.persistence.DBRepositories.UserDBRepository;
import com.example.radify_be.persistence.PlaylistRepo;
import com.example.radify_be.persistence.entities.PlaylistEntity;
import com.example.radify_be.persistence.entities.SongEntity;
import com.example.radify_be.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlaylistRepoImpl implements PlaylistRepo {

    private final PlaylistDBRepository repo;
    private final UserDBRepository userRepo;

    private final SongDBRepository songRepo;


    private PlaylistEntity playlistEntityConverter(Playlist playlist) {
        //List<Integer> songs = playlist.getSongs().stream().map(Song::getId).collect(Collectors.toList());

       return PlaylistEntity.builder()
                .id(playlist.getId())
                .title(playlist.getTitle())
                .isPublic(playlist.isPublic())
                //.songs(songs.stream().map(id -> SongEntity.builder().id(id).build()).collect(Collectors.toList()))
                .creator(UserEntity.builder().id(playlist.getCreator().getId()).build())
                .dateOfCreation(convert(playlist.getDateOfCreation())).build();
    }

    private Playlist playlistConverter(PlaylistEntity playlist) {

        List<Integer> songs = playlist.getSongs().stream().map(SongEntity::getId).collect(Collectors.toList());

        try {
            return Playlist.builder()
                    .id(playlist.getId())
                    .title(playlist.getTitle())
                    .isPublic(playlist.isPublic())
                    .creator(User.builder().id(playlist.getCreator().getId()).build())
                    .dateOfCreation(convert(playlist.getDateOfCreation()))
                    .songs(songs.stream().map(id -> Song.builder().id(id).build()).collect(Collectors.toList()))
                    .build();

        } catch (ParseException e) {
            return null;
        }

    }

    private String convert(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private Date convert(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }

    @Override
    public Playlist save(Playlist playlist) {
        PlaylistEntity entity = playlistEntityConverter(playlist);
        List<UserEntity> users = List.of(userRepo.findById(entity.getCreator().getId()).orElse(null));
        entity.setUsers(users);

         return playlistConverter(repo.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repo.existsById(id);
    }

    @Override
    public List<Playlist> getAllByUserId(Integer id) {
        List<PlaylistEntity> playlists = repo.getReferencesByUsersId(id);
        List<Playlist> playlists1 = new ArrayList<>();
        for (PlaylistEntity pl : playlists) {
            playlists1.add(playlistConverter(pl));
        }
        return playlists1;
    }

    @Override
    public List<Playlist> getAllPublicAndUser(Integer id){
        List<Playlist> playlists = new ArrayList<>();

        for (PlaylistEntity pl : repo.getReferencesByUsersIdOrIsPublic(id, true)){
            playlists.add(playlistConverter(pl));
        }

        return playlists;
    }


    @Override
    public Playlist findById(Integer id) {
        return playlistConverter(repo.findById(id).orElse(null));
    }


    @Override
    public List<Playlist> findByTitle(String title, Integer id){

        List<Playlist> playlists = new ArrayList<>();

        for (PlaylistEntity pl : repo.getReferencesByTitleContainingOrUsersIdOrIsPublic(title, id, true)){
            playlists.add(playlistConverter(pl));
        }
        return playlists;
    }


    
    @Override
    public void update(Integer playlistId, Integer songId){

        PlaylistEntity playlist = repo.findById(playlistId).orElse(null);

        SongEntity song = songRepo.findById(songId).orElse(null);

        List<SongEntity> songs = playlist.getSongs();

        if(!songs.stream().anyMatch(s -> s.getId().equals(songId))){
            songs.add(song);

            playlist.setSongs(songs);

            repo.save(playlist);
        }
    }


    @Override
    public void delete(Integer playlistId, Integer songId){
        PlaylistEntity playlist = repo.findById(playlistId).orElse(null);

        SongEntity song = songRepo.findById(songId).orElse(null);

        List<SongEntity> songs = playlist.getSongs();

        if(songs.stream().anyMatch(s -> s.getId().equals(songId))){
            songs.remove(song);

            playlist.setSongs(songs);

            repo.save(playlist);
        }
    }

}
