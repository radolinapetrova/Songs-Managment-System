package com.example.radify_be.bussines.impl;

import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.PlaylistRepo;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PlaylistServiceImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
class PlaylistServiceImplTest {

    @MockBean
    private PlaylistRepo playlistRepo;

    @Autowired
    private PlaylistServiceImpl playlistServiceImpl;

    @Mock
    private PlaylistRepo playlistRepoMock;

    @InjectMocks
    private PlaylistServiceImpl service;


    @org.junit.Test
    public void testGetPlaylistSongs() {
        assertNull(playlistServiceImpl.getPlaylistSongs(123));
    }

    private List<Playlist> getMockData() {
        User user = User.builder().id(1).fName("Radka").build();

        Playlist playlist = Playlist.builder().id(1).title("Da go duhat bednite").isPublic(false).creator(user).users(List.of(user)).dateOfCreation(new Date()).build();
        Playlist playlist3 = Playlist.builder().id(3).title("Hrana za prostoludieto").isPublic(true).creator(user).users(List.of(user)).dateOfCreation(new Date()).build();

        return List.of(playlist3, playlist);
    }




    @org.junit.Test
    void createPlaylist_shouldReturnTheNewPlaylist() {
        Playlist playlist = getMockData().get(0);
        when(playlistRepoMock.save(any(Playlist.class)))
                .thenReturn(playlist);
        Playlist result = service.createPlaylist(playlist);
        assertEquals(result, playlist);
        verify(playlistRepoMock).save(playlist);

    }


    @org.junit.Test
    public void testGetUserPlaylists_whenUserHasNoPlaylists() {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        when(playlistRepo.getAllByUserId((Integer) org.mockito.Mockito.any())).thenReturn(playlistList);
        List<Playlist> actualUserPlaylists = playlistServiceImpl.getUserPlaylists(1);
        assertSame(playlistList, actualUserPlaylists);
        assertTrue(actualUserPlaylists.isEmpty());
        verify(playlistRepo).getAllByUserId((Integer) org.mockito.Mockito.any());
    }

    @org.junit.Test
    void getUserPlaylist_shouldReturnUsersPlaylists() {

        List<Playlist> playlists = getMockData();
        when(playlistRepoMock.getAllByUserId(1))
                .thenReturn(playlists);
        List<Playlist> results = service.getUserPlaylists(1);
        assertEquals(playlists, results);
        verify(playlistRepoMock).getAllByUserId(1);
    }


    @org.junit.Test
    void getUserPlaylist_shouldReturnNull_WhenUserDoesntHavePlaylists() {
        when(playlistRepoMock.getAllByUserId(2))
                .thenReturn(null);
        List<Playlist> results = service.getUserPlaylists(2);
        assertEquals(null, results);
        verify(playlistRepoMock).getAllByUserId(2);
    }


//    @org.junit.Test
//    void deletePlaylist_shouldReturnNull_whenCheckedForExistById() {
//        when(playlistRepoMock.findById(any(Integer.class)))
//                .thenReturn(null);
//        //playlist with id 1
//        service.deletePlaylist(1);
//        Playlist result = service.findById(1);
//        assertEquals(null, result);
//        assertDoesNotThrow(() -> service.deletePlaylist(1));
//    }
//

//    @org.junit.Test
//    public void testDeletePlaylist_shoulSuccessfullyDeletePlaylist() throws RuntimeException {
//        doNothing().when(playlistRepo).deleteById((Integer) org.mockito.Mockito.any());
//        playlistServiceImpl.deletePlaylist(1);
//        verify(playlistRepo).deleteById((Integer) org.mockito.Mockito.any());
//    }



//    @org.junit.Test
//    public void testAddSongToPlaylist_shoulAddSongsToThePlaylist() throws RuntimeException {
//        doNothing().when(playlistRepo).update((Integer) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
//        playlistServiceImpl.addSongToPlaylist(2, 2);
//        verify(playlistRepo).update((Integer) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
//    }

//
//    @org.junit.Test
//    public void testAddSongToPlaylist_shouldThrowException_whenDataIsInvalid() throws RuntimeException {
//        doThrow(new RuntimeException()).when(playlistRepo)
//                .update((Integer) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
//        assertThrows(RuntimeException.class, () -> playlistServiceImpl.addSongToPlaylist(2, 2));
//        verify(playlistRepo).update((Integer) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
//    }
//
//
//    @org.junit.Test
//    public void testRemoveSongsFromPlaylist_shoulSuccessfullyDeleteTheSongs() {
//        doNothing().when(playlistRepo).delete((Integer) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
//        playlistServiceImpl.removeSongsFromPlaylist(1, 1);
//        verify(playlistRepo).delete((Integer) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
//    }


