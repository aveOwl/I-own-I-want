// Create Controller
(function () {
    var app = angular.module('application', []);

    // new Controller
    app.controller('MainController', function () {
        // code that runs when controller is called
        this.user = { // this.user - property of this controller
            firstname: ""
            , lastname: ""
            , username: ""
            , email: ""
            , password: ""
            , confirmPassword: ""
        };

    });

    app.directive('compareTo', function () {
        return {
            require: "ngModel"
            , scope: {
                otherModelValue: "=compareTo"
            }
            , link: function (scope, element, attributes, ngModel) {

                ngModel.$validators.compareTo = function (modelValue) {
                    return modelValue == scope.otherModelValue;
                };

                scope.$watch("otherModelValue", function () {
                    ngModel.$validate();
                });
            }
        };
    });

})();
