# Facebook Login for Desktop Clients

Note: A Desktop is a system that doesn't have facebook's SDK support and is able to launch a web view, such as Java GUI, .NET GUI and so on.

You will obtain Facebook's OAuth2 code according to [Manually Build a Login Flow](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow) and send it to http://your-backend/token/new/social/by-auth-code/facebook/desktop .


# Steps
* [Create a facebook APP](https://developers.facebook.com/apps/) if you haven't got one. 
* Add "https://www.facebook.com/connect/login_success.html" to the APP's "Valid OAuth redirect URIs". 
* Follow [the manual flow](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow), and check out our sample code.  
    * Make a simple button
    * See [facebookCodeLoginBtn.addActionListener()](https://github.com/chenjianjx/srb4j-desktop-client/blob/master/src/main/java/org/srb4j/desktopclient/view/MainForm.java). You will construct the facebook auth url, launch it with a web view inside your client application.  
    * After the user has signed in, he will land onto the redirected URL inside your html client. Extract the code from the redirected URL and send it to srb4j's backend. See [SocialLoginByFbCodeController](https://github.com/chenjianjx/srb4j-html-client/blob/master/home.js)
* [getEmailFromCode](https://github.com/chenjianjx/srb4jfullsample/blob/master/impl/src/main/java/com/github/chenjianjx/srb4jfullsample/impl/fo/auth/socialsite/FoFacebookAuthHelper.java) is the server-side code. You don't have to change it, but you will set "facebookClientId" and "facebookClientId" on app.properties.
  