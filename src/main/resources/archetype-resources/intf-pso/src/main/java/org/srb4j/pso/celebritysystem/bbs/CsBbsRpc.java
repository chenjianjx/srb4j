package org.srb4j.pso.celebritysystem.bbs;

import java.util.List;

/**
 * bbs rpc endpoint for celebrity system. (An exemplary pso rpc endpoint)
 * 
 * @author chenjianjx@gmail.com
 *
 */
public interface CsBbsRpc {

	List<CsPost> getPostsByCelebrity(String celebrity);

}
