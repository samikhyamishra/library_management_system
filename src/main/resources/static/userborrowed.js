$(document).ready(function() {
$('#footer').load('footer.html');

    // Load borrowed books data for a specific user on page load
    const username = sessionStorage.getItem('username');

     if (!username) {
            alert('User not logged in. Redirecting to login page...');
            window.location.href = '/login.html'; // Redirect to login page if user is not logged in
            return;
        }

    fetchBorrowedBooks(username);

    // Function to fetch borrowed books for a specific user and populate the table
    function fetchBorrowedBooks(username) {
        $.ajax({
            url: `http://localhost:8080/api/borrow/borrowed-books/${username}`,
            method: 'GET',
            success: function(data) {
                const tbody = $('#booksBody');
                tbody.empty();

                data.forEach(function(book) {
                    const row = `<tr>
                                    <td>${book.title}</td>
                                    <td>${book.issueDate}</td>
                                    <td>${book.dueDate}</td>
                                    <td>${book.returnDate}</td>
                                    <td><button class="returnBtn" data-bind="${book.borrowBookId}">Return</button></td>
                                </tr>`;
                    tbody.append(row);
                });

                // Initialize DataTables after populating data
                $('#borrowedBooksTable').DataTable();

                // Add click event handler for return button
                $('.returnBtn').on('click', function() {
                    const bookId = $(this).data('bind');
                    returnBook(bookId);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error loading borrowed books:', error);
                alert('Error loading borrowed books. Please try again.');
            }
        });
    }

    // Function to handle returning a book
    function returnBook(bookId,buttonElement) {
        $.ajax({
            url: `http://localhost:8080/api/books/return/`+bookId,
            method: 'POST',
            success: function(response) {
                alert('Book returned successfully!');
                 // Disable the button or change its text to "Returned"
                buttonElement.prop('disabled', true).text('Returned');
                // Refresh the borrowed books table after returning a book
                fetchBorrowedBooks(username);
            },
            error: function(xhr, status, error) {
                console.error('Error returning book:', error);
                alert('Error returning book. Please try again.');
            }
        });
    }
});
