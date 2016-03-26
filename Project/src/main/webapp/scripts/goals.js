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