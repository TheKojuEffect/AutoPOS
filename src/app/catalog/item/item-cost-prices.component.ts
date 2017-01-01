import * as angular from 'angular';

angular.module('autopos')
    .component('costPricesComponent', {
        templateUrl: './item-cost-prices.component.html',
        bindings: {
            resolve: '<'
        },
        controller: function () {
            const $ctrl = this;

            $ctrl.$onInit = function () {
                $ctrl.item = $ctrl.resolve.item;
                $ctrl.costPrices = $ctrl.resolve.costPrices;
            };
        }
    });