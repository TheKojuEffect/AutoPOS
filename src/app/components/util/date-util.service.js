(function() {
    'use strict';

    angular
        .module('autopos')
        .factory('DateUtils', DateUtils);

    function DateUtils ($filter, moment) {

        var service = {
            convertDateTimeFromServer : convertDateTimeFromServer,
            convertLocalDateFromServer : convertLocalDateFromServer,
            convertLocalDateToServer : convertLocalDateToServer,
            dateformat : dateformat
        };

        return service;

        function convertDateTimeFromServer (date) {
            if (date) {
                return moment(date).toDate();
            } else {
                return null;
            }
        }

        function convertLocalDateFromServer (date) {
            if (date) {
                return moment(date).toDate();
            }
            return null;
        }

        function convertLocalDateToServer (date) {
            if (date) {
                return $filter('date')(date, 'yyyy-MM-dd');
            } else {
                return null;
            }
        }

        function dateformat () {
            return 'yyyy-MM-dd';
        }
    }

})();
