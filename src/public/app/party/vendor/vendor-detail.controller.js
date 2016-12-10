(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('VendorDetailController', VendorDetailController);

    VendorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Vendor'];

    function VendorDetailController($scope, $rootScope, $stateParams, entity, Vendor) {
        var vm = this;
        vm.vendor = entity;
        vm.load = function (id) {
            Vendor.get({id: id}, function(result) {
                vm.vendor = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:vendorUpdate', function(event, result) {
            vm.vendor = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
