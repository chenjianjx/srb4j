# Manual Data Migration
Run 
````
mvn initialize flyway:migrate -f data-migration/pom.xml
````
Then run it again

# System startup 
## env=dev, dataMigrationOnStartup=fase, run in IDE
Expect
* No data migration was run
* System started up successfully

## env=an env other than "dev", credentials are from system variables, dataMigrationOnStartup=true, run in a uber jar
Expect
* Data migration was run 
* System started up successfully


# Client-Side Test Cases

## The generated html doc

## Authentication

### Registration

### Local Login

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
