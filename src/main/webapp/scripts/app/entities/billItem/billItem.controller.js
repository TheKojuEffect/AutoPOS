'use strict';

angular.module('autopos')
    .controller('BillItemController', function ($scope, $state, BillItem, ParseLinks) {

        $scope.billItems = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            BillItem.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.billItems = result;
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
            $scope.billItem = {
                quantity: null,
                rate: null,
                amount: null,
                date: null,
                remarks: null,
                issuedBy: null,
                id: null
            };
        };
    });
