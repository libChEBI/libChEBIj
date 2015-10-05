/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.io.*;
import java.text.*;

import org.junit.*;

/**
 * @author neilswainston
 */
public class InchiParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchi() throws IOException, ParseException
	{
		final int id = 746859;
		final String inchi = "InChI=1S/C26H35F3O6/c1-17(2)35-25(33)11-6-4-3-5-10-21-22(24(32)15-23(21)31)13-12-19(30)16-34-20-9-7-8-18(14-20)26(27,28)29/h3,5,7-9,12-14,17,19,21-24,30-32H,4,6,10-11,15-16H2,1-2H3/b5-3-,13-12+/t19-,21-,22-,23+,24-/m1/s1"; //$NON-NLS-1$
		Assert.assertEquals( inchi, InchiParser.getInstance().getInchi( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchiNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertEquals( null, InchiParser.getInstance().getInchi( id ) );
	}
}