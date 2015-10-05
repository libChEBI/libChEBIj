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
import java.util.zip.*;

/**
 * @author neilswainston
 */
class InchiParser extends FileParser
{
	/**
	 * 
	 */
	private static InchiParser parser;

	/**
	 * 
	 */
	private String[] inchis;

	/**
	 * 
	 * @return singleton InchiParser
	 * @throws ZipException
	 * @throws IOException
	 */
	synchronized static InchiParser getInstance() throws ZipException, IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "chebiId_inchi.tsv" ); //$NON-NLS-1$
			parser = new InchiParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private InchiParser( final File file )
	{
		super( file );
	}

	/**
	 * 
	 * @param chebiId
	 * @return InChI string
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized String getInchi( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getInchis().length ? inchis[ chebiId ] : null;
	}

	/**
	 * 
	 * @return array of InChI strings, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized String[] getInchis() throws IOException, ParseException
	{
		checkInit();
		return inchis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see libchebi.parser.Parser#init()
	 */
	@Override
	protected void init() throws IOException
	{
		final int CHEBI_ID = 0;
		final int INCHI = 1;

		final TreeMap<Integer,String> inchiMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$
				inchiMap.put( Integer.valueOf( tokens[ CHEBI_ID ] ), tokens[ INCHI ] );
			}
		}

		inchis = ParserUtils.mapToStringArray( inchiMap );
	}
}