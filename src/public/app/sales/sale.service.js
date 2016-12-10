(function () {
    'use strict';

    angular.module('autopos')
        .factory('SaleService', SaleService);

    function SaleService($resource, DateUtils) {
        return $resource('api/sales/:id', {}, {
                'get': {
                    method: 'GET',
                    transformResponse: data => {
                        const sale = angular.fromJson(data);
                        sale.date = DateUtils.convertLocalDateFromServer(sale.date);
                        return sale;
                    }
                },
                'update': {
                    method: 'PUT'
                }
            }
        );
    }
})();