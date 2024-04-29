package com.libmanage.library_management_system.controller;

import com.libmanage.library_management_system.entity.*;
import com.libmanage.library_management_system.service.BorrowedBookMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/borrow")
public class BorrowedBookController {

    private BorrowedBookMasterService borrowedBookMasterService;
    private Response response;

    @Autowired
    public BorrowedBookController(BorrowedBookMasterService borrowedBookMasterService, Response response) {
        this.borrowedBookMasterService = borrowedBookMasterService;
        this.response=response;
    }

    @GetMapping("/all")
    public List<BorrowedBookMaster> getAllBooks(){
        return borrowedBookMasterService.getAllBorrowedBooks();
    }
//    public ResponseEntity<List<BorrowedBookMaster>> getAllBorrowedBooks() {
//        List<BorrowedBookMaster> borrowedBooks = borrowedBookMasterService.getAllBorrowedBooks();
//        return ResponseEntity.ok(borrowedBooks);
//    }

    @GetMapping("/{borrowedBookId}")
    public ResponseEntity<BorrowedBookMaster> getBorrowedBookById(@PathVariable Integer borrowedBookId) {
        BorrowedBookMaster borrowedBook = borrowedBookMasterService.getBorrowedBookById(borrowedBookId);
        if (borrowedBook != null) {
            return ResponseEntity.ok(borrowedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/borrowed/user/{userName}")
//    public ResponseEntity<List<BorrowedBookMaster>> getBorrowedBookByUserName(@PathVariable String userName) {
//        response = new Response();
//
//        List<BorrowedBookMaster> borrowedBooks = borrowedBookMasterService.getBorrowedBookByUserName(userName);
//        if (borrowedBooks!=null) {
////            response.setStatus(1);
////            response.setError("Borrowed books for the userId " + userName);
////            response.setPost(borrowedBooks);
//            return ResponseEntity.ok(borrowedBooks);
//        } else {
////            response.setStatus(0);
////            response.setError("No borrowed books found for the userId " + userName);
////            response.setPost(Collections.emptyList());
//            return ResponseEntity.ok(Collections.emptyList());
//        }
//    }



    // Endpoint to get borrowed books for a specific user
    @GetMapping("/borrowed-books/{username}")
    public ResponseEntity<List<BorrowedBookMaster>> getBorrowedBooks(@PathVariable String username) {
        try {
            List<BorrowedBookMaster> borrowedBooks = borrowedBookMasterService.getBorrowedBookByUserName(username);
            if (borrowedBooks.isEmpty()) {
                return ResponseEntity.noContent().build(); // No borrowed books found
            } else {
                return ResponseEntity.ok(borrowedBooks); // Return list of borrowed books
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Response> createBorrowedBook(@RequestBody BorrowedBookMaster borrowedBook) {
        Response response = borrowedBookMasterService.createBorrowedBook(borrowedBook);
        if (response.getStatus() == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("/update/{borrowedBookId}")
    public ResponseEntity<Response> updateBorrowedBook(@PathVariable Integer borrowedBookId, @RequestBody BorrowedBookMaster updatedBorrowedBook) {
        if (updatedBorrowedBook.getTitle() == null || updatedBorrowedBook.getTitle().isEmpty() ||
                updatedBorrowedBook.getAuthorId()==null || updatedBorrowedBook.getGenreId() == null || updatedBorrowedBook.getIssueDate()==null||
        updatedBorrowedBook.getDueDate()==null || updatedBorrowedBook.getUpdatedBy()==null) {
            response.setStatus(0);
            response.setError("Missing required parameters");
            response.setPost(Collections.EMPTY_LIST);
            // If any required parameters are missing, return bad request response
            return ResponseEntity.badRequest().body(response);
        }
        Response response = borrowedBookMasterService.updateBorrowedBook(borrowedBookId, updatedBorrowedBook);
        if (response.getStatus() == 1) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/delete/{borrowedBookId}")
    public ResponseEntity<Response> deleteBorrowedBook(@PathVariable Integer borrowedBookId) {
        Response response = borrowedBookMasterService.deleteBorrowedBook(borrowedBookId);
        if (response.getStatus() == 1) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
