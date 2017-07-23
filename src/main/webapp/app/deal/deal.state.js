(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('deal', {
            parent: 'app',
            url: '/deal/contract/{contractId}',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/deal/deal.html',
                    controller: 'DealController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            	translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
            		$translatePartialLoader.addPart('contract');
                    return $translate.refresh();
                }],
            	contract: ['$stateParams', 'Contract', function($stateParams, Contract) {
                    return Contract.get({id : $stateParams.contractId}).$promise;
                }]
            }
        });
    }
})();
