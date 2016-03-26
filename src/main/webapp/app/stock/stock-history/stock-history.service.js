(function() {
    'use strict';
    angular
        .module('autopos')
        .factory('StockHistory', StockHistory);

    StockHistory.$inject = ['$resource', 'DateUtils'];

    function StockHistory ($resource, DateUtils) {
        var resourceUrl =  'api/stock-histories/:id';

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
