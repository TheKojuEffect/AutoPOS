'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('trade', {
                abstract: true,
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: 'app/transaction/trade.html',
                        controller: 'TradeController'
                    }
                }
            });
    });
