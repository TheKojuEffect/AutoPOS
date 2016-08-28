(function () {
    'use strict';

    class SalePanelController {

        constructor() {
            this.datePickerOptions = {
                maxDate: new Date()
            };
            this.datePickerOpenStatus = {};
            this.datePickerOpenStatus.date = false;

            this.saleLineEntryApi = null;
        }

        openCalendar(date) {
            this.datePickerOpenStatus[date] = true;
        }

        acceptSaleLine(saleLine) {
            this.sale.lines.push(saleLine);
        }

        editSaleLine(saleLine) {
            this.saleLineEntryApi.setSaleLine(saleLine);
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