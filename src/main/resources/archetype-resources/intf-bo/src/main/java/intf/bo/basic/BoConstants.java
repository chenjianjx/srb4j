package ${package}.intf.bo.basic;

import ${package}.intf.fo.basic.FoConstants;

/**
 * Since the error codes are similar, why not just inherit the front side of class?
 */
public interface BoConstants extends FoConstants {
    String BEC_FIRST_LOGIN_MUST_CHANGE_PASSWORD = "MUST_CHANGE_PASSWORD";
}
