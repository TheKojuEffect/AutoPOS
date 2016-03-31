(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('VehicleDialogController', VehicleDialogController);

    VehicleDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vehicle', 'Customer'];

    function VehicleDialogController ($scope, $stateParams, $uibModalInstance, entity, Vehicle, Customer) {
        var vm = this;
        vm.vehicle = entity;
        vm.customers = Customer.query();
        vm.load = function(id) {
            Vehicle.get({id : id}, function(result) {
                vm.vehicle = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:vehicleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.vehicle.id !== null) {
                Vehicle.update(vm.vehicle, onSaveSuccess, onSaveError);
            } else {
                Vehicle.save(vm.vehicle, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
