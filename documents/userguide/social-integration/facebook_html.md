# Facebook Login for Html Clients

You will obtain Facebook's OAuth2 code according to [Manually Build a Login Flow](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow) and send it to http://your-backend/token/new/social/by-auth-code/facebook/web

# Steps

* [Create a facebook APP](https://developers.facebook.com/apps/) if you haven't got one. 
* Follow [the manual flow](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow), and check out our sample code.  
    * Make a [button](https://github.com/chenjianjx/srb4j-html-client/blob/master/dashboard.html)
    * See [$scope.facebookLogin()](https://github.com/chenjianjx/srb4j-html-client/blob/master/home.js) to go to facebook dialog page. You will create a facebook url inside your html client, and add it to "Valid OAuth redirect URIs" on [facebook's app center](https://developers.facebook.com/apps/).
    * After the user has signed in, he will land onto the redirected_uri page inside your html client. Extract the code from current URL and send it to srb4j's backend. See [SocialLoginByFbCodeController](https://github.com/chenjianjx/srb4j-html-client/blob/master/home.js)
* [getEmailFromCode](https://github.com/chenjianjx/srb4jfullsample/blob/master/impl/src/main/java/com/github/chenjianjx/srb4jfullsample/impl/fo/auth/socialsite/FoFacebookAuthHelper.java) is the server-side code. You don't have to change it, but you will set "facebookClientId" and "facebookClientId" on app.properties.