$(document).ready(function() {
    $('#footer').load('footer.html');
    // Retrieve user role from session storage
    var userRole = sessionStorage.getItem('userRole');
    console.log("Rendering dashboard for role:", userRole);

    // Rendering logic based on userRole
    if (userRole === '1') {
        // Admin role: Show all submenu buttons
        $('.menu button').show();
    } else if (userRole === '2') {
        // Regular user role: Show specific submenus
                $('#manageBooks').show(); // Show Manage Books menu
                $('#manageAuthors').show(); // Show Manage Authors menu
                $('#manageGenres').show(); // Show Manage Genres menu
                $('#borrowedBooks').show(); // Show Borrowed Book List menu
                $('#manageInventory').hide();
                // Hide specific submenu items for regular user
                $('#manageBooksDropdown a:nth-child(2)').hide(); // Hide Book Management link
                $('#manageAuthorsDropdown a:nth-child(2)').hide(); // Hide Author Management link
                $('#manageGenresDropdown a:nth-child(2)').hide(); // Hide Genre Management link
                $('#borrowedBooksDropdown a:nth-child(2)').hide(); // Hide Manage Borrowed List link
    } else {
        // Default message for unauthenticated users
        $('#dashboardSection').html('<h1>Kindly Register/Login Yourself For the Dashboard!</h1>');
        $('.dropdown').hide();
    }

    // Toggle dropdowns on menu item click
    $('.menu button').on('click', function(e) {
        e.preventDefault(); // Prevent default link behavior

        var $submenu = $(this).siblings('.submenu');
                if ($submenu.length) {
                    $('.submenu').not($submenu).hide(); // Hide other submenus except the clicked one
                    $submenu.toggle(); // Toggle the clicked submenu
                }
    });

     // Logout functionality
        $('#logoutbtn').on('click', function(e) {
            e.preventDefault();

            // Retrieve username from session storage
            var userName = sessionStorage.getItem('userName');

            // AJAX request to logout endpoint
            $.ajax({
                type: 'DELETE',
                url: 'http://localhost:8080/api/users/logout',
                contentType: 'application/json',
                data: JSON.stringify({ userName: userName }),
                success: function(response) {
                    console.log('Logout Successful:', response);
                    // Redirect user to login page or perform other actions upon successful logout
                    window.location.href = 'login.html';
                },
                error: function(xhr, status, error) {
                    console.error('Logout Failed:', error);
                    // Handle error scenario appropriately
                }
            });
        });

    // Contact Us Modal
    $('#openContactModal').on('click', function(e) {
        e.preventDefault();
        $('#contactModal').show();
    });

    // Close Contact Us Modal
    $('.close').on('click', function() {
        $('#contactModal').hide();
    });

    // Submit Contact Form (Sample)
    $('#submitModal').on('click', function(e) {
        e.preventDefault();
        // Handle form submission here
        alert('Form submitted!'); // Sample action (replace with actual form submission)
        $('#contactModal').hide(); // Hide modal after submission
    });
});


    // JavaScript chart initialization
    $(document).ready(function() {
        const genresData = {
            labels: ['Fantasy', 'Sci-Fi', 'Mystery', 'Romance', 'Thriller'],
            datasets: [{
                label: 'Number of Books per Genre',
                data: [30, 20, 15, 25, 35],
                backgroundColor: ['#ff6384', '#36a2eb', '#cc65fe', '#ffce56', '#4bc0c0']
            }]
        };

        const genresChartCanvas = document.getElementById('genresChart').getContext('2d');
        new Chart(genresChartCanvas, {
            type: 'pie',
            data: genresData
        });

        const issuedBooksData = {
            labels: ['January', 'February', 'March', 'April', 'May'],
            datasets: [{
                label: 'Number of Issued Books',
                data: [50, 70, 90, 60, 80],
                backgroundColor: '#36a2eb'
            }]
        };

        const issuedBooksChartCanvas = document.getElementById('issuedBooksChart').getContext('2d');
        new Chart(issuedBooksChartCanvas, {
            type: 'bar',
            data: issuedBooksData,
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        const authorsData = {
                    labels: ['Neil Gaiman', 'John Scalzi', ' Robert Galbraith', 'Jane Austen', ' Mary Stone'],
                    datasets: [{
                        label: 'Number of Books per Author',
                        data: [30, 20, 15, 25, 35],
                        backgroundColor: ['#ff6384', '#36a2eb', '#cc65fe', '#ffce56', '#4bc0c0']
                    }]
                };

                const authorsChartCanvas = document.getElementById('authorChart').getContext('2d');
                new Chart(authorsChartCanvas, {
                    type: 'pie',
                    data: authorsData
                });


    });

