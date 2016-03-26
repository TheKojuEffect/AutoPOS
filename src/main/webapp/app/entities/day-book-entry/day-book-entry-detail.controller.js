(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('DayBookEntryDetailController', DayBookEntryDetailController);

    DayBookEntryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'DayBookEntry'];

    function DayBookEntryDetailController($scope, $rootScope, $stateParams, entity, DayBookEntry) {
        var vm = this;
        vm.dayBookEntry = entity;
        vm.load = function (id) {
            DayBookEntry.get({id: id}, function(result) {
                vm.dayBookEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:dayBookEntryUpdate', function(event, result) {
            vm.dayBookEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
