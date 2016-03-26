(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PriceHistoryDetailController', PriceHistoryDetailController);

    PriceHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'PriceHistory', 'Item'];

    function PriceHistoryDetailController($scope, $rootScope, $stateParams, entity, PriceHistory, Item) {
        var vm = this;
        vm.priceHistory = entity;
        vm.load = function (id) {
            PriceHistory.get({id: id}, function(result) {
                vm.priceHistory = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:priceHistoryUpdate', function(event, result) {
            vm.priceHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
