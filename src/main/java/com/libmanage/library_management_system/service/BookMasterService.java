package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.DTO.BooksDto;
import com.libmanage.library_management_system.entity.BookMaster;
import com.libmanage.library_management_system.entity.BorrowedBookMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.entity.UsersMaster;
import com.libmanage.library_management_system.repository.BookMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import java.util.List;

@Service
public class BookMasterService {

    private BookMasterRepository bookMasterRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;
    private Response response;


   // private static Logger logger = LoggerFactory.getLogger(BookMasterService.class);


    @Autowired
    public BookMasterService(BookMasterRepository bookMasterRepository, Response response) {
        this.bookMasterRepository = bookMasterRepository;
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.response = response;
    }

    //get book by id api
    public BookMaster getBookById(Integer bookId) {
        String selectSql = "SELECT * FROM book_master WHERE book_id = :bookId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("bookId", bookId);
        try {
            return namedParameterJdbcTemplate.queryForObject(selectSql, mapSqlParameterSource, new BeanPropertyRowMapper<>(BookMaster.class));
        } catch (Exception e) {
            return null;
        }
    }

    //get all book
    public Response getAllBooks(int page, int limit) {
        try {
            int offset = (page - 1) * limit; // Calculate offset based on page number

            String sql = "SELECT bm.book_id AS bookId, bm.title AS title, bm.is_active AS active, " +
                    "CONCAT(am.first_name, ' ', am.last_name) AS authorName," +
                    " gm.genre_name AS genreName " +
                    "FROM book_master bm " +
                    "LEFT JOIN author_master am ON bm.author_id = am.author_id " +
                    "LEFT JOIN genre_master gm ON bm.genre_id = gm.genre_id "+
                    "LIMIT :limit OFFSET :offset";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("limit", limit);
            mapSqlParameterSource.addValue("offset", offset);
            List<Map<String, Object>> books= namedParameterJdbcTemplate.queryForList(sql, mapSqlParameterSource);
            //logger.info("Retrieved {} books", books.size());
            response.setError("All Books retrieved successfully");
            response.setStatus(1);
            response.setPost(books);
            return response;
            //return namedParameterJdbcTemplate.queryForList(sql, Collections.emptyMap());
        } catch (Exception e) {
           // logger.error("Error occurred while fetching books", e);
            response.setStatus(0);
            response.setError("Error occurred while fetching books" + e.getMessage());
            response.setPost(Collections.emptyList());
            return response;
        }
    }
//    public List<BookMaster> getAllBooks() {
//        return bookMasterRepository.findAll();
//    }

