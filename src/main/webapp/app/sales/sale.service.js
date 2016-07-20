(function () {
    'use strict';

    angular.module('autopos')
        .factory('Sale', Sale);

    function Sale($resource) {
        return $resource('api/sales/:id');
    }

})();