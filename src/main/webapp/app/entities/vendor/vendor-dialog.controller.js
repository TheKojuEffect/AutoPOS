(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('VendorDialogController', VendorDialogController);

    VendorDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vendor'];

    function VendorDialogController ($scope, $stateParams, $uibModalInstance, entity, Vendor) {
        var vm = this;
        vm.vendor = entity;
        vm.load = function(id) {
            Vendor.get({id : id}, function(result) {
                vm.vendor = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:vendorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.vendor.id !== null) {
                Vendor.update(vm.vendor, onSaveSuccess, onSaveError);
            } else {
                Vendor.save(vm.vendor, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
