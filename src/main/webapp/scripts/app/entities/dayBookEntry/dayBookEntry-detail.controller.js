'use strict';

angular.module('autoposApp')
    .controller('DayBookEntryDetailController', function ($scope, $rootScope, $stateParams, entity, DayBookEntry) {
        $scope.dayBookEntry = entity;
        $scope.load = function (id) {
            DayBookEntry.get({id: id}, function(result) {
                $scope.dayBookEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:dayBookEntryUpdate', function(event, result) {
            $scope.dayBookEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
