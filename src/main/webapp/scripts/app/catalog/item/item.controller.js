'use strict';

angular.module('autopos')
    .controller('ItemController', function ($state, Item) {

        var vm = this;

        vm.items = [];
        vm.predicate = 'id';
        vm.reverse = true;
        vm.page = 1;
        vm.itemsPerPage = 20;
        vm.itemNameFilter = '';


        vm.loadAll = loadAll;
        vm.loadPage = loadPage;
        vm.refresh = refresh;
        vm.clear = clear;

        vm.loadAll();

        function loadAll() {
            var requestParams = {
                page: vm.page - 1,
                size: vm.itemsPerPage,
                sort: [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'), 'id']
            };

            if (vm.itemNameFilter) {
                requestParams.name = '*' + vm.itemNameFilter + '*';
            }

            Item.query(requestParams, function (result, headers) {
                vm.totalItems = headers('X-Total-Count');
                vm.items = result;
            });
        }

        function loadPage(page) {
            vm.page = page;
            vm.loadAll();
        }

        function refresh() {
            vm.loadAll();
            vm.clear();
        }

        function clear() {
            vm.item = {
                code: null,
                name: null,
                description: null,
                remarks: null,
                markedPrice: null,
                id: null
            };
        }

    });
