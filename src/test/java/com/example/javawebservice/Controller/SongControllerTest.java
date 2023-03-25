package com.example.javawebservice.Controller;

import com.example.javawebservice.Model.Song;
import com.example.javawebservice.Service.SongService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;


    @Test
    public void getAllSongs() throws Exception {
        Song song = new Song();
        song.setId("1");
        song.setArtist("Test Artist");
        song.setSong("Test Song");
        song.setGenre("Test Genre");

        List<Song> allSongs = Collections.singletonList(song);

        when(songService.getAllSongs()).thenReturn(allSongs);

        String expectedJsonResponse = "[{\"id\":\"1\",\"artist\":\"Test Artist\",\"song\":\"Test Song\",\"genre\":\"Test Genre\"}]";

        mockMvc.perform(get("/api/allSongs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonResponse));
    }

    @Test
    public void addSong() throws Exception {
        Song newSong = new Song();
        newSong.setArtist("New Artist");
        newSong.setSong("New Song");
        newSong.setGenre("New Genre");

        Song savedSong = new Song();
        savedSong.setId("2");
        savedSong.setArtist(newSong.getArtist());
        savedSong.setSong(newSong.getSong());
        savedSong.setGenre(newSong.getGenre());

        when(songService.addSong(any(Song.class))).thenReturn(savedSong);

        ObjectMapper objectMapper = new ObjectMapper();
        String newSongJson = objectMapper.writeValueAsString(newSong);

        String expectedJsonResponse = "{\"id\":\"2\",\"artist\":\"New Artist\",\"song\":\"New Song\",\"genre\":\"New Genre\"}";

        mockMvc.perform(post("/api/addSong")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newSongJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJsonResponse));
    }
}