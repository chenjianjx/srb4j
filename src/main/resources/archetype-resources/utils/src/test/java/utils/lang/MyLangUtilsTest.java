package com.github.chenjianjx.srb4jfullsample.utils.lang;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author chenjianjx@gmail.com
 *
 */
@SuppressWarnings("unused")
public class MyLangUtilsTest {

	private static final class BeanToTrim {

		private String withSpace = "    withSpace  ";
		private String allBlank = "        ";
		private Integer age = 10;
		private SubBean sb = new SubBean("  subName  ");

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}

	}

	private static final class SubBean {
		private String subName;

		private SubBean(String subName) {
			this.subName = subName;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}

	}

	@Test
	public void testTrimStringProps() throws Exception {
		BeanToTrim btt = new BeanToTrim();
		MyLangUtils.trimRootLevelStringFields(btt);
		System.out.println(btt);
		
		Assert.assertEquals("withSpace", btt.withSpace);
		Assert.assertNull(btt.allBlank);
		Assert.assertEquals("  subName  ", btt.sb.subName);

	}
}
