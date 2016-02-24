'use strict';

angular.module('autoposApp')
	.controller('BrandDeleteController', function($scope, $uibModalInstance, entity, Brand) {

        $scope.brand = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Brand.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
