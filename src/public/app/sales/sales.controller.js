(function () {
    'use strict';

    angular.module('autopos')
        .controller('SalesController',
            function ($state, $timeout, SaleService) {
                let $ctrl = this;
                $ctrl.$state = $state;

                $ctrl.createNewSale = createNewSale;

                function createNewSale() {

                    SaleService.save({}, onCreateSuccess);

                    function onCreateSuccess(sale) {
                        $timeout(function () {
                            $state.go('sales.detail', {id: sale.id})
                        }, 10);
                    }

                }
            });
})();
