(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('ContractDetailController', ContractDetailController);

    ContractDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Contract'];

    function ContractDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Contract) {
        var vm = this;

        vm.contract = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('contractcloudApp:contractUpdate', function(event, result) {
            vm.contract = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
