function load () {
    wrong();
    document.getElementById( "user_name/user_login_id" ).value = "";
    
}

function wrong () {
    document.getElementById( "login" ).disabled = true;
    document.getElementById( "user_name/user_login_id" ).focus();
    document.getElementById( "login" ).style.opacity = "0.5";
}
function validForm () {
    var username = document.getElementById( "user_name/user_login_id" ).value;
    var pass = document.getElementById( "password" ).value;

        if ( /\s/.test( username ) == true )
        {
            document.getElementById( "message" ).innerHTML = "Please enter a valid User Name.";
            wrong();
            return false;
        }
        else
        {
            if ( pass.length < 8 )
            {
                document.getElementById( "message" ).innerHTML = "Password must contain more than 8 charcaters.";
                wrong();
                return false;
            }
            else if ( pass.length >= 8 )
            {
                document.getElementById( "login" ).disabled = false;
                document.getElementById( "message" ).innerHTML = "";
                document.getElementById( "login" ).style.opacity = "1";
                return true;
            }
        }
    }
    
function goto () {
    document.getElementById( "login" ).style.borderStyle = "inset";
    window.location.replace( "index.html" );
    return true;
}