package com.libmanage.library_management_system.service;


import com.libmanage.library_management_system.entity.BookInventoryMaster;
import com.libmanage.library_management_system.entity.BookMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.repository.BookInventoryMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.rmi.MarshalledObject;
import java.util.Collections;
import java.util.List;

@Service
public class BookInventoryMasterService {
    private BookInventoryMasterRepository bookInventoryMasterRepository;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;
    private Response response;
    @Autowired
    public BookInventoryMasterService(BookInventoryMasterRepository bookInventoryMasterRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Response response) {
        this.bookInventoryMasterRepository = bookInventoryMasterRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.response=response;
    }

    //get all invent
    public List<BookInventoryMaster> getAllInventory() {
        return bookInventoryMasterRepository.findAll();
    }

    //get invent by invent id
    public BookInventoryMaster getInventoryByInventId(Integer inventId) {
        String selectSql = "SELECT * FROM book_inventory_master WHERE invent_id = :inventId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("inventId", inventId);
        try {
            return namedParameterJdbcTemplate.queryForObject(selectSql, mapSqlParameterSource, new BeanPropertyRowMapper<>(BookInventoryMaster.class));
        } catch (Exception e) {
            return null;
        }
    }
        //create invent

        public Response createInventory(Integer bookId, Integer noOfCopies){
            try {
                String sql = "INSERT INTO book_inventory_master(book_id, no_of_copies) " +
                        "VALUES (:bookId,:noOfCopies)";
                mapSqlParameterSource = new MapSqlParameterSource();
                mapSqlParameterSource.addValue("bookId", bookId);
                mapSqlParameterSource.addValue("noOfCopies", noOfCopies);
                namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
                response.setStatus(1);
                response.setError("Book inventory created successfully");
                response.setPost(Collections.EMPTY_LIST);
                return response;
                //return new Response<>(1, "Book inventory created successfully", new Object[]{});
            }
            catch (Exception e){
                e.printStackTrace();
                response.setStatus(0);
                response.setError("Inventory not Created. Error: "+ e.getMessage());
                response.setPost(Collections.EMPTY_LIST);
                return response;
               // return new Response<>(0, "Inventory not Created. Error: "+ e.getMessage(), new Object[]{});
            }
        }
        //update invent
        public Response updateInventory(Integer bookId, Integer noOfCopies) {
            String sql = "UPDATE book_inventory_master SET no_of_copies = :noOfCopies WHERE book_id = :bookId";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("noOfCopies", noOfCopies);
            mapSqlParameterSource.addValue("bookId", bookId);
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            response.setStatus(1);
            response.setError("Inventory updated successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;
            //return new Response<>(1, "Inventory updated successfully", new Object[]{});
        }
        //delete/deactive invent
        public Response deleteInventory(Integer inventId) {
            String sql = "UPDATE book_inventory_master SET no_of_copies = :0";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("inventId", inventId);
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            response.setStatus(1);
            response.setError("Inventory deleted successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;
           // return new Response<>(1, "Inventory deleted successfully.", new Object[]{});
        }

        ////    fetch all the inventories
//    public List<BookInventoryMaster> findAllInventory(){
//        return bookInventoryMasterRepository.findAll();
//    }
//
////    fetch inventory by id
//    public BookInventoryMaster findInventoryById(Integer bookId){
//        BookInventoryMaster inventory = bookInventoryMasterRepository.findById(bookId).or ElseThrow(()-> new RuntimeException("Inventory Not Found"));
//        return inventory;
//    }
//
////    create inventory
//    public void createInventory(BookInventoryMaster inventory){
//        bookInventoryMasterRepository.save(inventory);
//    }
//
////    remove an inventory with specific id
//    public void deleteInventory(Long bookId){
//        BookInventoryMaster inventory = bookInventoryMasterRepository.findById(bookId).orElseThrow(()-> new RuntimeException("Inventory Not Found"));
//        bookInventoryMasterRepository.deleteById(inventory.getBookId());
//    }

}
