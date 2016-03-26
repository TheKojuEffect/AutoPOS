(function() {
    'use strict';
    angular
        .module('autopos')
        .factory('Ledger', Ledger);

    Ledger.$inject = ['$resource'];

    function Ledger ($resource) {
        var resourceUrl =  'api/ledgers/:id';

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
