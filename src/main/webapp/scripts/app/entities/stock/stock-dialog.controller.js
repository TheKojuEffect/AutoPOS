'use strict';

angular.module('autoposApp').controller('StockDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Stock', 'Item',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Stock, Item) {

        $scope.stock = entity;
        $scope.items = Item.query({filter: 'stock-is-null'});
        $q.all([$scope.stock.$promise, $scope.items.$promise]).then(function() {
            if (!$scope.stock.item || !$scope.stock.item.id) {
                return $q.reject();
            }
            return Item.get({id : $scope.stock.item.id}).$promise;
        }).then(function(item) {
            $scope.items.push(item);
        });
        $scope.load = function(id) {
            Stock.get({id : id}, function(result) {
                $scope.stock = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autoposApp:stockUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.stock.id != null) {
                Stock.update($scope.stock, onSaveSuccess, onSaveError);
            } else {
                Stock.save($scope.stock, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
