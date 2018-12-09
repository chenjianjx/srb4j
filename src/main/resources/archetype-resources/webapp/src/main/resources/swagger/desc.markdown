
  * Authentication Notes:    
    * The authentication is based on OAuth 2.0 Password flow, plus OpenId verification if an open account is involved.  
    * If the user forgets password, the client can start a typical "forget password" flow, or ask the server to generate a random login code.      

  * Response codes and corresponding data structure
    * Status Code = 200
        * Status: Success
        * Data Structure: Biz Object in JSON format or "{}" returned in Http Body
        
    * Status Code = 460
        * Status: Biz Error, i.e. Non-OAuth2 Error
        * Data Structure: ErrorResult Object (See below) in JSON format returned in Http Body
        
    * Status Code = 400 in OAuth2 token endpoint responses
        * Status: OAuth2 Token Request Failure
        * Data Structure: OAuth2 error response in JSON format returned in Http Body
        
    * Status Code = 400,401,403 in OAuth2 resource response
        * Status: OAuth2 Resource Request Failure, such as invalid token and token expired. 
        * Data Structure: OAuth2 error response in JSON format returned in Http Header. See [Oauth2 document](https://tools.ietf.org/html/draft-ietf-oauth-v2-bearer-14#page-8)
        * If you see "invalid_token", please let the user login again.
        * If you see "expired_token", please let the user login again or you can refresh the token.                 