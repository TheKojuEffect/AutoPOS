(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('LedgerEntryDetailController', LedgerEntryDetailController);

    LedgerEntryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'LedgerEntry', 'Ledger'];

    function LedgerEntryDetailController($scope, $rootScope, $stateParams, entity, LedgerEntry, Ledger) {
        var vm = this;
        vm.ledgerEntry = entity;
        vm.load = function (id) {
            LedgerEntry.get({id: id}, function(result) {
                vm.ledgerEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:ledgerEntryUpdate', function(event, result) {
            vm.ledgerEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
