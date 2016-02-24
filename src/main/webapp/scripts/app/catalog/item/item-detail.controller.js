'use strict';

angular.module('autoposApp')
    .controller('ItemDetailController', function ($scope, $rootScope, $stateParams, entity, Item, Category, Brand, Tag) {
        $scope.item = entity;
        $scope.load = function (id) {
            Item.get({id: id}, function(result) {
                $scope.item = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:itemUpdate', function(event, result) {
            $scope.item = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
