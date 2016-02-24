'use strict';

angular.module('autoposApp').controller('ReceiptDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Receipt', 'Customer',
        function($scope, $stateParams, $uibModalInstance, entity, Receipt, Customer) {

        $scope.receipt = entity;
        $scope.customers = Customer.query();
        $scope.load = function(id) {
            Receipt.get({id : id}, function(result) {
                $scope.receipt = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:receiptUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.receipt.id != null) {
                Receipt.update($scope.receipt, onSaveSuccess, onSaveError);
            } else {
                Receipt.save($scope.receipt, onSaveSuccess, onSaveError);
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
