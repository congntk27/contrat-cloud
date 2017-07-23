(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('SignatureController', SignatureController);

    SignatureController.$inject = ['Signature'];

    function SignatureController(Signature) {

        var vm = this;

        vm.signatures = [];

        loadAll();

        function loadAll() {
            Signature.query(function(result) {
                vm.signatures = result;
                vm.searchQuery = null;
            });
        }
    }
})();
