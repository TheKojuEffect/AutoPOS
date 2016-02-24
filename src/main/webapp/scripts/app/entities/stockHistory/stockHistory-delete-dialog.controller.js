'use strict';

angular.module('autoposApp')
	.controller('StockHistoryDeleteController', function($scope, $uibModalInstance, entity, StockHistory) {

        $scope.stockHistory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StockHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
