(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('brand', {
            parent: 'catalog',
            url: '/brand?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.brand.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/catalog/brand/brands.html',
                    controller: 'BrandController',
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
                    $translatePartialLoader.addPart('brand');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('brand-detail', {
            parent: 'catalog',
            url: '/brand/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.brand.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/catalog/brand/brand-detail.html',
                    controller: 'BrandDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('brand');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Brand', function($stateParams, Brand) {
                    return Brand.get({id : $stateParams.id});
                }]
            }
        })
        .state('brand.new', {
            parent: 'brand',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/catalog/brand/brand-dialog.html',
                    controller: 'BrandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('brand', null, { reload: true });
                }, function() {
                    $state.go('brand');
                });
            }]
        })
        .state('brand.edit', {
            parent: 'brand',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/catalog/brand/brand-dialog.html',
                    controller: 'BrandDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Brand', function(Brand) {
                            return Brand.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('brand', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('brand.delete', {
            parent: 'brand',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/catalog/brand/brand-delete-dialog.html',
                    controller: 'BrandDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Brand', function(Brand) {
                            return Brand.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('brand', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
