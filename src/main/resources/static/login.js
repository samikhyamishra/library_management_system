$(document).ready(function() {
    $('#loginForm').submit(function(e) {
        e.preventDefault(); // Prevent form submission

        var username = $("#user_name").val();
        var password = $("#password").val();

        // Check if username and password are not empty
        if (username.trim() === "" || password.trim() === "") {
            alert("Please enter both username and password.");
            return;
        }

        var loginData = {
            userName: username,
            password: password
        };

        console.log("Username:", username);
        console.log("Password:", password);


        var loginSettings = {
            "url": "http://localhost:8080/api/users/login",
            "method": "POST",
            "timeout": 0,
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify(loginData),
        };

        $.ajax(loginSettings)
            .done(function(response) {
                // Check if login was successful
                if (response.status===1) {
                    // Store user role in session storage
                     sessionStorage.setItem('userRole', response.roleId);
                     sessionStorage.setItem('username', username);
                     console.log("Redirecting to dashboard...");
                     window.location.href = '/dashboard.html';
                } else {
                    // Display error message if login failed
                    alert(response.error);
                }
                console.log("Response:", response);
            })
            .fail(function(xhr, status, error) {
                // Display error message if AJAX request fails
                console.error('Error:', error);
                alert('An error occurred while processing your request. Please try again later.');
            });
    });

    // Forgot Password functionality
    $('#forgotPasswordLink').click(function(e) {
        e.preventDefault(); // Prevent default link behavior

        var email = prompt("Please enter your email address:");
        if (email === null || email.trim() === "") {
            alert("Email address cannot be empty.");
            return;
        }

        // Send AJAX request to the ForgotPassword endpoint
        var settings = {
            "url": "http://localhost:8080/api/users/forgotPassword",
            "method": "POST",
            "timeout": 0,
            "headers": {
              "Content-Type": "application/json"
            },
            "data": JSON.stringify({ email: email }),
        };

        $.ajax(settings)
        .done(function(response) {
            // Check if the response indicates that the password reset email was sent successfully
            if (response.status==1) {
// Store the token in localStorage
             var token = response.post.token;
             localStorage.setItem('resetToken', token);
           // Redirect the user to the password reset page
             window.location.href = '/resetPassword.html'; // Replace '/reset-password.html' with your actual reset password page URL
            } else {
                // Display other responses from the server
                alert(response);
            }
        })
        .fail(function(xhr, status, error) {
            // Display error message if AJAX request fails
            console.error('Error:', error);
            alert('An error occurred while processing your request. Please try again later.');
        });

    });
});


//using vanilla js to handle the process
//function loginUser() {
//    var username = document.getElementById("user_name").value;
//    var password = document.getElementById("password").value;
//
//    // Check if username and password are not empty
//    if (username.trim() === "" || password.trim() === "") {
//        alert("Please enter both username and password.");
//        return;
//    }
//
//    var data = {
//        userName: username,
//        password: password
//    };
//
//    var settings = {
//        url: "localhost:8080/api/users/login",
//        method: "POST",
//        timeout: 0,
//        headers: {
//            "Content-Type": "application/json"
//        },
//        data: JSON.stringify(data)
//    };
//
//    // Make an AJAX POST request to the login endpoint
//    $.ajax(settings)
//        .done(function(response) {
//            // Check if login was successful
//            if (response.success) {
//                // Redirect to dashboard page
//                window.location.href = '/dashboard.html';
//            } else {
//                // Display error message if login failed
//                alert('Invalid username or password');
//            }
//        })
//        .fail(function(xhr, status, error) {
//            // Display error message if AJAX request fails
//            console.error('Error:', error);
//            alert('An error occurred while processing your request. Please try again later.');
//        });
//}




//document.addEventListener("DOMContentLoaded", function() {
//    load();
//});
//
//function load () {
//    wrong();
//    document.getElementById( "user_name" ).focus();
//
//}
//
//function wrong () {
//    document.getElementById( "login" ).disabled = true;
//    // document.getElementById( "user_name" ).focus();
//    document.getElementById( "login" ).style.opacity = "0.5";
//}
//
//
//function validForm () {
//    var username = document.getElementById( "user_name" ).value;
//    var pass = document.getElementById( "password" ).value;
//
//        if ( /\s/.test( username ) == true )
//        {
//            wrong();
//            document.getElementById( "message" ).innerHTML = "Please enter a valid User Name.";
//            return false;
//        }
//        else
//        {
//            if ( pass.length < 8 )
//            {
//                wrong();
//                document.getElementById( "message" ).innerHTML = "Password must contain more than 8 charcaters.";
//                return false;
//            }
//            else if ( pass.length >= 8 )
//            {
//                document.getElementById( "login" ).disabled = false;
//                document.getElementById( "message" ).innerHTML = "";
//                document.getElementById( "login" ).style.opacity = "1";
//                return true;
//            }
//        }
//    }
//
//function goto () {
//    document.getElementById( "login" ).style.borderStyle = "inset";
//    window.location.replace( "index.html" );
//    return true;
//}
//
