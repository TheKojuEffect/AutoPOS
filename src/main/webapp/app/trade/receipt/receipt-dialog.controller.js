(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('ReceiptDialogController', ReceiptDialogController);

    ReceiptDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Receipt', 'Customer'];

    function ReceiptDialogController ($scope, $stateParams, $uibModalInstance, entity, Receipt, Customer) {
        var vm = this;
        vm.receipt = entity;
        vm.customers = Customer.query();
        vm.load = function(id) {
            Receipt.get({id : id}, function(result) {
                vm.receipt = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:receiptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.receipt.id !== null) {
                Receipt.update(vm.receipt, onSaveSuccess, onSaveError);
            } else {
                Receipt.save(vm.receipt, onSaveSuccess, onSaveError);
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
