package com.libmanage.library_management_system.controller;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.libmanage.library_management_system.DTO.BooksDto;
import com.libmanage.library_management_system.entity.BookMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.entity.UsersMaster;
import com.libmanage.library_management_system.service.BookMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import java.awt.print.Book;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
public class BookMasterController {
    private BookMasterService bookMasterService;
    private Response response;

    @Autowired
    public BookMasterController(BookMasterService bookMasterService, Response response) {
        this.bookMasterService = bookMasterService;
        this.response = response;
    }

    //get all book api controller
    @GetMapping("/allBooks")
    public ResponseEntity<Response> getAllBooks(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "5") int limit) {
        if (page < 1 || limit < 1) {
            response.setStatus(0);
            response.setError("Invalid page or limit value");
            response.setPost(Collections.EMPTY_LIST);
            return ResponseEntity.badRequest().body(response);
        }
        Response booksWithDetailsResponse = bookMasterService.getAllBooks(page, limit);
        // List<Map<String, Object>> booksWithDetails = bookMasterService.getAllBooks();
        if (booksWithDetailsResponse.getStatus() == 1) {
            return ResponseEntity.ok(booksWithDetailsResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(booksWithDetailsResponse);
        }
    }

//    @GetMapping("/allBooks")
//    public List<BookMaster> getAllBooks(){
//        return bookMasterService.getAllBooks();
//    }

    //get book by id api controller
    @GetMapping("/{bookId}")
    public ResponseEntity<BookMaster> getBookById(@PathVariable Integer bookId) {
        BookMaster book = bookMasterService.getBookById(bookId);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //create book api controller
    @PostMapping("/add")
    public ResponseEntity<Response> createBook(@RequestBody BookMaster newBook) {
        if (newBook.getTitle() == null || newBook.getTitle().isEmpty() ||
                newBook.getAuthorId() == null || newBook.getGenreId() == null) {
            response.setStatus(0);
            response.setError("Missing required parameters");
            response.setPost(Collections.EMPTY_LIST);
            // If any required fields are missing in the request body, return bad request response
            return ResponseEntity.badRequest().body(response);
        }

        Response successResponse = bookMasterService.createBook(newBook);
        if (successResponse.getStatus() == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(successResponse);
        }
    }

    //update book api controller
    @PutMapping("/update/{bookId}")
    public ResponseEntity<Response> updateBook(@PathVariable Integer bookId, @RequestBody BookMaster updatedBook) {
        if (updatedBook.getTitle() == null || updatedBook.getTitle().isEmpty() ||
                updatedBook.getAuthorId() == null || updatedBook.getGenreId() == null || updatedBook.getActive() == null) {
            response.setStatus(0);
            response.setError("Missing required parameters");
            response.setPost(Collections.EMPTY_LIST);
            // If any required parameters are missing, return bad request response
            return ResponseEntity.badRequest().body(response);
        }

        Response response = bookMasterService.updateBook(bookId, updatedBook);

        if (response.getStatus() == 1) {
            // If inventory update was successful (status = 1), return HTTP status code 200 (OK)
            return ResponseEntity.ok(response);
        } else {
            // If inventory update failed, return HTTP status code 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
//        boolean updated = bookMasterService.updateBook(bookId, updatedBook);
//        if (updated) {
//            return ResponseEntity.ok(updatedBook);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }

    @PostMapping("/issue")
    public ResponseEntity<Response> issueBook(@RequestBody String jsonData) {
        JSONObject jsonObject = new JSONObject(jsonData);
        BookMaster bookMaster = bookMasterService.getBookById((Integer) jsonObject.get("bookId"));
        if (bookMaster != null) {
            Response issueResponse = bookMasterService.issueBook((Integer) jsonObject.get("bookId"), jsonObject.getString("userName"));
//            // Check if the book is available in inventory (isActive=true)
//            // Perform logic to issue the book
//            bookMaster.setActive(false); // Set isActive to false after issuing
//            bookMasterService.updateBook(bookId, bookMaster);
            if (issueResponse.getStatus() == 1) {

                response.setStatus(1);
                response.setError("Book issued successfully");
                response.setPost(Collections.emptyList());
                return ResponseEntity.ok(issueResponse);
            } else {
                response.setStatus(0);
                response.setError("Book not available for issuing");
                response.setPost(Collections.emptyList());
                return ResponseEntity.badRequest().body(response);
            }
        } else {
            response.setStatus(0);
            response.setError("Book not available for issuing");
            response.setPost(Collections.emptyList());
            return ResponseEntity.badRequest().body(response);
        }

    }

    //delete api controller
    @DeleteMapping("/deleteBook")
    public ResponseEntity<Response> deleteBook(@RequestBody Boolean active) {
        Response response = bookMasterService.deleteBook(active);

        if (response.getStatus() == 1) {
            // If book deletion was successful (status = 1), return HTTP status code 200 (OK)
            return ResponseEntity.ok(response);
        } else {
            // If book deletion failed, return HTTP status code 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/return/{borrowBookId}")
    public ResponseEntity<Response> returnBook(@PathVariable Integer borrowBookId) {
        try {
            Response returnResponse = bookMasterService.returnBook(borrowBookId);
            if (returnResponse.getStatus() == 1) {
                response.setStatus(1);
                response.setError("Book returned successfully");
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(0);
                response.setError("Failed to return the book");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            response.setStatus(0);
            response.setError("Failed to return the book: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
//        boolean deleted = bookMasterService.deleteBook(bookId);
//        if (deleted) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    @GetMapping("/books")
//    public String findAllBooks(Model model){
//        List<BookMaster> books = bookMasterService.findAllBooks();
//        model.addAttribute("books",books);
//        return "books.html";
//    }

//    @GetMapping("/books/{id}")
//    public String findBookById(Model model){
//
//    }

//    @GetMapping("/employees")
//    public List<Employee> getAllEmployee(){
//        return this.employeeRepository.findAll();
//    }
//    //get single employee
//    @GetMapping("/employees/{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
//        Employee employee= employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//        return ResponseEntity.ok().body(employee);
//    }
//    //save employee
//    @PostMapping("/employees")
//    public Employee createEmployee(@RequestBody Employee employee){
//        return this.employeeRepository.save(employee);
//    }
//    //update employee
//    @PutMapping("/employees/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, @Validated @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
//        Employee employee= employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//
//        employee.setEmail(employeeDetails.getEmail());
//        employee.setFirstName(employeeDetails.getFirstName());
//        employee.setLastName(employeeDetails.getLastName());
//
//        return ResponseEntity.ok().body(this.employeeRepository.save(employee));
//    }
//    //delete employee
//    @DeleteMapping("/employees/{id}")
//    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
//        Employee employee= employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//
//        this.employeeRepository.delete(employee);
//
//        Map<String,Boolean> response = new HashMap<>();
//        response.put("deleted",Boolean.TRUE);
//
//        return response;
//    }










