(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PriceHistoryDeleteController',PriceHistoryDeleteController);

    PriceHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'PriceHistory'];

    function PriceHistoryDeleteController($uibModalInstance, entity, PriceHistory) {
        var vm = this;
        vm.priceHistory = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            PriceHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
