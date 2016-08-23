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

        acceptSaleLine() {
            this.onAccept({saleLine: this.saleLine});
            this.saleLine = new SaleLine();
        }
    }

    angular.module('autopos')
        .component('saleLineEntry', {
            templateUrl: 'app/sales/saleLineEntry.html',
            controller: ItemEntryCtrl,
            bindings: {
                onAccept: '&'
            }
        });

})();
