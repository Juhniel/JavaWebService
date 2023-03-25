package com.example.javawebservice.Service;

import com.example.javawebservice.Model.Song;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SongService {

    private static final String JSON_FILE_PATH = "src/main/resources/music.json";
    private final ObjectMapper objectMapper;

    public SongService() {
        objectMapper = new ObjectMapper();
    }

    public List<Song> getAllSongs() {
        try {
            File file = new File(JSON_FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Song addSong(Song song) {
        List<Song> songs = getAllSongs();
        song.setId(UUID.randomUUID().toString());
        songs.add(song);
        saveSongs(songs);
        return song;
    }

    private void saveSongs(List<Song> songs) {
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Song> getSongsByArtist(String artist) {
        List<Song> songs = getAllSongs();
        return songs.stream()
                .filter(song -> song.getArtist().equalsIgnoreCase(artist))
                .collect(Collectors.toList());
    }

    public List<Song> getSongsByGenre(String genre) {
        List<Song> songs = getAllSongs();
        return songs.stream()
                .filter(song -> song.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }
}