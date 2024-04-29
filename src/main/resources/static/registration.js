function registerUser() {
    var firstName = $("#firstname").val();
    var lastName = $("#lastname").val();
    var userName = $("#user_name").val();
    var email = $("#email").val();
    var phoneNo = $("#phonenumber").val();
    var address = $("#address").val();
    var password = $("#password").val();
    var roleId = 2; // Assuming default role_id for regular users

    var userData = {
        firstName: firstName,
        lastName:lastName,
        userName:userName,
        email:email,
        phoneNo:phoneNo,
        address:address,
        password:password,
        role_id:roleId
    };

    var settings = {
        "url": "http://localhost:8080/api/users/register",
        "method": "POST",
        "contentType": "application/json",
        "data": JSON.stringify(userData),
        "success": function(response) {
            console.log("Registration successful!");
            window.location.replace("login.html"); // Redirect to login page after successful registration
        },
        error: function(xhr, status, error) {
            console.error("Registration failed:", error);
            alert("Registration failed. Please try again later.");
        }
    };

    $.ajax(settings);
}

$(document).ready(function() {
    $("#register").click(function(event) {
        event.preventDefault(); // Prevent form submission
        if (validateForm()) {
            registerUser(); // Call the registerUser function to handle registration
        }
    });
});

function validateForm() {
    return validateUserName() && validatePassword() && validateConfirmPassword();
}

function validateUserName() {
    var userName = $("#user_name").val();
    if (/\s/.test(userName)) {
        displayErrorMessage("User name must not contain any space.");
        return false;
    }
    return true;
}

function validatePassword() {
    var password = $("#password").val();
    if (password.length < 8) {
        displayErrorMessage("Password should contain at least 8 characters.");
        return false;
    } else if (!/(?=.*[\d])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[\w!@#$%^&*]{8,}$/.test(password)) {
        displayErrorMessage("Password should contain Uppercase, lowercase, and numeric characters.");
        return false;
    }
    return true;
}

function validateConfirmPassword() {
    var password = $("#password").val();
    var confirmPassword = $("#repassword").val();
    if (password !== confirmPassword) {
        displayErrorMessage("Confirm Password must be the same as the Password.");
        return false;
    }
    return true;
}

function displayErrorMessage(message) {
    $("#message").text(message);
}


//function load () {
//    wrong();
//    document.getElementById( "firstname" ).focus();
//}
//
//function wrong () {
//    document.getElementById( "register" ).disabled = true;
//    document.getElementById( "register" ).style.opacity = "0.5";
//    // document.getElementById( "tnc" ).disabled = true;
//    // document.getElementById( "tnc" ).checked = false;
//
//}
//
//function right () {
//    document.getElementById( "message" ).innerHTML = "";
//}
//
//function validUserName () {
//    var username = document.getElementById( "user_name" ).value;
//    if ( /\s/.test( username )==true )
//    {
//        wrong();
//        document.getElementById( "message" ).innerHTML = "User name must not contain any space.";
//        document.getElementById( "user_name" ).focus();
//        document.getElementById( "user_name" ).style.border = "2px solid red";
//        return false;
//    }
//    else
//    {
//        right();
//        return true;
//    }
//}
//
//function validPassword () {
//    var pass = document.getElementById( "password" ).value;
//    if ( pass.length < 8 )
//    {
//        wrong();
//        document.getElementById( "message" ).innerHTML = "Password should contain atleast 8 characters.";
//        document.getElementById( "password" ).focus();
//        document.getElementById( "password" ).style.border = "2px solid red";
//        return false;
//    }
//    else if ( pass.length >= 8 )
//    {
//        if ( /(?=.*[\d])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[\w!@#$%^&*]{8,}$/.test( pass ) == false )
//        {
//            wrong();
//            document.getElementById( "message" ).innerHTML = "Password should contain Uppercase, lowercase, and numeric charcaters. ";
//            return false;
//        }
//        else
//        {
//            right();
//            document.getElementById( "password" ).style.border = "none";
//            return true;
//        }
//    }
//}
//
//function cnfrmPass () {
//    var pass1 = document.getElementById( "password" ).value;
//    var pass2 = document.getElementById( "repassword" ).value;
//    if ( pass1 != pass2 )
//    {
//        wrong();
//        document.getElementById( "message" ).innerHTML = "Confirm Password must be same as the Password.";
//        document.getElementById( "repassword" ).focus();
//        document.getElementById( "repassword" ).blur();
//        return false;
//    }
//    else
//    {
//        right();
//        if ( validUserName() && validPassword() )
//        {
//            // document.getElementById( "tnc" ).disabled = false;
//            return true;
//        }
//        else
//        {
//            document.getElementById( "message" ).innerHTML = "There is something wrong in the form.";
//            // document.getElementById( "tnc" ).checked = false;
//            return false;
//        }
//    }
//}
// function validForm () {
//     if ( cnfrmPass() )
//     {
//         if ( document.getElementById( "tnc" ).checked == false )
//         {
//             document.getElementById( "tnc" ).disabled = false;
//             document.getElementById( "signupbtn" ).disabled = true;
//             document.getElementById( "signupbtn" ).style.opacity = "0.5";
//             document.getElementById( "tnc" ).focus();
//             return false;
//         }
//         else
//         {
//             document.getElementById( "signupbtn" ).style.opacity = "1";
//             document.getElementById( "signupbtn" ).disabled = false;
//             return true;
//         }
//     }
//     else
//     {
//         wrong();
//     }
// }
//function goto () {
//    document.getElementById( "register" ).style.borderStyle = "inset";
//    window.location.replace( "login.html" );
//    return true;
//}
