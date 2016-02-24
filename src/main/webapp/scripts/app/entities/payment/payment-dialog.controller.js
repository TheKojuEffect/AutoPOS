'use strict';

angular.module('autoposApp').controller('PaymentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payment', 'Vendor',
        function($scope, $stateParams, $uibModalInstance, entity, Payment, Vendor) {

        $scope.payment = entity;
        $scope.vendors = Vendor.query();
        $scope.load = function(id) {
            Payment.get({id : id}, function(result) {
                $scope.payment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:paymentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.payment.id != null) {
                Payment.update($scope.payment, onSaveSuccess, onSaveError);
            } else {
                Payment.save($scope.payment, onSaveSuccess, onSaveError);
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
