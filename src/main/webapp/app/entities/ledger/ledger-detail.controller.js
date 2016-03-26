(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('LedgerDetailController', LedgerDetailController);

    LedgerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Ledger', 'Customer'];

    function LedgerDetailController($scope, $rootScope, $stateParams, entity, Ledger, Customer) {
        var vm = this;
        vm.ledger = entity;
        vm.load = function (id) {
            Ledger.get({id: id}, function(result) {
                vm.ledger = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:ledgerUpdate', function(event, result) {
            vm.ledger = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
