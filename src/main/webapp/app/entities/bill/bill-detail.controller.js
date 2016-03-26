(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BillDetailController', BillDetailController);

    BillDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Bill', 'BillItem', 'Vehicle'];

    function BillDetailController($scope, $rootScope, $stateParams, entity, Bill, BillItem, Vehicle) {
        var vm = this;
        vm.bill = entity;
        vm.load = function (id) {
            Bill.get({id: id}, function(result) {
                vm.bill = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:billUpdate', function(event, result) {
            vm.bill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
