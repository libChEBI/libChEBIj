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
public class CompoundOriginsParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsSize() throws IOException, ParseException
	{
		final int id = 16415;
		final int size = 4;
		Assert.assertTrue( size < CompoundOriginsParser.getInstance().getCompoundOrigins( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsSizeEmpty() throws IOException, ParseException
	{
		final int id = 1641;
		final int size = 0;
		Assert.assertEquals( size, CompoundOriginsParser.getInstance().getCompoundOrigins( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsSizeNeg() throws IOException, ParseException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertEquals( size, CompoundOriginsParser.getInstance().getCompoundOrigins( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOrigins() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "mycelium", "BTO:0001436", "74 OR23 1", null, "PubMed Id", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertTrue( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeSpeciesText() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Homo sapiens", "NCBI:txid5141", "mycelium", "BTO:0001436", "74 OR23 1", null, "PubMed Id", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeSpeciesAccession() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid123456789", "mycelium", "BTO:0001436", "74 OR23 1", null, "PubMed Id", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeComponentText() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "glucose", "BTO:0001436", "74 OR23 1", null, "PubMed Id", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeComponentAccession() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "mycelium", "BTO:000123456", "74 OR23 1", null, "PubMed Id", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeStrainText() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "mycelium", "BTO:0001436", "Strain 007", null, "PubMed Id", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeStrainAccession() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "mycelium", "BTO:0001436", "74 OR23 1", "STRAIN:12345", "PubMed Id", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeSourceType() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "mycelium", "BTO:0001436", "74 OR23 1", null, "Hello! Magazine", "21425845", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeSourceAccession() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "mycelium", "BTO:0001436", "74 OR23 1", null, "PubMed Id", "123456789", "Lyophilized mycelia extracted with mixture of methanol and chloroform" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCompoundOriginsNegativeComments() throws IOException, ParseException
	{
		final int id = 67813;
		final CompoundOrigin compoundOrigin = new CompoundOrigin( "Neurospora crassa", "NCBI:txid5141", "mycelium", "BTO:0001436", "74 OR23 1", null, "PubMed Id", "21425845", "Incorrect comments" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		Assert.assertFalse( CompoundOriginsParser.getInstance().getCompoundOrigins( id ).contains( compoundOrigin ) );
	}
}