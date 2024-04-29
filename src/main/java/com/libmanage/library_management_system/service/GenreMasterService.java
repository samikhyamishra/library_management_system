package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.entity.BorrowedBookMaster;
import com.libmanage.library_management_system.entity.GenreMaster;
import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.repository.GenreMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.security.CodeSigner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GenreMasterService {
    private GenreMasterRepository genreMasterRepository;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;

    private Response response;

    @Autowired
    public GenreMasterService(GenreMasterRepository genreMasterRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate, Response response) {
        this.genreMasterRepository = genreMasterRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.response=response;
    }

    public List<GenreMaster> getAllGenre() {
        String sql = "SELECT * FROM genre_master";
        List<GenreMaster> genreMasters = namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(GenreMaster.class));
        return genreMasters;
    }

    public GenreMaster getGenreByGenreId(Integer genreId) {
        String sql = "SELECT * FROM genre_master WHERE genre_id = :genreId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("genreId", genreId);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, new BeanPropertyRowMapper<>(GenreMaster.class));
        } catch (Exception e) {
            return null;
            // Handle if no record found or any other exception
        }
    }

    public Response createNewGenre(String genreName) {
        try{
            String insertSql = "INSERT INTO genre_master (genre_name) VALUES (:genreName)";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("genreName", genreName);
//            mapSqlParameterSource.addValue("createdBy", genreMaster.getCreatedBy());
            namedParameterJdbcTemplate.update(insertSql, mapSqlParameterSource);
            response.setStatus(1);
            response.setError("Genre Added successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;//new Response<>(1, "Genre Added successfully", new Object[]{});
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(0);
            response.setPost(Collections.EMPTY_LIST);
            response.setError("Error: "+ e.getMessage());
            return response;  //Response(0, "Error: "+ e.getMessage(), new ArrayList<>());
        }

  }
    public Response updatedGenre(Integer genreId, GenreMaster updatedGenre) {
        String updateSql = "UPDATE genre_master " +
                "SET genre_name = :genreName, updated_by = :updatedBy, updated_on = CURRENT_TIMESTAMP " +
                "WHERE genre_id = :genreId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("genreName", updatedGenre.getGenreName());
        mapSqlParameterSource.addValue("updatedBy", updatedGenre.getUpdatedBy());
        mapSqlParameterSource.addValue("genreId", genreId);

        int rowsAffected = namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);
        if (rowsAffected > 0) {
            response.setStatus(1);
            response.setError("Genre updated successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;
            //return new Response(1, "Genre updated successfully", new Object[]{});
        } else {
            response.setStatus(0);
            response.setError("Failed to update genre");
            response.setPost(Collections.EMPTY_LIST);
            return response;
            //return new Response(0, "Failed to update genre", new Object[]{});
        }
    }

    public Response deleteGenre(Integer genreId) {
        String deleteSql = "DELETE FROM genre_master WHERE genre_id = :genreId";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("genreId", genreId);

        int rowsAffected = namedParameterJdbcTemplate.update(deleteSql, mapSqlParameterSource);
        if (rowsAffected > 0) {
            response.setStatus(1);
            response.setError("Genre deleted successfully");
            response.setPost(Collections.EMPTY_LIST);
            return response;
           // return new Response<>(1, "Genre deleted successfully", new Object[]{});
        } else {
            response.setStatus(0);
            response.setError("Failed to delete genre");
            response.setPost(Collections.EMPTY_LIST);
            return response;
           // return new Response<>(0, "Failed to delete genre", new Object[]{});
        }
    }
    ////    fetch all the genres
//    public List<GenreMaster> findAllGenre(){
//        return genreMasterRepository.findAll();
//    }
////    fetch genres by specific id
//    public GenreMaster findAllGenreById(Long genreId){
//        GenreMaster genre = genreMasterRepository.findById(genreId).orElseThrow(()-> new RuntimeException("Invalid Genre Id"));
//        return genre;
//    }
////    create a new genre
//    public void addGenre(GenreMaster genre){
//        genreMasterRepository.save(genre);
//    }
////    delete genre
//    public void deleteGenre(Integer genreId){
//        GenreMaster genre = genreMasterRepository.findById(genreId).orElseThrow(()-> new RuntimeException("Invalid Genre Id"));
//        genreMasterRepository.deleteById(genre.getGenreId());
    }

