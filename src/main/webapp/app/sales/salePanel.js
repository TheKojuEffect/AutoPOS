(function () {
    'use strict';

    class SalePanelCtrl {

        constructor() {
            this.lines = [
                {
                    code: 'ABC',
                    name: 'First'
                },
                {
                    code: 'MNO',
                    name: 'Second'
                }
            ];

            this.datePickerOptions = {
                maxDate: new Date()
            };

            this.datePickerOpenStatus = {};
            this.datePickerOpenStatus.date = false;
        }

        openCalendar(date) {
            this.datePickerOpenStatus[date] = true;
        }
    }


    angular.module('autopos')
        .component('salePanel', {
            templateUrl: 'app/sales/salePanel.html',
            controller: SalePanelCtrl
        });

})();