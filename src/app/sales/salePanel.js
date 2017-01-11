(function (angular) {
    'use strict';

    class SalePanelController {

        constructor($state, $stateParams, $uibModal,  SaleService, SaleLineService) {
            this.$state = $state;
            this.$stateParams = $stateParams;
            this.$uibModal = $uibModal;
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

        gotoSaleList() {
            this.$state.go('sales.pending')
        }

        deleteSale() {
            this.$uibModal.open({
                templateUrl: 'app/sales/sale-delete-dialog.html',
                controller: 'SaleDeleteController',
                controllerAs: 'vm',
                bindToController: true,
                size: 'md',
                resolve: {
                    sale: ['SaleService', '$stateParams', function (SaleService, $stateParams) {
                        return SaleService.get({id: $stateParams.id});
                    }]
                }
            }).result.then(
                () => this.$state.go('sales.pending'),
                () => {});
        }

        get subTotal() {
            return _.sumBy(this.sale.lines, 'amount');
        }

        get total() {
            return this.subTotal - this.sale.discount;
        }

        get hasLines() {
            return this.sale.lines && this.sale.lines.length > 0;
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
