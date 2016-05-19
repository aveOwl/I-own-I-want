//toggle a password field
$(function () {
    $('.passButton').click(function () {
        var password = $('.password');
        password.slideToggle('200');
    });
});