(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('CustomerDialogController', CustomerDialogController);

    CustomerDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Customer'];

    function CustomerDialogController ($scope, $stateParams, $uibModalInstance, entity, Customer) {
        var vm = this;
        vm.customer = entity;
        vm.load = function(id) {
            Customer.get({id : id}, function(result) {
                vm.customer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:customerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.customer.id !== null) {
                Customer.update(vm.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save(vm.customer, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
