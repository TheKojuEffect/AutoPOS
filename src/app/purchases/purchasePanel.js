(function (angular) {
    'use strict';

    class PurchasePanelController {

        constructor($state, PurchaseService, PurchaseLineService, Vendor) {
            this.$state = $state;
            this.purchaseService = PurchaseService;
            this.purchaseLineService = PurchaseLineService;
            this.datePickerOptions = {
                maxDate: new Date()
            };
            this.datePickerOpenStatus = {};
            this.datePickerOpenStatus.date = false;

            this.purchaseLineEntryApi = null;
            this.vendors = Vendor.query();

        }

        openCalendar(date) {
            this.datePickerOpenStatus[date] = true;
        }

        acceptPurchaseLine(purchaseLine) {
            purchaseLine.purchase = {id: this.purchase.id};
            if (purchaseLine.id) {

                const lineIndex = _.findIndex(this.purchase.lines,
                    line => line.id === purchaseLine.id);

                this.purchaseLineService.update(
                    {
                        purchaseId: this.purchase.id,
                        purchaseLineId: purchaseLine.id
                    },
                    purchaseLine,
                    (line) => this.purchase.lines.splice(lineIndex, 1, line)
                );
            } else {
                this.purchaseLineService.save(
                    {purchaseId: this.purchase.id},
                    purchaseLine,
                    (line) => this.purchase.lines.push(line)
                );
            }
        }

        deletePurchaseLine(purchaseLine) {

            const lineIndex = _.findIndex(this.purchase.lines,
                line => line.id === purchaseLine.id);

            this.purchaseLineService.remove({
                purchaseId: this.purchase.id,
                purchaseLineId: purchaseLine.id
            }).$promise
                .then(() => this.purchase.lines.splice(lineIndex, 1));
        }

        updatePurchase() {
            this.purchaseService.update(
                {id: this.purchase.id},
                this.purchase,
                () => this.$state.go('purchases.list'));
        }

        editPurchaseLine(purchaseLine) {
            this.purchaseLineEntryApi.setPurchaseLine(purchaseLine);
        }

        get subTotal() {
            return _.reduce(this.purchase.lines, (sum, line) => line.amount + sum, 0);
        }

        get total() {
            return this.subTotal - this.purchase.discount;
        }
    }


    angular.module('autopos')
        .component('purchasePanel', {
            templateUrl: 'app/purchases/purchasePanel.html',
            controller: PurchasePanelController,
            bindings: {
                purchase: '<'
            }
        });

})(window.angular);