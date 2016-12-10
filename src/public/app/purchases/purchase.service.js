(function () {
    'use strict';

    angular.module('autopos')
        .factory('PurchaseService', PurchaseService);

    function PurchaseService($resource, DateUtils) {
        return $resource('api/purchases/:id', {}, {
                'get': {
                    method: 'GET',
                    transformResponse: data => {
                        const purchase = angular.fromJson(data);
                        purchase.date = DateUtils.convertLocalDateFromServer(purchase.date);
                        return purchase;
                    }
                },
                'update': {
                    method: 'PUT'
                }
            }
        );
    }
})();