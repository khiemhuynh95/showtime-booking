$(document).ready(function () {
    var confirmData = localStorage.getItem('confirmData');
    if (confirmData) {
        confirmData = JSON.parse(confirmData); // Parse the stored JSON string
        // Now you can use the confirmData object as needed
        console.log(confirmData);

        // Load the ticket.html component once
        $.get("../components/ticket.html", function (template) {
            confirmData.selectedSeats.forEach(function (seat) {
                

                var ticketItem = template
                .replace("{cinema-name}", confirmData.show.cinema)
                .replace("{movie-title}", confirmData.show.movie)
                .replace("{imgSrc}", confirmData.imgSrc)
                .replace("{price}", seat.price.toFixed(2))
                .replace("{row}", seat.number.charAt(0))
                .replace("{seat-number}", seat.number.substring(1))
                .replace("{date}", confirmData.show.date)
                .replace("{time}", confirmData.show.time)
                // Append the template for each seat
                $("#ticket-container").append(ticketItem);
            });
        });

    } else {
        // Handle the case where confirmData is not found in localStorage
    }

    //clear data seats
    localStorage.removeItem('confirmData');
});
