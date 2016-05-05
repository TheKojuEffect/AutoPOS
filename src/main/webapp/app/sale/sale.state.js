'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sale', {
                abstract: true,
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: 'app/sale/sale.html',
                        controller: 'SaleController'
                    }
                }
            });
    });
