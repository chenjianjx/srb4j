package com.github.chenjianjx.srb4jfullsample.webapp.fo.rest.support;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by chenjianjx@gmail.com on 11/11/18.
 */
public class FoCorsFilterTest {

    @Test
    public void testParseCorsAllowedOrigins() throws Exception {
        FoCorsFilter filter = new FoCorsFilter();
        assertArrayEquals(new String[0], filter.parseCorsAllowedOrigins("  ").toArray());
        assertArrayEquals(new String[]{"http://foo.com", "https://bar.com:2018"},
                filter.parseCorsAllowedOrigins(" http://foo.com ; https://bar.com:2018 ").toArray());
        assertArrayEquals(new String[]{"*"}, filter.parseCorsAllowedOrigins(" * ").toArray());
    }


    @Test
    public void testDecideResponseOriginHeader() {
        FoCorsFilter filter = new FoCorsFilter();
        assertNull(filter.decideResponseOriginHeader(null, "http://foo.com"));
        assertNull(filter.decideResponseOriginHeader(Collections.emptyList(), "http://foo.com"));
        assertNull(filter.decideResponseOriginHeader(Arrays.asList("http://foo.com", "http://bar.com"), "http://unwelcome.com"));
        assertEquals("*", filter.decideResponseOriginHeader(Arrays.asList("http://foo.com", "*"), "http://unwelcome.com"));
        assertEquals("http://foo.com", filter.decideResponseOriginHeader(Arrays.asList("http://foo.com", "http://bar.com"), "http://foo.com"));
    }
}