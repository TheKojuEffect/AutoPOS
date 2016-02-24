'use strict';

angular.module('autoposApp')
    .controller('StockDetailController', function ($scope, $rootScope, $stateParams, entity, Stock, Item) {
        $scope.stock = entity;
        $scope.load = function (id) {
            Stock.get({id: id}, function(result) {
                $scope.stock = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:stockUpdate', function(event, result) {
            $scope.stock = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
