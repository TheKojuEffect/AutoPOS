'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('accounting', {
                abstract: true,
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: 'app/accounting/accounting.html',
                        controller: 'AccountingController'
                    }
                }
            });
    });
