'use strict';

angular.module('autoposApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vendor', {
                parent: 'entity',
                url: '/vendors',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.vendor.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vendor/vendors.html',
                        controller: 'VendorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('vendor');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('vendor.detail', {
                parent: 'entity',
                url: '/vendor/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'autoposApp.vendor.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vendor/vendor-detail.html',
                        controller: 'VendorDetailController'
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
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vendor/vendor-dialog.html',
                        controller: 'VendorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    remarks: null,
                                    phoneNumbers: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('vendor', null, { reload: true });
                    }, function() {
                        $state.go('vendor');
                    })
                }]
            })
            .state('vendor.edit', {
                parent: 'vendor',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vendor/vendor-dialog.html',
                        controller: 'VendorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Vendor', function(Vendor) {
                                return Vendor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vendor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('vendor.delete', {
                parent: 'vendor',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/vendor/vendor-delete-dialog.html',
                        controller: 'VendorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Vendor', function(Vendor) {
                                return Vendor.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('vendor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
