'use strict';

angular.module('autopos').controller('ItemDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Item', 'Category', 'Brand', 'Tag',
        function($scope, $stateParams, $uibModalInstance, entity, Item, Category, Brand, Tag) {

        $scope.item = entity;
        $scope.categorys = Category.query();
        $scope.brands = Brand.query();
        $scope.tags = Tag.query();
        $scope.load = function(id) {
            Item.get({id : id}, function(result) {
                $scope.item = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('autopos:itemUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.item.id != null) {
                Item.update($scope.item, onSaveSuccess, onSaveError);
            } else {
                Item.save($scope.item, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
