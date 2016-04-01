(function() {
    'use strict';

    angular
        .module('autopos')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vehicle', {
            parent: 'party',
            url: '/vehicle?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.vehicle.home.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/party/vehicle/vehicles.html',
                    controller: 'VehicleController',
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
                    $translatePartialLoader.addPart('vehicle');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('vehicle-detail', {
            parent: 'party',
            url: '/vehicle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'autopos.vehicle.detail.title'
            },
            views: {
                'content-tab': {
                    templateUrl: 'app/party/vehicle/vehicle-detail.html',
                    controller: 'VehicleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('vehicle');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Vehicle', function($stateParams, Vehicle) {
                    return Vehicle.get({id : $stateParams.id});
                }]
            }
        })
        .state('vehicle.new', {
            parent: 'vehicle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/party/vehicle/vehicle-dialog.html',
                    controller: 'VehicleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number: null,
                                remarks: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vehicle', null, { reload: true });
                }, function() {
                    $state.go('vehicle');
                });
            }]
        })
        .state('vehicle.edit', {
            parent: 'vehicle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/party/vehicle/vehicle-dialog.html',
                    controller: 'VehicleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vehicle', function(Vehicle) {
                            return Vehicle.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('vehicle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vehicle.delete', {
            parent: 'vehicle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/party/vehicle/vehicle-delete-dialog.html',
                    controller: 'VehicleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vehicle', function(Vehicle) {
                            return Vehicle.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('vehicle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
