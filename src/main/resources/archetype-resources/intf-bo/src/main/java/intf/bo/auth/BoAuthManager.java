package ${package}.intf.bo.auth;

import ${package}.intf.bo.basic.BoResponse;

/**
 * @author chenjianjx@gmail.com
 */
public interface BoAuthManager {

    public BoResponse<BoLoginResult> login(BoLoginRequest request);

}
