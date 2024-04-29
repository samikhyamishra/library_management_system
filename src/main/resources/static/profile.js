$(document).ready(function() {
    // Function to fetch user profile details
    function fetchUserProfile(username) {
        console.log(username);
        // Make AJAX request to fetch user profile
        $.ajax({
            "url": "http://localhost:8080/api/users/" + username,
              "method": "GET",
              "timeout": 0,
            success: function(response) {
                // Update the section with user details
                if (response) {
                    var userProfileHtml = `
                                           <h2>Name: ${response.firstName} ${response.lastName}</h2>
                                           <p>Email: ${response.email}</p>
                                           <p>Phone: ${response.phoneNo}</p>
                                           <p>Address: ${response.address}</p>
                                       `;
                $('#userProfile').html(userProfileHtml);
                } else {
                    $('#userProfile').html('<p>User not found.</p>');
                }
            },
            error: function() {
                $('#userProfile').html('<p>Error fetching user profile.</p>');
            }
        });
    }

    // Retrieve the username from session storage (assuming it's stored upon login)
    var username = sessionStorage.getItem('username'); // Assuming 'username' is stored upon login
     // Debugging: Check the retrieved username
         console.log("Retrieved Username:", username);
    // Call fetchUserProfile function with the retrieved username
    if (username) {
        fetchUserProfile(username);
    } else {
        $('#userProfile').html('<p>Username not available.</p>');
    }
});
