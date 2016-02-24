'use strict';

angular.module('autoposApp')
    .controller('LedgerEntryController', function ($scope, $state, LedgerEntry, ParseLinks) {

        $scope.ledgerEntrys = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            LedgerEntry.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.ledgerEntrys = result;
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
            $scope.ledgerEntry = {
                date: null,
                particular: null,
                folio: null,
                debit: null,
                credit: null,
                balance: null,
                remarks: null,
                id: null
            };
        };
    });
