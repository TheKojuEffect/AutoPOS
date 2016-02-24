'use strict';

angular.module('autoposApp')
    .controller('StockHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, StockHistory, Item) {
        $scope.stockHistory = entity;
        $scope.load = function (id) {
            StockHistory.get({id: id}, function(result) {
                $scope.stockHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:stockHistoryUpdate', function(event, result) {
            $scope.stockHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
