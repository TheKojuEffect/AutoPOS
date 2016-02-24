'use strict';

angular.module('autopos').controller('LedgerEntryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LedgerEntry', 'Ledger',
        function($scope, $stateParams, $uibModalInstance, entity, LedgerEntry, Ledger) {

        $scope.ledgerEntry = entity;
        $scope.ledgers = Ledger.query();
        $scope.load = function(id) {
            LedgerEntry.get({id : id}, function(result) {
                $scope.ledgerEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:ledgerEntryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ledgerEntry.id != null) {
                LedgerEntry.update($scope.ledgerEntry, onSaveSuccess, onSaveError);
            } else {
                LedgerEntry.save($scope.ledgerEntry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
