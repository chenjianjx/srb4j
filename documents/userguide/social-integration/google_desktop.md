# Google Login for Desktop Clients

Note: A Desktop is a system that doesn't have google's SDK support and is able to launch a web view, such as Java GUI, .NET GUI and so on.

You will follow [Google's Installed Applications](https://developers.google.com/identity/protocols/OAuth2InstalledApp) flow, get the auth code and and send it to http://your-backend/token/new/social/by-auth-code/google/desktop .


# Steps
1. [Create Google Client ID](https://console.developers.google.com/apis/credentials). Pick "Other" on "Application type" selection. 
2. See [googleCodeLoginBtn.addActionListener()](https://github.com/chenjianjx/srb4j-desktop-client/blob/master/src/main/java/org/srb4j/desktopclient/view/MainForm.java). You will construct the facebook auth url, launch it with a web view inside your client application.
3. See [WebEngineChangeListener.changed()](https://github.com/chenjianjx/srb4j-desktop-client/blob/master/src/main/java/org/srb4j/desktopclient/view/auth/SocialLoginBrowser.java). You will monitor the browser's URL until you get the auth code, and then send the code to srb4j's back end.
4. The back end's code is [getEmailFromCode](https://github.com/chenjianjx/srb4jfullsample/blob/master/impl/src/main/java/com/github/chenjianjx/srb4jfullsample/impl/fo/auth/socialsite/FoFacebookAuthHelper.java). You don't need to change it, but you will set "googleClientId" and "googleClientSecret" on app.properties.
  