(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('ContractController', ContractController);

    ContractController.$inject = ['DataUtils', 'Contract'];

    function ContractController(DataUtils, Contract) {

        var vm = this;

        vm.contracts = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Contract.query(function(result) {
                vm.contracts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
