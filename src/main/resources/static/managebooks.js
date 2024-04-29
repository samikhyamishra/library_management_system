$(document).ready(function() {
    $('#footer').load('footer.html');

    // Load books on page load
    loadBooks();

    // Click event for Add Book button
    $('#addBookBtn').click(function() {
        $('#addBookModal').css('display', 'block');
    });

    // Click event for closing the modals
    $('.close').click(function() {
        $('#addBookModal').css('display', 'none');
        $('#updateBookModal').css('display', 'none');
    });

    // Submit event for Add Book form
    $('#addBookForm').submit(function(event) {
        event.preventDefault();
        var title = $('#title').val();
        var author = $('#author').val();
        var genre = $('#genre').val();

        // AJAX request to add a new book
        $.ajax({
            url: 'http://localhost:8080/api/books/add',
            method: 'POST',
            timeout: 0,
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                title: title,
                authorId: author,
                genreId: genre,
                active: true
            }),
            success: function(response) {
                alert('Book added successfully!');
                $('#addBookModal').css('display', 'none');
                loadBooks();  // Reload books table
            },
            error: function(err) {
                console.error('Error adding book:', err);
                alert('Error adding book. Please try again.');
            }
        });
    });

    // Function to load books into the table
    function loadBooks() {
        $.ajax({
            url: 'http://localhost:8080/api/books/allBooks',
            method: 'GET',
            timeout: 0,
            headers: {
                'Content-Type': 'application/json'
            },
            success: function(response) {
                console.log('Books data received:', response);
                $('#booksTable tbody').empty();  // Clear existing table rows

                // Populate table with book data
                response.post.forEach(function(book) {
                    $('#booksTable tbody').append(`
                        <tr>
                            <td>${book.bookid}</td>
                            <td>${book.title}</td>
                            <td>${book.authorname}</td>
                            <td>${book.genrename}</td>
                            <td>${book.active}</td>
                            <td><button class="updateBtn" data-bookid="${book.bookid}">Update</button></td>
                        </tr>
                    `);
                });

                // Click event for Update buttons
                $('.updateBtn').click(function() {
                    var bookId = $(this).data('bookid');
                    var selectedBook = response.post.find(book => book.bookid === bookId);

                    if (selectedBook) {
                        // Pre-fill update modal with existing book details
                        $('#updatedtitle').val(selectedBook.title);
                        $('#updatedauthor').val(selectedBook.authorName);
                        $('#updatedgenre').val(selectedBook.genreName);
                        // Store the bookId in the update modal for reference
                        $('#updateBookModal').data('bookid', bookId);
                        // Display update modal
                        $('#updateBookModal').css('display', 'block');
                    } else {
                        console.error('Selected book details not found for bookId:', bookid);
                    }
                });
            },
            error: function(xhr, status, error) {
                console.error('Error loading books:', error);
                alert('Error loading books. Please try again.');
            }
        });
    }

    // Submit event for Update Book form
    $('#updateBookForm').submit(function(event) {
        event.preventDefault();
        var title = $('#updatedtitle').val();
        var author = $('#updatedauthor').val();
        var genre = $('#updatedgenre').val();
        var bookId = $('#updateBookModal').data('bookid');

        // AJAX request to update the selected book
        $.ajax({
            url: 'http://localhost:8080/api/books/update'+ bookid,
            method: 'PUT',
            timeout: 0,
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify({
                title: title,
                authorId: author,
                genreId: genre,
                active: true
            }),
            success: function(response) {
                alert('Book updated successfully!');
                $('#updateBookModal').css('display', 'none');
                loadBooks();  // Reload books table
            },
            error: function(err) {
                console.error('Error updating book:', err);
                alert('Error updating book. Please try again.');
            }
        });
    });
});
$(document).ready(function() {
    $('#booksTable').DataTable();
});

