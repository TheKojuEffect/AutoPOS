(function () {
    'use strict';

    class ItemEntryController {

        constructor() {
            this.itemSelectApi = null;

            this.setPurchaseLine = purchaseLine => {
                this.purchaseLine = Object.assign({}, purchaseLine);
                this.itemSelectApi.setSelectedItem(purchaseLine.item)
            };
        }

        $onInit() {
            this.api = this.api || {};
            this.api.setPurchaseLine = this.setPurchaseLine;
            this.purchaseLine = new PurchaseLine();
        }

        itemSelected(item) {
            this.purchaseLine.item = item;
        }

        acceptPurchaseLine() {
            this.onAccept({purchaseLine: this.purchaseLine});
            this.reset();
        }

        reset() {
            this.purchaseLine = new PurchaseLine();
            this.itemSelectApi.setSelectedItem(null);
        }

        deletePurchaseLine() {
            const deleteConfirmation = confirm("Are you sure you want to delete this purchase line?");
            if (deleteConfirmation) {
                this.onDelete({purchaseLine: this.purchaseLine});
                this.reset();
            }
        }
    }

    angular.module('autopos')
        .component('purchaseLineEntry', {
            templateUrl: 'app/purchases/purchaseLineEntry.html',
            controller: ItemEntryController,
            bindings: {
                api: '=',
                purchaseId: '<',
                onAccept: '&',
                onDelete: '&'
            }
        });
})();
