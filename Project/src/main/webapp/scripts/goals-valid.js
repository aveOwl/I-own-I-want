//just a good habit to wrap JS in a closure
(function () {

    /*
     var app - that is our application
     angular - AngularJS library
     module - creating a new module
     we give it a name - 'application'
     [] - dependencies (we dont have any dependencies yet)
     */
    var app = angular.module('application', []);

    /*
    controllers help to get data onto the page
    controllers are where we define our app's behavoiur by
    defining functions and values
    */

    /*
    the code in here is what will be executed when our controller is called

    */
    app.controller('MainController' /* specifying name of the controller (always from the capital)*/, function () {
        //makes goal a property of this controller
        this.goal = {
            title: ""
            , cost: ""
            , shortDescr: ""
            , description: ""
        };
    });
})();