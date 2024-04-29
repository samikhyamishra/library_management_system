package com.libmanage.library_management_system.controller;

import com.libmanage.library_management_system.entity.AuthorMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.service.AuthorMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorMasterController {
    private AuthorMasterService authorMasterService;
    private Response response;
    @Autowired
    public AuthorMasterController(AuthorMasterService authorMasterService, Response response) {
        this.authorMasterService = authorMasterService;
        this.response=response;
    }

    //get all authors api controller
    @GetMapping("/allAuthors")
    public List<AuthorMaster> getAllBooks(){
        return authorMasterService.getAllAuthor();
    }

    //get author by id
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorMaster> getAuthorById(@PathVariable Integer authorId) {
        AuthorMaster author = authorMasterService.getAuthorById(authorId);
        if (author != null) {
            return ResponseEntity.ok(author);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //add author controller
    @PostMapping("/add")
    public ResponseEntity<Response> createAuthor(@RequestBody AuthorMaster author) {
        Response response = authorMasterService.createAuthor(author);
        if (response.getStatus() == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
//        AuthorMaster createdAuthor = authorMasterService.createAuthor(author);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    //update author controller
    @PutMapping("/{authorId}")
    public ResponseEntity<Response> updateAuthor(@PathVariable Integer authorId, @RequestBody AuthorMaster updatedAuthor) {
        if (updatedAuthor.getFirstName() == null || updatedAuthor.getFirstName().isEmpty() ||
                updatedAuthor.getLastName()==null || updatedAuthor.getAuthorId() == null) {
            response.setStatus(0);
            response.setError("Missing required parameters");
            response.setPost(Collections.EMPTY_LIST);
            // If any required parameters are missing, return bad request response
            return ResponseEntity.badRequest().body(response);
        }
        Response response = authorMasterService.updateAuthor(authorId, updatedAuthor);

        if (response.getStatus() == 1) {
            // If inventory update was successful (status = 1), return HTTP status code 200 (OK)
            return ResponseEntity.ok(response);
        } else {
            // If inventory update failed, return HTTP status code 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @DeleteMapping("/delete/{authorId}")
    public ResponseEntity<Response> deleteAuthor(@PathVariable Integer authorId) {
        Response response = authorMasterService.deleteAuthor(authorId);

        if (response.getStatus() == 1) {
            // If book deletion was successful (status = 1), return HTTP status code 200 (OK)
            return ResponseEntity.ok(response);
        } else {
            // If book deletion failed, return HTTP status code 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
//        boolean deleted = authorMasterService.deleteAuthor(authorId);
//        if (deleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }

    }


