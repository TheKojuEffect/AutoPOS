(function () {
    'use strict';

    class ItemEntryCtrl {

        itemSelected(item) {
            console.log(item);
        }
    }

    angular.module('autopos')
        .component('saleLineEntry', {
            templateUrl: 'app/sales/saleLineEntry.html',
            controller: ItemEntryCtrl
        });

})();
