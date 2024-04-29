$(document).ready(function() {
$('#footer').load('footer.html');

    // Load borrowed books data on page load
    fetchBorrowedBooks();

    // Function to fetch all borrowed books and populate the table
    function fetchBorrowedBooks() {
        $.ajax({
            url: 'http://localhost:8080/api/borrow/all',
            method: 'GET',
            success: function(data) {
                const tbody = $('#booksBody');
                tbody.empty();

                data.forEach(function(book) {
                    const row = `<tr>
                                    <td>${book.bookId}</td>
                                    <td>${book.title}</td>
                                    <td>${book.issueDate}</td>
                                    <td>${book.dueDate}</td>
                                </tr>`;
                    tbody.append(row);
                });

                // Initialize DataTables after populating data
                $('#borrowedBooksTable').DataTable();
            },
            error: function(xhr, status, error) {
                console.error('Error loading borrowed books:', error);
                alert('Error loading borrowed books. Please try again.');
            }
        });
    }
});
