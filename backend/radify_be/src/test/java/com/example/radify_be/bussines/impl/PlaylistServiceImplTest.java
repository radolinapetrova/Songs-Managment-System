package com.example.radify_be.bussines.impl;

import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.PlaylistRepo;
import com.example.radify_be.persistence.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceImplTest {

    @Mock
    private PlaylistRepo playlistRepoMock;

    @InjectMocks
    private PlaylistServiceImpl service;


    private List<Playlist> getMockData(){
        User user = User.builder().id(1).fName("Radka").build();
        User user2 = User.builder().id(2).fName("Stela").build();

        Playlist playlist = Playlist.builder().id(1).title("Da go duhat bednite").isPublic(false).creator(user).users(List.of(user)).dateOfCreation(new Date()).build();
        Playlist playlist2 = Playlist.builder().id(2).title("Luksat me uspokoqva").isPublic(false).creator(user).users(List.of(user2)).dateOfCreation(new Date()).build();
        Playlist playlist3 = Playlist.builder().id(3).title("Hrana za prostoludieto").isPublic(true).creator(user).users(List.of(user)).dateOfCreation(new Date()).build();

        return List.of(playlist3, playlist);
    }

    @Test
    void getPlaylistSongs() {

    }

    @Test
    void createPlaylist() {
        User user = User.builder().id(1).fName("Radka").build();
        List<User> users = new ArrayList<>();
        users.add(user);
        Playlist playlist = Playlist.builder().id(1).title("Da go duhat bednite").isPublic(true).creator(user).users(users).dateOfCreation(new Date()).build();

        when(playlistRepoMock.save(playlist))
                .thenReturn(playlist);

        Playlist result = service.createPlaylist(playlist);

        assertEquals(result, playlist);

        verify(playlistRepoMock).save(playlist);

    }

    @Test
    void getUserPlaylists() {

        List<Playlist> playlists = getMockData();

        when(playlistRepoMock.getAllByUserId(1))
                .thenReturn(playlists);

        List<Playlist> results = service.getUserPlaylists(1);

        assertEquals(playlists, results);

        verify(playlistRepoMock).getAllByUserId(1);

    }

    @Test
    void deletePlaylist() {


    }

    @Test
    void addSongToPlaylist() {
    }

    @Test
    void findById() {


    }



    @Test
    void getAllPublicAndUser() {
        List<Playlist> playlists = getMockData();

        when(playlistRepoMock.getAllPublicAndUser(1))
                .thenReturn(playlists);

        List<Playlist> results = service.getAllPublicAndUser(1);

        assertEquals(results, playlists);

        verify(playlistRepoMock).getAllPublicAndUser(1);
    }
}