# Manual Data Migration
Run 
````
mvn initialize flyway:migrate -f data-migration/pom.xml
````
Then run it again

# System startup 
## dev
env=dev, credentials are hardcoded, dataMigrationOnStartup=fase, run in IDE

Expect
* No data migration was run
* System started up successfully

## other envs
env=an env other than "dev", credentials are from system variables, dataMigrationOnStartup=true, run in a uber jar
Expect
* Data migration was run 
* System started up successfully


# Client-Side Test Cases

## The generated html doc

## Authentication

### Registration
* Empty input
  * Both empty
  * Username empty
  * Password empty
* Email pattern wrong
* Password pattern wrong

### Local Login
* Empty input
  * Both empty
  * Username empty
  * Password empty
* Email invalid
* Password invalid

### Social Login
* Google
* Facebook
* As a new user
* As an existing user

### Special check - email duplication
* Register with email A,  then register again with this email. Expecting failure. 
* Register with email A,  then do social login with an account assoicated with this email. Expecting failure. 
* Facebook login with an account, then do goole login with an account associated with the same email.  Expecting failure.
* Facebook login with an account, then do registration with the same email. Expecting failure. 

## Email verification 
### Register with email/password
Expect: the verification status is false
### Register with a social account
Expect: the verification status is true
### Ask for verification link
* Email not verified.      Expect receiving an email. 
* Email has been verified. Expect an error message
* Email not verified and have received an email.  Expect receiving a new email. 
### Do verification 
* Email not verified.  Expect verification successful
* Email has been verified.  Expect verification successful
* An invalid link.  Expect error message. 
* A valid but expired link.  Expect error message. 

## Invoke Business

* Try Protected Resource without Logging in
  * Expectation: User redirected to login UI

* Try Protected Resource after Logging in

# Other aspects of security
## CORS from html clients
You can use http://srb4jclient.chenjianjx.com:8000 as the html client 
* Set the allowed domains as *
* Set the allowed domains as empty string
* Set the allowed domains as http://foo.com
* Set the allowed domains as http://srb4jclient.chenjianjx.com:8000 and http://foo.com

# Back Office Test Cases

## Username/password generation 
* Input empty username
* Input valid username
* Input invalid username

## First time log in 
* After logging in, check the prompt message about why you should reset password
* Go to the users page without changing password
* Go to the users page after changing password
 

## Log in
* Empty input
  * Both empty
  * Username empty
  * Password empty
* Wrong username 
* Wrong password
* Correct input
* Correct input with spaces around
* Go to password changing page without logging in 
* Go to user list page without logging in 
* Hit "log out" url without logging in 

## Log out 
Try it 

## Change password
* Empty input
  * All empty
  * Fill current password only
  * Fill new password only 
  * Fill confirmation password only
* Wrong current password
* New password do not match the pattern
* New passwords don't match 
* New password is the same with the current one

## Front Users page
* Check when there is no user
* Check when there are a few users
* Check when there are enough users that paginantion is possible
