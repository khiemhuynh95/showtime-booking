$(document).ready(function () {
  $("#search-tab").load("../components/search-tab.html", function (e) {
    // Nice Select
    $.getScript("../assets/js/nice-select.js", function () {
      $('.select-bar').niceSelect();
    });
  });
});
