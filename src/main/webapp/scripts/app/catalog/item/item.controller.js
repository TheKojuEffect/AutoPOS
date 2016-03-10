'use strict';

angular.module('autopos')
    .controller('ItemController', function ($state, Item) {

        var ctrl = this;

        ctrl.items = [];
        ctrl.predicate = 'id';
        ctrl.reverse = true;
        ctrl.page = 1;
        ctrl.itemsPerPage = 20;
        ctrl.itemNameFilter = '';


        ctrl.loadAll = loadAll;
        ctrl.loadPage = loadPage;
        ctrl.refresh = refresh;
        ctrl.clear = clear;

        ctrl.loadAll();

        function loadAll() {
            var requestParams = {
                page: ctrl.page - 1,
                size: ctrl.itemsPerPage,
                sort: [ctrl.predicate + ',' + (ctrl.reverse ? 'asc' : 'desc'), 'id']
            };

            if (ctrl.itemNameFilter) {
                requestParams.name = '*' + ctrl.itemNameFilter + '*';
            }

            Item.query(requestParams, function (result, headers) {
                ctrl.totalItems = headers('X-Total-Count');
                ctrl.items = result;
            });
        }

        function loadPage(page) {
            ctrl.page = page;
            ctrl.loadAll();
        }

        function refresh() {
            ctrl.loadAll();
            ctrl.clear();
        }

        function clear() {
            ctrl.item = {
                code: null,
                name: null,
                description: null,
                remarks: null,
                markedPrice: null,
                id: null
            };
        }

    });
