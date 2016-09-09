(function () {
    'use strict';

    angular.module('autopos')
        .config(stateConfig);

    function stateConfig($stateProvider) {
        $stateProvider
            .state('purchases', {
                abstract: true,
                parent: 'app',
                data: {
                    authorities: ['ROLE_USER']
                },
                resolve: {
                    translatePartialLoader: function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('purchase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }
                }
            })
            .state('purchases.list', {
                parent: 'purchases',
                url: '/purchases?page&sort&search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.purchase.purchases'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/purchases/purchaseList.html',
                        controller: 'PurchaseListCtrl',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,desc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
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
            .state('purchases.detail', {
                parent: 'purchases',
                url: '/purchases/:id',
                data: {
                    pageTitle: 'autopos.purchase.purchaseDetail'
                },
                views: {
                    'content@': {
                        template: '<purchase-panel purchase="$resolve.purchase"></purchase-panel>'
                    }
                },
                resolve: {
                    purchase: function ($stateParams, PurchaseService) {
                        return PurchaseService.get({id: $stateParams.id});
                    },
                    translatePartialLoader: function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('purchaseLine');
                        return $translate.refresh();
                    }
                }
            });
    }

})();
