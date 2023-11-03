$(document).ready(function () {
    //demo data
    //fill demo data
    $('#fname-input').val('John')
    $('#lname-input').val('Doe')
    $('#email-input').val('johndoe@example.com')
    $('#number-input').val('123-456-7890')

    $('#cc-number-input').val('1234 4567 7890 1234')
    $('#cc-name-input').val('John Doe')
    $('#cc-date-exp-input').val('10/23')
    $('#cc-cvv-input').val('123')


    // Get the query parameter value from the URL
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    // Get the 'data' query parameter
    var showId = urlParams.get("show-id");

    // Perform an AJAX request to fetch show details from db using the showid
    $.get("http://localhost:8080/show/show-id=" + showId, function (show) {
        $("#movie-title").text(show.movie.title);
        $("#booking-movie-title").text(show.movie.title);
        $("#cinema-name").text(show.cinemaHall.cinema.split('|')[1]);
        $("#booking-cinema").text(show.cinemaHall.cinema.split('|')[1]);
        $("#show-date").text(show.date);
        $("#show-time").text(show.startTime);

        $("#booking-date-time").text(`${show.date}, ${show.startTime}`);
        var bannerURL = show.movie.backdrops.slice(1, -1).split(",")[0].replace("w300", "w1280");
        $(".bg_img").css("background-image", function () {
            var bg = "url(" + bannerURL + ")";
            return bg;
        });
    });


    //getting seats 
    $.get("http://localhost:8080/seats/show-id=" + showId, function (seats) {
        console.log(seats)

        // Create an object to hold seat data
        var seatData = {};
        // Initialize the seatData map
        for (let rowIndex = 0; rowIndex < 6; rowIndex++) {
            const rowLabel = String.fromCharCode('A'.charCodeAt(0) + rowIndex); // Convert number to letter
            seatData[rowLabel] = Array.from({ length: 14 }, (_, index) => ({
                status: 'AVAILABLE',
                seatNumber: index + 1
            }));
        }

        console.log(seatData);


        // Update seatData with seats object
        seats.forEach(function (seat) {
            var { row, seatNumber } = getRowAndSeatNumber(seat.cinemaSeat);
            // console.log(`Seat: ${row}-${seatNumber}`)
            seatData[row][seatNumber - 1] = {
                status: seat.status,
                price: seat.price,
                showSeatID: seat.showSeatID,
                cinemaSeat: seat.cinemaSeat,
            };
        });

        // Render the show seats in the corresponding rows
        for (var rowIndex = 0; rowIndex < 6; rowIndex++) {
            var rowLabel = String.fromCharCode('A'.charCodeAt(0) + rowIndex);
            var rowContainer = $('#seat-row-' + rowLabel);
            var rowSeats = seatData[rowLabel];

            for (var seatNumber = 1; seatNumber <= 14; seatNumber++) {
                var seat = rowSeats[seatNumber - 1];
                var seatStatus = seat.status;
                var seatPrice = seat.price; // Assuming you have seat price data
                var seatID = seat.showSeatID; // Assuming you have seat ID data
                var cinemaSeat = seat.cinemaSeat; // Assuming you have cinema seat data

                var seatClass = seatStatus === "BOOKED" ? "booked" : "available";
                var seatImageSrc = seatStatus === "BOOKED" ? "assets/images/movie/seat-booked.png" : "assets/images/movie/seat-free.png";

                var seatHTML = `
                    <li class="single-seat ${seatClass}" data-cinema-seat="${cinemaSeat}" data-seat-price="${seatPrice}" data-seat-id="${seatID}">
                        <img src="${seatImageSrc}" alt="seat">
                    </li>
                `;

                rowContainer.append(seatHTML);
            }
        }
        var totalSelectedPrice = 0;
        var tax = 1.0;
        var total = 0;
        var selectedSeats = []
        // Handle seat selection and deselection
        $('.single-seat.available').click(function () {
            var selectedSeat = $(this);
            var cinemaSeat = selectedSeat.data('cinema-seat');
            var seatPrice = selectedSeat.data('seat-price');
            var selectedSeatLabel = $('#selected-seat-label');
            var totalPriceLabel = $('#tickets-price-label');
            var taxLabel = $('#tax-label');
            var totalLabel = $('#total-price-label');
            if (selectedSeat.hasClass('selected')) {
                // Deselect the seat
                selectedSeat.removeClass('selected');
                selectedSeatLabel.html(selectedSeatLabel.html().replace(cinemaSeat + ', ', ''));
                totalSelectedPrice -= seatPrice;
                //remove unselected seat
                selectedSeats = selectedSeats.filter(seat => seat.number !== cinemaSeat)
                
            } else {
                // Select the seat
                selectedSeat.addClass('selected');
                selectedSeatLabel.append(cinemaSeat + ', ');
                totalSelectedPrice += seatPrice;

                selectedSeats.push({
                    id: seatID,
                    number: cinemaSeat,
                    price: seatPrice
                })
            }
            console.log("Seats: " + selectedSeats)
            
            tax = totalSelectedPrice * 0.0825;
            total = tax + totalSelectedPrice;

            totalPriceLabel.text(`$${totalSelectedPrice.toFixed(2)}`);
            taxLabel.text(`$${tax.toFixed(2)}`);
            totalLabel.text(`$${total.toFixed(2)}`);

            // Change seat image when selected
            var newImageSrc = selectedSeat.hasClass('selected') ? "assets/images/movie/seat-selected.png" : "assets/images/movie/seat-free.png";
            selectedSeat.find('img').attr('src', newImageSrc);
        });

        // Handle payment button click
        $('#payment-button').click(function (event) {

            // Prevent the default form submission behavior
            event.preventDefault();
            // Prepare data to send in the POST request
            var postData = {
                seatIds: selectedSeats.map(function (seat) {
                    return seat.id;
                }),
                showId: showId,
                user: {
                    firstName: $('#fname-input').val(),
                    lastName: $('#lname-input').val(),
                    email: $('#email-input').val(),
                    phoneNumber: $('#number-input').val()
                }
            };
            console.log(postData)
            // Send a POST request to http://localhost:8080/book using $.post
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/book',
                data: JSON.stringify(postData),
                contentType: 'application/json',
                success: function (response) {
                    // Handle the response from the server (if needed)
                    console.log('Booking successful!', response);

                    //get image url
                    // Get the style attribute value from the element with the ID "banner-section"
                    var styleAttribute = $("#banner-section").attr("style");

                    // Use a regular expression to extract the URL from the style attribute
                    var match = /url\(['"]?([^'"]+)['"]?\)/.exec(styleAttribute);

                    //direct to a confirmation page
                    var confirmData = {
                        selectedSeats: selectedSeats,
                        imgSrc: match[1].replace("w1280","w400"),
                        show: {
                            date: $("#show-date").text(),
                            time: $("#show-time").text(),
                            movie: $("#movie-title").text(),
                            cinema: $("#cinema-name").text()
                        },
                    }

                    console.log(confirmData)

                    localStorage.setItem('confirmData', JSON.stringify(confirmData));

                    // Redirect to confirmation.html
                    window.location.href = 'confirmation.html';
                },
                error: function (error) {
                    // Handle any errors (if needed)
                    console.error('Booking failed:', error);
                }
            })


        });



    });


});

// Function to extract row and seat number from cinemaSeat
function getRowAndSeatNumber(cinemaSeat) {
    var row = cinemaSeat.charAt(0);
    var seatNumber = cinemaSeat.substr(1);
    return { row: row, seatNumber: seatNumber };
}