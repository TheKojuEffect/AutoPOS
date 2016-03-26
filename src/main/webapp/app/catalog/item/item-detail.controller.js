(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('ItemDetailController', ItemDetailController);

    ItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Item', 'Category', 'Brand', 'Tag'];

    function ItemDetailController($scope, $rootScope, $stateParams, entity, Item, Category, Brand, Tag) {
        var vm = this;
        vm.item = entity;
        vm.load = function (id) {
            Item.get({id: id}, function(result) {
                vm.item = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:itemUpdate', function(event, result) {
            vm.item = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
