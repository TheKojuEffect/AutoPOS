(function() {
    'use strict';

    angular
        .module('autopos')
        .controller('PhoneNumberDeleteController',PhoneNumberDeleteController);

    PhoneNumberDeleteController.$inject = ['$uibModalInstance', 'entity', 'PhoneNumber'];

    function PhoneNumberDeleteController($uibModalInstance, entity, PhoneNumber) {
        var vm = this;
        vm.phoneNumber = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            PhoneNumber.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
