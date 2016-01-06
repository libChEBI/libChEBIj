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
public class Relation
{
	/**
	 * 
	 * @author neilswainston
	 */
	public static enum Type
	{
		/**
		 * 
		 */
		has_functional_parent,

		/**
		 * 
		 */
		has_parent_hydride,

		/**
		 * 
		 */
		has_part,

		/**
		 * 
		 */
		has_role,

		/**
		 * 
		 */
		is_a,

		/**
		 * 
		 */
		is_conjugate_acid_of,

		/**
		 * 
		 */
		is_conjugate_base_of,

		/**
		 * 
		 */
		is_enantiomer_of,

		/**
		 * 
		 */
		is_substituent_group_from,

		/**
		 * 
		 */
		is_tautomer_of
	}

	/**
	 * 
	 */
	private final Type type;

	/**
	 * 
	 */
	private final int targetChebiId;

	/**
	 * 
	 */
	private final String status;

	/**
	 * 
	 * @param type
	 * @param targetChebiId
	 * @param status
	 */
	Relation( final Type type, final String targetChebiId, final String status )
	{
		assert type != null;
		assert status != null;

		this.type = type;
		this.targetChebiId = Integer.parseInt( targetChebiId.replace( "CHEBI:", "" ) ); //$NON-NLS-1$ //$NON-NLS-2$
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * @return target ChEBI id
	 */
	public String getTargetChebiId()
	{
		return "CHEBI:" + targetChebiId; //$NON-NLS-1$
	}

	/**
	 * @return status
	 */
	String getStatus()
	{
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return type + "\t" + getTargetChebiId() + "\t" + status; //$NON-NLS-1$ //$NON-NLS-2$
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
		result = PRIME * result + status.hashCode();
		result = PRIME * result + targetChebiId;
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
		if( !( obj instanceof Relation ) )
		{
			return false;
		}

		Relation other = (Relation)obj;

		if( !status.equals( other.status ) )
		{
			return false;
		}
		if( targetChebiId != other.targetChebiId )
		{
			return false;
		}
		if( type != other.type )
		{
			return false;
		}

		return true;
	}
}