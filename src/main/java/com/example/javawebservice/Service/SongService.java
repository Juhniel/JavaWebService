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
                return objectMapper.readValue(file, new TypeReference<List<Song>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<Song> getSong(String songId) {
        List<Song> songs = getAllSongs();
        return songs.stream().filter(song -> song.getId().equals(songId)).findFirst();
    }

    public Song addSong(Song song) {
        List<Song> songs = getAllSongs();
        song.setId(UUID.randomUUID().toString());
        songs.add(song);
        saveSongs(songs);
        return song;
    }

    public Optional<Song> updateSong(String songId, Song updatedSong) {
        List<Song> songs = getAllSongs();
        Optional<Song> existingSong = songs.stream().filter(song -> song.getId().equals(songId)).findFirst();
        if (existingSong.isPresent()) {
            Song song = existingSong.get();
            song.setArtist(updatedSong.getArtist());
            song.setSong(updatedSong.getSong());
            saveSongs(songs);
        }
        return existingSong;
    }

    public boolean deleteSong(String songId) {
        List<Song> songs = getAllSongs();
        Optional<Song> songToDelete = songs.stream().filter(song -> song.getId().equals(songId)).findFirst();
        if (songToDelete.isPresent()) {
            songs.remove(songToDelete.get());
            saveSongs(songs);
            return true;
        }
        return false;
    }

    private void saveSongs(List<Song> songs) {
        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}