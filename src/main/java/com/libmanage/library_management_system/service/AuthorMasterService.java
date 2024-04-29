package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.entity.AuthorMaster;
import com.libmanage.library_management_system.entity.BookMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.repository.AuthorMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorMasterService {

    private AuthorMasterRepository authorMasterRepository;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;
    private Response response;

    @Autowired
    public AuthorMasterService(AuthorMasterRepository authorMasterRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Response response) {
        this.authorMasterRepository = authorMasterRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.response= response;
    }

    //get all author
    public List<AuthorMaster> getAllAuthor(){
        return authorMasterRepository.findAll();
    }

    //get author by id
    public AuthorMaster getAuthorById(Integer authorId) {
        String selectSql = "SELECT * FROM author_master WHERE author_id = :authorId";
        mapSqlParameterSource  = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("authorId", authorId);
        try {
            return namedParameterJdbcTemplate.queryForObject(selectSql, mapSqlParameterSource, new BeanPropertyRowMapper<>(AuthorMaster.class));
        } catch (Exception e) {
            return null;
        }
    }

    //add author
    public Response createAuthor(AuthorMaster author) {
        try{
        String insertSql = "INSERT INTO author_master (first_name, last_name) VALUES (:firstName, :lastName)";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("firstName", author.getFirstName());
        mapSqlParameterSource.addValue("lastName", author.getLastName());
        namedParameterJdbcTemplate.update(insertSql, mapSqlParameterSource);
        response.setStatus(1);
        response.setError("New Author added successfully");
        response.setPost(Collections.EMPTY_LIST);
        return response;
        //return new Response<>(1, "New Author added successfully", new Object[]{});
    }
            catch (Exception e){
        e.printStackTrace();
                response.setStatus(0);
                response.setError("Error: "+ e.getMessage());
                response.setPost(Collections.EMPTY_LIST);
                return response;
        //return new Response<>(0, "Error: "+ e.getMessage(), new Object[]{});
    }
//        // You may need to fetch the generated authorId and set it in the Author object
//        return author;
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int updatedRows=0;
//        try {
//            updatedRows = namedParameterJdbcTemplate.update(insertSql, mapSqlParameterSource, keyHolder, new String[]{"author_id"});
//            if (updatedRows > 0) {
//                author.setAuthorId(Objects.requireNonNull(keyHolder.getKey()).intValue());
//                return author;
//                //return keyHolder.getKey().intValue();
//            } else {
//                return null;
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
    }

    //update author api
    public Response updateAuthor(Integer authorId, AuthorMaster updatedAuthor) {
        String sql = "UPDATE author_master SET first_name = :firstName, last_name = :lastName WHERE author_id = :authorId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("firstName", updatedAuthor.getFirstName());
        mapSqlParameterSource.addValue("lastName", updatedAuthor.getLastName());
        mapSqlParameterSource.addValue("authorId", authorId);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        response.setStatus(1);
        response.setError("Author Details updated successfully");
        response.setPost(Collections.EMPTY_LIST);
        return response;

        //return new Response<>(1, "Author Details updated successfully", new Object[]{});
//        if (updatedRows > 0) {
//            // Return the updated author object
//            return updatedAuthor;
//        } else {
//            return null;
//        }
    }

    //delete author api
    public Response deleteAuthor(Integer authorId) {
        String sql = "DELETE FROM author WHERE author_id = :authorId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("authorId", authorId);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        response.setStatus(1);
        response.setError("Author deleted successfully");
        response.setPost(Collections.EMPTY_LIST);
        return response;
        //return new Response<>(1, "Inventory deleted successfully.", new Object[]{});

//        return deletedRows > 0;
    }




    ////    fetch all authors
//    public List<AuthorMaster> findAllAuthors(){
//        return authorMasterRepository.findAll();
//    }
//
//    //    fetch authors by specific id
//    public AuthorMaster findAuthorById(Long authorId){
//
//        AuthorMaster author= authorMasterRepository.findById(authorId).orElseThrow(()-> new RuntimeException("Author Not Found"));
//        return author;
//    }
//
//    //    create the author
//    public void  createBook(AuthorMaster author){
//        authorMasterRepository.save(author);
//    }
//
//    //    remove a author with a particular id
//    public void deleteAuthor(Long authorId){
//        AuthorMaster author= authorMasterRepository.findById(authorId).orElseThrow(()-> new RuntimeException("Author Not Found"));
//        authorMasterRepository.deleteById(author.getAuthorId());
//    }

}
