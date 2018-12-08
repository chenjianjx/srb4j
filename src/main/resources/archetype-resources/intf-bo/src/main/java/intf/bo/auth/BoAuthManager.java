package com.github.chenjianjx.srb4jfullsample.intf.bo.auth;

import com.github.chenjianjx.srb4jfullsample.intf.bo.basic.BoResponse;

/**
 * @author chenjianjx@gmail.com
 */
public interface BoAuthManager {

    public BoResponse<BoLoginResult> login(BoLoginRequest request);

}
