(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('DealController', DealController);

    DealController.$inject = ['contract'];

    function DealController (contract) {
        var vm = this;
        vm.contract = contract;

    }
})();
