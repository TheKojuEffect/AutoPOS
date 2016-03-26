(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('LedgerEntryDialogController', LedgerEntryDialogController);

    LedgerEntryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LedgerEntry', 'Ledger'];

    function LedgerEntryDialogController ($scope, $stateParams, $uibModalInstance, entity, LedgerEntry, Ledger) {
        var vm = this;
        vm.ledgerEntry = entity;
        vm.ledgers = Ledger.query();
        vm.load = function(id) {
            LedgerEntry.get({id : id}, function(result) {
                vm.ledgerEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:ledgerEntryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.ledgerEntry.id !== null) {
                LedgerEntry.update(vm.ledgerEntry, onSaveSuccess, onSaveError);
            } else {
                LedgerEntry.save(vm.ledgerEntry, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
