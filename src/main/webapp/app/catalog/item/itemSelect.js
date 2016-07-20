(function () {
    'use strict';

    angular.module('autopos')
        .component('itemSelect', {
            templateUrl: 'app/catalog/item/itemSelect.html',
            controller: ItemSelectCtrl,
            bindings: {
                onSelect: '&'
            }
        });

    function ItemSelectCtrl(Item) {
        var ctrl = this;
        ctrl.searchItem = searchItem;
        ctrl.itemTitle = itemTitle;
        ctrl.onItemSelect = onItemSelect;

        function searchItem(term) {
            return Item.query({
                name: '*' + term + '*',
                code: term + '*'
            }).$promise;
        }

        function itemTitle(item) {
            if (angular.isObject(item)) {
                return '[' + item.code + '] ' + item.name;
            }
        }

        function onItemSelect(selectedItem) {
            ctrl.onSelect({'item': selectedItem});
        }

    }

})();