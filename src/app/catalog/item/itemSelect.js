(function (angular) {
    'use strict';

    class ItemSelectController {

        constructor(Item) {
            this.itemService = Item;
            this.selectedItem = null;
            this.setSelectedItem = (item) => this.selectedItem = item;

        }

        $onInit() {
            this.api = this.api || {};
            this.api.setSelectedItem = this.setSelectedItem;
        }

        searchItem(term) {
            return this.itemService.query({
                q: term
            }).$promise;
        }

        onItemSelect(selectedItem) {
            this.onSelect({'item': selectedItem});
        }

        itemTitle(item) {
            if (angular.isObject(item)) {
                return '[' + item.code + '] ' + item.name;
            }
        }
    }

    angular.module('autopos')
        .component('itemSelect', {
            templateUrl: 'app/catalog/item/itemSelect.html',
            controller: ItemSelectController,
            bindings: {
                api: '=',
                onSelect: '&'
            }
        });

})(window.angular);