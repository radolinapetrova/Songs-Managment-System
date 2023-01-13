package com.example.radify_be.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.example.radify_be.bussines.SongService;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.controller.requests.CreateSongRequest;
import com.example.radify_be.controller.requests.DeleteSongRequest;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.Song;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class SongControllerTest {
    @Autowired
    private SongController songController;

    @MockBean
    private SongService songService;



    //HAPPY FLOW
    @Test
    public void testConvertSong_shouldSuccessfullyReturnSongObject(){
        //ARRANGE
        CreateSongRequest createSongRequest = new CreateSongRequest();
        createSongRequest.setArtistsIds(new ArrayList<>());
        createSongRequest.setGenre("pop");
        createSongRequest.setSeconds(125);
        createSongRequest.setTitle("Heheh");

        //ACT
        Song song = songController.convert(createSongRequest);

        //ASSERT
        assertEquals(song.getTitle(), createSongRequest.getTitle());
        assertEquals(song.getSeconds(), createSongRequest.getSeconds());
        assertEquals(song.getTitle(), createSongRequest.getTitle());

    }

    //UNHAPPY FLOW
    @Test
    public void testConvertSong_shouldThrowException_whenNoInputIsProvided(){
        //ARRANGE
        CreateSongRequest createSongRequest = new CreateSongRequest();
        createSongRequest.setArtistsIds(null);
        createSongRequest.setGenre(null);
        createSongRequest.setSeconds(null);
        createSongRequest.setTitle(null);

        //ACT
        assertThrows(InvalidInputException.class, () -> songController.convert(createSongRequest));
    }

    //HAPPY FLOW
    @Test
    public void testAddSong_shouldSuccessfullyCreateNewSong() throws Exception {
        //ARRANGE
        CreateSongRequest createSongRequest = new CreateSongRequest();
        createSongRequest.setArtistsIds(new ArrayList<>());
        createSongRequest.setGenre("pop");
        createSongRequest.setSeconds(125);
        createSongRequest.setTitle("Heheh");
        Song song = songController.convert(createSongRequest);
        String content = (new ObjectMapper()).writeValueAsString(createSongRequest);
        String expected = (new ObjectMapper()).writeValueAsString(song);
        when(songService.createSong(any(Song.class))).thenReturn(song);

        //ACTS
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }





    //HAPPY FLOW
    @Test
    public void testGetAllSongs_shouldReturnListOfAllTheSongs() throws Exception {
        //ARRANGE
        List<Song> songs = new ArrayList<Song>();
        when(songService.getAllSongs()).thenReturn(songs);
        String expected = (new ObjectMapper()).writeValueAsString(songs);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/all");

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)

        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }


    @Test
    public void testGetAllSongs2() throws Exception {
        ArrayList<Song> songList = new ArrayList<>();
        songList.add(new Song());
        when(songService.getAllSongs()).thenReturn(songList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/all");
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":null,\"title\":null,\"seconds\":null,\"genre\":null,\"artists\":null}]"));
    }



    //HAPPY FLOW
    @Test
    public void testDeleteSong_shouldSuccessfullyDeleteTheSong() throws Exception {
        //ARRANGE
        DeleteSongRequest deleteSongRequest = new DeleteSongRequest();
        deleteSongRequest.setSongId(123);
        deleteSongRequest.setUserId(123);

        String content = (new ObjectMapper()).writeValueAsString(deleteSongRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(200));

        verify(songService).deleteSong(any(Integer.class), any(Integer.class));
    }

    //UNHAPPY FLOW
    @Test
    public void testDeleteSong_shouldThrowException_whenUserHasNoAuthority() throws Exception {
        //ARRANGE
        DeleteSongRequest deleteSongRequest = new DeleteSongRequest();
        deleteSongRequest.setSongId(123);
        deleteSongRequest.setUserId(123);

        doThrow(UnauthorizedAction.class).when(songService).deleteSong(any(Integer.class), any(Integer.class));

        String content = (new ObjectMapper()).writeValueAsString(deleteSongRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)
                //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(401));

        verify(songService).deleteSong(any(Integer.class), any(Integer.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testDeleteSong_shouldThrowException_whenTheInputIsInvalid() throws Exception {
        //ARRANGE
        DeleteSongRequest deleteSongRequest = new DeleteSongRequest();
        deleteSongRequest.setSongId(123);
        deleteSongRequest.setUserId(123);

        doThrow(InvalidInputException.class).when(songService).deleteSong(any(Integer.class), any(Integer.class));

        String content = (new ObjectMapper()).writeValueAsString(deleteSongRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)
                //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(417));

        verify(songService).deleteSong(any(Integer.class), any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testGetByTitle_shouldReturnListOfAllSongsThatContainTheInput() throws Exception {
        //ARRANGE
        when(songService.getSongsByTitle(any(String.class))).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/title/{title}", "hehe");

        String content = (new ObjectMapper()).writeValueAsString(new ArrayList<>());

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(content));
    }



    //UNHAPPY FLOW
    @Test
    public void testGetByTitle_shouldReturnListOfAllSongsWhenThereIsNoInput() throws Exception {
        //ARRANGE
        when(songService.getSongsByTitle(any(String.class))).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/title/{title}", "j");

        String content = (new ObjectMapper()).writeValueAsString(new ArrayList<>());

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)
                //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(content));
    }


    //HAPPY FLOW
    @Test
    public void testGetPlaylistSongs_shouldReturnAllSongsFromParticularPlaylist() throws Exception {
        //ARRANGE
        Playlist playlist = Playlist.builder().id(1).build();
        playlist.setSongs(List.of(Song.builder().id(1).build(), Song.builder().id(2).build()));

        when(songService.getAllPlaylistSongs(any(Integer.class))).thenReturn(playlist.getSongs());
        String expected = (new ObjectMapper()).writeValueAsString(playlist.getSongs());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/playlist/{id}", 1);

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)

        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    //HAPPY FLOW
    @Test
    public void testGetPlaylistSongs_shouldReturnEmptyList_whenThePlaylistHasNoSongs() throws Exception {
        //ARRANGE
        List<Song> songList = List.of(Song.builder().build());
        when(songService.getAllPlaylistSongs(any(Integer.class))).thenReturn(songList);

        String expected = (new ObjectMapper()).writeValueAsString(songList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/playlist/{id}", 1);

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)

        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(expected));
    }


    //HAPPY FLOW
    @Test
    public void testGetSongById_shouldSuccessfullyRetrieveTheSong() throws Exception {
        //ARRANGE
        Song song = Song.builder().id(1).title("iwkmsrb").build();
        when(songService.getById(any(Integer.class))).thenReturn(song);

        String expected = (new ObjectMapper()).writeValueAsString(song);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/{id}", 1);

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)

        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }


    @Test
    public void testGetSongById_shouldThrowAnError_whenTheInputWasInvalid() throws Exception {
        //ARRANGE
        when(songService.getById(any(Integer.class))).thenThrow(InvalidInputException.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/songs/{id}", 1);

        //ACT
        MockMvcBuilders.standaloneSetup(songController)
                .build()
                .perform(requestBuilder)

        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(417))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"));
    }

}

