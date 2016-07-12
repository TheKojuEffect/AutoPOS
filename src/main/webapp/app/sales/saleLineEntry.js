(function () {
    angular.module('autopos')
        .component('saleLineEntry', {
            templateUrl: 'app/sales/saleLineEntry.html',
            controller: ItemEntryCtrl
        });

    function ItemEntryCtrl() {
        var ctrl = this;
        ctrl.datePickerOptions = {
            maxDate: new Date()
        };
        ctrl.datePickerOpenStatus = {};
        ctrl.datePickerOpenStatus.date = false;

        ctrl.openCalendar = function (date) {
            ctrl.datePickerOpenStatus[date] = true;
        };
    }

})();