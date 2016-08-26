(function () {
    'use strict';

    class SalePanelController {

        constructor() {
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
            controller: SalePanelController,
            bindings: {
                sale: '<'
            }
        });

})();