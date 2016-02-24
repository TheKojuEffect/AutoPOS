'use strict';

angular.module('autoposApp')
    .controller('BrandDetailController', function ($scope, $rootScope, $stateParams, entity, Brand) {
        $scope.brand = entity;
        $scope.load = function (id) {
            Brand.get({id: id}, function(result) {
                $scope.brand = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:brandUpdate', function(event, result) {
            $scope.brand = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
