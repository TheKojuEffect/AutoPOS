'use strict';

angular.module('autopos')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


