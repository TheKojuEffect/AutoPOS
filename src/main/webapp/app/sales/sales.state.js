(function () {
    'use strict';

    angular.module('autopos')
        .config(stateConfig);

    function stateConfig($stateProvider) {
        $stateProvider
            .state('sales', {
                abstract: true,
                parent: 'app',
                resolve: {
                    translatePartialLoader: function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sales');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }
                }
            })
            .state('sales.new', {
                parent: 'sales',
                url: '/sales/new',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.sales.newSale'
                },
                views: {
                    'content@': {
                        template: '<h2>New Sale Interface</h2>'
                    }
                }
            })
            .state('sales.list', {
                abstract: true,
                parent: 'sales',
                views: {
                    'content@': {
                        templateUrl: 'app/sales/sales.html',
                        controller: 'SalesController'
                    }
                }
            })
            .state('sales.pending', {
                parent: 'sales.list',
                url: '/sales/pending?page&sort&search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.sales.pendingSales'
                },
                views: {
                    'content-tab': {
                        templateUrl: 'app/sales/saleList.html',
                        controller: 'SaleListCtrl',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    saleStatus: function () {
                        return 'PENDING';
                    },
                    pagingParams: function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }
                }
            })
            .state('sales.completed', {
                parent: 'sales.list',
                url: '/sales/completed?page&sort&search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.sales.completedSales'
                },
                views: {
                    'content-tab': {
                        templateUrl: 'app/sales/saleList.html',
                        controller: 'SaleListCtrl',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    saleStatus: function () {
                        return 'COMPLETED';
                    },
                    pagingParams: function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }
                }
            });
    }

})();
