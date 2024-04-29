package com.libmanage.library_management_system.controller;

import com.libmanage.library_management_system.entity.Response;
import com.libmanage.library_management_system.entity.UsersMaster;
import com.libmanage.library_management_system.service.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/api/users")
public class UserMasterController {
    private UserMasterService userMasterService;

    @Autowired
    public UserMasterController(UserMasterService userMasterService) {
        this.userMasterService = userMasterService;
    }

    @GetMapping("/AllUsers")
    public List<UsersMaster> getAllUsers() {
        return userMasterService.getAllUsers();
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UsersMaster> getUserByUserName(@PathVariable String userName) {
        return userMasterService.getUserByUserName(userName).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //login api controller
    //public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest)
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody Map<String, String> loginRequest) {
        String userName = loginRequest.get("userName");
        String password = loginRequest.get("password");
        Response response = userMasterService.isValidUser(userName, password);
        return ResponseEntity.ok(response);
    }

    //logout api controller
    // public ResponseEntity<String> logout(@RequestBody Map<String, String> logoutRequest)
    @DeleteMapping("/logout")
    public ResponseEntity<Response> logout(@RequestBody Map<String, String> logoutRequest) {
        String userName = logoutRequest.get("userName");
        Response response = userMasterService.logout(userName);
        return ResponseEntity.ok(response);
    }
//        try {
////            String userName = getUserByUserName(@RequestBody String userName);
//            //boolean result = userMasterService.logout(userName);
////            if (result) {
////                return ResponseEntity.ok("Logout Successful!");
////            } else {
////                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed.");
////            }
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed. Error: " + e.getMessage());
//        }


    //registration api controller
    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody Map<String, String> UserRegistrationRequest) {
        String firstName = UserRegistrationRequest.get("firstName");
        String lastName = UserRegistrationRequest.get("lastName");
        String userName = UserRegistrationRequest.get("userName");
        String email = UserRegistrationRequest.get("email");
        String phoneNo = UserRegistrationRequest.get("phoneNo");
        String address = UserRegistrationRequest.get("address");
        String password = UserRegistrationRequest.get("password");
        String role_id = UserRegistrationRequest.get("role_id");
        Response response = userMasterService.registerUser(firstName, lastName, userName, email, phoneNo, address, password, role_id);
        return ResponseEntity.ok(response);
//        try{
//            userMasterService.registerUser(firstName, lastName, userName, email, phoneNo, address, password,role_id);
//            return ResponseEntity.ok("User registered successfully!");
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed. Error: "+e.getMessage());
//        }
    }

    //forgot password api controller
    @PostMapping("/forgotPassword")
    public ResponseEntity<Response> forgotPassword(@RequestBody Map<String, String> requestEmail) {
        String email = requestEmail.get("email");
        Response emailResponse = userMasterService.forgotPassword(email);
        return ResponseEntity.ok(emailResponse);
    }
//        if (email!=null && !email.isEmpty()){
//            String token = userMasterService.generateToken();
//            boolean saved = userMasterService.saveToken(email, token);
//            if (saved) {
//                boolean emailSent = userMasterService.sendPasswordResetEmail(email, token);
//                if (emailSent) {
//                    return ResponseEntity.ok("Password reset email sent successfully.");
//                } else {
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send password reset email.");
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save reset token.");
//            }
//        }else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email parameter is required.");
//        }


    @PostMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Response resetResponse = userMasterService.resetPassword(token, newPassword);
        return ResponseEntity.ok(resetResponse);
    }

    //getLoggedInUserId
//    @GetMapping("/getLoggedInUserId")
//    public ResponseEntity<String> getLoggedInUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String loggedInUserId= authentication.getName(); // Assuming the user ID is the username
//            System.out.println("Authenticated User: " + loggedInUserId);
//            return ResponseEntity.ok(loggedInUserId);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }
//    }
//    @GetMapping("/fetchBorrowedBooks")
//    public void fetchBorrowedBooks() {
//        ResponseEntity<String> response = getLoggedInUserId();
//        if (response.getStatusCode() == HttpStatus.OK) {
//            String loggedInUserId = response.getBody();
//            userMasterService.fetchBorrowedBooks(loggedInUserId);
//        } else {
//            System.out.println("Kindly login to view your borrowed book list!");
//        }
//    }
}
//        if (reset) {
//            return ResponseEntity.ok("Password reset successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password.");
//        }



