(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('item', {
            parent: 'catalog',
            url: '/items?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.item.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/catalog/item/items.html',
                    controller: 'ItemController',
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
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('item');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('item-detail', {
            parent: 'catalog',
            url: '/items/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.item.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/catalog/item/item-detail.html',
                    controller: 'ItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('item');
                    return $translate.refresh();
                }],
                item: ['$stateParams', 'Item', function($stateParams, Item) {
                    return Item.getWithDetail({id : $stateParams.id});
                }]
            }
        })
        .state('item.new', {
            parent: 'item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
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
                }).result.then(function() {
                    $state.go('item', null, { reload: true });
                }, function() {
                    $state.go('item');
                });
            }]
        })
        .state('item.edit', {
            parent: 'item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/catalog/item/item-dialog.html',
                    controller: 'ItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        item: ['Item', function(Item) {
                            return Item.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('item', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('item.delete', {
            parent: 'item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/catalog/item/item-delete-dialog.html',
                    controller: 'ItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Item', function(Item) {
                            return Item.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('item', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
