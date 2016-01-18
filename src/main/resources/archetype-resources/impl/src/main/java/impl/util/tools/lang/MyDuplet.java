package ${groupId}.${rootArtifactId}.impl.util.tools.lang;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * a pair of objects. like other containers, you'd rather return an empty
 * container than a null one
 * 
 * 
 *
 */
public class MyDuplet<L, R> {
	public L left;
	public R right;

	public static <L, R> MyDuplet<L, R> newInstance(L left, R right) {
		MyDuplet<L, R> instance = new MyDuplet<L, R>();
		instance.left = left;
		instance.right = right;
		return instance;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
