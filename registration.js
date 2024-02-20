function load () {
    wrong();
    document.getElementById( "firstname" ).focus();
}
function wrong () {
    document.getElementById( "register" ).disabled = true;
    document.getElementById( "register" ).style.opacity = "0.5";
    // document.getElementById( "tnc" ).disabled = true;
    // document.getElementById( "tnc" ).checked = false;

}
function right () {
    document.getElementById( "message" ).innerHTML = "";
}
function validUserName () {
    var username = document.getElementById( "username" ).value;
    if ( /\s/.test( username )==true )
    {
        wrong();
        document.getElementById( "message" ).innerHTML = "User name must not contain any space.";
        document.getElementById( "username" ).focus();
        return false;
    }
    else
    {
        right();
        return true;
    }
}

function validPassword () {
    var pass = document.getElementById( "password" ).value;
    if ( pass.length < 8 )
    {
        wrong();
        document.getElementById( "message" ).innerHTML = "Password should contain atleast 8 characters.";
        document.getElementById( "password" ).focus();
        document.getElementById( "password" ).style.border = "2px solid red";
        return false;
    }
    else if ( pass.length >= 8 )
    {
        if ( /(?=.*[\d])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[\w!@#$%^&*]{8,}$/.test( pass ) == false )
        {
            wrong();
            document.getElementById( "message" ).innerHTML = "Password should contain Uppercase, lowercase, and numeric charcaters. ";
            return false;
        }
        else
        {
            right();
            document.getElementById( "password" ).style.border = "none";
            return true;
        }
    }
}

function cnfrmPass () {
    var pass1 = document.getElementById( "password" ).value;
    var pass2 = document.getElementById( "repassword" ).value;
    if ( pass1 != pass2 )
    {
        wrong();
        document.getElementById( "message" ).innerHTML = "Confirm Password must be same as the Password.";
        document.getElementById( "repassword" ).focus();
        document.getElementById( "repassword" ).blur();
        return false;
    }
    else
    {
        right();
        if ( validUserName() && validPassword() )
        {
            // document.getElementById( "tnc" ).disabled = false;
            return true;
        }
        else
        {
            document.getElementById( "message" ).innerHTML = "There is something wrong in the form.";
            // document.getElementById( "tnc" ).checked = false;
            return false;
        }
    }
}
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
function goto () {
    document.getElementById( "register" ).style.borderStyle = "inset";
    window.location.replace( "index.html" );
    return true;
}
