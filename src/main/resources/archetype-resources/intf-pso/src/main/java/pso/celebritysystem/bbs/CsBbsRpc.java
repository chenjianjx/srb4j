package ${package}.pso.celebritysystem.bbs;

import java.util.List;

#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")
/**
 * bbs rpc endpoint for celebrity system. (An exemplary pso rpc endpoint)
 * 
 * @author chenjianjx@gmail.com
 *
 */
#end	
	
public interface CsBbsRpc {
#if( $includePso == "true" ||  $includePso == "y" ||  $includePso == "yes")
	List<CsPost> getPostsByCelebrity(String celebrity);
#end	
}
