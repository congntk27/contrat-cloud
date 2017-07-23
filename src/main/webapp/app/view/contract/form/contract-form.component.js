(function(){
    'use strict';
    angular
    .module('contractcloudApp')
	.component('contractForm', {
        bindings: {
        	contract: '<'
        },
        templateUrl: "app/view/contract/form/contract-form.html",
        controller: componentController,
        controllerAs: 'contractForm'
    });

    /////////////////////////////////
    componentController.$inject =['$interval', 'Contract', 'Principal'];
    function componentController($interval, Contract, Principal){

        var contractForm = this;
        var stop;

        contractForm.$onInit = onInit;
        contractForm.save = save;
        contractForm.$onDestroy = onDestroy;

        return;

		/////////////////////////////////
        //controller implementation detail
        /////////////////////////////////

        function onInit() {
    		if(!Principal.hasAnyAuthority(['ROLE_ADMIN'])) {
    			stop = $interval(synchContract, 5000);
    		}   			
    	
       }
        
        function synchContract(){ 
        	Contract.get({id: contractForm.contract.id}, function(contract){
        		contractForm.contract = contract;
        	});
    	
        }
        
        function save(){    
        	contractForm.isSaving = true;
        	Contract.update(contractForm.contract, function(contract){
        		contractForm.contract = contract;
        		contractForm.isSaving = false;
        	});     	
        }
        function onDestroy() {
        	$interval.cancel(stop);
        };
       
    }
})();
