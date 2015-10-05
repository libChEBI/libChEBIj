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
class ReferenceParser
{
	/**
	 * 
	 */
	private static ReferenceParser parser;

	/**
	 * 
	 */
	private final File file;

	/**
	 * 
	 * @return singleton ReferenceParser
	 * @throws IOException
	 */
	synchronized static ReferenceParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "reference.tsv.gz" ); //$NON-NLS-1$
			parser = new ReferenceParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private ReferenceParser( final File file )
	{
		this.file = file;
	}

	/**
	 * 
	 * @param chebiId
	 * @return references
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Reference> getReferences( final int[] chebiIds ) throws IOException
	{
		final int COMPOUND_ID = 0;
		final int REFERENCE_ID = 1;
		final int REFERENCE_DB_NAME = 2;
		final int LOCATION_IN_REF = 3;
		final int REFERENCE_NAME = 4;

		final List<Reference> references = new ArrayList<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$

				if( line.length() > 0 )
				{
					final int id = Integer.parseInt( tokens[ COMPOUND_ID ] );

					for( int chebiId : chebiIds )
					{
						if( id == chebiId )
						{
							references.add( tokens.length > LOCATION_IN_REF ? new Reference( tokens[ REFERENCE_ID ], tokens[ REFERENCE_DB_NAME ], tokens[ LOCATION_IN_REF ], tokens[ REFERENCE_NAME ] ) : new Reference( tokens[ REFERENCE_ID ], tokens[ REFERENCE_DB_NAME ] ) );
						}
					}
				}
			}
		}

		return references;
	}
}