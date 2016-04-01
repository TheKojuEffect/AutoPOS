(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BillItemDetailController', BillItemDetailController);

    BillItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'BillItem', 'Item', 'Bill'];

    function BillItemDetailController($scope, $rootScope, $stateParams, entity, BillItem, Item, Bill) {
        var vm = this;
        vm.billItem = entity;
        vm.load = function (id) {
            BillItem.get({id: id}, function(result) {
                vm.billItem = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:billItemUpdate', function(event, result) {
            vm.billItem = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
