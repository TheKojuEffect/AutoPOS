(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('ReceiptDeleteController',ReceiptDeleteController);

    ReceiptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Receipt'];

    function ReceiptDeleteController($uibModalInstance, entity, Receipt) {
        var vm = this;
        vm.receipt = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Receipt.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
