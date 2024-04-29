$(document).ready(function() {
$('#footer').load('footer.html');
    // Load author data on page load
    fetchAuthors();

    // Click event for Add Author button
    $('#addAuthorBtn').click(function() {
        $('#addAuthorModal').css('display', 'block');
    });

    // Click event for closing modals
    $('.close').click(function() {
        $('#addAuthorModal').css('display', 'none');
    });

    // Submit event for Add Author form
    $('#addAuthorForm').submit(function(event) {
        event.preventDefault();
        var firstName = $('#firstName').val();
        var lastName = $('#lastName').val();

        // AJAX request to add a new author
        $.ajax({
            url: 'http://localhost:8080/api/authors/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                firstName: firstName,
                lastName: lastName
            }),
            success: function(response) {
                alert('Author added successfully!');
                $('#addAuthorModal').css('display', 'none');
                fetchAuthors(); // Reload author table
            },
            error: function(err) {
                console.error('Error adding author:', err);
                alert('Error adding author. Please try again.');
            }
        });
    });

    // Function to fetch all authors and populate the table
    function fetchAuthors() {
        $.ajax({
            url: 'http://localhost:8080/api/authors/allAuthors',
            method: 'GET',
            success: function(data) {
                const tbody = $('#authorTable tbody');
                tbody.empty();

                data.forEach(function(author) {
                    const row = `<tr>
                                    <td>${author.authorId}</td>
                                    <td>${author.firstName} ${author.lastName}</td>
                                </tr>`;
                    tbody.append(row);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error loading authors:', error);
                alert('Error loading authors. Please try again.');
            }
        });
    }
});
