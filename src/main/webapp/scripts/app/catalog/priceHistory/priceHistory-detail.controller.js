'use strict';

angular.module('autoposApp')
    .controller('PriceHistoryDetailController', function ($scope, $rootScope, $stateParams, entity, PriceHistory, Item) {
        $scope.priceHistory = entity;
        $scope.load = function (id) {
            PriceHistory.get({id: id}, function(result) {
                $scope.priceHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:priceHistoryUpdate', function(event, result) {
            $scope.priceHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
