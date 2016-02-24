'use strict';

angular.module('autoposApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('catalog', {
                abstract: true,
                parent: 'site'
            });
    });
