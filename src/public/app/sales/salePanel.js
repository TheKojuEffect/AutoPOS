(function (angular) {
    'use strict';

    class SalePanelController {

        constructor(SaleService, SaleLineService, $state) {
            this.$state = $state;
            this.saleService = SaleService;
            this.saleLineService = SaleLineService;
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
            saleLine.sale = {id: this.sale.id};
            if (saleLine.id) {

                const lineIndex = _.findIndex(this.sale.lines,
                    line => line.id === saleLine.id);

                this.saleLineService.update(
                    {
                        saleId: this.sale.id,
                        saleLineId: saleLine.id
                    },
                    saleLine,
                    (line) => this.sale.lines.splice(lineIndex, 1, line)
                );
            } else {
                this.saleLineService.save(
                    {saleId: this.sale.id},
                    saleLine,
                    (line) => this.sale.lines.push(line)
                );
            }
        }

        deleteSaleLine(saleLine) {

            const lineIndex = _.findIndex(this.sale.lines,
                line => line.id === saleLine.id);

            this.saleLineService.remove({
                saleId: this.sale.id,
                saleLineId: saleLine.id
            }).$promise
                .then(() => this.sale.lines.splice(lineIndex, 1));
        }

        updateSale() {
            this.saleService.update(
                {id: this.sale.id},
                this.sale,
                () => this.$state.go('sales.pending'));
        }

        editSaleLine(saleLine) {
            this.saleLineEntryApi.setSaleLine(saleLine);
        }

        get subTotal() {
            return _.sumBy(this.sale.lines, 'amount');
        }

        get total() {
            return this.subTotal - this.sale.discount;
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

})(window.angular);
