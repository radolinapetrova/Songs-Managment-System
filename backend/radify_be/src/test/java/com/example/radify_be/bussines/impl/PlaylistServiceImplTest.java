package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.PlaylistRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceImplTest {

    @Mock
    private PlaylistRepo playlistRepoMock;

    @InjectMocks
    private PlaylistServiceImpl service;


    private List<Playlist> getMockData() {
        User user = User.builder().id(1).fName("Radka").build();

        Playlist playlist = Playlist.builder().id(1).title("Da go duhat bednite").isPublic(false).creator(user).users(List.of(user)).dateOfCreation(new Date()).build();
        Playlist playlist3 = Playlist.builder().id(3).title("Hrana za prostoludieto").isPublic(true).creator(user).users(List.of(user)).dateOfCreation(new Date()).build();

        return List.of(playlist3, playlist);
    }

    @Test
    void getPlaylistSongs() {


    }


    //HAPPY FLOW
    @Test
    void createPlaylist_shouldReturnTheNewPlaylist() {

        Playlist playlist = getMockData().get(0);

        when(playlistRepoMock.save(any(Playlist.class)))
                .thenReturn(playlist);

        Playlist result = service.createPlaylist(playlist);

        assertEquals(result, playlist);

        verify(playlistRepoMock).save(playlist);

    }

    //HAPPY FLOW
    @Test
    void getUserPlaylist_shouldReturnUsersPlaylists() {

        List<Playlist> playlists = getMockData();


        when(playlistRepoMock.getAllByUserId(1))
                .thenReturn(playlists);

        List<Playlist> results = service.getUserPlaylists(1);

        assertEquals(playlists, results);

        verify(playlistRepoMock).getAllByUserId(1);
    }

    //UNHAPPY FLOW
    @Test
    void getUserPlaylist_shouldReturnNull_WhenUserDoesntHavePlaylists() {

        when(playlistRepoMock.getAllByUserId(2))
                .thenReturn(null);

        List<Playlist> results = service.getUserPlaylists(2);

        assertEquals(null, results);

        verify(playlistRepoMock).getAllByUserId(2);
    }


    //HAPPY FLOW
    @Test
    void deletePlaylist_shouldReturnNull_whenCheckedForExistById() {

        List<Playlist> playlists = getMockData();


        when(playlistRepoMock.findById(any(Integer.class)))
                .thenReturn(null);

        //playlist with id 1
        service.deletePlaylist(1);


        Playlist result = service.findById(1);

        assertEquals(null, result);

        assertDoesNotThrow(() -> service.deletePlaylist(1));
    }



    //UNHAPPY FLOW
    @Test
    void deletePlaylist_shouldThrowException() throws UnauthorizedAction {
        List<Playlist> playlists = getMockData();
        when(playlistRepoMock.existsById(any(Integer.class)))
                .thenReturn(true);

         assertThrows(UnauthorizedAction.class, () -> service.deletePlaylist(2));

         //Boolean result = service.ex

        //verify(playlistRepoMock).deleteById(1);
    }

    @Test
    void addSongToPlaylist() {

    }


    //HAPPY FLOW
    @Test
    void findById_shouldReturnThePlaylist() {
        User user = User.builder().id(1).fName("Radka").build();
        List<User> users = List.of(user);

        Playlist playlist = Playlist.builder().id(1).title("Da go duhat bednite").isPublic(true).creator(user).users(users).dateOfCreation(new Date()).build();

        when(playlistRepoMock.findById(1))
                .thenReturn(playlist);

        Playlist result = service.findById(1);

        assertEquals(playlist, result);

        verify(playlistRepoMock).findById(1);

    }

    //UNHAPPY FLOW
    @Test
    void findById_shouldReturnNull_whenThePlaylistDoesntExist() {

        when(playlistRepoMock.findById(any(Integer.class)))
                .thenReturn(null);

        Playlist result = service.findById(1);

        assertEquals(null, result);

        verify(playlistRepoMock).findById(1);
    }



    //HAPPY FLOW
    @Test
    void getAllPublicAndUser_shouldReturnAllPublicAndUsersPlaylist() {
        //Get mock playlists, that include one public and one private user playlist
        List<Playlist> playlists = getMockData();

        when(playlistRepoMock.getAllPublicAndUser(1))
                .thenReturn(playlists);

        List<Playlist> results = service.getAllPublicAndUser(1);

        assertEquals(results, playlists);

        verify(playlistRepoMock).getAllPublicAndUser(1);
    }

    //KINDA UNHAPPY FLOW
    @Test
    void getAllPublicAndUser_shouldReturnAllPublicPlaylists_whenUserIsNotAuthorized() {
        //Get the only public playlist in the collection
        List<Playlist> playlists = List.of(getMockData().get(1));

        //Pass zero as the user id, because they are not authorized
        when(playlistRepoMock.getAllPublicAndUser(0))
                .thenReturn(playlists);

        List<Playlist> results = service.getAllPublicAndUser(0);

        assertEquals(results, playlists);

        verify(playlistRepoMock).getAllPublicAndUser(0);
    }
}