    //add new book
    public Response createBook(BookMaster newBook) {
        try {
            String insertSql = "INSERT INTO book_master(title, author_id, genre_id, is_active) VALUES (:title, :authorId, :genreId, :active)";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("title", newBook.getTitle());
            mapSqlParameterSource.addValue("authorId", newBook.getAuthorId());
            mapSqlParameterSource.addValue("genreId", newBook.getGenreId());
            mapSqlParameterSource.addValue("active", newBook.getActive());
            namedParameterJdbcTemplate.update(insertSql, mapSqlParameterSource);
            response.setStatus(1);
            response.setError("Book added successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;
            //  return new Response(1, "Book added successfully", new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(0);
            response.setError("Book not added. Error: " + e.getMessage());
            response.setPost(Collections.EMPTY_LIST);
            return response;
            //return new Response<>(0, "Book not added. Error: "+ e.getMessage(), new Object[]{});
        }
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int updatedRows=0;
//        try {
//            updatedRows = namedParameterJdbcTemplate.update(insertSql, mapSqlParameterSource, keyHolder, new String[]{"book_id"});
//            if (updatedRows > 0) {
//                return keyHolder.getKey().intValue();
//            } else {
//                return null;
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
    }

    //update book
    public Response updateBook(Integer bookId, BookMaster updatedBook) {
        String updateSql = "UPDATE book_master SET title = :title, author_id = :authorId, genre_id = :genreId, is_active = :active WHERE book_id = :bookId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", updatedBook.getTitle());
        mapSqlParameterSource.addValue("authorId", updatedBook.getAuthorId());
        mapSqlParameterSource.addValue("genreId", updatedBook.getGenreId());
        mapSqlParameterSource.addValue("active", updatedBook.getActive());
        mapSqlParameterSource.addValue("bookId", bookId);
        namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);
        response.setStatus(1);
        response.setError("Book List updated successfully");
        response.setPost(Collections.EMPTY_LIST);
        return response;
        //return new Response<>(1, "Book List updated successfully", new Object[]{});
//        int updatedRows = namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);
//        return updatedRows > 0;
    }

    //delete book
    public Response deleteBook(Boolean active) {
        String sql = "UPDATE book_master SET is_active = :false";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("active", active);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        response.setStatus(1);
        response.setError("Book deleted successfully");
        response.setPost(Collections.EMPTY_LIST);
        return response;
        //return new Response<>(1, "Inventory deleted successfully.", new Object[]{});

//        int deletedRows = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
//        return deletedRows > 0;
    }

    public Response issueBook(Integer bookId, String userName) {
        String checkInventorySql = "SELECT COUNT(*) FROM book_inventory_master WHERE book_id = :bookId AND no_of_copies > 0";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("bookId", bookId);
        // Check available count after updating inventory
        List<BooksDto> availableBooksCount = namedParameterJdbcTemplate.query(checkInventorySql, mapSqlParameterSource, new BeanPropertyRowMapper<>(BooksDto.class));
        if ( !availableBooksCount.isEmpty()){
            // Update inventory first
            String updateInventorySql = "UPDATE book_inventory_master SET no_of_copies = no_of_copies - 1 WHERE book_id = :bookId";
            int updatedCount = namedParameterJdbcTemplate.update(updateInventorySql, mapSqlParameterSource);
            BookMaster bookMaster = getBookById(bookId);
//            String userName = userMasterService.getUserByUserName(userName);
            // Insert into borrowed table
            String insertBorrowedSql = "INSERT INTO borrowed_book_master (book_id, title, author_id, genre_id, issue_date, due_date, is_active, user_name) " +
                    "VALUES (:bookId, :title, :authorId, :genreId, CURRENT_DATE, CURRENT_DATE + INTERVAL '10 days', true, :userName)";
            mapSqlParameterSource.addValue("bookId",bookId);
            mapSqlParameterSource.addValue("title", bookMaster.getTitle());
            mapSqlParameterSource.addValue("authorId", bookMaster.getAuthorId());
            mapSqlParameterSource.addValue("genreId", bookMaster.getGenreId());
            mapSqlParameterSource.addValue("userName", userName);
            namedParameterJdbcTemplate.update(insertBorrowedSql, mapSqlParameterSource);

            response.setStatus(1);
            response.setError("Book issued successfully");
        }
        else {

            response.setStatus(0);
            response.setError("This Book is out of stock.");
        }
        response.setPost(Collections.emptyList());
        return response;
    }


    public Response returnBook(Integer borrowBookId) {
        try {
            // Update book_master to set is_active = true
//            String updateBookSql = "UPDATE book_master SET is_active = true WHERE book_id = :bookId";
//            mapSqlParameterSource = new MapSqlParameterSource();
//            mapSqlParameterSource.addValue("bookId", borrowBookId);
//            namedParameterJdbcTemplate.update(updateBookSql, mapSqlParameterSource);
            // Perform other operations related to returning a book (e.g., update inventory)
            String updateBorrowSql = "UPDATE borrowed_book_master SET is_active = false, return_date = CURRENT_DATE WHERE borrow_book_id= :borrowBookId AND is_active = true";
            mapSqlParameterSource= new MapSqlParameterSource();
            mapSqlParameterSource.addValue("borrowBookId",borrowBookId);
            namedParameterJdbcTemplate.update(updateBorrowSql,mapSqlParameterSource);
            String fetchSql = "SELECT * FROM borrowed_book_master WHERE borrow_book_id= :borrowBookId";
           List<BorrowedBookMaster> borrowedBookMasters = namedParameterJdbcTemplate.query(fetchSql,mapSqlParameterSource, new BeanPropertyRowMapper<>(BorrowedBookMaster.class));
            String updateInventorySql = "UPDATE book_inventory_master SET no_of_copies= no_of_copies+1 WHERE book_id = :bookId ";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("bookId", borrowedBookMasters.getFirst().getBookId());
            namedParameterJdbcTemplate.update(updateInventorySql, mapSqlParameterSource);
            response.setStatus(1);
            response.setError("Book returned successfully");
        } catch (Exception e) {
            response.setStatus(0);
            response.setError("Failed to return the book: " + e.getMessage());
        }
        response.setPost(Collections.emptyList());
        return response;
    }
}


//    get all books
//get employee
//@GetMapping("/employees")
//public List<Employee> getAllEmployee(){
//    return this.employeeRepository.findAll();
//}
//    //get single employee
//    @GetMapping("/employees/{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
//        Employee employee= employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//        return ResponseEntity.ok().body(employee);
//    }
//    public List<BookMaster> findAllBooks(){
//        return bookMasterRepository.findAll();
//    }
//
////    fetch book by specific id
//    public BookMaster findBookById(Long bookId){
//
//       BookMaster book= bookMasterRepository.findById(bookId).orElseThrow(()-> new RuntimeException("Book Not Found"));
//        return book;
//    }
//
////    create the book
//    public void  createBook(BookMaster book){
//        bookMasterRepository.save(book);
//    }
//
////    remove a book with a particular id
//    public void deleteBook(Long bookId){
//        BookMaster book= bookMasterRepository.findById(bookId).orElseThrow(()-> new RuntimeException("Book Not Found"));
//        bookMasterRepository.deleteById(book.getBookId());
//    }

//    saving the updates


