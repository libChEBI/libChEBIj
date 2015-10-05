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
public class DatabaseAccession extends SourcedData
{
	/**
	 * 
	 */
	private final String type;

	/**
	 * 
	 */
	private final String accessionNumber;

	/**
	 * 
	 * @param type
	 * @param accessionNumber
	 * @param source
	 */
	DatabaseAccession( final String type, final String accessionNumber, final String source )
	{
		super( source );

		assert type != null;
		assert accessionNumber != null;

		this.type = type;
		this.accessionNumber = accessionNumber;
	}

	/**
	 * @return type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return accession number
	 */
	public String getAccessionNumber()
	{
		return accessionNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return source + "\t" + type + "\t" + accessionNumber; //$NON-NLS-1$ //$NON-NLS-2$
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
		int result = super.hashCode();
		result = PRIME * result + accessionNumber.hashCode();
		result = PRIME * result + type.hashCode();
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

		if( !( obj instanceof DatabaseAccession ) )
		{
			return false;
		}

		final DatabaseAccession other = (DatabaseAccession)obj;

		if( !type.equals( other.type ) )
		{
			return false;
		}

		if( !accessionNumber.equals( other.accessionNumber ) )
		{
			return false;
		}

		return super.equals( other );
	}
}