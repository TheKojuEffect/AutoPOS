'use strict';

angular.module('autopos')
    .controller('DayBookEntryDetailController', function ($scope, $rootScope, $stateParams, entity, DayBookEntry) {
        $scope.dayBookEntry = entity;
        $scope.load = function (id) {
            DayBookEntry.get({id: id}, function(result) {
                $scope.dayBookEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:dayBookEntryUpdate', function(event, result) {
            $scope.dayBookEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
