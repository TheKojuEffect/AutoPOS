(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('VendorDeleteController',VendorDeleteController);

    VendorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vendor'];

    function VendorDeleteController($uibModalInstance, entity, Vendor) {
        var vm = this;
        vm.vendor = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Vendor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
