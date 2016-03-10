'use strict';

angular.module('autopos')
    .config(function ($stateProvider) {
        $stateProvider
            .state('item', {
                parent: 'catalog',
                url: '/items',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.item.home.title'
                },
                views: {
                    'content-tab': {
                        templateUrl: 'scripts/app/catalog/item/items.html',
                        controller: 'ItemController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('item');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('item.detail', {
                parent: 'catalog',
                url: '/item/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autopos.item.detail.title'
                },
                views: {
                    'content-tab': {
                        templateUrl: 'scripts/app/catalog/item/item-detail.html',
                        controller: 'ItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('item');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Item', function($stateParams, Item) {
                        return Item.get({id : $stateParams.id});
                    }]
                }
            })
            .state('item.new', {
                parent: 'item',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/catalog/item/item-dialog.html',
                        controller: 'ItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
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
                    }).result.then(function(result) {
                        $state.go('item', null, { reload: true });
                    }, function() {
                        $state.go('item');
                    })
                }]
            })
            .state('item.edit', {
                parent: 'item.detail',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/catalog/item/item-dialog.html',
                        controller: 'ItemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Item', function(Item) {
                                return Item.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('item', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('item.delete', {
                parent: 'item.detail',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/catalog/item/item-delete-dialog.html',
                        controller: 'ItemDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Item', function(Item) {
                                return Item.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('item', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
