'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('transaction', {
                abstract: true,
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: 'app/transaction/transaction.html',
                        controller: 'TransactionController'
                    }
                }
            });
    });
