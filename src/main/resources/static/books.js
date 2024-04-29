$(document).ready(function() {
    // Function to fetch books data from backend API and populate the table
    function fetchBooksData() {
        var settings = {
            "url": "http://localhost:8080/api/books/allBooks",
            "method": "GET",
            "timeout": 0,
        };

        $.ajax(settings)
            .done(function(response) {
                console.log(response);
                if (response.status === 1) {
                    // If books data is successfully retrieved, update the table
                    var books = response.post;
                    updateBookTable(books);
                } else {
                    console.error("Error fetching books:", response.error);
                    // Display an error message or handle appropriately
                }
            })
            .fail(function(xhr, status, error) {
                console.error("Failed to fetch books:", error);
                // Display an error message or handle appropriately
            });
    }


     // Function to issue a book
        function issueBook(bookId) {
        var username = sessionStorage.getItem('username');
         if (!username) {
                    alert('User not logged in. Redirecting to login page...');
                    window.location.href = '/login.html'; // Redirect to login page if user is not logged in
                    return;
                }
                else{
                // Create a JSON object to send in the request body
                        var requestData = {
                            userName: username,
                            bookId: bookId
                        };

            $.ajax({
            url: "http://localhost:8080/api/books/issue",
            method: "POST",
            contentType: "application/json", // Set content type to JSON
            data: JSON.stringify(requestData), // Convert JSON object to string
            success: function(response) {
                alert("Book issued successfully!");
                fetchBooksData(); // Refresh book list after issuing
            },
            error: function(xhr, status, error) {
                alert("Failed to issue book: " + error);
            }
         });
          }
     }

    // Function to update the table with fetched books data
    function updateBookTable(books) {
        var tableBody = $('#bookTableBody');
        tableBody.empty(); // Clear existing table rows

        books.forEach(function(book) {
            var row = $('<tr>');
            row.append($('<td>').text(book.bookid));
            row.append($('<td>').text(book.title));
            row.append($('<td>').text(book.authorname));
            row.append($('<td>').text(book.genrename));
            var issueButton = $("<button>")
                   .text("Issue")
                   .addClass("issue-btn")
                   .attr("data-book-id", book.bookid)
                   .attr("data-active", book.active);
            row.append($("<td>").append(issueButton));
            tableBody.append(row);
        });
    }
          // Attach click event listener for the issue button
            $('#bookTableBody').on('click', '.issue-btn', function() {
                var bookId = $(this).data('book-id');
                var isActive = $(this).data("active");
                if (isActive) {
                   issueBook(bookId);
                } else {
                   alert("Book is not available for issuing.");
                }
            });
    // Initial load of books data when the page is ready
    fetchBooksData();
});
$(document).ready(function() {
    $('#bookTable').DataTable();
});

