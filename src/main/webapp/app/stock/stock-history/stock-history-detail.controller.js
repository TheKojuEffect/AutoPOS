(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('StockHistoryDetailController', StockHistoryDetailController);

    StockHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'StockHistory', 'Item'];

    function StockHistoryDetailController($scope, $rootScope, $stateParams, entity, StockHistory, Item) {
        var vm = this;
        vm.stockHistory = entity;
        vm.load = function (id) {
            StockHistory.get({id: id}, function(result) {
                vm.stockHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:stockHistoryUpdate', function(event, result) {
            vm.stockHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
