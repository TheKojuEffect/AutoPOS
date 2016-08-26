(function () {
    'use strict';

    angular.module('autopos')
        .controller('SalesController',
            function ($state, SaleService) {
                let $ctrl = this;
                $ctrl.$state = $state;

                $ctrl.createNewSale = createNewSale;

                function createNewSale() {

                    SaleService.save({}, onCreateSuccess);

                    function onCreateSuccess(sale) {
                        $state.go('sales.detail', {id: sale.id})
                    }

                }
            });
})();
