'use strict';

angular.module('autoposApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


