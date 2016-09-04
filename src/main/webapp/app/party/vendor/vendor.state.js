(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vendor', {
            parent: 'party',
            url: '/vendors?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.vendor.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/party/vendor/vendors.html',
                    controller: 'VendorController',
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
                    $translatePartialLoader.addPart('vendor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('vendor-detail', {
            parent: 'party',
            url: '/vendors/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.vendor.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/party/vendor/vendor-detail.html',
                    controller: 'VendorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vendor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Vendor', function($stateParams, Vendor) {
                    return Vendor.get({id : $stateParams.id});
                }]
            }
        })
        .state('vendor.new', {
            parent: 'vendor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/party/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                remarks: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: true });
                }, function() {
                    $state.go('vendor');
                });
            }]
        })
        .state('vendor.edit', {
            parent: 'vendor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/party/vendor/vendor-dialog.html',
                    controller: 'VendorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vendor.delete', {
            parent: 'vendor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/party/vendor/vendor-delete-dialog.html',
                    controller: 'VendorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vendor', function(Vendor) {
                            return Vendor.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendor', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
