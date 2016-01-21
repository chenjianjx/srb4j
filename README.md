#Still Under Construction

#srbj4j


__srb4j__ (pronounced "sreb for J") is an open-source jax-rs back end code-skeleton with full-fledged authentication support based on OAuth2. With __srb4j__ you can quickly launch a restful java back end from zero and focus on your business logic.


## Summary of Features

1. Registration/login based on standard OAuth2 with grant_type = 'password'
2. Social account login support (Google, Facebook etc.)
3. Password resetting/random code login support  
4. Swagger-based document generation and client stub generation
5. Popular J2EE Stack: Jersey2 + Spring + MyBatis + MySQL
6. Modularized structure design 
7. An out-of-box back office web portal.


## Quick Start

````
cd /path/to/your/workspace

mvn -X archetype:generate \ 
-DarchetypeGroupId=com.github.chenjianjx -DarchetypeArtifactId=srb4j -DarchetypeVersion=1.0 \
-DgroupId=your.groupId  \
-DartifactId=yourArtifactid \
-Dpackage=your.package.name \
-Dversion=1.0-SNAPSHOT \
-DarchetypeRepository=https://jitpack.io \


mkdir ~/yourArtifactId
cd ~/yourArtifactId

wget https://raw.githubusercontent.com/chenjianjx/srb4j/master/src/main/resources/archetype-resources/doc/app.properties.sample -O app.properties

````

Please edit app.properties according to your environment.

