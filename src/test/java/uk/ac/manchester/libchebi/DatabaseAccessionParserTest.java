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
public class DatabaseAccessionParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDatabaseAccessionsSize() throws IOException, ParseException
	{
		final int id = 7;
		final int size = 5;
		Assert.assertTrue( size < DatabaseAccessionParser.getInstance().getDatabaseAccessions( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDatabaseAccessionsSizeNeg() throws IOException, ParseException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertEquals( size, DatabaseAccessionParser.getInstance().getDatabaseAccessions( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDatabaseAccessionsSizeEmpty() throws IOException, ParseException
	{
		final int id = 60260;
		final int size = 0;
		Assert.assertEquals( size, DatabaseAccessionParser.getInstance().getDatabaseAccessions( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDatabaseAccessions() throws IOException, ParseException
	{
		final int id = 60261;
		final DatabaseAccession databaseAccession = new DatabaseAccession( "PubMed citation", "214717", "SUBMITTER" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Assert.assertTrue( DatabaseAccessionParser.getInstance().getDatabaseAccessions( id ).contains( databaseAccession ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDatabaseAccessionsNegativeType() throws IOException, ParseException
	{
		final int id = 60261;
		final DatabaseAccession databaseAccession = new DatabaseAccession( "ChEBI", "214717", "SUBMITTER" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Assert.assertFalse( DatabaseAccessionParser.getInstance().getDatabaseAccessions( id ).contains( databaseAccession ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDatabaseAccessionsNegativeAccessionNumber() throws IOException, ParseException
	{
		final int id = 60261;
		final DatabaseAccession databaseAccession = new DatabaseAccession( "PubMed citation", "123456", "SUBMITTER" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Assert.assertFalse( DatabaseAccessionParser.getInstance().getDatabaseAccessions( id ).contains( databaseAccession ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDatabaseAccessionsNegativeSource() throws IOException, ParseException
	{
		final int id = 60261;
		final DatabaseAccession databaseAccession = new DatabaseAccession( "PubMed citation", "214717", "PubChem" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Assert.assertFalse( DatabaseAccessionParser.getInstance().getDatabaseAccessions( id ).contains( databaseAccession ) );
	}
}