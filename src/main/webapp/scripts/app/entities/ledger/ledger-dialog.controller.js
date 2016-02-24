'use strict';

angular.module('autopos').controller('LedgerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Ledger', 'Customer',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Ledger, Customer) {

        $scope.ledger = entity;
        $scope.partys = Customer.query({filter: 'ledger-is-null'});
        $q.all([$scope.ledger.$promise, $scope.partys.$promise]).then(function() {
            if (!$scope.ledger.party || !$scope.ledger.party.id) {
                return $q.reject();
            }
            return Customer.get({id : $scope.ledger.party.id}).$promise;
        }).then(function(party) {
            $scope.partys.push(party);
        });
        $scope.load = function(id) {
            Ledger.get({id : id}, function(result) {
                $scope.ledger = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:ledgerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ledger.id != null) {
                Ledger.update($scope.ledger, onSaveSuccess, onSaveError);
            } else {
                Ledger.save($scope.ledger, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
