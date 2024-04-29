package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.entity.AuthorMaster;
import com.libmanage.library_management_system.entity.BookMaster;
import com.libmanage.library_management_system.entity.BorrowedBookMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.repository.BorrowedBookMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BorrowedBookMasterService {
    private BorrowedBookMasterRepository borrowedBookMasterRepository;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;
    private Response response;

    @Autowired
    public BorrowedBookMasterService(BorrowedBookMasterRepository borrowedBookMasterRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Response response) {
        this.borrowedBookMasterRepository = borrowedBookMasterRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.response=response;
    }

    public List<BorrowedBookMaster> getAllBorrowedBooks() {
        String sql = "SELECT * FROM borrowed_book_master";
        List<BorrowedBookMaster> borrowedBooks = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BorrowedBookMaster.class));
        return borrowedBooks;
    }

    public BorrowedBookMaster getBorrowedBookById(Integer borrowedBookId) {
        String sql = "SELECT * FROM borrowed_book_master WHERE borrow_book_id = :borrowBookId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("borrowBookId", borrowedBookId);
        try {
              return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, new BeanPropertyRowMapper<>(BorrowedBookMaster.class));
        } catch (Exception e) {
            return null;
            // Handle if no record found or any other exception
        }
    }

    public List<BorrowedBookMaster> getBorrowedBookByUserName(String userName){
        String sql = "SELECT * FROM borrowed_book_master WHERE user_name = :userName";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", userName);

        try {
            return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new BeanPropertyRowMapper<>(BorrowedBookMaster.class));
//             response.setStatus(1);
//             response.setError("Borrowed books for the userName"+userName);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
//            response.setStatus(0);
//            response.setPost("Error in retrieving data.");// Return an empty list on error
        }
//        response.setPost(Collections.EMPTY_LIST);
//         response;
    }


    public Response createBorrowedBook(BorrowedBookMaster borrowedBook) {
        try{
        String insertSql = "INSERT INTO borrowed_book_master (title, author_id, genre_id, issue_date, due_date, created_by) " +
                "VALUES (:title, :authorId, :genreId, :issueDate, :dueDate, :createdBy)";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", borrowedBook.getTitle());
        mapSqlParameterSource.addValue("authorId", borrowedBook.getAuthorId());
        mapSqlParameterSource.addValue("genreId", borrowedBook.getGenreId());
        mapSqlParameterSource.addValue("issueDate", borrowedBook.getIssueDate());
        mapSqlParameterSource.addValue("dueDate", borrowedBook.getDueDate());
        mapSqlParameterSource.addValue("createdBy", borrowedBook.getCreatedBy());
        response.setStatus(1);
        response.setError("Book Borrowed successfully");
        response.setPost(Collections.EMPTY_LIST);
        return response;
       // return new Response<>(1, "Book Borrowed successfully", new Object[]{});
    }
            catch (Exception e){
        e.printStackTrace();
                response.setStatus(0);
                response.setError("Error: "+ e.getMessage());
                response.setPost(Collections.EMPTY_LIST);
                return response;
        //return new Response<>(0, "Error: "+ e.getMessage(), new Object[]{});
    }

//        int rowsAffected = namedParameterJdbcTemplate.update(insertSql, params);
//        if (rowsAffected > 0) {
//            return new Response<>(1, "Borrowed book created successfully", new Object[]{});
//        } else {
//            return new Response<>(0, "Failed to create borrowed book", new Object[]{});
//        }
    }

    public Response updateBorrowedBook(Integer borrowedBookId, BorrowedBookMaster updatedBorrowedBook) {
        String updateSql = "UPDATE borrowed_book_master " +
                "SET title = :title, author_id = :authorId, genre_id = :genreId, issue_date = :issueDate, " +
                "due_date = :dueDate, updated_by = :updatedBy, updated_on = CURRENT_TIMESTAMP " +
                "WHERE borrow_book_id = :borrowBookId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", updatedBorrowedBook.getTitle());
        mapSqlParameterSource.addValue("authorId", updatedBorrowedBook.getAuthorId());
        mapSqlParameterSource.addValue("genreId", updatedBorrowedBook.getGenreId());
        mapSqlParameterSource.addValue("issueDate", updatedBorrowedBook.getIssueDate());
        mapSqlParameterSource.addValue("dueDate", updatedBorrowedBook.getDueDate());
        mapSqlParameterSource.addValue("updatedBy", updatedBorrowedBook.getUpdatedBy());
        mapSqlParameterSource.addValue("borrowBookId", borrowedBookId);

        int rowsAffected = namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);
        if (rowsAffected > 0) {
            response.setStatus(1);
            response.setError("Borrowed book updated successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;
           // return new Response<>(1, "Borrowed book updated successfully", new Object[]{});
        } else {
            response.setStatus(0);
            response.setError("Failed to update borrowed book");
            response.setPost(Collections.EMPTY_LIST);
            return response;
          //  return new Response<>(0, "Failed to update borrowed book", new Object[]{});
        }
    }

    public Response deleteBorrowedBook(Integer borrowedBookId) {
        String deleteSql = "DELETE FROM borrowed_book_master WHERE borrow_book_id = :borrowBookId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("borrowBookId", borrowedBookId);

        int rowsAffected = namedParameterJdbcTemplate.update(deleteSql, mapSqlParameterSource);
        if (rowsAffected > 0) {
            response.setStatus(1);
            response.setError("Borrowed book deleted successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;
            //return new Response<>(1, "Borrowed book deleted successfully", new Object[]{});
        } else {
            response.setStatus(0);
            response.setError("Failed to delete borrowed book");
            response.setPost(Collections.EMPTY_LIST);
            return response;
           // return new Response<>(0, "Failed to delete borrowed book", new Object[]{});
        }
    }

//    public List<BorrowedBookMaster> fetchBorrowedBooksByUserId(String userId) {
//        String sql = "SELECT * FROM borrowed_book_master WHERE user_login_id = :userId";
//        mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("userId", userId);
//
//        try {
//            return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new BeanPropertyRowMapper<>(BorrowedBookMaster.class));
//        } catch (Exception e) {
//            // Handle any exceptions (e.g., database query errors)
//            e.printStackTrace();
//            return Collections.emptyList(); // Return an empty list on error
//        }
//    }
}

    ////    fetch all borrowed books
//    public List<BorrowedBookMaster> findAllBorrowed(){
//        return borrowedBookMasterRepository.findAll();
//    }
//
////    fetch borrowed books by specific bookId
//    public BorrowedBookMaster findBorrowedById(Long bookId){
//        BorrowedBookMaster borrowed = borrowedBookMasterRepository.findById(bookId).orElseThrow(()-> new RuntimeException("No Such Book with this Id Borrowed"));
//        return borrowed;
//    }
//
////    add borrowed book
//    public void addBorrowed(BorrowedBookMaster borrowed){
//        borrowedBookMasterRepository.save(borrowed);
//    }
//
////    remove a book from borrowed with a specific bookid
//    public void deleteBorrowed(Long bookId){
//        BorrowedBookMaster borrowed = borrowedBookMasterRepository.findById(bookId).orElseThrow(()-> new RuntimeException("No such book with this book id in borrowed list"));
//        borrowedBookMasterRepository.deleteById(borrowed.getBookId());
//    }


