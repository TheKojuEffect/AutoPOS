(function () {
    'use strict';

    angular
        .module('autopos')
        .controller('SaleDeleteController', SaleDeleteController);

    SaleDeleteController.$inject = ['$uibModalInstance', 'sale', 'SaleService'];

    function SaleDeleteController($uibModalInstance, sale, SaleService) {
        const vm = this;
        vm.sale = sale;
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            SaleService.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
