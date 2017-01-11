(function () {
    'use strict';

    angular
        .module('autopos')
        .controller('PurchaseDeleteController', PurchaseDeleteController);

    PurchaseDeleteController.$inject = ['$uibModalInstance', 'purchase', 'PurchaseService'];

    function PurchaseDeleteController($uibModalInstance, purchase, PurchaseService) {
        const vm = this;
        vm.purchase = purchase;
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            PurchaseService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
