$(document).on("submit", ".login", function myFunc() {

    $.ajax({
        url: $(".login").attr('action'),
        type: 'post', // 'get' or 'post'
        data: $(".login").serialize(),
        success : function(response) {
            if (response == "success") {
                window.location.assign("showGoalsServlet");
            } else {
                $('#logError').removeClass("hideME");
                $('#logError').effect( 'bounce', {direction: "up", distance: 5}, 1000 );
            }
        }
    });
});

function myFunc() {
    return false;
}
