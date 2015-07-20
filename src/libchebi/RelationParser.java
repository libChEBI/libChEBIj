/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package libchebi;

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
	private final File verticeFile;
	
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
			final File verticeFile = Downloader.getInstance().getFile( "vertice.tsv" ); //$NON-NLS-1$
			parser = new RelationParser( relationFile, verticeFile );
		}
		
		return parser;
	}
	
	/**
	 * 
	 * @param relationFile
	 * @param verticeFile
	 */
	private RelationParser( final File relationFile, final File verticeFile )
	{
		this.relationFile = relationFile;
		this.verticeFile = verticeFile;
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
	 * @see libchebi.Parser#init()
	 */
	@Override
	protected void init() throws IOException
	{
		final int ID_VERTICE = 0;
		final int COMPOUND_CHILD_ID_VERTICE = 1;
		
		// final int ID_REACTION = 0;
		final int TYPE_REACTION = 1;
		final int INIT_ID_REACTION = 2;
		final int FINAL_ID_REACTION = 3;
		final int STATUS_REACTION = 4;
		
		final TreeMap<String,String> verticeMap = new TreeMap<>();
		final TreeMap<Integer,List<Relation>> outgoingsMap = new TreeMap<>();
		final TreeMap<Integer,List<Relation>> incomingsMap = new TreeMap<>();
		
		try( BufferedReader verticeReader = new BufferedReader( new FileReader( verticeFile ) );
				BufferedReader relationReader = new BufferedReader( new FileReader( relationFile ) ) )
		{
			String verticeLine = verticeReader.readLine(); // Read header
				
			while( ( verticeLine = verticeReader.readLine() ) != null )
			{
				final String[] tokens = verticeLine.split( "\\t" ); //$NON-NLS-1$
				verticeMap.put( tokens[ ID_VERTICE ], tokens[ COMPOUND_CHILD_ID_VERTICE ] );
			}
			
			// Read relationFile:
			String reactionLine = relationReader.readLine(); // Read header
				
			while( ( reactionLine = relationReader.readLine() ) != null )
			{
				final String[] tokens = reactionLine.split( "\\t" ); //$NON-NLS-1$
				final Integer sourceChebiId = Integer.valueOf( verticeMap.get( tokens[ FINAL_ID_REACTION ] ) );
				final Integer targetChebiId = Integer.valueOf( verticeMap.get( tokens[ INIT_ID_REACTION ] ) );
				final Relation.Type type = Relation.Type.valueOf( ( tokens[ TYPE_REACTION ] ) );
				ParserUtils.add( sourceChebiId, outgoingsMap, new Relation( type, targetChebiId.intValue(), tokens[ STATUS_REACTION ] ) );
				ParserUtils.add( targetChebiId, incomingsMap, new Relation( type, sourceChebiId.intValue(), tokens[ STATUS_REACTION ] ) );
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