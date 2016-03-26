(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('StockHistoryDeleteController',StockHistoryDeleteController);

    StockHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'StockHistory'];

    function StockHistoryDeleteController($uibModalInstance, entity, StockHistory) {
        var vm = this;
        vm.stockHistory = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            StockHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
