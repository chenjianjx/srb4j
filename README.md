# Srb4j = Simple RESTFul Backend for Java


__Srb4j__ (pronounced "/srəb/ for J") is a Java RESTFul backend code skeleton, with __common response data structures__, __user/password/access-token support__, __social login__ and __API document generation__.

It can collaborate with __html clients__, __mobile clients__ and other types of clients such as __desktop applications__ at the same time.  

__With Srb4j you can launch a restful backend in several minutes.__ 

__Checkout a demo client right away__ at https://srb4jclient.chenjianjx.com/ , or install an __Android__ client on https://github.com/chenjianjx/Srb4jAndroidClient, or download a __desktop__ client on https://github.com/chenjianjx/srb4j-desktop-client .

__You can also see its out-of-box RESTFul APIs [here](https://srb4jdemo.chenjianjx.com/fo-rest-doc) .__


Table of Contents
=================
  * [Summary of Features](#summary-of-features)
  * [Prerequisites](#prerequisites)
  * [Quick Start in Dev Env](#quick-start-in-dev-env)
  * [Quick Start for Client-Side Developers](#quick-start-for-client-side-developers)
  * [DDL/DML migration](#ddldml-migration)
  * [PaaS Cloud Integration](#paas-cloud-integration)
  * [Development of Backend](#development-of-backend)
  * [Social Login Integration](#social-login-integration)
  * [API Documentation and Client Stub Generation and Online Testing](#api-documentation-and-client-stub-generation-and-online-testing)
  * [The Back Office Portal](#the-back-office-portal)

# Summary of Features

1. Registration/login based on standard OAuth2 password flow (access tokens, refresh tokens, etc.)
2. Social account login support (Google,Facebook...) 
3. Forget password flow and random code login  
4. [Swagger](http://swagger.io/)-based API document generation and client stub generation
5. Built as a uber jar, instead of a war file
6. Managed SQL migration which can be done automatically during system startup, or run manually with Maven
7. PaaS friendly (e.g. AWS Beanstalk)
8. Robust J2EE Stack: JAX-RS + Spring + MyBatis + MySQL
9. An out-of-box back office portal


# Prerequisites
1. JDK 8+
2. Maven 3.1+
3. MySQL Server 5.7+


<!-- toc -->

# Quick Start in Dev Env

### Generate a Java project

```bash
cd /path/to/your/workspace

mvn -X org.apache.maven.plugins:maven-archetype-plugin:3.0.0:generate  \
-DarchetypeGroupId=com.github.chenjianjx -DarchetypeArtifactId=srb4j -DarchetypeVersion=3.0.0 \
-DgroupId=your.groupId  \
-DartifactId=yourArtifactId \
-Dpackage=your.pkg.name \
-Dversion=1.0-SNAPSHOT 

```

### Setup database and configure db credentials

Create a db and a user

```SQL
mysql> create database yourdb default character set utf8;	 ## Has to be utf8
mysql> create user 'your_user'@'localhost' identified by 'your_password';
mysql> grant all privileges on yourdb.* to 'your_user'@'localhost' with grant option;	
```

Update db credentials for the system 
```bash
vi yourArtifactId/webapp/src/main/resources/config/app.override.dev.properties 
#You can hard code the credentials for now. A safer way will be told later. 
```

Create tables
```bash
cd yourArtifactId/data-migration
mvn clean package flyway:migrate
```

 
### Build the Java project 
```bash
cd /path/to/your/workspace/yourArtifactId

mvn clean install

java -jar webapp/target/yourArtifactId-webapp-1.0-SNAPSHOT-shaded.jar

```

### Verify the installation

Open http://locahost:8080 in a browser

# Run the backend in QA/PROD/Other Environments
* Decide your environment name, such as "qa". 
* Set the environment name as env prop. The key is "yourArtifactId_environment"
````
export yourArtifactId_environment="production"
````
If you don't do this, the default environment is "dev" 
* Create "app.override.qa.properties" under "webapp/src/main/resources/config", and edit the env-specific properties according to "app.properties"
** They system will read "app.properties" first, and then "app.override.qa.properties" to override. So you don't have to create a duplicate entry in "app.override.qa.properties" if you are not going to override that property
** A value can be a hardcoded one, or the value of an environment variable. The suggested strategy is to put as many properties in the properties file as possible, and only define sensitive information as environment variables. 
** By default, the db's username/password is got from env variables, as you can see on "app.properties": 
````
dbUsername=${env:yourArtifactId_dbUsername}
dbPassword=${env:yourArtifactId_dbPassword}
````
So what you need to do is 
````
export yourArtifactId_dbUsername="your_user"
export yourArtifactId_dbPassword="your_password"
````
* Let SQL Migration be Done Automatically during System Startup

Go to "app.override.qa.properties" and add 
````
dataMigrationOnStartup=true
````
Then the SQLs will be run automatically when you deploy the application. 

* Build the artifact
````
mvn clean install -DskipTests
````
And you will get a uber jar like  "webapp/target/yourArtifactId-webapp-1.0-SNAPSHOT-shaded.jar".  

* Deploy the artifact
Upload the built uber jar to your target machine and run 
````
java -jar yourArtifactId-webapp-1.0-SNAPSHOT-shaded.jar
````


# Quick Start for Client-Side Developers 

## Refer to the API doc 

The API doc has been generated on your backend at http://your-backend/fo-rest-doc 

## Sample Code For HTML and Javascript Developers

```javascript		 
//login
$.ajax({
	async: false,
	url: "http://localhost:8080/fo/rest/token/new/local",
	type: "POST",
	contentType: 'application/x-www-form-urlencoded',				
	data: "grant_type=password&username=chenjianjx@gmail.com&password=abc123",
	success: function(data, statusText, xhr){					
		console.log(data.access_token); //You can save this token to cookie or LocalStorage
		console.log(data.refresh_token);
		console.log(data.expires_in);
		console.log(data.user_principal); // the full user name			
		 
		
	},
	error: function(xhr,statusText, e){
		console.log(xhr.status)										
		var response = $.parseJSON(xhr.responseText);
		console.log(response.error); // "the error code"
		console.log(response.error_description); // "the error description for developers"
		console.log(response.error_description_for_user); // "user-friendly error desc for users"
		// "the server side developer can use this id to do troubleshooting"
		console.log(response.exception_id); 
	}				
});

//call a business web service
$.ajax({
	async: false,
	url: "http://localhost:8080/fo/rest/bbs/posts/new",
	type: "POST",
	contentType: 'application/json',
	headers: {					 
		'Authorization': "Bearer " + accessToken
	},				  
	data: JSON.stringify({content:"my-first-post"}),
	success: function(data, statusText, xhr){					
		console.log(data); 
	},
	error: function(xhr,statusText, e){
		console.log(xhr.status);
		
		if(xhr.status == 400 || xhr.status == 401 || xhr.status == 403){// "token error"
		    // "See https://tools.ietf.org/html/rfc6750#page-7"						 
			var authHeader = xhr.getResponseHeader("WWW-Authenticate");
			console.log(authHeader); 
			//in this case, you can redirect the user to login 
		}
		else if (xhr.status == 460) { // "biz error"
			var response = $.parseJSON(xhr.responseText);
			console.log(response.error); // "the error code"
			console.log(response.error_description); // "the error description for developers"
			console.log(response.error_description_for_user); // "user-friendly error desc for users"
			// "the server side developer can use this id to do troubleshooting"
			console.log(response.exception_id); 
		}else{
			console.log(xhr.responseText);
		}
			
	}
});

//logout
$.ajax({
	async: false,
	url: "http://localhost:8080/fo/rest/token/delete",
	type: "POST",
	contentType: 'application/json',
	headers: {
		'Authorization': "Bearer " + accessToken
	}
});
			
``` 

Check out more code [here](https://github.com/chenjianjx/srb4j-html-client) .


## Sample Code For Desktop and Mobile Developers
 
```java

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;


// do login
HttpResponse<JsonNode> loginResponse = Unirest
		.post("http://localhost:8080/fo/rest/token/new/local")
		.header("Content-Type", "application/x-www-form-urlencoded")
		.field("grant_type", "password")
		.field("username", "chenjianjx@gmail.com")
		.field("password", "abc123").asJson();

if (loginResponse.getStatus() == 200) {
	JSONObject token = loginResponse.getBody().getObject();
	System.out.println(token.get("access_token")); //You can save the token for later use
	System.out.println(token.get("refresh_token"));
	System.out.println(token.get("expires_in"));
	System.out.println(token.get("user_principal")); // "the full user name"			
} else {
	System.out.println(loginResponse.getStatus());
	System.out.println(loginResponse.getStatusText());
	JSONObject error = loginResponse.getBody().getObject();
	System.out.println(error.get("error")); // "the error code"
	// "the error description for developers"
	System.out.println(error.get("error_description")); 
	// "user-friendly error desc for users"
	System.out.println(error.get("error_description_for_user")); 
	// "the server side developer can use this id to do troubleshooting"
	System.out.println(error.get("exception_id")); 
}

// call a business web service
NewPostRequest bizRequest = new NewPostRequest();
bizRequest.setContent("my-first-post");
HttpResponse<String> bizResponse = Unirest
		.post("http://localhost:8080/fo/rest/bbs/posts/new")
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer " + accessToken)
		.body(toJson(bizRequest)).asString();

if (bizResponse.getStatus() == 200) {
	Post post = fromJson(bizResponse.getBody(), Post.class);
	System.out.println(post);
}

else if (Arrays.asList(400, 401, 403).contains(bizResponse.getStatus())) { // "token error"
	String authHeader = bizResponse.getHeaders()
			// "See https://tools.ietf.org/html/rfc6750#page-7"
			.get("WWW-Authenticate").get(0);
	System.out.println(bizResponse.getStatus());
	
	//You can also further parse auth header if needed. 
	//Search "decodeOAuthHeader" in this repository.
	System.out.println(authHeader);  
	
	//You should then redirect the user to login UI
}

else if (bizResponse.getStatus() == 460) { // "biz error"
	JSONObject error = new JSONObject(bizResponse.getBody());
	System.out.println(error.get("error")); // "the error code"
	System.out.println(error.get("error_description")); // "the error description for developers"
	// "user-friendly error desc for users"
	System.out.println(error.get("error_description_for_user")); 
	// "the server side developer can use this id to do troubleshooting"
	System.out.println(error.get("exception_id")); 
} else {
	System.out.println(bizResponse.getStatus());
	System.out.println(bizResponse.getBody());
}


// logout
Unirest.post("http://localhost:8080/fo/rest/bbs/posts/delete")
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer " + accessToken).asJson();

```

Check out more code [here](https://github.com/chenjianjx/srb4j-desktop-client) or [here](https://github.com/chenjianjx/Srb4jAndroidClient) . 

# DDL/DML migration
Let the system take care of SQL migraiton for you.  Srb4j uses [flyway](https://flywaydb.org/) to do this. Flyway won't re-run SQLs that have been run before.  So don't worry. 
* Go to "data-migration/src/main/resources/data-migration-flyway-sql", and add a new SQL file
* In "dev" environment, "dataMigrationOnStartup" is by default false. So you should run the sql manually.  
````
cd data-migration
mvn clean package flyway:migrate
````
* In other environments, if "dataMigrationOnStartup" is set as true, the system will automatically run the SQL when the system starts up. 

# PaaS Cloud Integration
Here we use AWS Beanstalk as an example. 
* Make sure "dataMigrationOnStartup=true" in your "app.override.xxx.properties", unless you prefer to run the sql manually. 
* Configure environment variables in Beanstalk's web console, such as environment name, RDS db username/password and smtp credentials.
* (If you want) On the load balancer configuration page, you can add "https://your-backend/health" as the health check endpoint (The handler is HealthCheckServlet.java) 
* Build your artifact, upload to Beanstalk and trigger a deployment. 

# Development of Backend

## User Model

1. Every user has a "source" property to indicate where this user is from. "local" means the user registers here, "facebook" means the user is created when he logged into the backend with a facebook account.
2. source + email make up of a username, the business key.

## The code organization

* The layers:
  
![layering](documents/project-organization/layering-ppt/ultimate-layering/Slide2.jpg)

* Notes:
  * Front End:   Encapsulate use case-specific logic from business services. The users are common users such customers.
  * Back Office: Encapsulate use case-specific logic from business services. The users are administrators, staff and so on.

* And you get these maven projects: 

![code org](documents/project-organization/fo-and-bo.png)

* Notes 
  * "webapp" has "runtime" dependency on "impl"
  * "intf.bo" depends on "intf.fo" since back office users also need common-user perspectives.
  * Check full explanation [here](http://www.chenjianjx.com/myblog/entry/layering-in-java-webapps-final)   

# Social Login Integration

## Basic Flow

1. The client obtains an auth code or an access token/id token from the social website after the user has loggged into a social website
2. The client then exchanges this code or token with the backend for srb4j's access token
3. The backend will verify the code or token against the social website's server, before it sends an access token to the client

## Integrate with this or that social site 

* [Google + Html Client](documents/userguide/social-integration/google_html.md)
* [Google + Mobile Client](documents/userguide/social-integration/google_mobile.md)
* [Google + Desktop Client](documents/userguide/social-integration/google_desktop.md)
* [Facebook + Html Client](documents/userguide/social-integration/facebook_html.md)
* [Facebook + Mobile Client](documents/userguide/social-integration/facebook_mobile.md)
* [Facebook + Desktop Client](documents/userguide/social-integration/facebook_desktop.md)
* ...
* [Integrate a new social site](documents/userguide/social-integration/new_site.md) 

# API Documentation and Client Stub Generation and Online Testing

Thanks to [swagger](http://swagger.io/), you can have a WSDL-like API document of your restful web services with [swagger-ui](https://github.com/swagger-api/swagger-ui), generate client stubs with [swagger-codegen](https://github.com/swagger-api/swagger-codegen) and test the services with a [web-ui](https://github.com/swagger-api/swagger-ui). 

Srb4j has embedded swagger support. The document endpoint is http://your-backend/fo/rest/swagger.json . If you know swagger well, you know what to do.

If you just want to see a download-able API doc, check http://your-backend/fo-rest-doc , which is generated by [swagger2html](https://github.com/chenjianjx/swagger2html).    


# The Back Office Portal
You can log into the back office to manage some data such as a list of users.  

* To enable the protal, go to "app.override.xxx.properties" and set
````
enableBackOfficePortal=false
````

* To let someone login, you must generate a StaffUser record for him or her: 
````
cd data-migration
mvn clean package exec:java -Dexec.mainClass="your.pkg.name.datagen.StaffUserPasswordGenerator"
````
With the username/password they can then go to http://your-backend/bo/portal/login  . They'll have to change the password after first logging in, so the password generator won't be able to log in with the original password. 

* Development
* The portal is based on Jersey MVC + JSP + Twitter Bootstrap
* Users of the portal are called "StaffUser", which has nothing to do with front-end "User"s.  Different entity class, different table. 
* No Role/Permission infrastructure. You must do it yourself if necessary. 
