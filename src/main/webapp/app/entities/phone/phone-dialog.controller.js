(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PhoneNumberDialogController', PhoneNumberDialogController);

    PhoneNumberDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Phone'];

    function PhoneNumberDialogController ($scope, $stateParams, $uibModalInstance, entity, Phone) {
        var vm = this;
        vm.phone = entity;
        vm.load = function(id) {
            Phone.get({id : id}, function(result) {
                vm.phone = result;
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
            if (vm.phone.id !== null) {
                Phone.update(vm.phone, onSaveSuccess, onSaveError);
            } else {
                Phone.save(vm.phone, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
