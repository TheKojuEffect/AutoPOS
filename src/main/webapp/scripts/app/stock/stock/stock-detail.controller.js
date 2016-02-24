'use strict';

angular.module('autopos')
    .controller('StockDetailController', function ($scope, $rootScope, $stateParams, entity, Stock, Item) {
        $scope.stock = entity;
        $scope.load = function (id) {
            Stock.get({id: id}, function(result) {
                $scope.stock = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:stockUpdate', function(event, result) {
            $scope.stock = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
