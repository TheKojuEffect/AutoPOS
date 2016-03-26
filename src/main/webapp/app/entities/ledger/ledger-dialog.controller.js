(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('LedgerDialogController', LedgerDialogController);

    LedgerDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Ledger', 'Customer'];

    function LedgerDialogController ($scope, $stateParams, $uibModalInstance, $q, entity, Ledger, Customer) {
        var vm = this;
        vm.ledger = entity;
        vm.partys = Customer.query({filter: 'ledger-is-null'});
        $q.all([vm.ledger.$promise, vm.partys.$promise]).then(function() {
            if (!vm.ledger.party || !vm.ledger.party.id) {
                return $q.reject();
            }
            return Customer.get({id : vm.ledger.party.id}).$promise;
        }).then(function(party) {
            vm.partys.push(party);
        });
        vm.load = function(id) {
            Ledger.get({id : id}, function(result) {
                vm.ledger = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:ledgerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.ledger.id !== null) {
                Ledger.update(vm.ledger, onSaveSuccess, onSaveError);
            } else {
                Ledger.save(vm.ledger, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
