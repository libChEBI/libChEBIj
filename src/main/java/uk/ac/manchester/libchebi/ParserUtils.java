/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.text.*;
import java.util.*;

/**
 * @author neilswainston
 */
class ParserUtils
{
	/**
	 * 
	 */
	private static final DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd", Locale.ENGLISH ); //$NON-NLS-1$

	/**
	 * 
	 * @param date
	 * @return parsed Date object
	 * @throws ParseException
	 */
	static Date parseDate( final String date ) throws ParseException
	{
		return dateFormat.parse( date );
	}

	/**
	 * 
	 * @param map
	 * @return float[]
	 */
	static float[] mapToFloatArray( final TreeMap<Integer,Float> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final float[] array = new float[ lastIndex + 1 ];
		Arrays.fill( array, ChebiEntity.UNDEFINED_VALUE );

		for( Map.Entry<Integer,Float> entry : map.entrySet() )
		{
			array[ entry.getKey().intValue() ] = entry.getValue().floatValue();
		}

		return array;
	}

	/**
	 * 
	 * @param map
	 * @return int[]
	 */
	static int[] mapToIntArray( final TreeMap<Integer,Integer> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final int[] array = new int[ lastIndex + 1 ];
		Arrays.fill( array, ChebiEntity.UNDEFINED_VALUE );

		for( Map.Entry<Integer,Integer> entry : map.entrySet() )
		{
			array[ entry.getKey().intValue() ] = entry.getValue().intValue();
		}

		return array;
	}

	/**
	 * 
	 * @param map
	 * @return short[]
	 */
	static short[] mapToShortArray( final TreeMap<Integer,Short> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final short[] array = new short[ lastIndex + 1 ];
		Arrays.fill( array, ChebiEntity.UNDEFINED_VALUE );

		for( Map.Entry<Integer,Short> entry : map.entrySet() )
		{
			array[ entry.getKey().intValue() ] = entry.getValue().shortValue();
		}

		return array;
	}

	/**
	 * 
	 * @param map
	 * @return String[]
	 */
	static String[] mapToStringArray( final TreeMap<Integer,String> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final String[] array = new String[ lastIndex + 1 ];

		for( Map.Entry<Integer,String> entry : map.entrySet() )
		{
			array[ entry.getKey().intValue() ] = entry.getValue();
		}

		return array;
	}

	/**
	 * 
	 * @param map
	 * @return Date[]
	 */
	static Date[] mapToDateArray( final TreeMap<Integer,Date> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final Date[] array = new Date[ lastIndex + 1 ];

		for( Map.Entry<Integer,Date> entry : map.entrySet() )
		{
			array[ entry.getKey().intValue() ] = entry.getValue();
		}

		return array;
	}

	/**
	 * 
	 * @param map
	 * @return Structure[]
	 */
	static Structure[] mapToStructureArray( final TreeMap<Integer,Structure> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final Structure[] array = new Structure[ lastIndex + 1 ];

		for( Map.Entry<Integer,Structure> entry : map.entrySet() )
		{
			array[ entry.getKey().intValue() ] = entry.getValue();
		}

		return array;
	}

	/**
	 * 
	 * @param map
	 * @return int[][]
	 */
	static int[][] mapTo2dIntArray( final TreeMap<Integer,List<Integer>> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final int capacity = lastIndex + 1;
		final int[][] array = new int[ capacity ][];

		for( Map.Entry<Integer,List<Integer>> entry : map.entrySet() )
		{
			final int i = entry.getKey().intValue();
			final List<Integer> values = entry.getValue();

			array[ i ] = new int[ values.size() ];

			for( int j = 0; j < values.size(); j++ )
			{
				array[ i ][ j ] = values.get( j ).intValue();
			}
		}

		return array;
	}

	/**
	 * 
	 * @param map
	 * @return List<Collection<T>>
	 */
	static <T> List<List<T>> mapToList( final TreeMap<Integer,List<T>> map )
	{
		final int lastIndex = map.lastKey().intValue();
		final int capacity = lastIndex + 1;
		final List<List<T>> list = new ArrayList<>( capacity );

		for( int i = 0; i < capacity; i++ )
		{
			list.add( new ArrayList<T>() );
		}

		for( Map.Entry<Integer,List<T>> entry : map.entrySet() )
		{
			list.set( entry.getKey().intValue(), entry.getValue() );
		}

		return list;
	}

	/**
	 * 
	 * @param id
	 * @param map
	 * @param object
	 */
	static <T> void add( final Integer id, final TreeMap<Integer,List<T>> map, T object )
	{
		List<T> list = map.get( id );

		if( list == null )
		{
			list = new ArrayList<>();
			map.put( id, list );
		}

		list.add( object );
	}
}