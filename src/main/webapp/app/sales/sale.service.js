(function () {
    'use strict';

    angular.module('autopos')
        .factory('SaleService', SaleService);

    function SaleService($resource) {
        return $resource('api/sales/:id');
    }

})();