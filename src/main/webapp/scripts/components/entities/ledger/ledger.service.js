'use strict';

angular.module('autoposApp')
    .factory('Ledger', function ($resource, DateUtils) {
        return $resource('api/ledgers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
