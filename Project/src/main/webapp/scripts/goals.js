var counter = 0;

// toggle an article
$(document).on('click', '.article', function () {
    $(this).toggleClass('darker');
    var description = $(this).children('.description');
    description.slideToggle('200');
});

// show the window to add an article [.goal = new Goal button]
$(function () {
    $('.goal').click(function () {
        $(".insertion").fadeIn();
    });
});

// close the window to add an article [.closeForm = close Button on Form]
$(function () {
    $('.closeForm').click(function () {
        $(".insertion").fadeOut();
    });
});

// add Goal => persist it in DataBase
$(document).on("click", "#confirm", function() {

    counter++;
    var title =         document.getElementById("title").value;
    var cost =          document.getElementById("cost").value;
    var shorten =       document.getElementById("shorten").value;
    var description =   document.getElementById("description").value;

    $.ajax({
        url: 'addGoalsServlet',
        type: 'post', // 'get' or 'post'
        dataType: 'text',
        data: {title : title, 
               cost : cost,
               shorten : shorten, 
               description : description}, // variable you want to send.
        success : function(data) {
            var itemId = 'item' + counter;
            var rowId = 'row' + counter;
            var titleId = 'title' + counter;
            var sourceId = 'source' + counter;
            var dateId = 'date' + counter;
            var descId = 'desc' + counter;

            var time = new Date().getTime();
            var pubdate = new Date(time).toDateString();

            var html = '<div class="article"' + '>' +
                '<div class="item"' + ' id=' + itemId + '>' +
                '<div class="row"' + ' id=' + rowId + '>' +

                '<p class="title"' + ' id=' + titleId + '></p>' +

                '</div>' + '<div class="item"' + ' id=' + itemId + '>' +
                '<p class="source"' + ' id=' + sourceId + '></p>' +

                '</div>' + '<div class="item"' + ' id=' + itemId + '>' +
                '<p class="pubdate"' + ' id=' + dateId + '></p>' +
                '</div>' +
                '<div class="buttonContainer"><button class="close">✗</button>' +
                '<button class="edit">✍</button></div>' +
                '</div>' + '<div class="description"><div class="">&nbsp;</div>' +
                '<h1' + ' id=' + descId + '></h1><div class="">&nbsp;</div>' +
                '</div></div>';

            $(".articles").append(html);

            document.getElementById(titleId).innerHTML = "#" + data + " " + title;
            document.getElementById(sourceId).innerHTML = shorten;
            document.getElementById(dateId).innerHTML = pubdate;
            document.getElementById(descId).innerHTML = description;
        }
    });
});

// close goal => remove it from DataBase.
$(document).on('click', '.close', function () {

    var sp = $(this).closest("div").siblings().find("span").text();
    var container = $(this).closest('.buttonContainer');
    container.closest('.article').remove();
    
    $.ajax({
        url: 'removeGoalsServlet',
        type: 'post', // 'get' or 'post'
        data: {id: sp}, // variable you want to send.
        success : function(data) {
        }
    });
});

// edit goal => update it in DataBase
$(document).on('click', '.edit', function () {

    var sp = $(this).closest("div").siblings().find("span").text();

    $.ajax({
        url: 'editGoalsServlet',
        type: 'post', // 'get' or 'post'
        data: {id: sp}, // variable you want to send.
        success : function(data) {
            
        }
    });
});