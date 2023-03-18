package com.example.javawebservice.Controller;

import com.example.javawebservice.Service.SongService;
import com.example.javawebservice.Model.Song;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/allSongs")
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<Song>> getSongsByArtist(@PathVariable String artist) {
        return ResponseEntity.ok(songService.getSongsByArtist(artist));
    }

    @GetMapping("/{songId}")
    public ResponseEntity<Song> getSong(@PathVariable String songId) {
        return songService.getSong(songId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/songs")
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        Song createdSong = songService.addSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }
}