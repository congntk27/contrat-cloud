(function() {
    'use strict';

    angular
        .module('contractcloudApp')
        .controller('SignatureDialogController', SignatureDialogController);

    SignatureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Signature'];

    function SignatureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Signature) {
        var vm = this;

        vm.signature = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.signature.id !== null) {
                Signature.update(vm.signature, onSaveSuccess, onSaveError);
            } else {
                Signature.save(vm.signature, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('contractcloudApp:signatureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.signdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
