package com.example.javawebservice.Controller;

import com.example.javawebservice.Service.SongService;
import com.example.javawebservice.Model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SongController {


    @Autowired
    private SongService songService;

    // GetMappings för respektive url
    @GetMapping("/allSongs")
    public ResponseEntity<List<Song>> getAllSongs() {
        return ResponseEntity.ok(songService.getAllSongs());
    }

    @GetMapping("/artist/{artist}")
    public ResponseEntity<List<Song>> getSongsByArtist(@PathVariable String artist) {
        return ResponseEntity.ok(songService.getSongsByArtist(artist));
    }

    @PostMapping("/addSong")
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        // Skapar ny låt och lägger till den till response
        Song createdSong = songService.addSong(song);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSong);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Song>> getSongsByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(songService.getSongsByGenre(genre));
    }
}