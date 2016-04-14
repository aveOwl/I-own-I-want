var counter = 0;

//toggle an arcticle
$(document).on('click', '.article',function () {
        $(this).toggleClass('darker');
        var description = $(this).children('.description');
        description.slideToggle('200');
});

//Removing an article
$(document).on('click', '.closeForm',function () {
        $('.insertion').fadeOut();
    });


$(document).on('click', '.close',function () {
        var container = $(this).closest('.buttonContainer');
        container.closest('.article').remove();
    });

$(function () {
    $('.goal').click(function () {

        $(".insertion").fadeIn();
    });
});


$(document).on("click", "#confirm", function() {// When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...

    //var myForm = $('#MyForm');

    counter++;
    var title = document.getElementById("title").value;
    var cost = document.getElementById("cost").value;
    var shorten = document.getElementById("shorten").value;
    var description = document.getElementById("description").value;



    $.ajax({
        url: 'addGoalsServlet',
        type: 'post',   // 'get' or 'post'
        data: {title : title, cost : cost,
            shorten : shorten, description : description},   //variable you want to send.
        success : function(data)
        {

            //var articleId = 'article' + counter;
            var itemId = 'item' + counter;
            var rowId = 'row' + counter;
            var titleId = 'title' + counter;
            var sourceId = 'source' + counter;
            var dateId = 'date' + counter;
            var descId = 'desc' + counter;

            var time = new Date().getTime();
            var pubdate = new Date(time).toDateString();


            var html = '<div class="article"' + /*' id=' + articleId + */'>' +
                '<div class="itemForm"' + ' id=' + itemId + '>' +
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

            document.getElementById(titleId).innerHTML = title;
            document.getElementById(sourceId).innerHTML = shorten;
            document.getElementById(dateId).innerHTML = pubdate;
            document.getElementById(descId).innerHTML = description;


            /*var articleId = 'article' + counter;
            var itemId = 'item' + counter;
            var rowId = 'row' + counter;
            var titleId = 'title' + counter;

            $('<div class="article">').(id, articleId).appendTo(".articles");
            $('<div class="item">').setAttribute(id, itemId).appendTo("#" + articleId);
            $('<div class="row">').setAttribute(id, rowId).appendTo("#" + itemId);
            document.getElementById(titleId).innerHTML = title;*/


            /*document.getElementById("newP" + counter).innerHTML = title;

            $('<div class="item" id="newItem">').appendTo('#newArticle');
            $('<p class="source" id="newS">').appendTo('#newItem');
            document.getElementById("newS").innerHTML = shorten;*/


            /*var article = document.createElement('div');
            article.className = "article";

            article.innerHTML = '<div class="item"><div class="row"> <p class="title">'+title+'</div> </div> </div>';
*/

          /*  var art = document.createElement('div');
            div.className = "newArticle";

            var item = document.createElement('div');
            div.className = "newItem";

            art.appendChild(item);

            var row = document.createElement('div');
            div.className = "row";

            item.appendChild(row);

            row.innerHTML = text;*/



            //$('<div class="row">').innerHTML = "<p>" + title + "</p>";



            //$('<div>').text(title).appendTo('.articles');

            // This happens AFTER the backend has returned an JSON array (or other object type)
           /* var res1, res2;

            for(var i = 0; i < data.length; i++)
            {
                // Parse through the JSON array which was returned.
                // A proper error handling should be added here (check if
                // everything went successful or not)


                var newDiv = document.createElement("div");
                newDiv.innerHTML = "<p>" + title + "</p>";
                $(".articles").appendChild(newDiv);



                res1 = data[i].res1;
                res2 = data[i].res2;

                // Do something with the returned data
                var para = document.createElement("P");                       // Create a <p> element
                var t = res1      // Create a text node
                // Create a text node
                para.appendChild(t);                                          // Append the text to <p>
                document.body.appendChild(para);                              // Append <p> to <body>
            }*/
        }


    });

    /*jQuery('<div/>', {
        id : "someId"
    }).appendTo('.articles container');*/


    //$.post("ajaxServlet",{description:"Nikos"})

    /*$.get("ajaxServlet", myForm.serialize(), function(data) {

            $('#someId').text(data);*/

        /*var container = document.createElement('div')

        container.innerHTML = '<div class="article"> \
            <div class="item">'+title+'</div> \
            <div class="row">'+body+'</div> \
            <p>text(data)</p> \
            </div>';
    });*/
});

