(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('StockHistoryDialogController', StockHistoryDialogController);

    StockHistoryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StockHistory', 'Item'];

    function StockHistoryDialogController ($scope, $stateParams, $uibModalInstance, entity, StockHistory, Item) {
        var vm = this;
        vm.stockHistory = entity;
        vm.items = Item.query();
        vm.load = function(id) {
            StockHistory.get({id : id}, function(result) {
                vm.stockHistory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:stockHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.stockHistory.id !== null) {
                StockHistory.update(vm.stockHistory, onSaveSuccess, onSaveError);
            } else {
                StockHistory.save(vm.stockHistory, onSaveSuccess, onSaveError);
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
