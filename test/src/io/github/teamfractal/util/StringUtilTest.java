package io.github.teamfractal.util;

import io.github.teamfractal.TesterFile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link StringUtil}
 */
public class StringUtilTest extends TesterFile {
	@Test
	public void capitaliseShouldBeDoneProperly(){
		assertEquals("Java", StringUtil.Capitalise("java"));
		assertEquals("JAVA", StringUtil.Capitalise("jAVA"));
		assertEquals("J", StringUtil.Capitalise("j"));
	}
}
