(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Category'];

    function CategoryDetailController($scope, $rootScope, $stateParams, entity, Category) {
        var vm = this;
        vm.category = entity;
        vm.load = function (id) {
            Category.get({id: id}, function(result) {
                vm.category = result;
            });
        };
        var unsubscribe = $rootScope.$on('autopos:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
