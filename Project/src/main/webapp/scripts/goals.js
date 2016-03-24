var main = function() {
    $('.article').click(function() {

        var description = $(this).children('.description');

        if (description.is(':hidden')) {
            description.slideDown('200');
        } else {
            description.slideUp('200');
        }
    });
};

$(document).ready(main);

/* 

children() allows you to search through elements nested directly under the current element. 

show() is jQuery's way of saying list or display these.

*/