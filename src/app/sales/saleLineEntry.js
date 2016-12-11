(function () {
    'use strict';

    class ItemEntryController {

        constructor() {
            this.itemSelectApi = null;

            this.setSaleLine = saleLine => {
                this.saleLine = Object.assign({}, saleLine);
                this.itemSelectApi.setSelectedItem(saleLine.item)
            };
        }

        $onInit() {
            this.api = this.api || {};
            this.api.setSaleLine = this.setSaleLine;
            this.saleLine = new SaleLine();
        }

        itemSelected(item) {
            this.saleLine.item = item;
            this.saleLine.rate = item.markedPrice;
        }

        acceptSaleLine() {
            this.onAccept({saleLine: this.saleLine});
            this.reset();
        }

        reset() {
            this.saleLine = new SaleLine();
            this.itemSelectApi.setSelectedItem(null);
        }

        deleteSaleLine() {
            const deleteConfirmation = confirm("Are you sure you want to delete this sale line?");
            if (deleteConfirmation) {
                this.onDelete({saleLine: this.saleLine});
                this.reset();
            }
        }
    }

    angular.module('autopos')
        .component('saleLineEntry', {
            templateUrl: 'app/sales/saleLineEntry.html',
            controller: ItemEntryController,
            bindings: {
                api: '=',
                saleId: '<',
                onAccept: '&',
                onDelete: '&'
            }
        });
})();