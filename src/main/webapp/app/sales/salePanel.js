(function () {
    angular.module('autopos')
        .component('salePanel', {
            templateUrl: 'app/sales/salePanel.html',
            controller: SalePanelCtrl
        });

    function SalePanelCtrl() {

        var ctrl = this;

        ctrl.lines = [
            {
                code: 'ABC',
                name: 'First'
            },
            {
                code: 'MNO',
                name: 'Second'
            }
        ]
    }
})();