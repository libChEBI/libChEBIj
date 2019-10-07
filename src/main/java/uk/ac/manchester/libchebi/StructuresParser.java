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

import uk.ac.manchester.libchebi.Structure.*;

/**
 * @author neilswainston
 */
class StructuresParser extends Parser
{
	/**
	 * 
	 */
	private static StructuresParser parser;

	/**
	 * 
	 */
	private final File structuresFile;

	/**
	 * 
	 */
	private int[] defaultStructureIds;

	/**
	 * 
	 */
	private Structure[] smiles;

	/**
	 * 
	 */
	private Structure[] inchiKeys;

	/**
	 * 
	 * @return singleton CompoundsParser
	 * @throws IOException
	 */
	synchronized static StructuresParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File structuresFile = Downloader.getInstance().getFile( "structures.csv.gz" ); //$NON-NLS-1$
			parser = new StructuresParser( structuresFile );
		}

		return parser;
	}

	/**
	 * 
	 * @param structuresFile
	 */
	private StructuresParser( final File structuresFile )
	{
		this.structuresFile = structuresFile;
	}

	/**
	 * 
	 * @param chebiId
	 * @return inchiKey
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized Structure getInchiKey( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getInchiKeys().length ? inchiKeys[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return mol
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized Structure getMol( final int chebiId ) throws IOException
	{
		final int STRUCTURE_ID = 0;
		final int STRUCTURE = 2;
		final int MOL_STRUCTURE = 0;
		final int MOL_DIMENSION = 2;
		final int DEFAULT_STRUCTURE = 3;
		final int ZERO = 0;
		final String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$
		final String QUOTES = "\""; //$NON-NLS-1$
		final String EMPTY_STRING = ""; //$NON-NLS-1$
		final String COMMA = ","; //$NON-NLS-1$

		final String chebiIdRegexp = "^\\d+\\," + chebiId + "\\,.*"; //$NON-NLS-1$ //$NON-NLS-2$
		final String molFileEndRegexp = "\",mol,\\dD,[Y\\|N],[Y\\|N]$"; //$NON-NLS-1$
		final StringBuilder structure = new StringBuilder();

		try ( BufferedReader reader = new BufferedReader( new FileReader( structuresFile ) ) )
		{
			String line = reader.readLine(); // Read header

			boolean inChebiId = false;

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( COMMA );

				if( line.matches( chebiIdRegexp ) )
				{
					inChebiId = true;
					structure.setLength( ZERO );

					for( int i = STRUCTURE; i < tokens.length; i++ )
					{
						structure.append( tokens[ i ].replaceFirst( QUOTES, EMPTY_STRING ) );
						structure.append( COMMA );
					}

					structure.setLength( structure.length() - 1 );
					structure.append( LINE_SEPARATOR );
				}
				else if( inChebiId )
				{
					if( line.matches( molFileEndRegexp ) && isDefaultStructure(tokens[DEFAULT_STRUCTURE]))
					{
						structure.append( tokens[ MOL_STRUCTURE ].replace( QUOTES, EMPTY_STRING ) );
						return new Structure( structure.toString(), Type.mol, parseDimension( tokens[MOL_DIMENSION ] ) );
					}
					else if(line.matches( molFileEndRegexp ) && !isDefaultStructure(tokens[DEFAULT_STRUCTURE]))
					{
						structure.setLength( ZERO );
						inChebiId = false;
					}
					else
					{
						structure.append( line );
						structure.append( LINE_SEPARATOR );
					}
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return smiles
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized Structure getSmiles( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getSmiles().length ? smiles[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return mol File
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized File getMolFile( final int chebiId ) throws IOException
	{
		final Structure mol = getMol( chebiId );

		if( mol == null )
		{
			return null;
		}

		final File molFile = File.createTempFile( chebiId + "_", ".mol" ); //$NON-NLS-1$ //$NON-NLS-2$

		try ( BufferedWriter writer = new BufferedWriter( new FileWriter( molFile ) ) )
		{
			writer.write( mol.getStructure() );
			return molFile;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see libchebi.Parser#init()
	 */
	@Override
	protected void init() throws IOException
	{
		final int COMPOUND_ID = 1;
		final int STRUCTURE = 2;
		final int TYPE = 3;
		final int DIMENSION = 4;
		final int DEFAULT_TOKENS_LENGTH = 7;

		final String INCHI_KEY = Structure.Type.InChIKey.toString();
		final String SMILES = Structure.Type.SMILES.toString();

		final TreeMap<Integer,Structure> inchiKeysMap = new TreeMap<>();
		final TreeMap<Integer,Structure> smilesMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( structuresFile ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "," ); //$NON-NLS-1$

				if( tokens.length == DEFAULT_TOKENS_LENGTH )
				{
					if( tokens[ TYPE ].equals( INCHI_KEY ) )
					{
						final Structure structure = new Structure( tokens[ STRUCTURE ], Type.valueOf( tokens[ TYPE ] ), parseDimension( tokens[ DIMENSION ] ) );
						inchiKeysMap.put( Integer.valueOf( tokens[ COMPOUND_ID ] ), structure );
					}
					else if( tokens[ TYPE ].equals( SMILES ) )
					{
						final Structure structure = new Structure( tokens[ STRUCTURE ], Type.valueOf( tokens[ TYPE ] ), parseDimension( tokens[ DIMENSION ] ) );
						smilesMap.put( Integer.valueOf( tokens[ COMPOUND_ID ] ), structure );
					}
				}
			}
		}

		inchiKeys = ParserUtils.mapToStructureArray( inchiKeysMap );
		smiles = ParserUtils.mapToStructureArray( smilesMap );
	}

	/**
	 * 
	 * @return Array of InchiKeys, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized Structure[] getInchiKeys() throws IOException, ParseException
	{
		checkInit();
		return inchiKeys;
	}

	/**
	 * 
	 * @return Array of Smiles, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized Structure[] getSmiles() throws IOException, ParseException
	{
		checkInit();
		return smiles;
	}

	/**
	 * 
	 * @param dimension
	 * @return int
	 */
	private static int parseDimension( final String dimension )
	{
		final int DIMENSION_CHAR = 0;
		return dimension.charAt( DIMENSION_CHAR ) - '0';
	}

	private static Boolean isDefaultStructure(final String defaultStructure)
	{
		return defaultStructure.equalsIgnoreCase("Y");

	}
}