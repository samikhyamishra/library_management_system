$(document).ready(function() {
    // Open the modal when "TALK TO US" button is clicked
    $('#openContactModal').click(function() {
        $('#contactModal').css('display', 'block');
    });

    // Close the modal when the close button (x) is clicked
    $('.close').click(function() {
        $('#contactModal').css('display', 'none');
    });

    // Close the modal if user clicks outside of the modal content
    $(window).click(function(event) {
        if (event.target == $('#contactModal')[0]) {
            $('#contactModal').css('display', 'none');
        }
    });

    // Handle form submission
    $('#contactForm').submit(function(event) {
        event.preventDefault(); // Prevent default form submission

        // Collect form data
        var formData = $(this).serialize();

        // Submit form data using AJAX
        $.ajax({
            type: 'POST',
            url: '/api/contact', // Specify the API endpoint to handle contact form submission
            data: formData,
            success: function(response) {
                alert('Message sent successfully!');
                $('#contactForm')[0].reset(); // Reset the form
                $('#contactModal').css('display', 'none'); // Close the modal after successful submission
            },
            error: function(error) {
                alert('Failed to send message. Please try again later.');
            }
        });
    });
});
