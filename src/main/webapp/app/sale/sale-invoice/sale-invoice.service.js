(function() {
    'use strict';
    angular
        .module('autopos')
        .factory('SaleInvoice', SaleInvoice);

    SaleInvoice.$inject = ['$resource', 'DateUtils'];

    function SaleInvoice ($resource, DateUtils) {
        var resourceUrl =  'api/bills/:id';

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
