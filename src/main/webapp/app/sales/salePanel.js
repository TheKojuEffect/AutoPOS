(function () {
    'use strict';

    class SalePanelCtrl {

        constructor() {
            this.lines = [
                {
                    item: {
                        code: 'ABC',
                        name: 'First'
                    },
                    amount: 1450
                },
                {
                    item: {
                        code: 'XYZ',
                        name: 'Tail'
                    },
                    amount: 2000
                }];

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