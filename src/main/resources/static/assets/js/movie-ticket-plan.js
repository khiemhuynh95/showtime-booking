$(document).ready(function () {
    // Nice Select
    $.getScript("../assets/js/nice-select.js", function () {
        $(".select-bar").niceSelect();
    });

    //adjust the tabe
    // // Get the width of the parent container
    // var parentWidth = $("#ticket-table-container").width();

    // // Set the child div's width to match the parent
    // //$(".child-div").css("width", parentWidth);
    // $(".seat-plan-wrapper").css("width", "100%");

    //handle date list 
    var dateSelect = $("#date-picker");

    // Get the current date
    var currentDate = new Date();

    // Loop to add the next 7 days
    for (var i = 0; i < 7; i++) {
        if (i != 0) {
            // Increment the date by one day
            currentDate.setDate(currentDate.getDate() + 1);
        }

        // Format the date as dd/mm/yyyy
        var formattedDate = currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate();

        // Create an option element and append it to the select
        var option = $('<option>').val(formattedDate).text(formattedDate);
        dateSelect.append(option);
    }

    // Get the query parameter value from the URL
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);

    // Get the 'data' query parameter
    var movieId = urlParams.get("movie-id");

    // Perform an AJAX request to fetch movie details from db using the movieId
    $.get("http://localhost:8080/movie/" + movieId, function (movie) {
        $(".title").text(movie.title);
        // Remove the square brackets and split the string into individual URL strings
        var bannerURL = movie.backdrops
            .slice(1, -1)
            .split(",")[0]
            .replace("w300", "w1280");
        $(".bg_img").css("background-image", function () {
            var bg = "url(" + bannerURL + ")";
            return bg;
        });
    });

    //add click event listener to search button
    $("#search-button").click(function (event) {
        // Prevent the default behavior of the anchor tag (e.g., navigating to a URL)
        event.preventDefault();

        var cityInput = document.getElementById("city-input");

        // Get the value of the input field
        var inputValue = cityInput.value.trim(); // Use trim() to remove leading/trailing spaces

        // Check if the input is empty
        if (inputValue === "") {
            alert("Please enter a valid city name.");
        } else {
            // The input is not empty, you can proceed with your search logic here
            // Get the current selected value
            var selectedDate = $("#date-picker :selected").text();
            //alert(`zipcode: ${inputValue}, selected date: ${selectedDate}`);
            $.get(`http://localhost:8080/show/movie-id=${movieId}?city=${inputValue}&date=${selectedDate}`, function (shows) {
                //console.log(shows)
                shows.forEach(function (show) {
                    var cinemaId = show.cinemaHall.cinema.split('|')[0];
                    var cinemaName = show.cinemaHall.cinema.split('|')[1];
                    var cinemaAddress = show.cinemaHall.cinema.split('|')[2];
                    // get startTime
                    var timeString = show.startTime
                    var timeParts = timeString.split(":");
                    var extractedTime = timeParts[0] + ":" + timeParts[1];
                    //build time item based on startTime
                    var timeItemHTML = `<div class="item">${extractedTime}</div>`;


                    //find the row with the id
                    if ($(`#${cinemaId}`).length > 0) {
                        //row exist
                        console.log($(`#${cinemaId}`).prop('outerHTML'))
                    } else {
                        //no row -> insert
                        $.get("../components/movie-ticket-row.html", function (template) {
                            // Convert the HTML template into a jQuery object
                            var $template = $(template);

                            //assign id for row template
                            $template.closest('.movie-ticket-row').attr('id', cinemaId);
                            //find schedule div
                            var movieScheduleDiv = $template.find('.movie-schedule');

                            //insert time
                            movieScheduleDiv.append(timeItemHTML)

                            //console.log($template)

                            var modifiedTemplateHTML = $template.prop('outerHTML');

                            //insert row                            
                            var rowStr = modifiedTemplateHTML
                                .replace("{cinema-name}", cinemaName);
                            //console.log(rowStr);
                            $('.seat-plan-wrapper').append(rowStr);
                        });
                    }




                });




            });



        }
    });
    //use movieId to query all its shows



});
