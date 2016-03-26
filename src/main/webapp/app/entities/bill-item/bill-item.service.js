(function() {
    'use strict';
    angular
        .module('autopos')
        .factory('BillItem', BillItem);

    BillItem.$inject = ['$resource', 'DateUtils'];

    function BillItem ($resource, DateUtils) {
        var resourceUrl =  'api/bill-items/:id';

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
