(function(){
    'use strict';
    angular
    .module('contractcloudApp')
	.component('chatView', {
        bindings: {
        	contract: '<'
        },
        templateUrl: "app/view/chat/chat-view.html",
        controller: componentController,
        controllerAs: 'chatView'
    });

    /////////////////////////////////
    componentController.$inject =['$interval', 'Discussion', 'Principal'];
    function componentController($interval, Discussion, Principal){

        var chatView = this;
        var stop;

        chatView.newChatMsg = null;
        chatView.discussion = null;
        chatView.account = null;
        
        chatView.$onInit = onInit;
        chatView.$onDestroy = onDestroy;
        
        chatView.send = send;

        return;

		/////////////////////////////////
        //controller implementation detail
        /////////////////////////////////

        function onInit() {    	
        	Principal.identity().then(function(account) {
        		chatView.account = account;
            });
			stop = $interval(synchDiscussion, 5000);			
       }
        
        function synchDiscussion(){ 
        	Discussion.get({contractId: chatView.contract.id}, function(discussion){
        		chatView.discussion = discussion;
        	});
    	
        }
        
        function send(){
        	var comment = {
        			author: chatView.account.lastName,
        			mail: chatView.account.email,
        			comment: chatView.newChatMsg
        		};
        	Discussion.get({contractId: chatView.contract.id}, function(discussion){
        		chatView.discussion = discussion;
        		
        		if(!chatView.discussion.comments){
        			chatView.discussion.comments = [];
        		} 
        		chatView.discussion.comments.push(comment);
        		Discussion.update(chatView.discussion, function(discussion){
        			chatView.discussion = discussion;
        			chatView.newChatMsg = null;
        		});
        	});
        }
        
        function onDestroy() {
        	$interval.cancel(stop);
        };
       
    }
})();
