$(document).ready(function() {
    // Load footer content
    $('#footer').load('footer.html');

    // Load inventory data on page load
    fetchInventory();

    // Click event for Add Inventory button
    $('#addInventoryBtn').click(function() {
        openAddInventoryModal();
    });

    // Click event for closing modals
    $('.close').click(function() {
        $('#addInventoryModal').css('display', 'none');
    });

    // Submit event for Add Inventory form
    $('#addInventoryForm').submit(function(event) {
        event.preventDefault();
        const bookId = $('#bookId').val();
        const noOfCopies = $('#noOfCopies').val();

        if (!bookId || !noOfCopies) {
            alert('Please fill in all required fields.');
            return;
        }

        const inventoryData = {
            bookId: parseInt(bookId),
            noOfCopies: parseInt(noOfCopies)
        };

        // AJAX request to add new inventory
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/inventory/create',
            contentType: 'application/json',
            data: JSON.stringify(inventoryData),
            success: function(response) {
                alert(response.error || 'Inventory created successfully.');
                $('#addInventoryModal').css('display', 'none');
                fetchInventory(); // Refresh inventory table after adding
            },
            error: function(xhr, status, error) {
                alert('Failed to add inventory. Please try again.');
                console.error(error);
            }
        });
    });

    // Function to open the add inventory modal
    function openAddInventoryModal() {
        $('#addInventoryModal').css('display', 'block');
    }

    // Function to fetch all inventory and populate the table
    function fetchInventory() {
        $.ajax({
            url: 'http://localhost:8080/api/inventory/AllInventory',
            method: 'GET',
            timeout: 0,
            success: function(data) {
                const tbody = $('#inventoryTable tbody');
                tbody.empty();

                data.forEach(function(item) {
                    const row = `<tr>
                                    <td>${item.inventId}</td>
                                    <td>${item.bookId}</td>
                                    <td>${item.noOfCopies}</td>
                                </tr>`;
                    tbody.append(row);
                });
            },
            error: function(xhr, status, error) {
                alert('Failed to fetch inventory: ' + error);
                console.error(error);
            }
        });
    }
});
