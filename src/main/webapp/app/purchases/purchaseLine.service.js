(function () {
    'use strict';

    angular.module('autopos')
        .factory('PurchaseLineService', PurchaseLineService);

    function PurchaseLineService($resource) {
        return $resource('api/purchases/:purchaseId/lines/:purchaseLineId',
            {},
            {update: {method: 'PUT'}});
    }

})();