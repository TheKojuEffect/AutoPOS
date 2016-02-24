'use strict';

angular.module('autopos').controller('BillItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'BillItem', 'Item', 'Bill',
        function($scope, $stateParams, $uibModalInstance, entity, BillItem, Item, Bill) {

        $scope.billItem = entity;
        $scope.items = Item.query();
        $scope.bills = Bill.query();
        $scope.load = function(id) {
            BillItem.get({id : id}, function(result) {
                $scope.billItem = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:billItemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.billItem.id != null) {
                BillItem.update($scope.billItem, onSaveSuccess, onSaveError);
            } else {
                BillItem.save($scope.billItem, onSaveSuccess, onSaveError);
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
