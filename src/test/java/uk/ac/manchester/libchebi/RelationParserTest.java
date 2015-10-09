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
public class RelationParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getOutgoingsSize() throws IOException, ParseException
	{
		final int id = 4167;
		final int size = 2;
		Assert.assertTrue( size < RelationParser.getInstance().getOutgoings( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getIncomingsSize() throws IOException, ParseException
	{
		final int id = 4167;
		final int size = 19;
		Assert.assertTrue( size < RelationParser.getInstance().getIncomings( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getOutgoingsNegSize() throws IOException, ParseException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertTrue( size == RelationParser.getInstance().getOutgoings( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getIncomingsNegSize() throws IOException, ParseException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertTrue( size == RelationParser.getInstance().getIncomings( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getOutgoingsEmptySize() throws IOException, ParseException
	{
		final int id = 1;
		final int size = 0;
		Assert.assertTrue( size == RelationParser.getInstance().getOutgoings( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getIncomingsEmptySize() throws IOException, ParseException
	{
		final int id = 1;
		final int size = 0;
		Assert.assertTrue( size == RelationParser.getInstance().getIncomings( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getOutgoings() throws IOException, ParseException
	{
		final int id = 4167;
		Assert.assertTrue( RelationParser.getInstance().getOutgoings( id ).contains( new Relation( Type.is_a, "17634", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getOutgoingsTypeNegative() throws IOException, ParseException
	{
		final int id = 4167;
		Assert.assertFalse( RelationParser.getInstance().getOutgoings( id ).contains( new Relation( Type.is_conjugate_acid_of, "CHEBI:17634", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getOutgoingsTargetChebiIdNegative() throws IOException, ParseException
	{
		final int id = 4167;
		Assert.assertFalse( RelationParser.getInstance().getOutgoings( id ).contains( new Relation( Type.is_a, "1111", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getOutgoingsStatusNegative() throws IOException, ParseException
	{
		final int id = 4167;
		Assert.assertFalse( RelationParser.getInstance().getOutgoings( id ).contains( new Relation( Type.is_a, "CHEBI:17634", "ZZZ" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getIncomings() throws IOException, ParseException
	{
		final int id = 4167;
		Assert.assertTrue( RelationParser.getInstance().getIncomings( id ).contains( new Relation( Type.has_functional_parent, "CHEBI:15866", "C" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}
}