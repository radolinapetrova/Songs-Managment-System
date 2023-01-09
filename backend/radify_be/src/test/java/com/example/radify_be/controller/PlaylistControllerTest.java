package com.example.radify_be.controller;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.controller.requests.CreatePlaylistRequest;
import com.example.radify_be.controller.requests.DeletePlaylistRequest;
import com.example.radify_be.controller.requests.EditPlaylistSongsRequest;
import com.example.radify_be.controller.requests.GetPlaylistsByTitleAndUser;
import com.example.radify_be.controller.requests.UpdatePlaylistRequest;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class PlaylistControllerTest {
    @Autowired
    private PlaylistController playlistController;

    @MockBean
    private PlaylistService playlistService;


    //HAPPY FLOW
    @Test
    public void testGetUserPlaylists_shouldReturnTheUserPlaylists() throws Exception {
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
    public void testGetUserPlaylists_shouldReturnEmptyArray_whenTheUserHasNoPlaylists() throws Exception {
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
    public void testAddSongToPlaylist_shouldSuccessfullyAddTheSongToThePlaylist() throws Exception {
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
    public void testAddSongToPlaylist_shouldThrowException_whenUserHasNoAuthority() throws Exception {
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
    public void testAddSongToPlaylist_shouldThrowException_whenTheOperationWasUnsuccessful() throws Exception {
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
    public void testGetAllByTitle_shouldReturnThePlaylists_whenTheirTitleContainsTheInput() throws Exception {
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
    public void testCreateNewPlaylist_shouldSuccessfullyCreateNewPlaylist() throws Exception {
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
    public void testCreateNewPlaylist_shouldThrowException_whenInputIsInvalid() throws Exception {
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
    public void testCreateNewPlaylist_shouldThrowException_whenOperationWasUnsuccessful() throws Exception {
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
    public void testDeletePlaylist_shouldSuccessfullyDeleteThePlaylist() throws Exception {
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

//    //UNHAPPY FLOW
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
    public void testDeleteSongsFromPlaylist_shouldSuccessfullyDeleteTheSongFromThePlaylist() throws Exception {
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
    public void testDeleteSongsFromPlaylist_shouldThrowException_whenUserHasNoAuthority() throws Exception {
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
    public void testDeleteSongsFromPlaylist_shouldThrowException_whenOperationWasUnsuccessful() throws Exception {
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
    public void testGetAllPlaylists_shouldReturnAllThePlaylists() throws Exception {
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
    public void testGetAllPlaylists_shouldReturnEmptyBody_whenThereAreNoResults() throws Exception {
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



    @Test
    public void testGetPlaylistById() throws Exception {
        when(playlistService.findById((Integer) any())).thenReturn(new Playlist());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/{id}", 1);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":null,\"title\":null,\"dateOfCreation\":null,\"creator\":null,\"users\":null,\"songs\":null,\"public"
                                        + "\":false}"));
    }


    @Test
    public void testGetPlaylistById2() throws Exception {
        when(playlistService.findById((Integer) any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    public void testGetPlaylistById3() throws Exception {
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date dateOfCreation = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        User creator = new User();
        ArrayList<User> users = new ArrayList<>();
        when(playlistService.findById((Integer) any()))
                .thenReturn(new Playlist(1, "Dr", true, dateOfCreation, creator, users, new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/{id}", 1);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"title\":\"Dr\",\"dateOfCreation\":0,\"creator\":{\"id\":null,\"account\":null,\"role\":null,\"fname\":null"
                                        + ",\"lname\":null},\"users\":[],\"songs\":[],\"public\":true}"));
    }


    @Test
    public void testUpdatePlaylist() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

