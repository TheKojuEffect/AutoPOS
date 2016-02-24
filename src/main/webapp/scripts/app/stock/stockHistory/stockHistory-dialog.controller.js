'use strict';

angular.module('autoposApp').controller('StockHistoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StockHistory', 'Item',
        function($scope, $stateParams, $uibModalInstance, entity, StockHistory, Item) {

        $scope.stockHistory = entity;
        $scope.items = Item.query();
        $scope.load = function(id) {
            StockHistory.get({id : id}, function(result) {
                $scope.stockHistory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:stockHistoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.stockHistory.id != null) {
                StockHistory.update($scope.stockHistory, onSaveSuccess, onSaveError);
            } else {
                StockHistory.save($scope.stockHistory, onSaveSuccess, onSaveError);
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
