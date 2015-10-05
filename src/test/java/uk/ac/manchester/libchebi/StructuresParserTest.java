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
public class StructuresParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchiKeyMissing() throws IOException, ParseException
	{
		final int id = 1;
		Assert.assertNull( StructuresParser.getInstance().getInchiKey( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchiKeyNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertNull( StructuresParser.getInstance().getInchiKey( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchiKey() throws IOException, ParseException
	{
		final int id = 73938;
		final Structure structure = new Structure( "VIDUVSPOWYVZIC-IMJSIDKUSA-O", Structure.Type.InChIKey, 1 ); //$NON-NLS-1$
		Assert.assertEquals( structure, StructuresParser.getInstance().getInchiKey( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchiKeyNegativeStructure() throws IOException, ParseException
	{
		final int id = 73938;
		final Structure structure = new Structure( "made_up", Structure.Type.InChIKey, 1 ); //$NON-NLS-1$
		Assert.assertNotEquals( structure, StructuresParser.getInstance().getInchiKey( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchiKeyNegativeType() throws IOException, ParseException
	{
		final int id = 73938;
		final Structure structure = new Structure( "VIDUVSPOWYVZIC-IMJSIDKUSA-O", Structure.Type.mol, 1 ); //$NON-NLS-1$
		Assert.assertNotEquals( structure, StructuresParser.getInstance().getInchiKey( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getInchiKeyNegativeDimension() throws IOException, ParseException
	{
		final int id = 73938;
		final Structure structure = new Structure( "VIDUVSPOWYVZIC-IMJSIDKUSA-O", Structure.Type.InChIKey, 123456 ); //$NON-NLS-1$
		Assert.assertNotEquals( structure, StructuresParser.getInstance().getInchiKey( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getSmilesMissing() throws IOException, ParseException
	{
		final int id = 1;
		Assert.assertNull( StructuresParser.getInstance().getSmiles( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getSmilesNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertNull( StructuresParser.getInstance().getSmiles( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getSmiles() throws IOException, ParseException
	{
		final int id = 73938;
		final Structure structure = new Structure( "NC(=[NH2+])NCC[C@H](O)[C@H]([NH3+])C([O-])=O", Structure.Type.SMILES, 1 ); //$NON-NLS-1$
		Assert.assertEquals( structure, StructuresParser.getInstance().getSmiles( id ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolMissing() throws IOException
	{
		final int id = 1;
		Assert.assertNull( StructuresParser.getInstance().getMol( id ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolNeg() throws IOException
	{
		final int id = -1;
		Assert.assertNull( StructuresParser.getInstance().getMol( id ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMol() throws IOException
	{
		final int id = 73938;
		final Structure structure = new Structure( MolTestUtils.readMol( id ), Structure.Type.mol, 2 );
		Assert.assertEquals( structure, StructuresParser.getInstance().getMol( id ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolCommaName() throws IOException
	{
		final int id = 57587;
		final Structure structure = new Structure( MolTestUtils.readMol( id ), Structure.Type.mol, 2 );
		Assert.assertEquals( structure, StructuresParser.getInstance().getMol( id ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolFileMissing() throws IOException
	{
		final int id = 1;
		Assert.assertNull( StructuresParser.getInstance().getMolFile( id ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolFileNeg() throws IOException
	{
		final int id = -1;
		Assert.assertNull( StructuresParser.getInstance().getMolFile( id ) );
	}

	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolFile() throws IOException
	{
		final int id = 73938;

		try ( InputStream is = new FileInputStream( StructuresParser.getInstance().getMolFile( id ) ) )
		{
			final String molRead = MolTestUtils.readMol( id );
			final String molRetrieved = MolTestUtils.readMol( is );
			Assert.assertEquals( molRead, molRetrieved );
		}
	}
}