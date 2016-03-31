'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('party', {
                abstract: true,
                parent: 'app',
                views: {
                    'content@': {
                        templateUrl: 'app/party/party.html',
                        controller: 'PartyController'
                    }
                }
            });
    });
