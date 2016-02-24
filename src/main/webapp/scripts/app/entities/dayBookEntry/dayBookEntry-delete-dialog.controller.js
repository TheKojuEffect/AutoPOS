'use strict';

angular.module('autopos')
	.controller('DayBookEntryDeleteController', function($scope, $uibModalInstance, entity, DayBookEntry) {

        $scope.dayBookEntry = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DayBookEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
