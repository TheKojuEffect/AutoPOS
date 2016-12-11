'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('catalog', {
                abstract: true,
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: 'app/catalog/catalog.html',
                        controller: 'CatalogController'
                    }
                }
            });
    });
