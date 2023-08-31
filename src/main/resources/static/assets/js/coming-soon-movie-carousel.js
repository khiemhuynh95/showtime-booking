$(document).ready(function () {
  $("#coming-soon-movie-carousel").load(
    "../components/movie-carousel.html",
    function (e) {
      $.getScript("../assets/js/owl.carousel.min.js", function () {
        var movieCarousel = $("#coming-soon-movie-carousel");

        //update tab title
        movieCarousel.find("#tab-title").text("coming soon");
        //update tab slider behavior
        var tabSlider = movieCarousel.find(".tab-slider");
        //console.log(tabSlider)
        tabSlider.owlCarousel({
          loop: true,
          responsiveClass: true,
          nav: false,
          dots: false,
          margin: 30,
          autoplay: true,
          autoplayTimeout: 3000,
          autoplayHoverPause: true,
          responsive: {
            0: {
              items: 1,
            },
            576: {
              items: 2,
            },
            768: {
              items: 2,
            },
            992: {
              items: 3,
            },
            1200: {
              items: 4,
            },
          },
        });
        //populate real data
        // REST API URL to fetch movie data
        var apiURL = "http://localhost:8080/movie/upcoming";

        // Load movie data from the REST API
        $.get(apiURL, function (data) {
          // Iterate through the movie data and insert movie items
          data.forEach(function (movie) {
            // Load the movie item template
            $.get("../components/movie-grid-item.html", function (template) {
              // Replace placeholders in the template with movie data
              var movieItem = template
                .replace("{{movieImage}}", movie.posterURL)
                .replace("{{movieTitle}}", movie.title)
                .replace("{{rating}}", `${movie.rating*10}%`);

              // Append the modified template to the tab slider
              tabSlider.trigger("add.owl.carousel", movieItem);

              // Find the newly added movie item in the DOM
              var $addedMovieItem = tabSlider.find(".owl-item").last();

              // Add a click event listener to the movie item
              $addedMovieItem.on("click", function () {
                // Handle the click event here
                // You can use 'movie' object to access the clicked movie's data
                console.log("Clicked on movie:", movie.title);
                // Save selected movie into local storage

                // Navigate to the detailed page for the clicked movie
                // var movieQueryParam = encodeURIComponent(JSON.stringify(movie));
                window.location.href =
                  "../../movie-details.html?movie-id=" + movie.movieID;
              });
            });
          });
          //refresh to apply new items
          tabSlider.trigger("refresh.owl.carousel");
        });
      });
    }
  );
});
