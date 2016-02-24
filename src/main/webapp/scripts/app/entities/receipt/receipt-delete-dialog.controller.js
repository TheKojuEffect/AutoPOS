'use strict';

angular.module('autoposApp')
	.controller('ReceiptDeleteController', function($scope, $uibModalInstance, entity, Receipt) {

        $scope.receipt = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Receipt.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
