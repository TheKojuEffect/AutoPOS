(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('LedgerDeleteController',LedgerDeleteController);

    LedgerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ledger'];

    function LedgerDeleteController($uibModalInstance, entity, Ledger) {
        var vm = this;
        vm.ledger = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Ledger.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
