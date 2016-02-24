'use strict';

angular.module('autoposApp')
    .controller('DayBookEntryController', function ($scope, $state, DayBookEntry, ParseLinks) {

        $scope.dayBookEntrys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            DayBookEntry.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.dayBookEntrys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dayBookEntry = {
                date: null,
                incomingAmount: null,
                outgoingAmount: null,
                miscExpenses: null,
                id: null
            };
        };
    });
