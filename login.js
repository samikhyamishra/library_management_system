function load () {
    wrong();
    document.getElementById( "user_name" ).focus();
    
}

function wrong () {
    document.getElementById( "login" ).disabled = true;
    // document.getElementById( "user_name" ).focus();
    document.getElementById( "login" ).style.opacity = "0.5";
}
function validForm () {
    var username = document.getElementById( "user_name" ).value;
    var pass = document.getElementById( "password" ).value;

        if ( /\s/.test( username ) == true )
        {
            wrong();
            document.getElementById( "message" ).innerHTML = "Please enter a valid User Name.";
            return false;
        }
        else
        {
            if ( pass.length < 8 )
            {
                wrong();
                document.getElementById( "message" ).innerHTML = "Password must contain more than 8 charcaters.";
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