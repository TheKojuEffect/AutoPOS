'use strict';

angular.module('autopos')
    .factory('PhoneNumber', function ($resource, DateUtils) {
        return $resource('api/phoneNumbers/:id', {}, {
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
