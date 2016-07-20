(function () {
    'use strict';

    angular.module('autopos')
        .controller('SalesController', function ($scope, $state) {
            $scope.$state = $state;
        });

})();
