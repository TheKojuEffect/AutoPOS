'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('catalog', {
                abstract: true,
                parent: 'site',
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/catalog/catalog.html',
                        controller: 'CatalogController'
                    }
                }
            });
    });
