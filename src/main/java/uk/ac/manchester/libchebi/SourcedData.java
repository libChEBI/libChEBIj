/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

/**
 * @author neilswainston
 */
abstract class SourcedData implements Comparable<SourcedData>
{
	/**
	 * 
	 */
	private final static String CHEBI_SOURCE = "ChEBI"; //$NON-NLS-1$

	/**
	 * 
	 */
	protected final String source;

	/**
	 * 
	 * @param data
	 * @param source
	 */
	SourcedData( final String source )
	{
		assert source != null;

		this.source = source;
	}

	/**
	 * @return source
	 */
	public String getSource()
	{
		return source;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + source.hashCode();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if( this == obj )
		{
			return true;
		}
		if( obj == null )
		{
			return false;
		}
		if( !( obj instanceof SourcedData ) )
		{
			return false;
		}

		final SourcedData other = (SourcedData)obj;

		if( !source.equals( other.source ) )
		{
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo( final SourcedData o )
	{
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if( this == o )
		{
			return EQUAL;
		}

		if( source.equals( CHEBI_SOURCE ) )
		{
			if( o.source.equals( CHEBI_SOURCE ) )
			{
				return EQUAL;
			}
			// else
			return BEFORE;
		}

		if( o.source.equals( CHEBI_SOURCE ) )
		{
			return AFTER;
		}

		return source.compareTo( o.source );
	}
}