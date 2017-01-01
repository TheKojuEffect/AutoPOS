(function () {
    'use strict';

    angular
        .module('autopos')
        .controller('ItemDetailController', ItemDetailController);

    ItemDetailController.$inject = ['$scope', '$rootScope', '$uibModal', 'Item', 'item'];

    function ItemDetailController($scope, $rootScope, $uibModal, Item, item) {
        const vm = this;
        vm.item = item;
        vm.load = load;
        vm.showCostPrices = showCostPrices;

        const unsubscribe = $rootScope.$on('autopos:itemUpdate', function (event, result) {
            vm.item = result;
        });
        $scope.$on('$destroy', unsubscribe);


        function load(id) {
            Item.get({id: id}, function (result) {
                vm.item = result;
            });
        }

        function showCostPrices() {
            $uibModal.open({
                component: 'costPricesComponent',
                resolve: {
                    item: function() {
                        return vm.item;
                    },
                    costPrices: function() {
                        return Item.costPrices({id: vm.item.id}).$promise;
                    }
                }
            })
        }
    }
})();
