(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BillItemDeleteController',BillItemDeleteController);

    BillItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'BillItem'];

    function BillItemDeleteController($uibModalInstance, entity, BillItem) {
        var vm = this;
        vm.billItem = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            BillItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
