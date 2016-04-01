(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BillDeleteController',BillDeleteController);

    BillDeleteController.$inject = ['$uibModalInstance', 'entity', 'Bill'];

    function BillDeleteController($uibModalInstance, entity, Bill) {
        var vm = this;
        vm.bill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Bill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
