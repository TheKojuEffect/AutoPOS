(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PaymentDetailController', PaymentDetailController);

    PaymentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Payment', 'Vendor'];

    function PaymentDetailController($scope, $rootScope, $stateParams, entity, Payment, Vendor) {
        var vm = this;
        vm.payment = entity;
        vm.load = function (id) {
            Payment.get({id: id}, function(result) {
                vm.payment = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:paymentUpdate', function(event, result) {
            vm.payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
