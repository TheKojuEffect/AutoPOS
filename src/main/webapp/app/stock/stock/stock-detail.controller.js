(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('StockDetailController', StockDetailController);

    StockDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Stock', 'Item'];

    function StockDetailController($scope, $rootScope, $stateParams, entity, Stock, Item) {
        var vm = this;
        vm.stock = entity;
        vm.load = function (id) {
            Stock.get({id: id}, function(result) {
                vm.stock = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:stockUpdate', function(event, result) {
            vm.stock = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
