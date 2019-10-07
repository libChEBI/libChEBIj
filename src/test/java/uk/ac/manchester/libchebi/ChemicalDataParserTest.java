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
public class ChemicalDataParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getFormulaeSize() throws IOException, ParseException
	{
		final int id = 6504;
		final int size = 1;
		Assert.assertTrue( size < ChemicalDataParser.getInstance().getFormulae( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getFormulaeSizeNeg() throws IOException, ParseException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertTrue( size == ChemicalDataParser.getInstance().getFormulae( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getFormulae() throws IOException, ParseException
	{
		final int id = 18357;
		final Formula formula = new Formula( "C8H11NO3", "KEGG COMPOUND" ); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertTrue( ChemicalDataParser.getInstance().getFormulae( id ).contains( formula ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getFormulaeNegativeFormula() throws IOException, ParseException
	{
		final int id = 1;
		final Formula formula = new Formula( "C9H11NO3", "KEGG COMPOUND" ); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertFalse( ChemicalDataParser.getInstance().getFormulae( id ).contains( formula ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getFormulaeNegativeSource() throws IOException, ParseException
	{
		final int id = 1;
		final Formula formula = new Formula( "C9H11NO3", "KEGG Drug" ); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertFalse( ChemicalDataParser.getInstance().getFormulae( id ).contains( formula ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMass() throws IOException, ParseException
	{
		final double EPSILON = 1e-15;
		final int id = 77120;
		final float mass = 338.20789f;
		Assert.assertEquals( mass, ChemicalDataParser.getInstance().getMass( id ), EPSILON );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getMassNeg() throws IOException, ParseException
	{
		final double EPSILON = 1e-15;
		final int id = -1;
		final float mass = ChebiEntity.UNDEFINED_VALUE;
		Assert.assertEquals( mass, ChemicalDataParser.getInstance().getMass( id ), EPSILON );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCharge() throws IOException, ParseException
	{
		final int id = 77099;
		final int charge = -4;
		Assert.assertEquals( charge, ChemicalDataParser.getInstance().getCharge( id ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getChargeNeg() throws IOException, ParseException
	{
		final double EPSILON = 1e-15;
		final int id = -1;
		final int charge = ChebiEntity.UNDEFINED_VALUE;
		Assert.assertEquals( charge, ChemicalDataParser.getInstance().getCharge( id ), EPSILON );
	}
}