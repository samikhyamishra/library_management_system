$(document).ready(function() {
    $('#resetPasswordForm').submit(function(e) {
        e.preventDefault();

        var token = localStorage.getItem('resetToken');
        var newPassword = $('#newPassword').val();
       // Ensure the 'token' and 'newPassword' are not null or empty
           if (!token || !newPassword) {
               alert('Token or newPassword is missing. Please try again.');
               return;
           }
        $.ajax({
            url: 'http://localhost:8080/api/users/resetPassword'
                             + '?token=' + encodeURIComponent(token)
                             + '&newPassword=' + encodeURIComponent(newPassword),
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                newPassword: newPassword
            }),
            success: function(response) {
                alert('Password reset successfully.');
                window.location.href = '/login.html'; // Redirect to login page
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
                alert('Failed to reset password. Please try again later.');
            }
        });
    });
});
