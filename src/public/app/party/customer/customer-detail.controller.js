(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Customer'];

    function CustomerDetailController($scope, $rootScope, $stateParams, entity, Customer) {
        var vm = this;
        vm.customer = entity;
        vm.load = function (id) {
            Customer.get({id: id}, function(result) {
                vm.customer = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
