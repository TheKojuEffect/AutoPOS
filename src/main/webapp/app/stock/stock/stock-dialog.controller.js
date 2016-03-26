(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('StockDialogController', StockDialogController);

    StockDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Stock', 'Item'];

    function StockDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Stock, Item) {
        var vm = this;
        vm.stock = entity;
        vm.items = Item.query({filter: 'stock-is-null'});
        $q.all([vm.stock.$promise, vm.items.$promise]).then(function() {
            if (!vm.stock.item || !vm.stock.item.id) {
                return $q.reject();
            }
            return Item.get({id : vm.stock.item.id}).$promise;
        }).then(function(item) {
            vm.items.push(item);
        });
        vm.load = function(id) {
            Stock.get({id : id}, function(result) {
                vm.stock = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:stockUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.stock.id !== null) {
                Stock.update(vm.stock, onSaveSuccess, onSaveError);
            } else {
                Stock.save(vm.stock, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
