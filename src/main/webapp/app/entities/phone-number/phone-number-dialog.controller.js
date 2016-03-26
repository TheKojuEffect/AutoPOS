(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PhoneNumberDialogController', PhoneNumberDialogController);

    PhoneNumberDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PhoneNumber'];

    function PhoneNumberDialogController ($scope, $stateParams, $uibModalInstance, entity, PhoneNumber) {
        var vm = this;
        vm.phoneNumber = entity;
        vm.load = function(id) {
            PhoneNumber.get({id : id}, function(result) {
                vm.phoneNumber = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:phoneNumberUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.phoneNumber.id !== null) {
                PhoneNumber.update(vm.phoneNumber, onSaveSuccess, onSaveError);
            } else {
                PhoneNumber.save(vm.phoneNumber, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
