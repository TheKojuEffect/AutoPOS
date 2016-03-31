(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PhoneNumberDeleteController',PhoneNumberDeleteController);

    PhoneNumberDeleteController.$inject = ['$uibModalInstance', 'entity', 'Phone'];

    function PhoneNumberDeleteController($uibModalInstance, entity, Phone) {
        var vm = this;
        vm.phone = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Phone.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
