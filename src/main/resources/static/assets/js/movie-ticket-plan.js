$(document).ready(function () {
    initializeNiceSelect();
    initializeDateSelect();
    initializeMovieDetails();
    attachSearchButtonListener();
});

function initializeNiceSelect() {
    $.getScript("../assets/js/nice-select.js", function () {
        $(".select-bar").niceSelect();
    });
}

function initializeDateSelect() {
    var dateSelect = $("#date-picker");
    var currentDate = new Date();

    for (var i = 0; i < 7; i++) {
        if (i != 0) {
            currentDate.setDate(currentDate.getDate() + 1);
        }

        var formattedDate = currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate();
        var option = $('<option>').val(formattedDate).text(formattedDate);
        dateSelect.append(option);
    }
}

function initializeMovieDetails() {
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);
    var movieId = urlParams.get("movie-id");

    $.get("http://localhost:8080/movie/" + movieId, function (movie) {
        $(".title").text(movie.title);
        var bannerURL = movie.backdrops.slice(1, -1).split(",")[0].replace("w300", "w1280");
        $(".bg_img").css("background-image", function () {
            var bg = "url(" + bannerURL + ")";
            return bg;
        });
    });
}

function attachSearchButtonListener() {
    $("#search-button").click(function (event) {
        event.preventDefault();
        var cityInput = document.getElementById("city-input");
        var inputValue = cityInput.value.trim();

        if (inputValue === "") {
            alert("Please enter a valid city name.");
        } else {
            var selectedDate = $("#date-picker :selected").text();
            performSearch(inputValue, selectedDate);
        }
    });
}

function performSearch(city, date) {
    // Get the query parameter value from the URL
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    // Get the 'data' query parameter
    var movieId = urlParams.get("movie-id");

    //return new Promise(function (resolve, reject) {
    $.get(`http://localhost:8080/show/movie-id=${movieId}?city=${city}&date=${date}`, function (shows) {
        const rowsMap = new Map();

        shows.forEach(function (show) {
            var cinemaId = show.cinemaHall.cinema.split('|')[0];
            var cinemaName = show.cinemaHall.cinema.split('|')[1];
            var cinemaAddress = show.cinemaHall.cinema.split('|')[2];
            var timeString = show.startTime;
            var timeParts = timeString.split(":");
            //get start time
            var extractedTime = timeParts[0] + ":" + timeParts[1];
            var timeItemHTML = `<a href="http://localhost:8080/movie-seat-plan.html?show-id=${show.showID}"><div class="item">${extractedTime}</div></a>`;

            if (rowsMap.has(cinemaId)) {
                console.log(`#${cinemaId} already exists`);
                //load existing row and convert to Jquery obj
                var $tempRow = $(rowsMap.get(cinemaId));
                //console.log(`before: ${$tempRow.prop('outerHTML')}`);
                var movieScheduleDiv = $tempRow.find('.movie-schedule');

                //append new time
                movieScheduleDiv.append(timeItemHTML);
                var rowStr = $tempRow.prop('outerHTML');
                //console.log(`after: ${$tempRow.prop('outerHTML')}`);

                //update new row
                rowsMap.set(cinemaId, rowStr);
            } else {
                //load movie-ticket-row template
                var $movieTicketRow = $('<li class="movie-ticket-row">' +
                    '<div class="movie-name">' +
                    '<div class="icons">' +
                    '<i class="far fa-heart"></i>' +
                    '<i class="fas fa-heart"></i>' +
                    '</div>' +
                    '<a href="#0" class="name">{cinema-name}</a>' +
                    '<div class="location-icon">' +
                    '<i class="fas fa-map-marker-alt"></i>' +
                    '</div>' +
                    '</div>' +
                    '<div class="movie-schedule">' +
                    '</div>' +
                    '</li>');

                $movieTicketRow.closest('.movie-ticket-row').attr('id', cinemaId);
                var movieScheduleDiv = $movieTicketRow.find('.movie-schedule');
                movieScheduleDiv.append(timeItemHTML);
                var modifiedTemplateHTML = $movieTicketRow.prop('outerHTML');
                var rowStr = modifiedTemplateHTML.replace("{cinema-name}", cinemaName);

                rowsMap.set(cinemaId, rowStr);

            }
        });
        //find the plan wrapper
        var $ticketTable = $('.seat-plan-wrapper');

        // Loop through the Map and print its elements
        rowsMap.forEach((value, key) => {
            //console.log(`Key: ${key}, Value: ${value}`);
            $ticketTable.append($(value));
        });
    });

}

