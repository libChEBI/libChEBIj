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

import uk.ac.manchester.libchebi.Relation.*;

/**
 * @author neilswainston
 */
public class ChebiEntityTest
{
	/**
	 * 
	 */
	private final ChebiEntity existing = new ChebiEntity( "4167" ); //$NON-NLS-1$

	/**
	 * 
	 */
	private final ChebiEntity secondary = new ChebiEntity( "CHEBI:5585" ); //$NON-NLS-1$

	/**
	 * 
	 */
	public ChebiEntityTest() throws IOException, ParseException, ChebiException
	{
		// No implementation.
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings({ "static-method", "unused" })
	@Test(expected = ChebiException.class)
	public void getNonExisting() throws IOException, ParseException, ChebiException
	{
		new ChebiEntity( "-1" ); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	@Test
	public void getIdExisting()
	{
		Assert.assertTrue( existing.getId().equals( "CHEBI:4167" ) ); //$NON-NLS-1$
	}

	/**
	 * 
	 */
	@Test
	public void getIdSecondary()
	{
		Assert.assertTrue( secondary.getId().equals( "CHEBI:5585" ) ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getFormulaeExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getFormulae().contains( new Formula( "C6H12O6", "KEGG COMPOUND" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getFormulaeSecondary() throws IOException, ParseException
	{
		Assert.assertTrue( secondary.getFormulae().contains( new Formula( "H2O", "ChEBI" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getFormulaExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getFormula().equals( "C6H12O6" ) ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getFormulaSecondary() throws IOException, ParseException
	{
		Assert.assertTrue( secondary.getFormula().equals( "H2O" ) ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getMassExisting() throws IOException, ParseException
	{
		final double EPSILON = 1e-3;
		Assert.assertEquals( existing.getMass(), 180.15588, EPSILON );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getMassSecondary() throws IOException, ParseException
	{
		final double EPSILON = 1e-3;
		Assert.assertEquals( secondary.getMass(), 18.01530, EPSILON );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getChargeExisting() throws IOException, ParseException
	{
		final double EPSILON = 1e-16;
		Assert.assertEquals( existing.getCharge(), 0, EPSILON );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getChargeSecondary() throws IOException, ParseException
	{
		final double EPSILON = 1e-16;
		Assert.assertEquals( secondary.getCharge(), 0, EPSILON );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getChargeSecondary2() throws IOException, ParseException, ChebiException
	{
		final double EPSILON = 1e-16;
		final int charge = -2;
		Assert.assertEquals( new ChebiEntity( "43474" ).getCharge(), charge, EPSILON ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsExisting() throws IOException, ParseException, ChebiException
	{
		Assert.assertTrue( new ChebiEntity( "CHEBI:29044" ).getComments().contains( new Comment( "29044", "General", "The substituent name '3-oxoprop-2-enyl' is incorrect but is used in various databases.", ParserUtils.parseDate( "2005-03-18" ) ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsSecondary() throws IOException, ParseException, ChebiException
	{
		Assert.assertTrue( new ChebiEntity( "11505" ).getComments().contains( new Comment( "29044", "General", "The substituent name '3-oxoprop-2-enyl' is incorrect but is used in various databases.", ParserUtils.parseDate( "2005-03-18" ) ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getStatusExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getStatus(), "C" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getStatusSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getStatus(), "C" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getSourceExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getSource(), "KEGG COMPOUND" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getSourceSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getSource(), "KEGG COMPOUND" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getParentIdExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getParentId(), ChebiEntity.UNDEFINED_VALUE );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getParentIdSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getParentId(), 15377 );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getAllIdsExisting() throws IOException, ParseException
	{
		boolean found = false;

		for( int id : existing.getAllIds() )
		{
			if( id == Integer.parseInt( existing.getId().replace( "CHEBI:", "" ) ) ) //$NON-NLS-1$ //$NON-NLS-2$
			{
				found = true;
				break;
			}
		}

		Assert.assertTrue( found );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getAllIdsSecondary() throws IOException, ParseException
	{
		boolean found = false;

		for( int id : secondary.getAllIds() )
		{
			if( id == 15377 )
			{
				found = true;
				break;
			}
		}

		Assert.assertTrue( found );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getNameExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getName(), "D-glucopyranose" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getNameSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getName(), "water" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getDefinitionExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getDefinition(), "A glucopyranose having D-configuration." ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDefinitionSecondary() throws IOException, ParseException, ChebiException
	{
		Assert.assertEquals( new ChebiEntity( "41140" ).getDefinition(), "D-Glucopyranose with beta configuration at the anomeric centre." ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getModifiedOnExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getModifiedOn().after( ParserUtils.parseDate( "2014-01-01" ) ) ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getModifiedOnSecondary() throws IOException, ParseException
	{
		Assert.assertNotNull( secondary.getModifiedOn() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getCreatedByExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getCreatedBy(), "CHEBI" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getCreatedBySecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getCreatedBy(), "ops$mennis" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getStarExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getStar(), 3 );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getStarSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getStar(), 3 );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getDatabaseAccessionsExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getDatabaseAccessions().contains( new DatabaseAccession( "MetaCyc accession", "D-Glucose", "MetaCyc" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getDatabaseAccessionsSecondary() throws IOException, ParseException
	{
		Assert.assertTrue( secondary.getDatabaseAccessions().contains( new DatabaseAccession( "MetaCyc accession", "WATER", "MetaCyc" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getInchiExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getInchi(), "InChI=1S/C6H12O6/c7-1-2-3(8)4(9)5(10)6(11)12-2/h2-11H,1H2/t2-,3-,4+,5-,6?/m1/s1" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getInchiSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getInchi(), "InChI=1S/H2O/h1H2" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getInchiKeyExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getInchiKey(), "WQZGKKKJIJFFOK-GASJEMHNSA-N" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getInchiKeySecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getInchiKey(), "XLYOFNOQVPJJNP-UHFFFAOYSA-N" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getSmilesExisting() throws IOException, ParseException
	{
		Assert.assertEquals( existing.getSmiles(), "OC[C@H]1OC(O)[C@H](O)[C@@H](O)[C@@H]1O" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getSmilesSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getSmiles(), "[H]O[H]" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolExisting() throws IOException, ParseException, ChebiException
	{
		final int id = 73938;
		Assert.assertEquals( new ChebiEntity( Integer.toString( id ) ).getMol(), MolTestUtils.readMol( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getMolSecondary() throws IOException, ParseException
	{
		Assert.assertEquals( secondary.getMol(), MolTestUtils.readMol( 15377 ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 * @throws MolFileException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolFileExisting() throws IOException, ParseException, ChebiException
	{
		final int id = 73938;
		testGetMolFile( id, Integer.toString( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 * @throws MolFileException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMolFileSecondary() throws IOException, ParseException, ChebiException
	{
		testGetMolFile( 15377, "42857" ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getNamesExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getNames().contains( new Name( "Grape sugar", "SYNONYM", "KEGG COMPOUND", false, "en" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getNamesSecondary() throws IOException, ParseException
	{
		Assert.assertTrue( secondary.getNames().contains( new Name( "eau", "SYNONYM", "ChEBI", false, "fr" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesExisting() throws IOException, ParseException, ChebiException
	{
		Assert.assertTrue( new ChebiEntity( "15347" ).getReferences().contains( new Reference( "WO2006008754", "Patent", "", "NOVEL INTERMEDIATES FOR LINEZOLID AND RELATED COMPOUNDS" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getReferencesSecondary() throws IOException, ParseException, ChebiException
	{
		Assert.assertTrue( new ChebiEntity( "22182" ).getReferences().contains( new Reference( "WO2006008754", "Patent", "", "NOVEL INTERMEDIATES FOR LINEZOLID AND RELATED COMPOUNDS" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getCompoundOriginsExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getCompoundOrigins().contains( new CompoundOrigin( "Homo sapiens", "NCBI:txid9606", null, null, null, null, "DOI", "10.1038/nbt.2488", null ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getCompoundOriginsSecondary() throws IOException, ParseException
	{
		Assert.assertTrue( secondary.getCompoundOrigins().contains( new CompoundOrigin( "Homo sapiens", "NCBI:txid9606", null, null, null, null, "DOI", "10.1038/nbt.2488", null ) ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getOutgoingsExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getOutgoings().contains( new Relation( Type.is_a, "CHEBI:17634", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getOutgoingsSecondary() throws IOException, ParseException
	{
		Assert.assertTrue( secondary.getOutgoings().contains( new Relation( Type.has_role, "48360", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getIncomingsExisting() throws IOException, ParseException
	{
		Assert.assertTrue( existing.getIncomings().contains( new Relation( Type.has_functional_parent, "15866", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test
	public void getIncomingsSecondary() throws IOException, ParseException
	{
		Assert.assertTrue( secondary.getIncomings().contains( new Relation( Type.is_conjugate_acid_of, "CHEBI:29412", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * 
	 * @param readId
	 * @param retrievedId
	 * @throws IOException
	 * @throws ParseException
	 * @throws ChebiException
	 */
	private static void testGetMolFile( final int readId, final String retrievedId ) throws IOException, ParseException, ChebiException
	{
		try ( InputStream is = new FileInputStream( new ChebiEntity( retrievedId ).getMolFile() ) )
		{
			final String molRead = MolTestUtils.readMol( readId );
			final String molRetrieved = MolTestUtils.readMol( is );
			Assert.assertEquals( molRead, molRetrieved );
		}
	}
}