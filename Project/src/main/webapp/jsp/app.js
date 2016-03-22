var main = function() {
$('.article').click(function() {
    $('.article').removeClass('current');
    $('.description').hide();

    $(this).addClass('current');
    $(this).children('.description').show();
    
});
$(document).keypress(function(event){
    if(event.which===111)
    $('.current').children('.description').toggle();
   
    
    if(event.which===110)
    {
     var currentArticle = $('.current');
     var nextArticle = currentArticle.next();

    currentArticle.removeClass('current');
    nextArticle.addClass('current');
    }
});
};

$(document).ready(main);

/* 

children() allows you to search through elements nested directly under the current element. 

show() is jQuery's way of saying list or display these.

*/