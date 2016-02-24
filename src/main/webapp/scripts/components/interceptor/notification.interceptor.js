 'use strict';

angular.module('autopos')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-autopos-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-autopos-params')});
                }
                return response;
            }
        };
    });
