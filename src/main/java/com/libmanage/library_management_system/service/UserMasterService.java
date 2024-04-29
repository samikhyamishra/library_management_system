package com.libmanage.library_management_system.service;

import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.entity.UsersMaster;
import com.libmanage.library_management_system.repository.UsersMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserMasterService {

    private UsersMasterRepository usersMasterRepository;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;
    private JavaMailSender javaMailSender;
    private Response response;

    private BorrowedBookMasterService borrowedBookMasterService;

    @Autowired
    public UserMasterService(UsersMasterRepository usersMasterRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate, JavaMailSender javaMailSender, Response response) {
        this.usersMasterRepository = usersMasterRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.javaMailSender = javaMailSender;
        this.response = response;
    }

    //to get all users
    public List<UsersMaster> getAllUsers() {
        return usersMasterRepository.findAll();
    }

    //get user by userName
    public Optional<UsersMaster> getUserByUserName(String userName) {
        return usersMasterRepository.findByUserName(userName);
    }


    //to check if the userName is valid or not(login)
    //public Integer isValidUser(String userName, String password)
    public Response isValidUser(String userName, String password) {
//        Map<String, Object> params= new HashMap<>();
//        params.put("userName", userName);
//        params.put("password", password);
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", userName);
        mapSqlParameterSource.addValue("password", password);

        String sql = "SELECT role_id FROM users_master WHERE user_name = :userName AND password = :password";
//        String sql = "UPDATE users SET is_active = false WHERE user_name = :userName ";
        int roleId = namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Integer.class);
        if (roleId > 0) {
            String updateSql = "UPDATE users_master SET is_active = true WHERE user_name = :userName";
            namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);

           // assignMenuPermissions(roleId,userName);
                response.setStatus(1);
                response.setError("Valid User");
                response.setRoleId(roleId); // Set the role ID in the response
            //return new Response<>(1,"",new Object[]{});
        } else {
            response.setStatus(0);
            response.setError("Invalid username or password");
            //return new Response<>(0, "Invalid username or password", new Object[]{});
        }
        response.setPost(Collections.EMPTY_LIST);
        return response;
    }
    //logout
    //public boolean logout(String userName)
    public Response logout(String userName) {
        try {
//            Update User Status in database
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("userName", userName);
            String sql = "UPDATE users_master SET is_active = false WHERE user_name = :userName";
            Integer rowsUpdated = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

//           check if the update operation was successful
            if (rowsUpdated > 0) {
                System.out.println("Logout Successful!");
                response.setStatus(1);
                response.setError("User Logout.");
                //return new Response<>(1, "", new Object[]{});
            } else {
                response.setStatus(0);
                response.setError("User not found or already logged out");
                //System.out.println("User not found or already logged out");
                //return new Response<>(0, "User not found or already logged out", new Object[]{});
            }
        } catch (Exception e) {
            response.setStatus(0);
            response.setError("Logout Failed! Error: " + e.getMessage());
            e.printStackTrace();
            // System.err.println("Logout Failed! Error: " +e.getMessage());
            //return new Response<>(0, "Logout Failed!", new Object[]{});
        }
        response.setPost(Collections.EMPTY_LIST);
        return response;
    }

    //registration
    //    public void registerUser(String firstName,String lastName, String userName, String email,String phoneNo,String address, String password,String role_id){

    public Response registerUser(String firstName, String lastName, String userName, String email, String phoneNo, String address, String password, String role_id) {
        try {
            //Determine role_id based on username
            int roleId = getRoleIdByUserName(userName);

            // Insert user record into users_master table
            String sql = "INSERT INTO users_master(first_name,last_name,user_name, email, phone_no, address,password,role_id) " +
                    "VALUES (:firstName,:lastName, :userName, :email, :phone_no, :address, :password,:role_id)";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("firstName", firstName);
            mapSqlParameterSource.addValue("lastName", lastName);
            mapSqlParameterSource.addValue("userName", userName);
            mapSqlParameterSource.addValue("email", email);
            mapSqlParameterSource.addValue("phone_no", phoneNo);
            mapSqlParameterSource.addValue("address", address);
            mapSqlParameterSource.addValue("password", password);
            mapSqlParameterSource.addValue("role_id", Integer.valueOf(role_id));
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

            //Assign menu permissions based on the assigned role
           // assignMenuPermissions(roleId,userName);

            //Return success response
            response.setStatus(1);
            response.setError("User registered successfully");
            response.setPost(Collections.EMPTY_LIST);
            response.setRoleId(roleId);
            return response;
            //return new Response<>(1, "User registered successfully", new Object[]{});
        } catch (Exception e) {
            response.setStatus(0);
            response.setError("Registration Failed! Error: " + e.getMessage());
            response.setPost(Collections.EMPTY_LIST);
            e.printStackTrace();
            return response;
            // return new Response<>(0, "Registration Failed! Error: "+ e.getMessage(), new Object[]{});
        }

    }

    private int getRoleIdByUserName(String userName) {
// Define the role mappings based on usernames
        Map<String, Integer> roleMappings = new HashMap<>();
        roleMappings.put("admin", 1); // Role ID 1 for "admin" username

        // Check if the username exists in the mappings
        // Default role ID (Regular User) if username not found in mappings
        return roleMappings.getOrDefault(userName, 2); // Return the role ID mapped to the username
    }

    public String getUserByName(String email) {
        try {
            String sql = "SELECT user_name FROM users_master WHERE email = :email";
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("email", email);

            // Execute the query
            String userName = namedParameterJdbcTemplate.queryForObject(
                    sql,
                    parameterSource,
                    String.class
            );

            return userName; // Return the user_name associated with the email
        }  catch (Exception e) {
            // Handle other exceptions, log the error, and return null or throw an appropriate exception
            e.printStackTrace();
            return null;
        }
    }


