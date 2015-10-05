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
import java.util.*;

/**
 * @author neilswainston
 */
class CommentsParser extends FileParser
{
	/**
	 * 
	 */
	private static CommentsParser parser;

	/**
	 * 
	 */
	private List<List<Comment>> comments;

	/**
	 * 
	 * @return singleton CommentsParser
	 * @throws IOException
	 */
	synchronized static CommentsParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "comments.tsv" ); //$NON-NLS-1$
			parser = new CommentsParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private CommentsParser( final File file )
	{
		super( file );
	}

	/**
	 * 
	 * @param chebiId
	 * @return comments
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Comment> getComments( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getComments().size() ? comments.get( chebiId ) : new ArrayList<Comment>();
	}

	/**
	 * 
	 * @param chebiIds
	 * @return comments
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Comment> getComments( final int[] chebiIds ) throws IOException, ParseException
	{
		final List<Comment> allComments = new ArrayList<>();

		for( int chebiId : chebiIds )
		{
			allComments.addAll( getComments( chebiId ) );
		}

		return allComments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see libchebi.parser.Parser#init()
	 */
	@Override
	protected void init() throws IOException, ParseException
	{
		// final int ID = 0;
		final int COMPOUND_ID = 1;
		final int CREATED_ON = 2;
		final int DATATYPE_ID = 3;
		final int DATATYPE = 4;
		final int TEXT = 5;

		final TreeMap<Integer,List<Comment>> commentsMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$
				ParserUtils.add( Integer.valueOf( tokens[ COMPOUND_ID ] ), commentsMap, new Comment( tokens[ DATATYPE_ID ], tokens[ DATATYPE ], tokens[ TEXT ], ParserUtils.parseDate( tokens[ CREATED_ON ] ) ) );
			}
		}

		comments = ParserUtils.mapToList( commentsMap );
	}

	/**
	 * 
	 * @return List of comments, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized List<List<Comment>> getComments() throws IOException, ParseException
	{
		checkInit();
		return comments;
	}
}