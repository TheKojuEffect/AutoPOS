'use strict';

angular.module('autoposApp').controller('VehicleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vehicle', 'Customer',
        function($scope, $stateParams, $uibModalInstance, entity, Vehicle, Customer) {

        $scope.vehicle = entity;
        $scope.customers = Customer.query();
        $scope.load = function(id) {
            Vehicle.get({id : id}, function(result) {
                $scope.vehicle = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:vehicleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.vehicle.id != null) {
                Vehicle.update($scope.vehicle, onSaveSuccess, onSaveError);
            } else {
                Vehicle.save($scope.vehicle, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