//    private void assignMenuPermissions(int roleId, String userName) {
//        try {
//            // Update menu permissions based on the assigned role and username
//
//            String sql = "WITH ranked_menus AS (" +
//                    "    SELECT " +
//                    "        m.menu_id, " +
//                    "        m.menu_name, " +
//                    "        m.parent_id, " +
//                    "        r.role_id, " +
//                    "        r.user_name, " +
//                    "        ROW_NUMBER() OVER (PARTITION BY r.role_id ORDER BY m.menu_id) AS menu_rank " +
//                    "    FROM " +
//                    "        menu_master m " +
//                    "    JOIN " +
//                    "        role_master r ON r.role_id = m.role_id" +
//                    ") " +
//                    "UPDATE role_menu_permission rpm " +
//                    "SET is_accessible = CASE " +
//                    "                   WHEN rm.user_name = 'admin' THEN 1 " +
//                    "                   WHEN rm.user_name = :userName AND rm.menu_rank <= 5 THEN 1 " +
//                    "                   ELSE 0 " +
//                    "               END " +
//                    "FROM ranked_menus rm " +
//                    "WHERE rpm.role_id = rm.role_id " +
//                    "  AND rpm.menu_id = rm.menu_id";
//
//            mapSqlParameterSource= new MapSqlParameterSource();
//            mapSqlParameterSource.addValue("userName", userName);
//
//            // Execute the update query based on the provided parameters
//            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            // Handle exception appropriately
//        }
//    }

    //forgot password api
    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public Boolean saveToken(String email, String token) {
        try {
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("email", email);
            mapSqlParameterSource.addValue("token", token);
            String sql = "UPDATE users_master SET token=:token WHERE email=:email";
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean sendPasswordResetEmail(String email, String token) {
//        creating email message
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Dear User, \n\n As per your request to reset the password, click the following link to reset your password:\n\n" +
                    "http://localhost:8080/reset-password?token=" + token);
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Response forgotPassword(String email) {
        try {
            String token = generateToken();

            boolean saveToken = saveToken(email, token);
            if (saveToken) {
                boolean emailSent = sendPasswordResetEmail(email, token);
                if (emailSent) {
                    response.setStatus(1);
                    response.setError("Password reset email sent");
// Include the token in the response
                    Map<String, String> responseData = new HashMap<>();
                    responseData.put("token", token);
                    response.setPost(responseData);
                    return response;
                    //return new Response<>(1, "Password reset email sent", new Object[]{});
                } else {
                    response.setStatus(0);
                    response.setError("Failed to send password reset email");
                    response.setPost(Collections.EMPTY_LIST);
                    return response;
                    // return new Response<>(0,"Failed to send password reset email", new Object[]{});
                }
            } else {
                response.setStatus(0);
                response.setError("Failed to save reset token");
                response.setPost(Collections.EMPTY_LIST);
                return response;
                // return new Response<>(0, "Failed to save reset token", new Object[]{});
            }
        } catch (Exception e) {
            response.setStatus(0);
            response.setError("Error in processing the password reset request. Error: " + e.getMessage());
            response.setPost(Collections.EMPTY_LIST);
            e.printStackTrace();
            return response;
            // return new Response<>(0, "Error processing the password reset request.", new Object[]{});
        }
    }

    //public Boolean resetPassword(String token, String newPassword){
    public Response resetPassword(String token, String newPassword) {
        try {
//            Update Users password based on the email36
            String updateSql = "UPDATE users_master SET password = :newPassword WHERE token = :token";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("token", token);
            mapSqlParameterSource.addValue("newPassword", newPassword);
            int rowsUpdated = namedParameterJdbcTemplate.update(updateSql, mapSqlParameterSource);
            if (rowsUpdated > 0) {
                // Delete the token from the database after successful password reset
                deleteToken(token);
                response.setStatus(1);
                response.setError("Password reset successfully!");
                response.setPost(Collections.EMPTY_LIST);
                return response;
                //return new Response<>(1, "Password reset successfully!", new Object[]{});
            } else {
                response.setStatus(0);
                response.setError("Failed to reset password");
                response.setPost(Collections.EMPTY_LIST);
                return response;
                //return new Response<>(0, "Failed to reset password", new Object[]{});
            }

            //return rowsUpdated > 0;
        } catch (Exception e) {
            response.setStatus(0);
            response.setError("Error resetting the password. Error: " + e.getMessage());
            response.setPost(Collections.EMPTY_LIST);
            e.printStackTrace();
            return response;
            //return new Response<>(0, "Error resetting the password", new Object[]{});
        }
    }

    private void deleteToken(String token) {
        // Delete the token from the database after successful password reset
        String sql = "UPDATE users_master SET token = NULL WHERE token = :token";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("token", token);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }


}



////    fetch all users
//    public List<UsersMaster> findAllUsers(){
//        return usersMasterRepository.findAll();
//    }
////    fetch user by userLoginId
//    public UsersMaster findUserById(Long userLoginId){
//        UsersMaster user = usersMasterRepository.findById(userLoginId).orElseThrow(()-> new RuntimeException("Invalid User Id"));
//        return user;
//    }
//
////    add new user
//    public void addUser(UsersMaster user){
//        usersMasterRepository.save(user);
//    }
//
////    remove the user
//    public void deleteUser(Long userLoginId){
//        UsersMaster user= usersMasterRepository.findById(userLoginId).orElseThrow(()-> new RuntimeException("Invalid User Id"));
////        usersMasterRepository.deleteById(user.getUserLoginId());
//    }

