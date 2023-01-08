package com.example.radify_be.bussines.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.Song;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.SongRepo;
import com.example.radify_be.persistence.UserRepo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class SongServiceImplTest {
    @Mock
    private SongRepo songRepo;

    @InjectMocks
    private SongServiceImpl songServiceImpl;

    @Mock
    private UserRepo userRepo;


    //HAPPY FLOW
    @Test
    public void testGetSongsByTitle_shouldReturnTheSongsWhichContainTheUserInput() {
        //ARRANGE
        List<Song> songs = new ArrayList<>();

        //ACT
        when(songRepo.findAllByTitle(any(String.class))).thenReturn(songs);
        List<Song> result = songServiceImpl.getSongsByTitle("Lole lole lole - Shakiraa");

        //ASSERT
        assertSame(songs, result);
        assertTrue(result.isEmpty());
        verify(songRepo).findAllByTitle(any(String.class));
        verify(songRepo, never()).findAll();
    }


    //HAPPY FLOW
    @Test
    public void testGetSongsByTitle_shouldReturnAllTheSongs_whenNoUserInputWasProvided() {
        //ARRANGE
        Song song = new Song();
        List<Song> songs = List.of(song);

        //ACT
        when(songRepo.findAll()).thenReturn(songs);
        List<Song> result = songServiceImpl.getSongsByTitle("");

        //ASSERT
        assertSame(songs, result);
        assertFalse(result.isEmpty());
        verify(songRepo, never()).findAllByTitle(any(String.class));
        verify(songRepo).findAll();
    }


    //HAPPY FLOW
    @Test
    public void testDeleteSong_shouldSuccessfullyDeleteTheSong() throws UnauthorizedAction {
        //ARRANGE
        User user = User.builder().role(Role.ADMIN).build();

        //ACT
        when(userRepo.findById(any(Integer.class))).thenReturn(user);
        when(songRepo.existsById(any(Integer.class))).thenReturn(true);
        songServiceImpl.deleteSong(1, 1);

        //ASSERT
        verify(userRepo).findById(1);
        verify(songRepo).deleteById(1);
        verify(songRepo).existsById(1);
    }


    //UNHAPPY FLOW
    @Test
    public void testDeleteSong_shouldThrowAnException_whenTheUserHasNoAuthority() throws UnauthorizedAction {
        //ARRANGE
        User user = User.builder().role(Role.USER).build();

        //ACT
        when(userRepo.findById(any(Integer.class))).thenReturn(user);
        assertThrows(UnauthorizedAction.class, () -> songServiceImpl.deleteSong(1, 1));

        //ASSERT
        verify(userRepo).findById(1);
        verify(songRepo, never()).deleteById(1);
        verify(songRepo, never()).existsById(1);
    }

    //UNHAPPY FLOW
    @Test
    public void testDeleteSong_shouldThrowAnException_whenTheInputIsInvalid() throws UnauthorizedAction {
        //ARRANGE
        User user = User.builder().role(Role.ADMIN).build();

        //ACT
        when(userRepo.findById(any(Integer.class))).thenReturn(user);
        when(songRepo.existsById(any(Integer.class))).thenReturn(false);
        assertThrows(InvalidInputException.class, () -> songServiceImpl.deleteSong(1, 1));

        //ASSERT
        verify(userRepo).findById(1);
        verify(songRepo).existsById(1);
        verify(songRepo, never()).deleteById(1);
    }



    //HAPPY FLOW
    @Test
    public void testGetAllPlaylistSongs_shouldReturnAllTheSongsOfThePlaylist() {
        //ARRANGE
        ArrayList<Song> songList = new ArrayList<>();

        //ACT
        when(songRepo.findAllByPlaylists(any(Integer.class))).thenReturn(songList);
        List<Song> result = songServiceImpl.getAllPlaylistSongs(1);

        //ASSERT
        assertSame(songList, result);
        assertTrue(result.isEmpty());
        verify(songRepo).findAllByPlaylists(any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testCreateSong_shouldReturnTheSuccessfullyCreatedSong() {
        //ARRANGE
        Song song = new Song();

        //ACT
        when(songRepo.save(any(Song.class))).thenReturn(song);
        Song result = songServiceImpl.createSong(new Song());

        //ASSERT
        assertSame(song, result);
        verify(songRepo).save(any(Song.class));
    }



    //HAPPY FLOW
    @Test
    public void testGetAllSongs_shouldReturnAllTheSongs() {
        //ARRANGE
        List<Song> songList = new ArrayList<>();

        //ACT
        when(songRepo.findAll()).thenReturn(songList);
        List<Song> result = songServiceImpl.getAllSongs();

        //ASSERT
        assertSame(songList, result);
        assertTrue(result.isEmpty());
        verify(songRepo).findAll();
    }


    //HAPPY FLOW
    @Test
    public void testGetById_shouldReturnTheSongWithTheGivenId() throws InvalidInputException{
        //ARRANGE
        Song song = new Song();

        //ACT
        when(songRepo.existsById(any(Integer.class))).thenReturn(true);
        when(songRepo.getById(any(Integer.class))).thenReturn(song);
        Song result = songServiceImpl.getById(1);

        //ASSERT
        assertSame(song, result);
        verify(songRepo).getById(any(Integer.class));
    }

    //UNHAPPY FLOW
    @Test
    public void testGetById_shouldThrowExceptionWhenInputIsInvalid() throws InvalidInputException{
        //ACT
        when(songRepo.existsById(any(Integer.class))).thenReturn(false);
        assertThrows(InvalidInputException.class, () -> songServiceImpl.getById(1));

        //ASSERT
        verify(songRepo, never()).getById(any(Integer.class));
        verify(songRepo).existsById(any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testGetAllByIdIn_shouldReturnAllSongWithTheCertainIds() {
        //ARRANGE
        ArrayList<Song> songList = new ArrayList<>();

        //ACT
        when(songRepo.getAllByIdIn(any(List.class))).thenReturn(songList);
        List<Song> result = songServiceImpl.getAllByIdIn(new ArrayList<Integer>());

        //ASSERT
        assertSame(songList, result);
        assertTrue(result.isEmpty());
        verify(songRepo).getAllByIdIn(any(List.class));
    }

}

