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
class CompoundOriginsParser extends FileParser
{
	/**
	 * 
	 */
	private static CompoundOriginsParser parser;

	/**
	 * 
	 */
	private List<List<CompoundOrigin>> compoundOrigins;

	/**
	 * 
	 * @return singleton CompoundOriginsParser
	 * @throws IOException
	 */
	synchronized static CompoundOriginsParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "compound_origins.tsv" ); //$NON-NLS-1$
			parser = new CompoundOriginsParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private CompoundOriginsParser( final File file )
	{
		super( file );
	}

	/**
	 * 
	 * @param chebiId
	 * @return compound origins
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<CompoundOrigin> getCompoundOrigins( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getCompoundOrigins().size() ? compoundOrigins.get( chebiId ) : new ArrayList<CompoundOrigin>();
	}

	/**
	 * 
	 * @param chebiIds
	 * @return compound origins
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<CompoundOrigin> getCompoundOrigins( final int[] chebiIds ) throws IOException, ParseException
	{
		final List<CompoundOrigin> allCompoundOrigins = new ArrayList<>();

		for( int chebiId : chebiIds )
		{
			allCompoundOrigins.addAll( getCompoundOrigins( chebiId ) );
		}

		return allCompoundOrigins;
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
		final int SPECIES_TEXT = 2;
		final int SPECIES_ACCESSION = 3;
		final int COMPONENT_TEXT = 4;
		final int COMPONENT_ACCESSION = 5;
		final int STRAIN_TEXT = 6;
		final int STRAIN_ACCESSION = 7;
		final int SOURCE_TYPE = 8;
		final int SOURCE_ACCESSION = 9;
		final int COMMENTS = 10;

		final TreeMap<Integer,List<CompoundOrigin>> compoundOriginsMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$

				if( tokens.length > COMMENTS )
				{
					ParserUtils.add( Integer.valueOf( tokens[ COMPOUND_ID ] ), compoundOriginsMap, new CompoundOrigin( tokens[ SPECIES_TEXT ], tokens[ SPECIES_ACCESSION ], tokens[ COMPONENT_TEXT ], tokens[ COMPONENT_ACCESSION ], tokens[ STRAIN_TEXT ], tokens[ STRAIN_ACCESSION ], tokens[ SOURCE_TYPE ], tokens[ SOURCE_ACCESSION ], tokens[ COMMENTS ] ) );
				}
			}
		}

		compoundOrigins = ParserUtils.mapToList( compoundOriginsMap );
	}

	/**
	 * 
	 * @return List of compound origins, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized List<List<CompoundOrigin>> getCompoundOrigins() throws IOException, ParseException
	{
		checkInit();
		return compoundOrigins;
	}
}