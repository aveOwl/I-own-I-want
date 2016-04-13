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


$(document).on("click", "#confirm", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
    var myForm = $('#MyForm');
    $.get("ajaxServlet", myForm.serialize(), function(data) {

        var e = $("<p />", {
            id: "somediv" // you need to quote "class" since it's a reserved keyword
        });

        $("#somediv").text(responseText);

        $("body").append(e);

        /*var container = document.createElement('div')

        container.innerHTML = '<div class="article"> \
            <div class="item">'+title+'</div> \
            <div class="row">'+body+'</div> \
            <p>text(data)</p> \
            </div>';
    });*/
})
});

