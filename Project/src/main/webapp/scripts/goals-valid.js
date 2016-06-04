(function () {
    var app = angular.module('application', []);
    app.controller('MainController', function () {
        this.goal = {
            title: ""
            , cost: ""
            , shortDescr: ""
            , description: ""
        };
    });
})();