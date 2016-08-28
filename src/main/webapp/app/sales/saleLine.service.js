(function () {
    'use strict';

    angular.module('autopos')
        .factory('SaleLineService', SaleLineService);

    function SaleLineService($resource) {
        return $resource('api/sales/:saleId/lines/:saleLineId');
    }

})();