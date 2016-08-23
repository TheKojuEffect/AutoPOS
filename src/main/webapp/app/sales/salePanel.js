(function () {
    'use strict';

    class SalePanelCtrl {

        constructor() {
            this.lines = [];

            this.datePickerOptions = {
                maxDate: new Date()
            };

            this.datePickerOpenStatus = {};
            this.datePickerOpenStatus.date = false;
        }

        openCalendar(date) {
            this.datePickerOpenStatus[date] = true;
        }

        addSaleLine(saleLine) {
            this.lines.push(saleLine);
        }
    }


    angular.module('autopos')
        .component('salePanel', {
            templateUrl: 'app/sales/salePanel.html',
            controller: SalePanelCtrl
        });

})();