    @Test
    void findById_shouldReturnThePlaylist() {
        User user = User.builder().id(1).fName("Radka").build();
        List<User> users = List.of(user);
        Playlist playlist = Playlist.builder().id(1).title("Dr").isPublic(true).creator(user).users(users).dateOfCreation(new Date()).build();
        when(playlistRepoMock.findById(1)) .thenReturn(playlist);
        Playlist result = service.findById(1);
        assertEquals(playlist, result);
        verify(playlistRepoMock).findById(1);
    }


    //UNHAPPY FLOW
    @Test
    void findById_shouldReturnNull_whenThePlaylistDoesntExist() {
        when(playlistRepoMock.findById(any(Integer.class))).thenReturn(null);
        Playlist result = service.findById(1);
        assertEquals(null, result);
        verify(playlistRepoMock).findById(1);
    }


    @org.junit.Test
    void getAllPublicAndUser_shouldReturnAllPublicAndUsersPlaylist() {
        List<Playlist> playlists = getMockData();
        when(playlistRepoMock.getAllPublicAndUser(1)).thenReturn(playlists);
        List<Playlist> results = service.getAllPublicAndUser(1);
        assertEquals(results, playlists);
        verify(playlistRepoMock).getAllPublicAndUser(1);
    }

    @org.junit.Test
    void getAllPublicAndUser_shouldReturnAllPublicPlaylists_whenUserIsNotAuthorized() {
        List<Playlist> playlists = List.of(getMockData().get(1));
        when(playlistRepoMock.getAllPublicAndUser(0)).thenReturn(playlists);
        List<Playlist> results = service.getAllPublicAndUser(0);
        assertEquals(results, playlists);
        verify(playlistRepoMock).getAllPublicAndUser(0);
    }


    @org.junit.Test
    public void testGetAllPublicAndUser_shouldReturnEmptyList_whenNoMatchesAreFound() {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        when(playlistRepo.getAllPublicAndUser((Integer) org.mockito.Mockito.any())).thenReturn(playlistList);
        List<Playlist> actualAllPublicAndUser = playlistServiceImpl.getAllPublicAndUser(1);
        assertSame(playlistList, actualAllPublicAndUser);
        assertTrue(actualAllPublicAndUser.isEmpty());
        verify(playlistRepo).getAllPublicAndUser((Integer) org.mockito.Mockito.any());
    }

    @org.junit.Test
    public void testGetAllByTitle_shouldReturnEmptyList_whenNoMatchesAreFound() {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        when(playlistRepo.findByTitle((String) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(playlistList);
        List<Playlist> actualAllByTitle = playlistServiceImpl.getAllByTitle(1, "ha");
        assertSame(playlistList, actualAllByTitle);
        assertTrue(actualAllByTitle.isEmpty());
        verify(playlistRepo).findByTitle((String) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
    }

    @org.junit.Test
    public void testGetAllByTitle_shouldReturnResults() {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        playlistList.add(Playlist.builder().title("ha").build());
        when(playlistRepo.findByTitle((String) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(playlistList);
        List<Playlist> actualAllByTitle = playlistServiceImpl.getAllByTitle(1, "ha");
        assertSame(playlistList, actualAllByTitle);
        assertTrue(actualAllByTitle.isEmpty());
        verify(playlistRepo).findByTitle((String) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
    }


}