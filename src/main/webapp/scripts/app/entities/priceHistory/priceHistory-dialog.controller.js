'use strict';

angular.module('autoposApp').controller('PriceHistoryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'PriceHistory', 'Item',
        function($scope, $stateParams, $uibModalInstance, entity, PriceHistory, Item) {

        $scope.priceHistory = entity;
        $scope.items = Item.query();
        $scope.load = function(id) {
            PriceHistory.get({id : id}, function(result) {
                $scope.priceHistory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:priceHistoryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.priceHistory.id != null) {
                PriceHistory.update($scope.priceHistory, onSaveSuccess, onSaveError);
            } else {
                PriceHistory.save($scope.priceHistory, onSaveSuccess, onSaveError);
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
