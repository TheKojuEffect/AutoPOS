(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('BrandDetailController', BrandDetailController);

    BrandDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Brand'];

    function BrandDetailController($scope, $rootScope, $stateParams, entity, Brand) {
        var vm = this;
        vm.brand = entity;
        vm.load = function (id) {
            Brand.get({id: id}, function(result) {
                vm.brand = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:brandUpdate', function(event, result) {
            vm.brand = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
