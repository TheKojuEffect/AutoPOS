'use strict';

angular.module('autoposApp')
    .controller('VendorDetailController', function ($scope, $rootScope, $stateParams, entity, Vendor) {
        $scope.vendor = entity;
        $scope.load = function (id) {
            Vendor.get({id: id}, function(result) {
                $scope.vendor = result;
            });
        };
        var unsubscribe = $rootScope.$on('autoposApp:vendorUpdate', function(event, result) {
            $scope.vendor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
