(function () {
    'use strict';

    class ItemEntryCtrl {

        constructor() {
            this.saleLine = new SaleLine();
        }

        itemSelected(item) {
            this.saleLine.item = item;
            this.saleLine.rate = item.markedPrice;
        }
    }

    angular.module('autopos')
        .component('saleLineEntry', {
            templateUrl: 'app/sales/saleLineEntry.html',
            controller: ItemEntryCtrl
        });

})();
