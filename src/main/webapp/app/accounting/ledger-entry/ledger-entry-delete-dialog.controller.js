(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('LedgerEntryDeleteController',LedgerEntryDeleteController);

    LedgerEntryDeleteController.$inject = ['$uibModalInstance', 'entity', 'LedgerEntry'];

    function LedgerEntryDeleteController($uibModalInstance, entity, LedgerEntry) {
        var vm = this;
        vm.ledgerEntry = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            LedgerEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
