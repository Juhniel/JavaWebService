package com.example.javawebservice.Controller;

import com.example.javawebservice.Service.SongService;
import com.example.javawebservice.Model.Song;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/{songId}")
    public ResponseEntity<Song> getSong(@PathVariable String songId) {
        return songService.getSong(songId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        Song createdSong = songService.addSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<Song> updateSong(@PathVariable String songId, @RequestBody Song song) {
        return songService.updateSong(songId, song)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> deleteSong(@PathVariable String songId) {
        if (songService.deleteSong(songId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}