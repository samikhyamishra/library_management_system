$(document).ready(function() {
$('#footer').load('footer.html');

    // Load genre data on page load
    fetchGenres();

    // Click event for Add Genre button
    $('#addGenreBtn').click(function() {
        $('#addGenreModal').css('display', 'block');
    });

    // Click event for closing modals
    $('.close').click(function() {
        $('#addGenreModal').css('display', 'none');
    });

    // Submit event for Add Genre form
    $('#addGenreForm').submit(function(event) {
        event.preventDefault();
        var genreName = $('#genreName').val();

        // AJAX request to add a new genre
        $.ajax({
            url: 'http://localhost:8080/api/genre/addGenre',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                genreName: genreName
            }),
            success: function(response) {
                alert('Genre added successfully!');
                $('#addGenreModal').css('display', 'none');
                fetchGenres(); // Reload genre table
            },
            error: function(err) {
                console.error('Error adding genre:', err);
                alert('Error adding genre. Please try again.');
            }
        });
    });

    // Function to fetch all genres and populate the table
    function fetchGenres() {
        $.ajax({
            url: 'http://localhost:8080/api/genre/allGenre',
            method: 'GET',
            success: function(data) {
                const tbody = $('#genreTable tbody');
                tbody.empty();

                data.forEach(function(genre) {
                    const row = `<tr>
                                    <td>${genre.genreId}</td>
                                    <td>${genre.genreName}</td>
                                </tr>`;
                    tbody.append(row);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error loading genres:', error);
                alert('Error loading genres. Please try again.');
            }
        });
    }
});
