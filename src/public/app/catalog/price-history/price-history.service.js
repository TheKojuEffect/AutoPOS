(function() {
    'use strict';
    angular
        .module('autopos')
        .factory('PriceHistory', PriceHistory);

    PriceHistory.$inject = ['$resource', 'DateUtils'];

    function PriceHistory ($resource, DateUtils) {
        var resourceUrl =  'api/price-histories/:id';

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
