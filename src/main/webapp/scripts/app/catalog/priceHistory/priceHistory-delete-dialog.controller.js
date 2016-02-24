'use strict';

angular.module('autopos')
	.controller('PriceHistoryDeleteController', function($scope, $uibModalInstance, entity, PriceHistory) {

        $scope.priceHistory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PriceHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
