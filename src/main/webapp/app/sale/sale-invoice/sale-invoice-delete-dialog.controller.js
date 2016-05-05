(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('SaleInvoiceDeleteController',SaleInvoiceDeleteController);

    SaleInvoiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'SaleInvoice'];

    function SaleInvoiceDeleteController($uibModalInstance, entity, SaleInvoice) {
        var vm = this;
        vm.bill = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            SaleInvoice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
