(function() {
    'use strict';
    angular
        .module('autopos')
        .factory('LedgerEntry', LedgerEntry);

    LedgerEntry.$inject = ['$resource', 'DateUtils'];

    function LedgerEntry ($resource, DateUtils) {
        var resourceUrl =  'api/ledger-entries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date = DateUtils.convertDateTimeFromServer(data.date);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
