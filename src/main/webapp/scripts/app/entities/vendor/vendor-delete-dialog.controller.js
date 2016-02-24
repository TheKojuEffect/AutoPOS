'use strict';

angular.module('autopos')
	.controller('VendorDeleteController', function($scope, $uibModalInstance, entity, Vendor) {

        $scope.vendor = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Vendor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
