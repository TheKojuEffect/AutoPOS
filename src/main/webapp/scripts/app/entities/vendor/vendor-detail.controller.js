'use strict';

angular.module('autopos')
    .controller('VendorDetailController', function ($scope, $rootScope, $stateParams, entity, Vendor) {
        $scope.vendor = entity;
        $scope.load = function (id) {
            Vendor.get({id: id}, function(result) {
                $scope.vendor = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:vendorUpdate', function(event, result) {
            $scope.vendor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
