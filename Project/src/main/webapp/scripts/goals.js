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

    var myForm = $('#MyForm');

    //$('<div id="someId""></div>').appendTo('.articles container');

    $.ajax({
        url: '/',
        type: 'post',   // 'get' or 'post'
        data: 'description=' + arg0,  //variable you want to send.
        success: function(result) {
            console.log(result);
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

