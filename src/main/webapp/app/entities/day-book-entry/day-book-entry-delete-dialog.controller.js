(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('DayBookEntryDeleteController',DayBookEntryDeleteController);

    DayBookEntryDeleteController.$inject = ['$uibModalInstance', 'entity', 'DayBookEntry'];

    function DayBookEntryDeleteController($uibModalInstance, entity, DayBookEntry) {
        var vm = this;
        vm.dayBookEntry = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            DayBookEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
