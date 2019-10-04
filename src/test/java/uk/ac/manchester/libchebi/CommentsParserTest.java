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
public class CommentsParserTest
{
	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsSize() throws IOException, ParseException
	{
		final int id = 5407;
		final int size = 3;
		Assert.assertTrue( size < CommentsParser.getInstance().getComments( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsSizeEmpty() throws IOException, ParseException
	{
		final int id = 7;
		final int size = 0;
		Assert.assertEquals( size, CommentsParser.getInstance().getComments( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsSizeNeg() throws IOException, ParseException
	{
		final int id = -1;
		final int size = 0;
		Assert.assertEquals( size, CommentsParser.getInstance().getComments( id ).size() );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getComments() throws IOException, ParseException
	{
		final int id = 5407;
		final Comment comment = new Comment( "99025", "DatabaseAccession", "Z stereomer", ParserUtils.parseDate( "2006-09-01" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertTrue( CommentsParser.getInstance().getComments( id ).contains( comment ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsNegativeDatatypeId() throws IOException, ParseException
	{
		final int id = 5407;
		final Comment comment = new Comment( "DA", "DatabaseAccession", "Z stereomer", ParserUtils.parseDate( "2006-09-01" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( CommentsParser.getInstance().getComments( id ).contains( comment ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsNegativeDatatype() throws IOException, ParseException
	{
		final int id = 5407;
		final Comment comment = new Comment( "DatabaseAccession", "DA", "Z stereomer", ParserUtils.parseDate( "2006-09-01" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( CommentsParser.getInstance().getComments( id ).contains( comment ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsNegativeText() throws IOException, ParseException
	{
		final int id = 5407;
		final Comment comment = new Comment( "DatabaseAccession", "DatabaseAccession", "Random text", ParserUtils.parseDate( "2006-09-01" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( CommentsParser.getInstance().getComments( id ).contains( comment ) );
	}

	/**
	 * @throws ParseException
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getCommentsNegativeCreatedOn() throws IOException, ParseException
	{
		final int id = 5407;
		final Comment comment = new Comment( "DatabaseAccession", "DatabaseAccession", "Z stereomer", ParserUtils.parseDate( "2011-02-02" ) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		Assert.assertFalse( CommentsParser.getInstance().getComments( id ).contains( comment ) );
	}
}