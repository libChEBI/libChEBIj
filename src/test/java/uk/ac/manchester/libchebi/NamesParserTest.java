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
public class NamesParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesSize() throws IOException, ParseException
	{
		final int id = 15393;
		final int size = 12;
		Assert.assertTrue( size >= NamesParser.getInstance().getNames( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesSizeNeg() throws IOException, ParseException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertEquals( size, NamesParser.getInstance().getNames( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesSizeEmpty() throws IOException, ParseException
	{
		final int id = 81100;
		final int size = 0;
		Assert.assertEquals( size, NamesParser.getInstance().getNames( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNames() throws IOException, ParseException
	{
		final int id = 75711;
		final Name name = new Name( "2-(p-Chloro-o-tolyloxy)propionic acid", "SYNONYM", "ChemIDplus", false, "en" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertTrue( NamesParser.getInstance().getNames( id ).contains( name ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesNegativeName() throws IOException, ParseException
	{
		final int id = 75711;
		final Name name = new Name( "Glucose", "SYNONYM", "ChemIDplus", false, "en" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( NamesParser.getInstance().getNames( id ).contains( name ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesNegativeType() throws IOException, ParseException
	{
		final int id = 75711;
		final Name name = new Name( "2-(p-Chloro-o-tolyloxy)propionic acid", "NAME", "ChemIDplus", false, "en" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( NamesParser.getInstance().getNames( id ).contains( name ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesNegativeSource() throws IOException, ParseException
	{
		final int id = 75711;
		final Name name = new Name( "2-(p-Chloro-o-tolyloxy)propionic acid", "SYNONYM", "ChEBI", false, "en" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( NamesParser.getInstance().getNames( id ).contains( name ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesNegativeAdapted() throws IOException, ParseException
	{
		final int id = 75711;
		final Name name = new Name( "2-(p-Chloro-o-tolyloxy)propionic acid", "SYNONYM", "ChemIDplus", true, "en" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( NamesParser.getInstance().getNames( id ).contains( name ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNamesNegativeLanguage() throws IOException, ParseException
	{
		final int id = 75711;
		final Name name = new Name( "2-(p-Chloro-o-tolyloxy)propionic acid", "SYNONYM", "ChemIDplus", false, "fr" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( NamesParser.getInstance().getNames( id ).contains( name ) );
	}
}