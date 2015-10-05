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
public class Reference
{
	/**
	 * 
	 */
	private final String referenceId;

	/**
	 * 
	 */
	private final String referenceDbName;

	/**
	 * 
	 */
	private final String locationInRef;

	/**
	 * 
	 */
	private final String referenceName;

	/**
	 * 
	 * @param referenceId
	 * @param referenceDbName
	 * @param locationInRef
	 * @param referenceName
	 */
	Reference( final String referenceId, final String referenceDbName, final String locationInRef, final String referenceName )
	{
		assert referenceId != null;
		assert referenceDbName != null;

		this.referenceId = referenceId;
		this.referenceDbName = referenceDbName;
		this.locationInRef = locationInRef;
		this.referenceName = referenceName;
	}

	/**
	 * 
	 * @param referenceId
	 * @param referenceDbName
	 */
	Reference( final String referenceId, final String referenceDbName )
	{
		this( referenceId, referenceDbName, null, null );
	}

	/**
	 * @return reference id
	 */
	public String getReferenceId()
	{
		return referenceId;
	}

	/**
	 * @return reference database name
	 */
	public String getReferenceDbName()
	{
		return referenceDbName;
	}

	/**
	 * @return location in reference
	 */
	public String getLocationInRef()
	{
		return locationInRef;
	}

	/**
	 * @return reference name
	 */
	public String getReferenceName()
	{
		return referenceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return referenceId + "\t" + referenceDbName + "\t" + locationInRef + "\t" + referenceName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
		result = PRIME * result + referenceId.hashCode();
		result = PRIME * result + referenceDbName.hashCode();
		result = PRIME * result + ( ( locationInRef == null ) ? 0 : locationInRef.hashCode() );
		result = PRIME * result + ( ( referenceName == null ) ? 0 : referenceName.hashCode() );
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
		if( !( obj instanceof Reference ) )
		{
			return false;
		}

		final Reference other = (Reference)obj;

		if( !referenceId.equals( other.referenceId ) )
		{
			return false;
		}

		if( !referenceDbName.equals( other.referenceDbName ) )
		{
			return false;
		}

		if( locationInRef == null )
		{
			if( other.locationInRef != null )
			{
				return false;
			}
		}
		else if( !locationInRef.equals( other.locationInRef ) )
		{
			return false;
		}

		if( referenceName == null )
		{
			if( other.referenceName != null )
			{
				return false;
			}
		}
		else if( !referenceName.equals( other.referenceName ) )
		{
			return false;
		}

		return true;
	}
}