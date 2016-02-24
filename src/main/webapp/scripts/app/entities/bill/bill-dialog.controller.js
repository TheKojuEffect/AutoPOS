'use strict';

angular.module('autoposApp').controller('BillDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bill', 'BillItem', 'Vehicle',
        function($scope, $stateParams, $uibModalInstance, entity, Bill, BillItem, Vehicle) {

        $scope.bill = entity;
        $scope.billitems = BillItem.query();
        $scope.vehicles = Vehicle.query();
        $scope.load = function(id) {
            Bill.get({id : id}, function(result) {
                $scope.bill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:billUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bill.id != null) {
                Bill.update($scope.bill, onSaveSuccess, onSaveError);
            } else {
                Bill.save($scope.bill, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate = {};

        $scope.datePickerForDate.status = {
            opened: false
        };

        $scope.datePickerForDateOpen = function($event) {
            $scope.datePickerForDate.status.opened = true;
        };
}]);
