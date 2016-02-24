'use strict';

angular.module('autoposApp')
    .controller('TagDetailController', function ($scope, $rootScope, $stateParams, entity, Tag) {
        $scope.tag = entity;
        $scope.load = function (id) {
            Tag.get({id: id}, function(result) {
                $scope.tag = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:tagUpdate', function(event, result) {
            $scope.tag = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
