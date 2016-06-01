# Facebook Login for Mobile Clients

You will integrate Facebook's Android/IOS SDK, get the access token after user has signed-in, and send the token to http://your-backend/token/new/social/by-token/google/mobile 


# Steps for Android
1. Integrate Facebook SDK according to https://developers.facebook.com/quickstarts/ and https://developers.facebook.com/docs/android/getting-started/ 
2. Integrate facebook sign-in according to [this tutorial](http://code.tutsplus.com/tutorials/quick-tip-add-facebook-login-to-your-android-app--cms-23837) and our sample code:
    * You can use a simple button instead of the button provided by the SDK. See [facebook_sign_in_button](https://github.com/chenjianjx/Srb4jAndroidClient/blob/master/app/src/main/res/layout/content_dashboard.xml)
    * See [DashboardActivity](https://github.com/chenjianjx/Srb4jAndroidClient/blob/master/app/src/main/java/org/srb4j/androidclient/DashboardActivity.java) for front end sample code 
3. Backend code is [getEmailFromToken](https://github.com/chenjianjx/srb4jfullsample/blob/master/impl/src/main/java/com/github/chenjianjx/srb4jfullsample/impl/fo/auth/socialsite/FoFacebookAuthHelper.java). You don't need to make any change, but you will set "facebookClientId" and "facebookClientId" on app.properties.


# Steps for IOS

It's similar with the Android one. Can anybody help write the steps for IOS ?  


 