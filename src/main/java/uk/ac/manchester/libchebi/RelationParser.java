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
class RelationParser extends Parser
{
	/**
	 * 
	 */
	private static RelationParser parser;

	/**
	 * 
	 */
	private final File relationFile;

	/**
	 * 
	 */
	private List<List<Relation>> outgoings;

	/**
	 * 
	 */
	private List<List<Relation>> incomings;

	/**
	 * 
	 * @return singleton RelationParser
	 * @throws IOException
	 */
	synchronized static RelationParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File relationFile = Downloader.getInstance().getFile( "relation.tsv" ); //$NON-NLS-1$
			parser = new RelationParser( relationFile );
		}

		return parser;
	}

	/**
	 * 
	 * @param relationFile
	 */
	private RelationParser( final File relationFile)
	{
		this.relationFile = relationFile;
	}

	/**
	 * 
	 * @param chebiId
	 * @return outgoing relations
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Relation> getOutgoings( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getOutgoings().size() ? outgoings.get( chebiId ) : new ArrayList<Relation>();
	}

	/**
	 * 
	 * @param chebiIds
	 * @return outgoing relations
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Relation> getOutgoings( final int[] chebiIds ) throws IOException, ParseException
	{
		final List<Relation> allOutgoings = new ArrayList<>();

		for( int chebiId : chebiIds )
		{
			allOutgoings.addAll( getOutgoings( chebiId ) );
		}

		return allOutgoings;
	}

	/**
	 * 
	 * @param chebiId
	 * @return incoming relations
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Relation> getIncomings( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getIncomings().size() ? incomings.get( chebiId ) : new ArrayList<Relation>();
	}

	/**
	 * 
	 * @param chebiIds
	 * @return incoming relations
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Relation> getIncomings( final int[] chebiIds ) throws IOException, ParseException
	{
		final List<Relation> allIncomings = new ArrayList<>();

		for( int chebiId : chebiIds )
		{
			allIncomings.addAll( getIncomings( chebiId ) );
		}

		return allIncomings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see libchebi.Parser#init()
	 */
	@Override
	protected void init() throws IOException
	{

		// final int ID_RELATION = 0;
		final int TYPE_RELATION = 1;
		final int INIT_ID_RELATION = 2;
		final int FINAL_ID_RELATION = 3;
		final int STATUS_RELATION = 4;

		final TreeMap<Integer,List<Relation>> outgoingsMap = new TreeMap<>();
		final TreeMap<Integer,List<Relation>> incomingsMap = new TreeMap<>();

		try (BufferedReader relationReader = new BufferedReader( new FileReader( relationFile ) ) )
		{
			// Read relationFile:
			String reactionLine = relationReader.readLine(); // Read header

			while( ( reactionLine = relationReader.readLine() ) != null )
			{
				final String[] tokens = reactionLine.split( "\\t" ); //$NON-NLS-1$
				final Integer sourceChebiId = Integer.valueOf( tokens[ FINAL_ID_RELATION ] ) ;
				final Integer targetChebiId = Integer.valueOf( tokens[ INIT_ID_RELATION ] ) ;
				final Relation.Type type = Relation.Type.valueOf( ( tokens[ TYPE_RELATION ] ) );
				ParserUtils.add( sourceChebiId, outgoingsMap, new Relation( type, targetChebiId.toString(), tokens[ STATUS_RELATION ] ) );
				ParserUtils.add( targetChebiId, incomingsMap, new Relation( type, sourceChebiId.toString(), tokens[ STATUS_RELATION ] ) );
			}
		}

		outgoings = ParserUtils.mapToList( outgoingsMap );
		incomings = ParserUtils.mapToList( incomingsMap );
	}

	/**
	 * 
	 * @return List of outgoing relations, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized List<List<Relation>> getOutgoings() throws IOException, ParseException
	{
		checkInit();
		return outgoings;
	}

	/**
	 * 
	 * @return List of incoming relations, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized List<List<Relation>> getIncomings() throws IOException, ParseException
	{
		checkInit();
		return incomings;
	}
}