Then you must set up a db according to the config in app.properties. After creating database run the [ddl](https://github.com/chenjianjx/srb4j/blob/master/src/main/resources/archetype-resources/doc/sql/ddl.sql). 
 
````
cd /path/to/your/workspace/yourArtifactid

mvn install -DskipTests

cd webapp

mvn jetty:run

````

now visit http://locahost:8080 to verify the startup


## What's next

### Instruct the client-side developer 

1. Submit the API doc.  Go to http://localhost:8080/fo-rest-doc, download this html file and give it to the developer of the client of this restful back end.

2. Show them the sample code for registration and login:

````java
		//registration: To login with a password, you must do registration first, which is implemented as OAuth2 login flow with grant_type = password. This demo uses apache oltu OAuth2 library.
				
		OAuthClientRequest request = OAuthClientRequest
				.tokenLocation(
						"http://localhost:8080/fo/rest/token/new/by-register/local")
				.setGrantType(GrantType.PASSWORD).setClientId(null)
				.setClientSecret(null)
				.setUsername(emailYouAreGoingToRegisterWith)
				.setPassword(password).buildBodyMessage();

		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		OAuthJSONAccessTokenResponse response = oAuthClient.accessToken(request);

		System.out.println("access token:" + response.getAccessToken());
		System.out.println("refresh token:" + response.getRefreshToken());
		System.out.println("expire in:" + response.getExpiresIn());

````
  

````
		//now log in.
		OAuthClientRequest request = OAuthClientRequest
				.tokenLocation("http://localhost:8080/fo/rest/token/new/local")
				.setGrantType(GrantType.PASSWORD).setClientId(null)
				.setClientSecret(null).setUsername(emailYouRegisterWith)
				.setPassword(password).buildBodyMessage();
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

		OAuthJSONAccessTokenResponse response = oAuthClient.accessToken(request);

		System.out.println("access token:" + response.getAccessToken());
		System.out.println("refresh token:" + response.getRefreshToken());
		System.out.println("expire in:" + response.getExpiresIn());

```` 

3. Show them the sample code of invoking business web services

````
		Builder restRequest = restClient
				.target("http://localhost:8080" + "/fo/rest")
				.path("/bbs/posts/new").request();

		restRequest = restRequest.header("Authorization", "Bearer " + token);
		NewPostRequest bizRequest = new NewPostRequest();
		bizRequest.setContent("Hi, welcome");
		Response restResponse = restRequest.post(Entity.entity(bizRequest, MediaType.APPLICATION_JSON));

		if (restResponse.getStatus() == 200) {
			Post post = restResponse.readEntity(Post.class);
			System.out.println("successful. the newly created post is :" + post);
		}

		if (Arrays.asList(400, 401, 403).contains(restResponse.getStatus())) {
			String wwwAuthHeader = restResponse.getHeaderString("www-authenticate");
			Map<String, String> headerValues = decodeOAuthHeader(wwwAuthHeader);
			System.err.println("OAuth2 Error");
			System.err.println(headerValues.get("error"));
			System.err.println(headerValues.get("error_description"));
			System.err.println(headerValues.get("error_description_for_user"));
			System.err.println("exception_id");
		}

		if (460 == restResponse.getStatus()) {
			ErrorResult errorResult = restResponse.readEntity(ErrorResult.class);
			System.err.println("Biz Error: " + errorResult);
		}
````

and the code of method decodeOAuthHeader()

````
	private static Map<String, String> decodeOAuthHeader(String header) {
		Map<String, String> headerValues = new HashMap<String, String>();
		if (header != null) {
			Matcher m = Pattern.compile("\\s*(\\w*)\\s+(.*)").matcher(header);
			if (m.matches()) {
				if ("Bearer".equalsIgnoreCase(m.group(1))) {
					for (String nvp : m.group(2).split("\\s*,\\s*")) {
						m = Pattern.compile("(\\S*)\\s*\\=\\s*\"([^\"]*)\"")
								.matcher(nvp);
						if (m.matches()) {
							String name = DemoClientUtils.urlDecode(m.group(1));
							String value = DemoClientUtils
									.urlDecode(m.group(2));
							headerValues.put(name, value);
						}
					}
				}
			}
		}
		return headerValues;
	}

````

All client sample code can be found in generated "yourArtifactId-demo-client" project.

### The code organization

![documents/project-organzation/fo-and-bo.png]


### Create your own business module

A business module called "bbs" is generated to demonstrate how to develop biz logic in srb4j. You can create your own by referring to the following code files:  

1. Table 'Post' in ddl.sql
2. Biz entity and repository(DAO) classes in package 'yourpackage.impl.biz.bbs' 
3. App-layer beans and managers in package 'yourpackage.intf.fo.bbs' and  'yourpackage.impl.fo.bbs'
4. Restful Resources in package 'yourpackage.webapp.fo.rest.bbs'  

Note: You are suggested to delete package 'yourpackage.impl.biz.bbs' if you don't it any more.  


## Introduction to the features

### OAuth2-based login flow with grant_type = password

#### User login with username/password 
1. Username is email
2. Both registration and login are treated as OAuth2 token request as shown above.  

#### Login with Google/Facebook account

Login with social account is also an OAuth2 process with grant_type=password. 

1. Your client login with google/facebook sdk and obtain an Open Id token or an access token
2. Then logs in srb4j backend using this social token with username = the-social token and password = anything
3. The srb4j backend will verify the token with google/facebook, create a user account with the returned email if it is a new user, and finally returns an access token srb4j-hosted to the client.    
4. The client will then talk to the srb4j backend using this srb4j-hosted access token.

For a client-side code sample, see [this demo](https://github.com/chenjianjx/srb4jfullsample/blob/master/demo-client/src/test/java/com/github/chenjianjx/srb4jfullsample/democlient/fo/rest/auth/DemoClientFoAuthUiMain.java). 


#### Source of user

Every user has a property called "source" depending on where this user is from, such as "google", "facebook" and "local", meaning the user is registered on the srb4j backend.

#### Other authentication features
See [FoAuthTokenResource] (https://github.com/chenjianjx/srb4jfullsample/blob/master/webapp/src/main/java/com/github/chenjianjx/srb4jfullsample/webapp/fo/rest/auth/FoAuthTokenResource.java ) for more authentication features, such as token refreshing, password resetting and random code login. 

Note: A refresh token can be used only once.


### API documentation and client support

Thanks to [swagger](http://swagger.io/), you can check the API document of your restful web services (swagger-ui), generate client stubs(swagger-codegen) and test the services with a web-ui(swagger-ui). 

Srb4j has embedded swagger support. The endpoint is http://localhost:8080/fo/rest/swagger.json . If you know swagger well, you know what to do.

If you just want to see a download-able API doc and avoid learning swagger, check http://localhost:8080/fo-rest-doc  which is generated by [swagger2html] (https://github.com/chenjianjx/swagger2html)    

It may be vulnerable to expose the API doc or testing-web-ui in a PROD system. You can disable swagger (and the API doc) by setting app.properties: 
````
enableSwagger=false
```` 


