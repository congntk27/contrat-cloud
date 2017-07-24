(function(){
    'use strict';
    angular
    .module('contractcloudApp')
	.component('suggestionPanel', {
        bindings: {
        	contract: '<'
        },
        templateUrl: "app/view/suggestion-panel/suggestion-panel.html",
        controller: componentController,
        controllerAs: 'suggestionPanel'
    });

    /////////////////////////////////
    componentController.$inject =[];
    function componentController(){

        var suggestionPanel = this;

        suggestionPanel.$onInit = onInit;
        suggestionPanel.$onDestroy = onDestroy;

        return;

		/////////////////////////////////
        //controller implementation detail
        /////////////////////////////////

        function onInit() {
    	  			
    	
       }
        
        
        function onDestroy() {

        };
       
    }
})();
