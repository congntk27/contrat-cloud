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
    componentController.$inject =['$http'];
    function componentController($http){

        var suggestionPanel = this;
        suggestionPanel.categories;
        
    	var prevDescription = null;

        suggestionPanel.$onChanges = onChanges;

        //return;

		/////////////////////////////////
        //controller implementation detail
        /////////////////////////////////    

        
        function onChanges() {
        	var description = suggestionPanel.contract.description;
        	if(prevDescription != description){
        		prevDescription = description;
        		
            	$http.get('/api/category',{params: {description: description}}).then(function(response){
            		suggestionPanel.categories = response.data.category_list;
            	}, function(error){

            	});
        	}
        	
        };
        
    }
})();
