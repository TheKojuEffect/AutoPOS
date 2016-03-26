(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BillDialogController', BillDialogController);

    BillDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bill', 'BillItem', 'Vehicle'];

    function BillDialogController ($scope, $stateParams, $uibModalInstance, entity, Bill, BillItem, Vehicle) {
        var vm = this;
        vm.bill = entity;
        vm.billitems = BillItem.query();
        vm.vehicles = Vehicle.query();
        vm.load = function(id) {
            Bill.get({id : id}, function(result) {
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
                Bill.update(vm.bill, onSaveSuccess, onSaveError);
            } else {
                Bill.save(vm.bill, onSaveSuccess, onSaveError);
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
