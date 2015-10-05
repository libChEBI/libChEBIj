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
class NamesParser extends FileParser
{
	/**
	 * 
	 */
	private static NamesParser parser;

	/**
	 * 
	 */
	private List<List<Name>> names;

	/**
	 * 
	 * @return singleton NamesParser
	 * @throws IOException
	 */
	synchronized static NamesParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "names.tsv.gz" ); //$NON-NLS-1$
			parser = new NamesParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private NamesParser( final File file )
	{
		super( file );
	}

	/**
	 * 
	 * @param chebiId
	 * @return names
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Name> getNames( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getNames().size() ? names.get( chebiId ) : new ArrayList<Name>();
	}

	/**
	 * 
	 * @param chebiIds
	 * @return names
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Name> getNames( final int[] chebiIds ) throws IOException, ParseException
	{
		final List<Name> allNames = new ArrayList<>();

		for( int chebiId : chebiIds )
		{
			allNames.addAll( getNames( chebiId ) );
		}

		return allNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see libchebi.parser.Parser#init()
	 */
	@Override
	protected void init() throws IOException
	{
		// final int ID = 0;
		final int COMPOUND_ID = 1;
		final int TYPE = 2;
		final int SOURCE = 3;
		final int NAME_INDEX = 4;
		final int ADAPTED = 5;
		final int LANGUAGE = 6;

		final TreeMap<Integer,List<Name>> namesMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$
				ParserUtils.add( Integer.valueOf( tokens[ COMPOUND_ID ] ), namesMap, new Name( tokens[ NAME_INDEX ], tokens[ TYPE ], tokens[ SOURCE ], tokens[ ADAPTED ].equals( "T" ), tokens[ LANGUAGE ] ) ); //$NON-NLS-1$
			}
		}

		names = ParserUtils.mapToList( namesMap );
	}

	/**
	 * 
	 * @return List of Names, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized List<List<Name>> getNames() throws IOException, ParseException
	{
		checkInit();
		return names;
	}
}