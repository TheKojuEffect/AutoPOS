'use strict';

angular.module('autopos').controller('PhoneNumberDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PhoneNumber',
        function($scope, $stateParams, $uibModalInstance, entity, PhoneNumber) {

        $scope.phoneNumber = entity;
        $scope.load = function(id) {
            PhoneNumber.get({id : id}, function(result) {
                $scope.phoneNumber = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:phoneNumberUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.phoneNumber.id != null) {
                PhoneNumber.update($scope.phoneNumber, onSaveSuccess, onSaveError);
            } else {
                PhoneNumber.save($scope.phoneNumber, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
