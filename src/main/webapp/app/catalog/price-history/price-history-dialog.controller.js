(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PriceHistoryDialogController', PriceHistoryDialogController);

    PriceHistoryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PriceHistory', 'Item'];

    function PriceHistoryDialogController ($scope, $stateParams, $uibModalInstance, entity, PriceHistory, Item) {
        var vm = this;
        vm.priceHistory = entity;
        vm.items = Item.query();
        vm.load = function(id) {
            PriceHistory.get({id : id}, function(result) {
                vm.priceHistory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:priceHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.priceHistory.id !== null) {
                PriceHistory.update(vm.priceHistory, onSaveSuccess, onSaveError);
            } else {
                PriceHistory.save(vm.priceHistory, onSaveSuccess, onSaveError);
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
