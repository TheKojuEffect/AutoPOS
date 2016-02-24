'use strict';

angular.module('autopos')
	.controller('ItemDeleteController', function($scope, $uibModalInstance, entity, Item) {

        $scope.item = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Item.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
