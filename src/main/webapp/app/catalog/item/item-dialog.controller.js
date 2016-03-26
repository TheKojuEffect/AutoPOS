(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('ItemDialogController', ItemDialogController);

    ItemDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Item', 'Category', 'Brand', 'Tag'];

    function ItemDialogController ($scope, $stateParams, $uibModalInstance, entity, Item, Category, Brand, Tag) {
        var vm = this;
        vm.item = entity;
        vm.categorys = Category.query();
        vm.brands = Brand.query();
        vm.tags = Tag.query();
        vm.load = function(id) {
            Item.get({id : id}, function(result) {
                vm.item = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:itemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.item.id !== null) {
                Item.update(vm.item, onSaveSuccess, onSaveError);
            } else {
                Item.save(vm.item, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
