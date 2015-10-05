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
public class CompoundsParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getStatus() throws IOException, ParseException
	{
		final int id = 584977;
		final String status = "C"; //$NON-NLS-1$
		Assert.assertEquals( status, CompoundsParser.getInstance().getStatus( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getStatusNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertEquals( null, CompoundsParser.getInstance().getStatus( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getSource() throws IOException, ParseException
	{
		final int id = 718203;
		final String source = "ChEMBL"; //$NON-NLS-1$
		Assert.assertEquals( source, CompoundsParser.getInstance().getSource( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getSourceNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertEquals( null, CompoundsParser.getInstance().getSource( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getParentId() throws IOException, ParseException
	{
		final int id = 76262;
		final int parentId = 34107;
		Assert.assertEquals( parentId, CompoundsParser.getInstance().getParentId( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getParentIdNeg() throws IOException, ParseException
	{
		final int id = -1;
		final int parentId = ChebiEntity.UNDEFINED_VALUE;
		Assert.assertEquals( parentId, CompoundsParser.getInstance().getParentId( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getParentIdUndefined() throws IOException, ParseException
	{
		final int id = 41100;
		final int parentId = ChebiEntity.UNDEFINED_VALUE;
		Assert.assertEquals( parentId, CompoundsParser.getInstance().getParentId( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getName() throws IOException, ParseException
	{
		final int id = 41097;
		final String name = "3-(1H-indol-3-yl)-4-{1-[(2R)-2-(1-methylpyrrolidin-2-yl)ethyl]-1H-indol-3-yl}-1H-pyrrole-2,5-dione"; //$NON-NLS-1$
		Assert.assertEquals( name, CompoundsParser.getInstance().getName( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNameNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertEquals( null, CompoundsParser.getInstance().getName( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getNameNull() throws IOException, ParseException
	{
		final int id = 7483;
		Assert.assertNull( CompoundsParser.getInstance().getName( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDefinition() throws IOException, ParseException
	{
		final int id = 11502;
		final String definition = "A glycerophosphocholine having an unspecified acyl group attached at the 2-position."; //$NON-NLS-1$
		Assert.assertEquals( definition, CompoundsParser.getInstance().getDefinition( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDefinitionNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertEquals( null, CompoundsParser.getInstance().getDefinition( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getDefinitionNull() throws IOException, ParseException
	{
		final int id = 18945;
		Assert.assertNull( CompoundsParser.getInstance().getDefinition( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getModifiedOn() throws IOException, ParseException
	{
		final int id = 57857;
		Assert.assertTrue( CompoundsParser.getInstance().getModifiedOn( id ).after( ParserUtils.parseDate( "2014-01-01" ) ) ); //$NON-NLS-1$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getModifiedOnNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertEquals( null, CompoundsParser.getInstance().getModifiedOn( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getModifiedOnNull() throws IOException, ParseException
	{
		final int id = 6981;
		Assert.assertNull( CompoundsParser.getInstance().getModifiedOn( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCreatedBy() throws IOException, ParseException
	{
		final int id = 6030;
		final String createdBy = "CHEBI"; //$NON-NLS-1$
		Assert.assertEquals( createdBy, CompoundsParser.getInstance().getCreatedBy( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCreatedByNeg() throws IOException, ParseException
	{
		final int id = -1;
		Assert.assertEquals( null, CompoundsParser.getInstance().getCreatedBy( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getStar() throws IOException, ParseException
	{
		final int id = 8082;
		final int star = 3;
		Assert.assertEquals( star, CompoundsParser.getInstance().getStar( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getStarNeg() throws IOException, ParseException
	{
		final int id = -1;
		final int star = ChebiEntity.UNDEFINED_VALUE;
		Assert.assertEquals( star, CompoundsParser.getInstance().getStar( id ) );
	}
}