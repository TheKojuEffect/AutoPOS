'use strict';

angular.module('autoposApp').controller('DayBookEntryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'DayBookEntry',
        function($scope, $stateParams, $uibModalInstance, entity, DayBookEntry) {

        $scope.dayBookEntry = entity;
        $scope.load = function(id) {
            DayBookEntry.get({id : id}, function(result) {
                $scope.dayBookEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:dayBookEntryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dayBookEntry.id != null) {
                DayBookEntry.update($scope.dayBookEntry, onSaveSuccess, onSaveError);
            } else {
                DayBookEntry.save($scope.dayBookEntry, onSaveSuccess, onSaveError);
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
