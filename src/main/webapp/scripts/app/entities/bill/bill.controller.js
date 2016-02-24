'use strict';

angular.module('autoposApp')
    .controller('BillController', function ($scope, $state, Bill, ParseLinks) {

        $scope.bills = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Bill.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.bills = result;
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
            $scope.bill = {
                date: null,
                subTotal: null,
                discount: null,
                taxableAmount: null,
                tax: null,
                grandTotal: null,
                client: null,
                remarks: null,
                issuedBy: null,
                id: null
            };
        };
    });
