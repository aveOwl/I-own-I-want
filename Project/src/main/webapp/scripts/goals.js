//toggle an arcticle
$(function () {
    $('.article').click(function () {

        $(this).toggleClass('darker');
        var description = $(this).children('.description');
        description.slideToggle('200');
    });
});

//Removing an article
$(function () {
    $('.close').click(function () {

        var container = $(this).closest('.buttonContainer');
        container.closest('.article').remove();
    });
});

$(function () {
    $('.goal').click(function () {

        $(".insertion").fadeIn();
    });
});


$(document).on("click", "#confirm", function() {// When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...

    //var myForm = $('#MyForm');

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
            // This happens AFTER the backend has returned an JSON array (or other object type)
            var res1, res2;

            for(var i = 0; i < data.length; i++)
            {
                // Parse through the JSON array which was returned.
                // A proper error handling should be added here (check if
                // everything went successful or not)

                res1 = data[i].res1;
                res2 = data[i].res2;

                // Do something with the returned data
                var para = document.createElement("P");                       // Create a <p> element
                var t = res1      // Create a text node
                // Create a text node
                para.appendChild(t);                                          // Append the text to <p>
                document.body.appendChild(para);                              // Append <p> to <body>
            }
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

