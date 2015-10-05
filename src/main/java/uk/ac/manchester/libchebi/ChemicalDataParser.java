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
class ChemicalDataParser extends FileParser
{
	/**
	 * 
	 */
	private static ChemicalDataParser parser;

	/**
	 * 
	 */
	private List<List<Formula>> formulae;

	/**
	 * 
	 */
	private float[] masses;

	/**
	 * 
	 */
	private int[] charges;

	/**
	 * 
	 * @return singleton ChemicalDataParser
	 * @throws IOException
	 */
	synchronized static ChemicalDataParser getInstance() throws IOException
	{
		if( parser == null )
		{
			final File file = Downloader.getInstance().getFile( "chemical_data.tsv" ); //$NON-NLS-1$
			parser = new ChemicalDataParser( file );
		}

		return parser;
	}

	/**
	 * 
	 * @param file
	 */
	private ChemicalDataParser( final File file )
	{
		super( file );
	}

	/**
	 * 
	 * @param chebiId
	 * @return formulae
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized List<Formula> getFormulae( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getFormulae().size() ? formulae.get( chebiId ) : new ArrayList<Formula>();
	}

	/**
	 * 
	 * @param chebiIds
	 * @return formulae
	 * @throws ParseException
	 * @throws IOException
	 */
	synchronized List<Formula> getFormulae( final int[] chebiIds ) throws IOException, ParseException
	{
		final List<Formula> allFormulae = new ArrayList<>();

		for( int chebiId : chebiIds )
		{
			allFormulae.addAll( getFormulae( chebiId ) );
		}

		return allFormulae;
	}

	/**
	 * 
	 * @param chebiId
	 * @return mass
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized float getMass( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getMasses().length ? masses[ chebiId ] : ChebiEntity.UNDEFINED_VALUE;
	}

	/**
	 * 
	 * @param chebiId
	 * @return charge
	 * @throws IOException
	 * @throws ParseException
	 */
	synchronized int getCharge( final int chebiId ) throws IOException, ParseException
	{
		return chebiId > -1 && chebiId < getCharges().length ? charges[ chebiId ] : ChebiEntity.UNDEFINED_VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see libchebi.Parser#init()
	 */
	@Override
	protected void init() throws IOException
	{
		final String FORMULA = "FORMULA"; //$NON-NLS-1$
		final String MASS = "MASS"; //$NON-NLS-1$
		final String CHARGE = "CHARGE"; //$NON-NLS-1$
		// final String UNDEFINED_FORMULA = "."; //$NON-NLS-1$

		// final int ID = 0;
		final int COMPOUND_ID = 1;
		final int SOURCE = 2;
		final int TYPE = 3;
		final int CHEMICAL_DATA = 4;

		final TreeMap<Integer,List<Formula>> formulaeMap = new TreeMap<>();
		final TreeMap<Integer,Float> massMap = new TreeMap<>();
		final TreeMap<Integer,Integer> chargeMap = new TreeMap<>();

		try ( BufferedReader reader = new BufferedReader( new FileReader( file ) ) )
		{
			String line = reader.readLine(); // Read header

			while( ( line = reader.readLine() ) != null )
			{
				final String[] tokens = line.split( "\\t" ); //$NON-NLS-1$
				final Integer id = Integer.valueOf( tokens[ COMPOUND_ID ] );
				final String type = tokens[ TYPE ];
				final String chemicalData = tokens[ CHEMICAL_DATA ];

				if( type.equals( FORMULA ) )
				{
					ParserUtils.add( id, formulaeMap, new Formula( chemicalData, tokens[ SOURCE ] ) );
				}
				else if( type.equals( MASS ) )
				{
					/*
					 * if( massMap.get( id ) != null ) { // Only occurs once...
					 * // 1990562 28743 ChEBI MASS 1162.24960 // 1995179 28743
					 * ChEBI MASS 1177.28410 System.out.println( massMap.get( id
					 * ) + "\t" + chemicalData ); //$NON-NLS-1$ }
					 */

					massMap.put( id, Float.valueOf( chemicalData ) );
				}
				else if( type.equals( CHARGE ) )
				{
					chargeMap.put( id, Integer.valueOf( chemicalData.replaceAll( "\\+", "" ) ) ); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}

		formulae = ParserUtils.mapToList( formulaeMap );
		masses = ParserUtils.mapToFloatArray( massMap );
		charges = ParserUtils.mapToIntArray( chargeMap );
	}

	/**
	 * 
	 * @return List of formulae, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized List<List<Formula>> getFormulae() throws IOException, ParseException
	{
		checkInit();
		return formulae;
	}

	/**
	 * 
	 * @return array of masses, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized float[] getMasses() throws IOException, ParseException
	{
		checkInit();
		return masses;
	}

	/**
	 * 
	 * @return array of charges, indexed by ChEBI id
	 * @throws IOException
	 * @throws ParseException
	 */
	private synchronized int[] getCharges() throws IOException, ParseException
	{
		checkInit();
		return charges;
	}
}