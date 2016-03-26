(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('ReceiptDetailController', ReceiptDetailController);

    ReceiptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Receipt', 'Customer'];

    function ReceiptDetailController($scope, $rootScope, $stateParams, entity, Receipt, Customer) {
        var vm = this;
        vm.receipt = entity;
        vm.load = function (id) {
            Receipt.get({id: id}, function(result) {
                vm.receipt = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:receiptUpdate', function(event, result) {
            vm.receipt = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
