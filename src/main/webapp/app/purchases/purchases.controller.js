(function () {
    'use strict';

    angular.module('autopos')
        .controller('PurchasesController',
            function ($state, $timeout, PurchaseService) {
                let $ctrl = this;
                $ctrl.$state = $state;

                $ctrl.createNewPurchase = createNewPurchase;

                function createNewPurchase() {

                    PurchaseService.save({}, onCreateSuccess);

                    function onCreateSuccess(purchase) {
                        $timeout(function () {
                            $state.go('purchases.detail', {id: purchase.id})
                        }, 10);
                    }

                }
            });
})();
