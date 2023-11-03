$(document).ready(function () {
  // Get the query parameter value from the URL
  var queryString = window.location.search;
  var urlParams = new URLSearchParams(queryString);

  // Get the 'data' query parameter
  var movieId = urlParams.get("movie-id");

  // Now you have the 'movie' object available to use on the new page
  console.log("Selected movie: " + movieId);
  //init image slider
  var imageSlider = $(".details-photos");
  imageSlider.owlCarousel({
    // loop:true,
    dots: false,
    autoplay: true,
    autoplayTimeout: 5000,
    smartSpeed: 1000,
    margin: 30,
    nav: false,
    responsive: {
      0: {
        items: 1,
      },
      576: {
        items: 2,
      },
      768: {
        items: 3,
      },
      1024: {
        items: 3,
      },
      1200: {
        items: 3,
      },
    },
  });
  // Perform an AJAX request to fetch movie details from db using the movieId
  $.get("http://localhost:8080/movie/" + movieId, function (movie) {
    // Update the content on the page with the fetched movie details
    $("#movie-title").text(movie.title);
    $("#thumb-nail").attr("src", movie.posterURL);
    $("#movie-release-date").text(movie.releaseDate);
    $("#movie-duration").text(movie.duration);
    $("#movie-description").text(movie.description);

    // Remove the square brackets and split the string into individual URL strings
    var backdropUrls = movie.backdrops.slice(1, -1).split(",");

    //console.log(backdropUrls);
    // Loop through the images array and generate Owl Carousel items
    $.each(backdropUrls, function (index, imageUrl) {
      var itemHtml =
        '<div class="thumb"><a href="' +
        imageUrl.replace("w300", "w780") +
        '" class="img-pop"><img src="' +
        imageUrl.replace("w300", "w780") +
        '" alt="movie"></a></div>';

      // Convert the HTML string into a jQuery object
      var $item = $(itemHtml);

      // Attach the magnificPopup functionality to the .img-pop element
      $item.find(".img-pop").magnificPopup({
        type: "image",
        gallery: {
          enabled: true,
        },
      });
      imageSlider.trigger("add.owl.carousel", $item);
    });
    //refresh to apply new items
    imageSlider.trigger("refresh.owl.carousel");

    //perform api call to get trailer video
    $(".video-popup").attr("href", movie.trailerURL);

    //pick the first backdrop to be the background image
    //increase the size of background image
    backgroundUrl = backdropUrls[0].replace("w300", "w1280");
    $(".bg_img").css("background-image", function () {
      var bg = "url(" + backgroundUrl + ")";
      return bg;
    });

    //Odometer
    $(".odometer").attr("data-odometer-final", movie.rating * 10);
    $(".counter-item").each(function () {
      $(this).isInViewport(function (status) {
        if (status === "entered") {
          for (var i = 0; i < document.querySelectorAll(".odometer").length; i++) {
            var el = document.querySelectorAll('.odometer')[i];
            el.innerHTML = el.getAttribute("data-odometer-final");
          }
        }
      });
    });

    //Genres tags
    $.each(movie.genres, function (index, e) {
      //console.log(e.genre)
      var htmlTag = `<a class="button">${e.genre}</a>`
      $('#genre-tags').append($(htmlTag))
    })

    //if upcoming, dont show book ticket button
    if (movie.status === "UPCOMING") {
      // Disable the button
      $("#book-tickets-button").prop("disabled", true);

      // Change the text
      $("#book-tickets-button").text("TICKETS COMING SOON");
    } else {
      //Navigate to ticket-plan html
      $('#book-tickets-button').on("click", function () {
        window.location.href =
          "../../movie-ticket-plan.html?movie-id=" + movie.movieID;
      });
    }




  });


});
