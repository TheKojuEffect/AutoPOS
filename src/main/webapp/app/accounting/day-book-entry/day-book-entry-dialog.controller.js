(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('DayBookEntryDialogController', DayBookEntryDialogController);

    DayBookEntryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'DayBookEntry'];

    function DayBookEntryDialogController ($scope, $stateParams, $uibModalInstance, entity, DayBookEntry) {
        var vm = this;
        vm.dayBookEntry = entity;

        vm.datePickerOptions = {
            maxDate: new Date()
        };

        vm.load = function(id) {
            DayBookEntry.get({id : id}, function(result) {
                vm.dayBookEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:dayBookEntryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.dayBookEntry.id !== null) {
                DayBookEntry.update(vm.dayBookEntry, onSaveSuccess, onSaveError);
            } else {
                DayBookEntry.save(vm.dayBookEntry, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
