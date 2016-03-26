(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BillItemDialogController', BillItemDialogController);

    BillItemDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'BillItem', 'Item', 'Bill'];

    function BillItemDialogController ($scope, $stateParams, $uibModalInstance, entity, BillItem, Item, Bill) {
        var vm = this;
        vm.billItem = entity;
        vm.items = Item.query();
        vm.bills = Bill.query();
        vm.load = function(id) {
            BillItem.get({id : id}, function(result) {
                vm.billItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:billItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.billItem.id !== null) {
                BillItem.update(vm.billItem, onSaveSuccess, onSaveError);
            } else {
                BillItem.save(vm.billItem, onSaveSuccess, onSaveError);
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
