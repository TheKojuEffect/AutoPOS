(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('VehicleDeleteController',VehicleDeleteController);

    VehicleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vehicle'];

    function VehicleDeleteController($uibModalInstance, entity, Vehicle) {
        var vm = this;
        vm.vehicle = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Vehicle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
