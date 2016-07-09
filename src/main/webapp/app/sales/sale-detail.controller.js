(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('SaleInvoiceDetailController', SaleInvoiceDetailController);

    SaleInvoiceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SaleInvoice', 'SaleInvoiceItem', 'Vehicle'];

    function SaleInvoiceDetailController($scope, $rootScope, $stateParams, entity, SaleInvoice, SaleInvoiceItem, Vehicle) {
        var vm = this;
        vm.bill = entity;
        vm.load = function (id) {
            SaleInvoice.get({id: id}, function(result) {
                vm.bill = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:billUpdate', function(event, result) {
            vm.bill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
