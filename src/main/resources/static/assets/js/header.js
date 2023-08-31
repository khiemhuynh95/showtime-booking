$(document).ready(function () {
  $("#header").load("../components/header.html", function (e) {

    var selectedTab = sessionStorage.getItem('selectedTab') ? sessionStorage.getItem('selectedTab') : 'home';
    console.log('Selected: ' + selectedTab);
    $('.menu li').each(function () {
      // Check if the 'li' contains an 'a' tag
      var anchorTag = $(this).find('a');

      if (selectedTab === anchorTag.text()) {
        if (!anchorTag.hasClass('active')) {
          anchorTag.addClass('active');
        }
        return false;
      }
    });


    //MenuBar
    $('.header-bar').click(function () {
      $(".menu").toggleClass("active");
      $(".header-bar").toggleClass("active");
      $('.overlay').toggleClass('active');
    });

    //handle header menu item onclick
    $('.menu li a').click(function (e) {
      sessionStorage.setItem('selectedTab', $(this).text());
    })
  });
  // Header Sticky Here
  var headerOne = $(".header-section");
  $(window).on('scroll', function () {
    if ($(this).scrollTop() < 1) {
      headerOne.removeClass("header-active");
    } else {
      headerOne.addClass("header-active");
    }
  });

  //Menu Dropdown Icon Adding
  $("ul>li>.submenu").parent("li").addClass("menu-item-has-children");
  // drop down menu width overflow problem fix
  $('ul').parent('li').hover(function () {
    var menu = $(this).find("ul");
    var menupos = $(menu).offset();
    if (menupos.left + menu.width() > $(window).width()) {
      var newpos = -$(menu).width();
      menu.css({
        left: newpos
      });
    }
  });


});
