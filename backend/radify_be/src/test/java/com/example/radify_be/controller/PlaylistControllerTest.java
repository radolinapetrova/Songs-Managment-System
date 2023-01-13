package com.example.radify_be.controller;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.controller.requests.*;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
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

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
 class PlaylistControllerTest {
    @Autowired
    private PlaylistController playlistController;

    @MockBean
    private PlaylistService playlistService;


    //HAPPY FLOW
    @Test
    void testGetUserPlaylists_shouldReturnTheUserPlaylists() throws Exception {
        //ARRANGE
        List<Playlist> playlistList = new ArrayList<>(List.of(Playlist.builder().id(1).build(), Playlist.builder().id(2).build()));
        when(playlistService.getUserPlaylists(any(Integer.class))).thenReturn(playlistList);

        //ACT
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/user/{id}", 1);
        String content = (new ObjectMapper()).writeValueAsString(playlistList);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(content));


        verify(playlistService).getUserPlaylists(any(Integer.class));
    }

    //UNHAPPY FLOW
    @Test
    void testGetUserPlaylists_shouldReturnEmptyArray_whenTheUserHasNoPlaylists() throws Exception {
        //ARRANGE
        when(playlistService.getUserPlaylists(any(Integer.class))).thenReturn(new ArrayList<>());

        //ACT
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/user/{id}", 1);
        String content = (new ObjectMapper()).writeValueAsString(new ArrayList<>());
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(content));


        verify(playlistService).getUserPlaylists(any(Integer.class));

    }


    //HAPPY FLOW
    @Test
    void testAddSongToPlaylist_shouldSuccessfullyAddTheSongToThePlaylist() throws Exception {
        //ARRANGE
        EditPlaylistSongsRequest editPlaylistSongsRequest = new EditPlaylistSongsRequest();
        editPlaylistSongsRequest.setPlaylistId(1);
        editPlaylistSongsRequest.setUserId(1);
        editPlaylistSongsRequest.setSongId(1);
        String content = (new ObjectMapper()).writeValueAsString(editPlaylistSongsRequest);
        String expected = (new ObjectMapper()).writeValueAsString(new Playlist());
        when(playlistService.addSongToPlaylist(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(new Playlist());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }

    //UNHAPPY FLOW
    @Test
    void testAddSongToPlaylist_shouldThrowException_whenUserHasNoAuthority() throws Exception {
        //ARRANGE
        EditPlaylistSongsRequest editPlaylistSongsRequest = new EditPlaylistSongsRequest();
        editPlaylistSongsRequest.setPlaylistId(1);
        editPlaylistSongsRequest.setUserId(1);
        editPlaylistSongsRequest.setSongId(1);
        String content = (new ObjectMapper()).writeValueAsString(editPlaylistSongsRequest);
        when(playlistService.addSongToPlaylist(any(Integer.class), any(Integer.class), any(Integer.class))).thenThrow(UnauthorizedAction.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(401));
    }

    //UNHAPPY FLOW
    @Test
    void testAddSongToPlaylist_shouldThrowException_whenTheOperationWasUnsuccessful() throws Exception {
        //ARRANGE
        EditPlaylistSongsRequest editPlaylistSongsRequest = new EditPlaylistSongsRequest();
        editPlaylistSongsRequest.setPlaylistId(1);
        editPlaylistSongsRequest.setUserId(1);
        editPlaylistSongsRequest.setSongId(1);
        String content = (new ObjectMapper()).writeValueAsString(editPlaylistSongsRequest);
        when(playlistService.addSongToPlaylist(any(Integer.class), any(Integer.class), any(Integer.class))).thenThrow(UnsuccessfulAction.class);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(417));
    }


    //HAPPY FLOW
    @Test
    void testGetAllByTitle_shouldReturnThePlaylists_whenTheirTitleContainsTheInput() throws Exception {
        //ARRANGE
        GetPlaylistsByTitleAndUser getPlaylistsByTitleAndUser = new GetPlaylistsByTitleAndUser(); //request
        getPlaylistsByTitleAndUser.setTitle("heheh");
        getPlaylistsByTitleAndUser.setId(1);
        List<Playlist> playlistList = List.of(Playlist.builder().id(1).title("Radka").build());
        String content = (new ObjectMapper()).writeValueAsString(getPlaylistsByTitleAndUser);
        String expected = (new ObjectMapper()).writeValueAsString(playlistList); //converting the expected result
        when(playlistService.getAllByTitle(any(Integer.class), any(String.class))).thenReturn(playlistList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/playlists/title")//build the request
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT & ASSERT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }




    //HAPPY FLOW
    @Test
    void testCreateNewPlaylist_shouldSuccessfullyCreateNewPlaylist() throws Exception {
        //ARRANGE
        CreatePlaylistRequest createPlaylistRequest = new CreatePlaylistRequest();
        createPlaylistRequest.setPublic(true);
        createPlaylistRequest.setTitle("Radka");
        createPlaylistRequest.setUserId(1);
        when(playlistService.createPlaylist(any(Playlist.class))).thenReturn(new Playlist());
        String content = (new ObjectMapper()).writeValueAsString(createPlaylistRequest);
        String expected = (new ObjectMapper()).writeValueAsString(new Playlist());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }


    //UNHAPPY FLOW
    @Test
    void testCreateNewPlaylist_shouldThrowException_whenInputIsInvalid() throws Exception {
        //ARRANGE
        CreatePlaylistRequest createPlaylistRequest = new CreatePlaylistRequest();
        when(playlistService.createPlaylist(any(Playlist.class))).thenThrow(InvalidInputException.class);
        String content = (new ObjectMapper()).writeValueAsString(createPlaylistRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(417));
    }


    //UNHAPPY FLOW
    @Test
    void testCreateNewPlaylist_shouldThrowException_whenOperationWasUnsuccessful() throws Exception {
        //ARRANGE
        CreatePlaylistRequest createPlaylistRequest = new CreatePlaylistRequest();
        when(playlistService.createPlaylist(any(Playlist.class))).thenThrow(UnsuccessfulAction.class);
        String content = (new ObjectMapper()).writeValueAsString(createPlaylistRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(417));
    }


    //HAPPY FLOW
    @Test
    void testDeletePlaylist_shouldSuccessfullyDeleteThePlaylist() throws Exception {
        //ARRANGE
        DeletePlaylistRequest deletePlaylistRequest = new DeletePlaylistRequest();
        deletePlaylistRequest.setPlaylistId(1);
        deletePlaylistRequest.setUserId(1);
        String content = (new ObjectMapper()).writeValueAsString(deletePlaylistRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/playlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);


        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

   //UNHAPPY FLOW
//    @Test
//    public void testDeletePlaylist_shouldThrowException() throws Exception {
//        //ARRANGE
//        DeletePlaylistRequest deletePlaylistRequest = new DeletePlaylistRequest();
//        deletePlaylistRequest.setPlaylistId(1);
//        deletePlaylistRequest.setUserId(1);
//        when(playlistService.deletePlaylist(any(Integer.class), any(Integer.class))).thenThrow(UnauthorizedAction.class);
//        String content = (new ObjectMapper()).writeValueAsString(deletePlaylistRequest);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/playlists")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//
//
//        //ACT
//        MockMvcBuilders.standaloneSetup(playlistController)
//                .build()
//                .perform(requestBuilder)
//                //ASSERT
//                .andExpect(MockMvcResultMatchers.status().is(401));
//    }



    //HAPPY FLOW
    @Test
    void testDeleteSongsFromPlaylist_shouldSuccessfullyDeleteTheSongFromThePlaylist() throws Exception {
        //ARRANGE
        EditPlaylistSongsRequest editPlaylistSongsRequest = new EditPlaylistSongsRequest();
        editPlaylistSongsRequest.setPlaylistId(1);
        editPlaylistSongsRequest.setSongId(1);
        editPlaylistSongsRequest.setUserId(1);
        when(playlistService.removeSongsFromPlaylist(any(Integer.class), any(Integer.class), any(Integer.class))).thenReturn(new Playlist());
        String content = (new ObjectMapper()).writeValueAsString(editPlaylistSongsRequest);
        String expected = (new ObjectMapper()).writeValueAsString(new Playlist());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playlists/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));
    }


    //UNHAPPY FLOW
    @Test
    void testDeleteSongsFromPlaylist_shouldThrowException_whenUserHasNoAuthority() throws Exception {
        //ARRANGE
        EditPlaylistSongsRequest editPlaylistSongsRequest = new EditPlaylistSongsRequest();
        editPlaylistSongsRequest.setPlaylistId(1);
        editPlaylistSongsRequest.setSongId(1);
        editPlaylistSongsRequest.setUserId(1);
        when(playlistService.removeSongsFromPlaylist(any(Integer.class), any(Integer.class), any(Integer.class))).thenThrow(UnauthorizedAction.class);
        String content = (new ObjectMapper()).writeValueAsString(editPlaylistSongsRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playlists/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(401));
    }


    //UNHAPPY FLOW
    @Test
    void testDeleteSongsFromPlaylist_shouldThrowException_whenOperationWasUnsuccessful() throws Exception {
        //ARRANGE
        EditPlaylistSongsRequest editPlaylistSongsRequest = new EditPlaylistSongsRequest();
        editPlaylistSongsRequest.setPlaylistId(1);
        editPlaylistSongsRequest.setSongId(1);
        editPlaylistSongsRequest.setUserId(1);
        when(playlistService.removeSongsFromPlaylist(any(Integer.class), any(Integer.class), any(Integer.class))).thenThrow(UnsuccessfulAction.class);
        String content = (new ObjectMapper()).writeValueAsString(editPlaylistSongsRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playlists/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(417));
    }



    //HAPPY FLOW
    @Test
    void testGetAllPlaylists_shouldReturnAllThePlaylists() throws Exception {
        //ARRANGE
        List<Playlist> playlistList = List.of(Playlist.builder().id(1).build(), Playlist.builder().id(2).build());
        when(playlistService.getAllPublicAndUser(any(Integer.class))).thenReturn(playlistList);
        String expected = (new ObjectMapper()).writeValueAsString(playlistList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/all/{userId}", 1);


        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string(expected));

    }


    //HAPPY FLOW
    @Test
    void testGetAllPlaylists_shouldReturnEmptyBody_whenThereAreNoResults() throws Exception {
        //ASSERT
        List<Playlist> playlistList = new ArrayList<>();
        when(playlistService.getAllPublicAndUser(any(Integer.class))).thenReturn(playlistList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/all/{userId}", 1);


        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }


    //HAPPY FLOW
    @Test
    public void testGetPlaylistById_shouldSuccessfullyReturnThePlaylist() throws Exception {
        //ARRANGE
        when(playlistService.findById(any(Integer.class))).thenReturn(new Playlist());
        String expected = (new ObjectMapper()).writeValueAsString(new Playlist());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/{id}", 1);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(expected));
    }


    //UNHAPPY FLOW
    @Test
    public void testGetPlaylistById_shouldReturnEmptyBodyWhenThereIsNoSuchPlaylist() throws Exception {
        //ARRANGE
        when(playlistService.findById(any(Integer.class))).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/{id}", 1);

        //ACT
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
        //ASSERT
                .andExpect(MockMvcResultMatchers.status().is(417));
    }



}

