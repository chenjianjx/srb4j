# Google Login for Mobile Clients

You will integrate Google's Android/IOS SDK, get the id token after user has signed-in, and send the token to http://your-backend/token/new/social/by-token/google/mobile 


# Steps for Android
1. [Sign your APP](https://developer.android.com/studio/publish/app-signing.html) first. You will get a key store file.
2. run ````keytool -exportcert -keystore the-keys-store.jks -list -v```` to get the sha-1 fingerprint
3. [Get the configuration file](https://developers.google.com/identity/sign-in/android/start-integrating#get-config). You will input the sha-1 fingerprint obtained during previous steps.
4. [Go on with the integration] (https://developers.google.com/identity/sign-in/android/start-integrating) 
	* You can use a simple button instead of the button provided by the SDK. See [google_sign_in_button](https://github.com/chenjianjx/Srb4jAndroidClient/blob/master/app/src/main/res/layout/content_dashboard.xml)
	* See [DashboardActivity](https://github.com/chenjianjx/Srb4jAndroidClient/blob/master/app/src/main/java/org/srb4j/androidclient/DashboardActivity.java) for front end sample code 
6. Backend code is [getEmailFromToken](https://github.com/chenjianjx/srb4jfullsample/blob/master/impl/src/main/java/com/github/chenjianjx/srb4jfullsample/impl/fo/auth/socialsite/FoGoogleAuthHelper.java). You don't have to make any change, but you will set "googleWebClientId" and "googleWebClientSecret" on app.properties.(Yes, we use web client id on the back end even it's for mobile sign-in, as Google says so).


## Not Working during Development?
The previous setup is for the release version of your Android APP. You will encounter "unknown status code: 12501" (or 12500) errors during development, because you are running a debug version. 

To get your debug version running,
* Find out the sha-1 fingerprint for debug. ````keytool -exportcert -list -v -alias androiddebugkey -keystore ~/.android/debug.keystore````, input “android” as the password.
* Go to the google developer’s console, create another clientId under the same project, input the debug fingerprint for it, and update the client-id and fingerprint on the google-services.json accordingly. See the comments in the [sample code](https://github.com/chenjianjx/Srb4jAndroidClient/blob/master/app/google-services.json). 

# Steps for IOS

It's similar with the Android one. Can anybody help write the steps for IOS ?  


 