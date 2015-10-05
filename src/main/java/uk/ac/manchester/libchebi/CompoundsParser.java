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
class CompoundsParser extends FileParser
{
	/**
	 * 
	 */
	private static CompoundsParser parser;

	/**
	 * 
	 */
	private String[] statuses;

	/**
	 * 
	 */
	private String[] sources;

	/**
	 * 
	 */
	private int[] parentIds;

	/**
	 * 
	 */
	private int[][] allIds;

	/**
	 * 
	 */
	private String[] names;

	/**
	 * 
	 */
	private String[] definitions;

	/**
	 * 
	 */
	private Date[] modifiedOns;

	/**
	 * 
	 */
	private String[] createdBys;

	/**
	 * 
	 */
	private short[] stars;

	/**
	 * 
	 * @return singleton CompoundsParser
	 * @throws IOException
	 */
	synchronized static CompoundsParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "compounds.tsv.gz" ); //$NON-NLS-1$
			parser = new CompoundsParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private CompoundsParser( final File file )
	{
		super( file );
	}

	/**
	 * 
	 * @param chebiId
	 * @return status
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized String getStatus( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getStatuses().length ? statuses[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return source
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized String getSource( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getSources().length ? sources[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return parentId
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized int getParentId( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getParentIds().length ? parentIds[ chebiId ] : ChebiEntity.UNDEFINED_VALUE;
	}

	/**
	 * 
	 * @param chebiId
	 * @return allIds
	 * @throws ParseException
	 * @throws IOException
	 */
	synchronized int[] getAllIds( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getAllIds().length ? allIds[ chebiId ] : new int[ 0 ];
	}

	/**
	 * 
	 * @param chebiId
	 * @return name
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized String getName( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getNames().length ? names[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return definition
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized String getDefinition( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getDefinitions().length ? definitions[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return modifiedOn
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized Date getModifiedOn( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getModifiedOns().length ? modifiedOns[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiIds
	 * @return modifiedOn
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized Date getModifiedOn( final int[] chebiIds ) throws IOException, ParseException
	{
		final Set<Date> allDates = new TreeSet<>();

		for( int chebiId : chebiIds )
		{
			final Date date = getModifiedOn( chebiId );

			if( date != null )
			{
				allDates.add( date );
			}
		}

		return allDates.size() == 0 ? null : new ArrayList<>( allDates ).get( allDates.size() - 1 );
	}

	/**
	 * 
	 * @param chebiId
	 * @return star
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized String getCreatedBy( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getCreatedBys().length ? createdBys[ chebiId ] : null;
	}

	/**
	 * 
	 * @param chebiId
	 * @return star
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized short getStar( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getStars().length ? stars[ chebiId ] : ChebiEntity.UNDEFINED_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see libchebi.parser.Parser#init()
	 */
	@Override
	protected void init() throws IOException, ParseException
	{
		final String NULL = "null"; //$NON-NLS-1$

		final int ID = 0;
		final int STATUS = 1;
		// final int CHEBI_ACCESSION = 2;
		final int SOURCE = 3;
		final int PARENT_ID = 4;
		final int NAME = 5;
		final int DEFINITION = 6;
		final int MODIFIED_ON = 7;
		final int CREATED_BY = 8;
		final int STAR = 9;
		final int STAR_SECONDARY = 8; // Temporary "hack" while compounds.tsv
										// flatfile is fixed.

		final TreeMap<Integer,String> statusMap = new TreeMap<>();
		final TreeMap<Integer,String> sourceMap = new TreeMap<>();
		final TreeMap<Integer,Integer> parentIdMap = new TreeMap<>();
		final TreeMap<Integer,List<Integer>> allIdsMap = new TreeMap<>();
		final TreeMap<Integer,String> nameMap = new TreeMap<>();
		final TreeMap<Integer,String> definitionMap = new TreeMap<>();
		final TreeMap<Integer,Date> modifiedOnMap = new TreeMap<>();
		final TreeMap<Integer,String> createdByMap = new TreeMap<>();
		final TreeMap<Integer,Short> starMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$
				final Integer id = Integer.valueOf( tokens[ ID ] );
				statusMap.put( id, tokens[ STATUS ] );
				sourceMap.put( id, tokens[ SOURCE ] );
				putAllIds( allIdsMap, id, id );

				final String parentIdToken = tokens[ PARENT_ID ];

				if( !parentIdToken.equals( NULL ) )
				{
					final Integer parentId = Integer.valueOf( parentIdToken );
					parentIdMap.put( id, parentId );
					putAllIds( allIdsMap, parentId, id );
				}

				final String name = tokens[ NAME ];

				if( !name.equals( NULL ) )
				{
					nameMap.put( id, name );
				}

				final String definition = tokens[ DEFINITION ];

				if( !definition.equals( NULL ) )
				{
					definitionMap.put( id, definition );
				}

				final String modifiedOn = tokens[ MODIFIED_ON ];

				if( !modifiedOn.equals( NULL ) )
				{
					modifiedOnMap.put( id, ParserUtils.parseDate( modifiedOn ) );
				}

				// Temporary "hack" while compounds.tsv flatfile is fixed.
				final boolean allTokens = tokens.length > STAR;

				if( allTokens )
				{
					final String createdBy = tokens[ CREATED_BY ];

					if( !createdBy.equals( NULL ) )
					{
						createdByMap.put( id, createdBy );
					}
				}

				final String star = tokens[ allTokens ? STAR : STAR_SECONDARY ];

				if( !star.equals( NULL ) )
				{
					starMap.put( id, Short.valueOf( star ) );
				}
			}
		}

		statuses = ParserUtils.mapToStringArray( statusMap );
		sources = ParserUtils.mapToStringArray( sourceMap );
		parentIds = ParserUtils.mapToIntArray( parentIdMap );
		allIds = ParserUtils.mapTo2dIntArray( allIdsMap );
		names = ParserUtils.mapToStringArray( nameMap );
		definitions = ParserUtils.mapToStringArray( definitionMap );
		modifiedOns = ParserUtils.mapToDateArray( modifiedOnMap );
		createdBys = ParserUtils.mapToStringArray( createdByMap );
		stars = ParserUtils.mapToShortArray( starMap );
	}

	/**
	 * 
	 * @param allIdsMap
	 * @param parentId
	 * @param childId
	 */
	private static void putAllIds( final Map<Integer,List<Integer>> allIdsMap, final Integer parentId, final Integer childId )
	{
		List<Integer> parentAllIds = allIdsMap.get( parentId );

		if( parentAllIds == null )
		{
			parentAllIds = new ArrayList<>();
			allIdsMap.put( parentId, parentAllIds );
		}

		parentAllIds.add( childId );
	}

	/**
	 * 
	 * @return array of statuses, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized String[] getStatuses() throws IOException, ParseException
	{
		checkInit();
		return statuses;
	}

	/**
	 * 
	 * @return array of sources, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized String[] getSources() throws IOException, ParseException
	{
		checkInit();
		return sources;
	}

	/**
	 * 
	 * @return array of parentIds, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized int[] getParentIds() throws IOException, ParseException
	{
		checkInit();
		return parentIds;
	}

	/**
	 * 
	 * @return array of allIds, of all primary and secondary ids belonging to
	 *         one set, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized int[][] getAllIds() throws IOException, ParseException
	{
		checkInit();
		return allIds;
	}

	/**
	 * 
	 * @return array of names, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized String[] getNames() throws IOException, ParseException
	{
		checkInit();
		return names;
	}

	/**
	 * 
	 * @return array of definitions, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized String[] getDefinitions() throws IOException, ParseException
	{
		checkInit();
		return definitions;
	}

	/**
	 * 
	 * @return array of modifiedOns, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized Date[] getModifiedOns() throws IOException, ParseException
	{
		checkInit();
		return modifiedOns;
	}

	/**
	 * 
	 * @return array of createdBys, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized String[] getCreatedBys() throws IOException, ParseException
	{
		checkInit();
		return createdBys;
	}

	/**
	 * 
	 * @return array of stars, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized short[] getStars() throws IOException, ParseException
	{
		checkInit();
		return stars;
	}
}