'use strict';

angular.module('autoposApp')
	.controller('PhoneNumberDeleteController', function($scope, $uibModalInstance, entity, PhoneNumber) {

        $scope.phoneNumber = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PhoneNumber.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
