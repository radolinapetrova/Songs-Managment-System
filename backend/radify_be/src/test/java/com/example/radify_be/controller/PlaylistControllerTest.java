package com.example.radify_be.controller;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.controller.requests.AddSongRequest;
import com.example.radify_be.controller.requests.CreatePlaylistRequest;
import com.example.radify_be.controller.requests.GetPlaylistsByTitleAndUser;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;


import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlaylistControllerTest {

    @Autowired
    private PlaylistController playlistController;

    @MockBean
    private PlaylistService playlistService;

    @Autowired
    private MockMvc mockMvc;

    @org.junit.Test
    public void testGetUserPlaylists() throws Exception {
        when(playlistService.getUserPlaylists((Integer) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/user/{id}", 1);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @org.junit.Test
    public void testGetUserPlaylists_shouldReturnNull_whenTheUserDoesntHaveAnyPlaylists() throws Exception {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        playlistList.add(new Playlist());
        when(playlistService.getUserPlaylists((Integer) any())).thenReturn(playlistList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/user/{id}", 1);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":null,\"title\":null,\"dateOfCreation\":null,\"creator\":null,\"users\":null,\"songs\":null,\"public"
                                        + "\":false}]"));
    }


    @Test
    public void testGetUserPlaylists_shouldReturnThePlaylist() throws Exception {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date dateOfCreation = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        User creator = new User();
        creator.setId(1);
        ArrayList<User> users = new ArrayList<>();
        playlistList.add(new Playlist(1, "Dr", true, dateOfCreation, creator, users, new ArrayList<>()));
        when(playlistService.getUserPlaylists((Integer) any())).thenReturn(playlistList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/user/{id}", 1);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"title\":\"Dr\",\"dateOfCreation\":0,\"creator\":{\"id\":1,\"account\":null,\"role\":null,\"lname\":null,\"fname\":null},\"users\":[],\"songs\":[],\"public\":true}]"));
    }




    @Test
    public void testDeletePlaylist() throws Exception {
        doNothing().when(playlistService).deletePlaylist((Integer) any());

        DeletePlaylistRequest deletePlaylistRequest = new DeletePlaylistRequest();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date dateOfCreation = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        User creator = new User();
        ArrayList<User> users = new ArrayList<>();
        deletePlaylistRequest.setPlaylist(new Playlist(1, "Dr", true, dateOfCreation, creator, users, new ArrayList<>()));
        deletePlaylistRequest.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(deletePlaylistRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/playlists/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Successful deletion of the playlist"));
    }



    @Test
    public void testGetAllByTitle_shouldReturnNull_whenThereArentMatchingResults() throws Exception {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        playlistList.add(new Playlist());
        when(playlistService.getAllByTitle((Integer) any(), (String) any())).thenReturn(playlistList);

        GetPlaylistsByTitleAndUser getPlaylistsByTitleAndUser = new GetPlaylistsByTitleAndUser();
        getPlaylistsByTitleAndUser.setId(1);
        getPlaylistsByTitleAndUser.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(getPlaylistsByTitleAndUser);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/playlists/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":null,\"title\":null,\"dateOfCreation\":null,\"creator\":null,\"users\":null,\"songs\":null,\"public"
                                        + "\":false}]"));
    }


    @Test
    public void testGetAllByTitle_shouldReturnPlaylists() throws Exception {
        ArrayList<Playlist> playlistList = new ArrayList<>();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date dateOfCreation = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        User creator = new User();
        ArrayList<User> users = new ArrayList<>();
        playlistList.add(new Playlist(1, "Dr", true, dateOfCreation, creator, users, new ArrayList<>()));
        when(playlistService.getAllByTitle((Integer) any(), (String) any())).thenReturn(playlistList);

        GetPlaylistsByTitleAndUser getPlaylistsByTitleAndUser = new GetPlaylistsByTitleAndUser();
        getPlaylistsByTitleAndUser.setId(1);
        getPlaylistsByTitleAndUser.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(getPlaylistsByTitleAndUser);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/playlists/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(playlistController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"title\":\"Dr\",\"dateOfCreation\":0,\"creator\":{\"id\":null,\"account\":null,\"role\":null,\"lname\":null,\"fname\":null},\"users\":[],\"songs\":[],\"public\":true}]"));
    }





    @Test
    public void testAddSongToPlaylist() throws Exception {
      AddSongRequest addSongRequest = new AddSongRequest();
      addSongRequest.setPlaylistId(123);
      addSongRequest.setSongId(123);
      String content = (new ObjectMapper()).writeValueAsString(addSongRequest);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playlists")
          .contentType(MediaType.APPLICATION_JSON)
          .content(content);
      ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(playlistController)
          .build()
          .perform(requestBuilder);
      actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200))
              .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
              .andExpect(MockMvcResultMatchers.content().string("Idk if it worked tbh"));
    }


    @Test
    public void testCreateNewPlaylist() throws Exception {
      CreatePlaylistRequest createPlaylistRequest = new CreatePlaylistRequest();
      createPlaylistRequest.setPublic(true);
      createPlaylistRequest.setTitle("Dr");
      createPlaylistRequest.setUserId(123);
      String content = (new ObjectMapper()).writeValueAsString(createPlaylistRequest);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/playlists")
          .contentType(MediaType.APPLICATION_JSON)
          .content(content);
      ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(playlistController)
          .build()
          .perform(requestBuilder);
      actualPerformResult.andExpect(MockMvcResultMatchers.status().is(201))
              .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
              .andExpect(MockMvcResultMatchers.content().string("Playlist created"));
    }


    @Test
    public void testGetAllPlaylists() throws Exception {
      when(playlistService.getAllPublicAndUser((Integer) any())).thenReturn(new ArrayList<>());
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/all/{userId}", 123);
      MockMvcBuilders.standaloneSetup(playlistController)
          .build()
          .perform(requestBuilder)
          .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @org.junit.Test
    public void testGetAllPlaylists2() throws Exception {
      ArrayList<Playlist> playlistList = new ArrayList<>();
      playlistList.add(new Playlist());
      when(playlistService.getAllPublicAndUser((Integer) any())).thenReturn(playlistList);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/all/{userId}", 123);
      MockMvcBuilders.standaloneSetup(playlistController)
          .build()
          .perform(requestBuilder)
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
          .andExpect(MockMvcResultMatchers.content()
              .string(
                  "[{\"id\":null,\"title\":null,\"dateOfCreation\":null,\"creator\":null,\"users\":null,\"songs\":null,\"public"
                      + "\":false}]"));
    }


    @org.junit.Test
    public void testGetAllPlaylists3() throws Exception {
      ArrayList<Playlist> playlistList = new ArrayList<>();
      LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
      Date dateOfCreation = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
      User creator = new User();
      ArrayList<User> users = new ArrayList<>();
      playlistList.add(new Playlist(1, "Dr", true, dateOfCreation, creator, users, new ArrayList<>()));
      when(playlistService.getAllPublicAndUser((Integer) any())).thenReturn(playlistList);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/all/{userId}", 123);
      MockMvcBuilders.standaloneSetup(playlistController)
          .build()
          .perform(requestBuilder)
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
          .andExpect(MockMvcResultMatchers.content()
              .string(
                  "[{\"id\":1,\"title\":\"Dr\",\"dateOfCreation\":0,\"creator\":{\"id\":null,\"account\":null,\"role\":null,\"fname\":null"
                      + ",\"lname\":null},\"users\":[],\"songs\":[],\"public\":true}]"));
    }


    @org.junit.Test
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


    @org.junit.Test
    public void testGetPlaylistById3() throws Exception {
      LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
      Date dateOfCreation = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
      User creator = new User();
      ArrayList<User> users = new ArrayList<>();
      when(playlistService.findById((Integer) any()))
          .thenReturn(new Playlist(1, "Test", true, dateOfCreation, creator, users, new ArrayList<>()));
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlists/{id}", 1);
      MockMvcBuilders.standaloneSetup(playlistController)
          .build()
          .perform(requestBuilder)
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
          .andExpect(MockMvcResultMatchers.content()
              .string(
                  "{\"id\":1,\"title\":\"Test\",\"dateOfCreation\":0,\"creator\":{\"id\":null,\"account\":null,\"role\":null,\"fname\":null"
                      + ",\"lname\":null},\"users\":[],\"songs\":[],\"public\":true}"));
    }
}