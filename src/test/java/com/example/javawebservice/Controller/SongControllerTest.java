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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;


    @Test
    public void getAllSongs_shouldReturnSongs() throws Exception {
        Song song = new Song();
        song.setId("1");
        song.setArtist("Test Artist");
        song.setSong("Test Song");
        song.setGenre("Test Genre");

        List<Song> allSongs = Collections.singletonList(song);

        when(songService.getAllSongs()).thenReturn(allSongs);

        mockMvc.perform(get("/api/allSongs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(allSongs.size()))
                .andExpect(jsonPath("$[0].id").value(song.getId()))
                .andExpect(jsonPath("$[0].artist").value(song.getArtist()))
                .andExpect(jsonPath("$[0].song").value(song.getSong()))
                .andExpect(jsonPath("$[0].genre").value(song.getGenre()));
    }

    @Test
    public void addSong_shouldAddSong() throws Exception {
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

        mockMvc.perform(post("/api/addSong")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newSongJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(savedSong.getId()))
                .andExpect(jsonPath("$.artist").value(savedSong.getArtist()))
                .andExpect(jsonPath("$.song").value(savedSong.getSong()))
                .andExpect(jsonPath("$.genre").value(savedSong.getGenre()));
    }
}