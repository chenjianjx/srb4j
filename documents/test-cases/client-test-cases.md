# Client-Side Test Cases


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

## Invoke Business

* Try Protected Resource without Logging in
  * Expectation: User redirected to login UI

* Try Protected Resource after Logging in
