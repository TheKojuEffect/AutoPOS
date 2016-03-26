(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BrandDialogController', BrandDialogController);

    BrandDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Brand'];

    function BrandDialogController ($scope, $stateParams, $uibModalInstance, entity, Brand) {
        var vm = this;
        vm.brand = entity;
        vm.load = function(id) {
            Brand.get({id : id}, function(result) {
                vm.brand = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:brandUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.brand.id !== null) {
                Brand.update(vm.brand, onSaveSuccess, onSaveError);
            } else {
                Brand.save(vm.brand, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
