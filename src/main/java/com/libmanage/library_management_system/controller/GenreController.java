package com.libmanage.library_management_system.controller;

import com.libmanage.library_management_system.entity.BorrowedBookMaster;
import com.libmanage.library_management_system.entity.GenreMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.service.GenreMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

    private GenreMasterService genreMasterService;
    private Response response;

    @Autowired
    public GenreController(GenreMasterService genreMasterService,Response response) {
        this.genreMasterService = genreMasterService;
        this.response=response;
    }

    @GetMapping("/allGenre")
    public List<GenreMaster> getAllGenre(){
        return genreMasterService.getAllGenre();
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<GenreMaster> getGenreById(@PathVariable Integer genreId) {
        GenreMaster genreMaster = genreMasterService.getGenreByGenreId(genreId);
        if (genreMaster != null) {
            return ResponseEntity.ok(genreMaster);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/addGenre")
    public ResponseEntity<Response> createGenre(@RequestBody String genreName) {
        Response response = genreMasterService.createNewGenre(genreName);
        if (response.getStatus() == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("/update/{genreId}")
    public ResponseEntity<Response> updatedGenre(@PathVariable Integer genreId, @RequestBody GenreMaster updatedGenre) {
        if (updatedGenre.getGenreName() == null || updatedGenre.getGenreName().isEmpty() ||
                updatedGenre.getUpdatedBy()==null) {
            response.setStatus(0);
            response.setError("Missing required parameters");
            response.setPost(Collections.EMPTY_LIST);
            // If any required parameters are missing, return bad request response
            return ResponseEntity.badRequest().body(response);
        }
        Response response = genreMasterService.updatedGenre(genreId, updatedGenre);
        if (response.getStatus() == 1) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{genreId}")
    public ResponseEntity<Response> deleteGenre(@PathVariable Integer genreId) {
        Response response = genreMasterService.deleteGenre(genreId);
        if (response.getStatus() == 1) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}


