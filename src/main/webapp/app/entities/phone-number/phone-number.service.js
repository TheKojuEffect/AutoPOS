(function() {
    'use strict';
    angular
        .module('autopos')
        .factory('PhoneNumber', PhoneNumber);

    PhoneNumber.$inject = ['$resource'];

    function PhoneNumber ($resource) {
        var resourceUrl =  'api/phone-numbers/:id';

        return $resource(resourceUrl, {}, {
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
    }
})();
