(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('SaleInvoiceDialogController', SaleInvoiceDialogController);

    SaleInvoiceDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SaleInvoice', 'SaleInvoiceItem', 'Vehicle'];

    function SaleInvoiceDialogController ($scope, $stateParams, $uibModalInstance, entity, SaleInvoice, SaleInvoiceItem, Vehicle) {
        var vm = this;
        vm.bill = entity;
        vm.billitems = SaleInvoiceItem.query();
        vm.vehicles = Vehicle.query();
        vm.load = function(id) {
            SaleInvoice.get({id : id}, function(result) {
                vm.bill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:billUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.bill.id !== null) {
                SaleInvoice.update(vm.bill, onSaveSuccess, onSaveError);
            } else {
                SaleInvoice.save(vm.bill, onSaveSuccess, onSaveError);
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
