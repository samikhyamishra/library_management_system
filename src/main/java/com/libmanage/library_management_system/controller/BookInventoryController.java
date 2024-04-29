package com.libmanage.library_management_system.controller;

import com.libmanage.library_management_system.entity.BookInventoryMaster;
import com.libmanage.library_management_system.entity.BookMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.entity.UsersMaster;
import com.libmanage.library_management_system.service.BookInventoryMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class BookInventoryController {
    private BookInventoryMasterService bookInventoryMasterService;
    private Response response;
    @Autowired
    public BookInventoryController(BookInventoryMasterService bookInventoryMasterService, Response response) {
        this.bookInventoryMasterService = bookInventoryMasterService;
        this.response=response;
    }

    //get all inventory api controller
    @GetMapping("/AllInventory")
    public List<BookInventoryMaster> getAllInventory(){
        return bookInventoryMasterService.getAllInventory();
    }

    //get invent by inventory id api controller
    @GetMapping("/{inventId}")
    public ResponseEntity<BookInventoryMaster> getInventoryByInventId(@PathVariable Integer inventId) {
        BookInventoryMaster bookInventory = bookInventoryMasterService.getInventoryByInventId(inventId);
        if (bookInventory != null) {
            return ResponseEntity.ok(bookInventory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//create Inventory api controller
     @PostMapping("/create")
        public ResponseEntity<Response> createInventory(@RequestBody Map<String, Integer> inventoryAddRequest) {
            Integer bookId = inventoryAddRequest.get("bookId");
            Integer noOfCopies = inventoryAddRequest.get("noOfCopies");
         if (bookId == null || noOfCopies == null) {
             response.setStatus(0);
             response.setError("Missing required parameters");
             response.setPost(Collections.EMPTY_LIST);
             // If either bookId or noOfCopies is missing in the request body, return bad request response
             return ResponseEntity.badRequest().body(response);
         }

         Response response = bookInventoryMasterService.createInventory(bookId, noOfCopies);

         if (response.getStatus() == 1) {
             // If inventory creation was successful (status = 1), return HTTP status code 201 (CREATED)
             return ResponseEntity.status(HttpStatus.CREATED).body(response);
         } else {
             // If inventory creation failed, return HTTP status code 500 (INTERNAL SERVER ERROR)
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
         }
        }

        //update inventory api controller
        @PutMapping("/updateInventory")
        public ResponseEntity<Response> updateInventory(@RequestBody Map<String, Integer> inventoryUpdateRequest) {
            Integer bookId = inventoryUpdateRequest.get("bookId");
            Integer noOfCopies = inventoryUpdateRequest.get("noOfCopies");
            if (bookId == null || noOfCopies == null) {
                response.setStatus(0);
                response.setError("Missing required parameters");
                response.setPost(Collections.EMPTY_LIST);
                // If any required parameters are missing, return bad request response
                return ResponseEntity.badRequest().body(response);
            }

            Response response = bookInventoryMasterService.updateInventory(bookId, noOfCopies);

            if (response.getStatus() == 1) {
                // If inventory update was successful (status = 1), return HTTP status code 200 (OK)
                return ResponseEntity.ok(response);
            } else {
                // If inventory update failed, return HTTP status code 500 (INTERNAL SERVER ERROR)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }

       // deactivate inventory api controller
       @DeleteMapping("/delete/{inventId}")
       public ResponseEntity<Response> deleteInventory(@PathVariable Integer inventId) {
           Response response = bookInventoryMasterService.deleteInventory(inventId);
           return ResponseEntity.status(HttpStatus.OK).body(response);
       }

//    @GetMapping("/{inventId}")
//    public ResponseEntity<BookInventoryMaster> getInventoryByInventId(@PathVariable Integer inventId){
//        return bookInventoryMasterService.getInventoryByInventId(inventId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }


}
