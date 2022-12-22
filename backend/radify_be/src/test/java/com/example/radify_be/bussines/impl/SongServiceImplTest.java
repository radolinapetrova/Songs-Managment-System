package com.example.radify_be.bussines.impl;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.radify_be.domain.Song;
import com.example.radify_be.persistence.SongRepo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {SongServiceImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
class SongServiceImplTest {

    @MockBean
    private SongRepo songRepo;

    @Autowired
    private SongServiceImpl songServiceImpl;


    @org.junit.Test
    public void testGetSongsByTitle_shouldReturnEmptyList_whenNoMatchesAreFound() {
        ArrayList<Song> songList = new ArrayList<>();
        when(songRepo.findAllByTitle((String) any())).thenReturn(songList);
        List<Song> actualSongsByTitle = songServiceImpl.getSongsByTitle("Dr");
        assertSame(songList, actualSongsByTitle);
        assertTrue(actualSongsByTitle.isEmpty());
        verify(songRepo).findAllByTitle((String) any());
    }


    @org.junit.Test
    public void testGetAllPlaylistSongs_shouldReturnResults() {
        ArrayList<Song> songList = new ArrayList<>();
        when(songRepo.findAllByPlaylists((Integer) any())).thenReturn(songList);
        List<Song> actualAllPlaylistSongs = songServiceImpl.getAllPlaylistSongs(1);
        assertSame(songList, actualAllPlaylistSongs);
        assertTrue(actualAllPlaylistSongs.isEmpty());
        verify(songRepo).findAllByPlaylists((Integer) any());
    }


    @org.junit.Test
    public void testCreateSong_shouldReturnTheCreatedSong() {
        Song song = new Song();
        when(songRepo.save((Song) any())).thenReturn(song);
        assertSame(song, songServiceImpl.createSong(new Song()));
        verify(songRepo).save((Song) any());
    }


    @org.junit.Test
    public void testGetAllSongs_shouldReturnTheListOfSongs() {
        ArrayList<Song> songList = new ArrayList<>();
        when(songRepo.findAll()).thenReturn(songList);
        List<Song> actualAllSongs = songServiceImpl.getAllSongs();
        assertSame(songList, actualAllSongs);
        assertTrue(actualAllSongs.isEmpty());
        verify(songRepo).findAll();
    }


    @org.junit.Test
    public void testGetById_shouldReturnTheSong() {
        Song song = new Song();
        when(songRepo.getById((Integer) any())).thenReturn(song);
        assertSame(song, songServiceImpl.getById(1));
        verify(songRepo).getById((Integer) any());
    }


}