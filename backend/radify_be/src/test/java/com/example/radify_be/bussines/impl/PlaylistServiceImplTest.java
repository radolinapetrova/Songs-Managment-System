package com.example.radify_be.bussines.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.domain.*;
import com.example.radify_be.persistence.PlaylistRepo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceImplTest {
    @Mock
    private PlaylistRepo playlistRepo;

    @InjectMocks
    private PlaylistServiceImpl playlistServiceImpl;


//    @Test
//    public void testGetPlaylistSongs() {
//        assertNull(playlistServiceImpl.getPlaylistSongs(123));
//    }


    //HAPPY FLOW
    @Test
    public void testCreatePlaylist_shouldReturnThePlaylist_whenSuccessful() throws UnsuccessfulAction, InvalidInputException {
        //ARRANGE
        Playlist playlist = new Playlist();

        //ACT
        when(playlistRepo.save(any(Playlist.class))).thenReturn(playlist);

        //ASSERT
        assertSame(playlist, playlistServiceImpl.createPlaylist(new Playlist()));
        verify(playlistRepo).save(any(Playlist.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testCreatePlaylist_shouldThrowException_whenCreationWasUnsuccessful() throws UnsuccessfulAction, InvalidInputException {
        //ARRANGE
        Playlist pl = Playlist.builder().title("test").build();

        //ACT
        when(playlistRepo.save(any(Playlist.class))).thenReturn(null);

        //ASSERT
        assertThrows(UnsuccessfulAction.class, () -> playlistServiceImpl.createPlaylist(pl));
        verify(playlistRepo).save(any(Playlist.class));
    }

    //UNHAPPY FLOW
    @Test
    public void testCreatePlaylist_shouldThrowException_whenInputIsInvalid() throws UnsuccessfulAction, InvalidInputException {
        //ARRANGE
        Playlist pl = Playlist.builder().title("he@?k!").build();

        //ACT & ASSERT
        assertThrows(InvalidInputException.class, () -> playlistServiceImpl.createPlaylist(pl));
        verify(playlistRepo, never()).save(any(Playlist.class));
    }


    //HAPPY FLOW
    @Test
    public void testGetUserPlaylists_shouldReturnTheUserPlaylists() {
        //ARRANGE
        Playlist pl = new Playlist();
        Playlist pl2 = new Playlist();
        ArrayList<Playlist> playlistList = new ArrayList<>();
        playlistList.add(pl);
        playlistList.add(pl2);

        //ACT
        when(playlistRepo.getAllByUserId(any(Integer.class))).thenReturn(playlistList);
        List<Playlist> actualUserPlaylists = playlistServiceImpl.getUserPlaylists(1);

        //ASSERT
        assertSame(playlistList, actualUserPlaylists);
        assertFalse(actualUserPlaylists.isEmpty());
        verify(playlistRepo).getAllByUserId(any(Integer.class));
    }

    //UNHAPPY FLOW
    @Test
    public void testGetUserPlaylists_shouldReturnEmptyArray_whenUserDoesNotHavePlaylists_orIdIsInvalid() {
        //ARRANGE
        ArrayList<Playlist> playlistList = new ArrayList<>();

        //ACT
        when(playlistRepo.ExistsByCreatorId(any(Integer.class))).thenReturn(false);
        List<Playlist> actualUserPlaylists = playlistServiceImpl.getUserPlaylists(1);

        //ASSERT
        assertEquals(playlistList, actualUserPlaylists);
        assertTrue(actualUserPlaylists.isEmpty());
        verify(playlistRepo, never()).getAllByUserId(any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testDeletePlaylist_shouldSuccessfullyDeleteThePlaylist() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist pl = Playlist.builder().creator(User.builder().id(1).build()).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(pl);
        when(playlistRepo.existsById(any(Integer.class))).thenReturn(false);
        playlistServiceImpl.deletePlaylist(1, 1);

        //ASSERT
        verify(playlistRepo).deleteById(any(Integer.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testDeletePlaylist_shouldThrowExceptionIfDeletionWasUnsuccessful() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist pl = Playlist.builder().creator(User.builder().id(1).build()).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(pl);
        when(playlistRepo.existsById(any(Integer.class))).thenReturn(true);


        //ASSERT
        assertThrows(UnsuccessfulAction.class, () -> playlistServiceImpl.deletePlaylist(1, 1));
        verify(playlistRepo).deleteById(any(Integer.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testDeletePlaylist_shouldThrowException_whenUserHasNoAuthority() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist pl = Playlist.builder().creator(User.builder().id(2).build()).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(pl);

        //ASSERT
        assertThrows(UnauthorizedAction.class, () -> playlistServiceImpl.deletePlaylist(1, 1));
        verify(playlistRepo, never()).deleteById(any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testAddSongToPlaylist_shouldAddTheSongToThePlaylistSuccessfully() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Song song = Song.builder().id(1).build();
        Playlist playlist = Playlist.builder().id(1).creator(User.builder().id(1).build()).songs(List.of(song)).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(playlist);
        when(playlistRepo.addSong(any(Integer.class), any(Integer.class))).thenReturn(playlist);

        Playlist result = playlistServiceImpl.addSongToPlaylist(1, 1, 1);

        //ASSERT
        assertEquals(result, playlist);
        assertEquals(result.getSongs(), List.of(song));
        verify(playlistRepo).findById(any(Integer.class));
        verify(playlistRepo).addSong(any(Integer.class), any(Integer.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testAddSongToPlaylist_shouldThrowException_whenTheSongWasNotAddedSuccessfully() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist playlist = Playlist.builder().id(1).creator(User.builder().id(1).build()).songs(new ArrayList<>()).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(playlist);
        when(playlistRepo.addSong(any(Integer.class), any(Integer.class))).thenReturn(playlist);

        assertThrows(UnsuccessfulAction.class, () -> playlistServiceImpl.addSongToPlaylist(1, 1, 1));

        //ASSERT
        verify(playlistRepo).findById(any(Integer.class));
        verify(playlistRepo).addSong(any(Integer.class), any(Integer.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testAddSongToPlaylist_shouldThrowException_whenTheUserIsUnauthorized() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist playlist = Playlist.builder().id(1).creator(User.builder().id(1).build()).songs(new ArrayList<>()).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(playlist);
        assertThrows(UnauthorizedAction.class, () -> playlistServiceImpl.addSongToPlaylist(1, 1, 2));

        //ASSERT
        verify(playlistRepo).findById(any(Integer.class));
        verify(playlistRepo, never()).addSong(any(Integer.class), any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testRemoveSongsFromPlaylist_shouldRemoveSongsFromThePlaylistSuccessfully() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist playlist = Playlist.builder().id(1).creator(User.builder().id(1).build()).songs(new ArrayList<>()).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(playlist);
        when(playlistRepo.deleteSong(any(Integer.class), any(Integer.class))).thenReturn(playlist);

        Playlist result = playlistServiceImpl.removeSongsFromPlaylist(1, 1, 1);

        //ASSERT
        assertEquals(result, playlist);
        assertEquals(result.getSongs(), new ArrayList<>());
        verify(playlistRepo).findById(any(Integer.class));
        verify(playlistRepo).deleteSong(any(Integer.class), any(Integer.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testRemoveSongsFromPlaylist_shouldThrowException_whenTheSongWasNotRemovedSuccessully() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Song song = Song.builder().id(1).build();
        Playlist playlist = Playlist.builder().id(1).creator(User.builder().id(1).build()).songs(List.of(song)).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(playlist);
        when(playlistRepo.deleteSong(any(Integer.class), any(Integer.class))).thenReturn(playlist);

        assertThrows(UnsuccessfulAction.class, () -> playlistServiceImpl.removeSongsFromPlaylist(1, 1, 1));

        //ASSERT
        verify(playlistRepo).findById(any(Integer.class));
        verify(playlistRepo).deleteSong(any(Integer.class), any(Integer.class));
    }

   //UNHAPPY FLOW
    @Test
    public void testRemoveSongsFromPlaylist_shoudThrowException_whenTheUserIsNotAuthorized() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Song song = Song.builder().id(1).build();
        Playlist playlist = Playlist.builder().id(1).creator(User.builder().id(1).build()).songs(List.of(song)).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(playlist);

        assertThrows(UnauthorizedAction.class, () -> playlistServiceImpl.removeSongsFromPlaylist(1, 1, 2));

        //ASSERT
        verify(playlistRepo).findById(any(Integer.class));
        verify(playlistRepo, never()).deleteSong(any(Integer.class), any(Integer.class));
    }



    //HAPPY FLOW
    @Test
    public void testUpdatePlaylistInfo_shouldUpdateThePlaylistSuccessfully() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist pl = Playlist.builder().id(1).title("title1").creator(User.builder().id(1).build()).build();
        Playlist pl2 = Playlist.builder().id(1).title("title2").creator(User.builder().id(1).build()).build();

        //ACT
        when(playlistRepo.save(any(Playlist.class))).thenReturn(pl2);
        Playlist result = playlistServiceImpl.updatePlaylistInfo(pl2, 1);

        //ASSERT
        assertNotEquals(pl, result);
        verify(playlistRepo).save(any(Playlist.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testUpdatePlaylistInfo_shouldThrowException_whenThePlaylistWasNotUpdatedSuccessully() throws UnauthorizedAction, UnsuccessfulAction {
        //ARRANGE
        Playlist pl = Playlist.builder().id(1).title("title1").creator(User.builder().id(1).build()).build();
        Playlist pl2 = Playlist.builder().id(1).title("title2").creator(User.builder().id(1).build()).build();

        //ACT
        when(playlistRepo.save(any(Playlist.class))).thenReturn(pl);
        assertThrows(UnsuccessfulAction.class, () -> playlistServiceImpl.updatePlaylistInfo(pl2, 1));

        //ASSERT
        verify(playlistRepo).save(any(Playlist.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testUpdatePlaylistInfo_shouldThrowException_whenUserHasNoAuthority() throws UnauthorizedAction {
        //ARRANGE
        Playlist pl2 = Playlist.builder().id(1).title("title2").creator(User.builder().id(1).build()).build();

        //ACT
        assertThrows(UnauthorizedAction.class, () -> playlistServiceImpl.updatePlaylistInfo(pl2, 2));

        //ASSERT
        verify(playlistRepo, never()).save(any(Playlist.class));
    }


    //HAPPY FLOW
    @Test
    public void testFindById_shouldReturnThePlaylist() {
        //ARRANGE
        Playlist playlist = Playlist.builder().id(1).build();

        //ACT
        when(playlistRepo.findById(any(Integer.class))).thenReturn(playlist);
        Playlist result =  playlistServiceImpl.findById(1);

        //ASSERT
        assertSame(playlist, result);
        verify(playlistRepo).findById(any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testGetAllPublicAndUser() {
        //ARRANGE
        Playlist pl = Playlist.builder().build();
        List<Playlist> playlistList = List.of(pl);

        //ACT
        when(playlistRepo.getAllPublicAndUser(any(Integer.class))).thenReturn(playlistList);
        List<Playlist> result = playlistServiceImpl.getAllPublicAndUser(1);

        //ASSERT
        assertSame(playlistList, result);
        assertFalse(result.isEmpty());
        verify(playlistRepo).getAllPublicAndUser(any(Integer.class));
    }



    //HAPPY FLOW
    @Test
    public void testGetAllByTitle() {
        //ARRANGE
        Playlist pl = Playlist.builder().build();
        List<Playlist> playlistList = List.of(pl);

        //ACT
        when(playlistRepo.findByTitle(any(String.class), any(Integer.class))).thenReturn(playlistList);
        List<Playlist> result = playlistServiceImpl.getAllByTitle(1, "Hellou");

        //ASSERT
        assertSame(playlistList, result);
        assertFalse(result.isEmpty());
        verify(playlistRepo).findByTitle(any(String.class), any(Integer.class));
    }

}

