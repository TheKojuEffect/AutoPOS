(function (angular) {
    'use strict';

    class ItemSelectController {

        constructor(Item, $uibModal) {
            this.itemService = Item;
            this.selectedItem = null;
            this.setSelectedItem = (item) => this.selectedItem = item;
            this.$uibModal = $uibModal;
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

        createNewItem() {
            this.$uibModal.open({
                templateUrl: 'app/catalog/item/item-dialog.html',
                controller: 'ItemDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    item: function () {
                        return {
                            code: null,
                            name: null,
                            description: null,
                            remarks: null,
                            markedPrice: null,
                            id: null
                        };
                    }
                }
            });
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