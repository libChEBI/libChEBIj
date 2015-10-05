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
class DatabaseAccessionParser extends FileParser
{
	/**
	 * 
	 */
	private static DatabaseAccessionParser parser;

	/**
	 * 
	 */
	private List<List<DatabaseAccession>> databaseAccessions;

	/**
	 * 
	 * @return singleton DatabaseAccessionParser
	 * @throws IOException
	 */
	synchronized static DatabaseAccessionParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "database_accession.tsv" ); //$NON-NLS-1$
			parser = new DatabaseAccessionParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private DatabaseAccessionParser( final File file )
	{
		super( file );
	}

	/**
	 * 
	 * @param chebiId
	 * @return database accessions
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<DatabaseAccession> getDatabaseAccessions( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getDatabaseAccessions().size() ? databaseAccessions.get( chebiId ) : new ArrayList<DatabaseAccession>();
	}

	/**
	 * 
	 * @param chebiIds
	 * @return database accessions
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<DatabaseAccession> getDatabaseAccessions( final int[] chebiIds ) throws IOException, ParseException
	{
		final List<DatabaseAccession> allDatabaseAccessions = new ArrayList<>();

		for( int chebiId : chebiIds )
		{
			allDatabaseAccessions.addAll( getDatabaseAccessions( chebiId ) );
		}

		return allDatabaseAccessions;
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
		final int SOURCE = 2;
		final int TYPE = 3;
		final int ACCESSION_NUMBER = 4;

		final TreeMap<Integer,List<DatabaseAccession>> databaseAccessionsMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$
				ParserUtils.add( Integer.valueOf( tokens[ COMPOUND_ID ] ), databaseAccessionsMap, new DatabaseAccession( tokens[ TYPE ], tokens[ ACCESSION_NUMBER ], tokens[ SOURCE ] ) );
			}
		}

		databaseAccessions = ParserUtils.mapToList( databaseAccessionsMap );
	}

	/**
	 * 
	 * @return List of database accessions, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized List<List<DatabaseAccession>> getDatabaseAccessions() throws IOException, ParseException
	{
		checkInit();
		return databaseAccessions;
	}
}