(function () {
    'use strict';

    angular
        .module('autopos')
        .filter('fromArrayDate', arrToDate);

    function arrToDate() {
        return arrToDateFilter;

        function arrToDateFilter(inputArr) {
            return inputArr ? new Date(inputArr) : null;
        }
    }
})();
