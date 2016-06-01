# Google Login for Html Clients

You will obtain Google's OAuth2 code according to [Google Sign-in's Server Side Flow](https://developers.google.com/identity/sign-in/web/server-side-flow#implementing_the_one-time-code_flow) and send the code to http://your-backend/token/new/social/by-auth-code/google/web

# Notes 

* You can refer to [index.html](https://github.com/chenjianjx/srb4j-html-client/blob/master/index.html) and [SocialLoginGoogleController](https://github.com/chenjianjx/srb4j-html-client/blob/master/home.js) for client-side code
* [getEmailFromCode](https://github.com/chenjianjx/srb4jfullsample/blob/master/impl/src/main/java/com/github/chenjianjx/srb4jfullsample/impl/fo/auth/socialsite/FoGoogleAuthHelper.java) is the server-side code. You don't have to change it, but you will set "googleWebClientId" and "googleWebClientSecret" on app.properties.