$(document).ready(function(){
    $("#userName").change(function(){
        var userName = $(this).val();
        $.ajax({
            type: "POST",
            url: "check",
            data: "userName="+ userName,
            success: function(msg){
                $(".status").ajaxComplete(function(event, request, settings){
                    $(".status").html(msg);
                });
            }
        });
    });
});  