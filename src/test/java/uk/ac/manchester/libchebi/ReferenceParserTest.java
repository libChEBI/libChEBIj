/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.io.*;

import org.junit.*;

/**
 * @author neilswainston
 */
public class ReferenceParserTest
{
	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesSize() throws IOException
	{
		final int id = 76181;
		final int size = 100;
		Assert.assertTrue( size < ReferenceParser.getInstance().getReferences( new int[] { id } ).size() );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesSizeNeg() throws IOException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertEquals( size, ReferenceParser.getInstance().getReferences( new int[] { id } ).size() );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesSizeEmpty() throws IOException
	{
		final int id = 1;
		final int size = 0;
		Assert.assertEquals( size, ReferenceParser.getInstance().getReferences( new int[] { id } ).size() );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferences() throws IOException
	{
		final int id = 27594;
		final Reference reference = new Reference( "O13340", "UniProt", "CC - INDUCTION", "Podosporapepsin" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertTrue( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesNegativeReferenceId() throws IOException
	{
		final int id = 27594;
		final Reference reference = new Reference( "Random id", "UniProt", "CC - INDUCTION", "Podosporapepsin" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesNegativeReferenceDbName() throws IOException
	{
		final int id = 27594;
		final Reference reference = new Reference( "O13340", "Random db name", "CC - INDUCTION", "Podosporapepsin" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesNegativeLocationInRef() throws IOException
	{
		final int id = 27594;
		final Reference reference = new Reference( "O13340", "UniProt", "Random location in ref", "Podosporapepsin" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesNegativeReferenceName() throws IOException
	{
		final int id = 27594;
		final Reference reference = new Reference( "O13340", "UniProt", "CC - INDUCTION", "Random reference name" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesThreeTokens() throws IOException
	{
		final int id = 8;
		final Reference reference = new Reference( "SID: 49658669", "PubChem" ); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertTrue( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesThreeTokensNegativeReferenceId() throws IOException
	{
		final int id = 8;
		final Reference reference = new Reference( "Random reference id", "PubChem" ); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertFalse( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesThreeTokensNegativeReferenceDbName() throws IOException
	{
		final int id = 8;
		final Reference reference = new Reference( "SID: 49658669", "Random db name" ); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertFalse( ReferenceParser.getInstance().getReferences( new int[] { id } ).contains( reference ) );
	}
}
