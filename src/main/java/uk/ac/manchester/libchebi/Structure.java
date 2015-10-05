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
class Structure
{
	/**
	 * 
	 * @author neilswainston
	 */
	public static enum Type
	{
		InChIKey, mol, SMILES
	}

	/**
	 * 
	 */
	private final String structure;

	/**
	 * 
	 */
	private final Type type;

	/**
	 * 
	 */
	private final int dimension;

	/**
	 * 
	 * @param structure
	 * @param type
	 * @param dimension
	 */
	Structure( final String structure, final Type type, final int dimension )
	{
		assert structure != null;
		assert type != null;

		this.structure = structure;
		this.type = type;
		this.dimension = dimension;
	}

	/**
	 * @return structure
	 */
	public String getStructure()
	{
		return structure;
	}

	/**
	 * @return type
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * @return dimension
	 */
	public int getDimension()
	{
		return dimension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return structure + "\t" + type + "\t" + dimension + "D"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
		result = PRIME * result + dimension;
		result = PRIME * result + structure.hashCode();
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
		if( !( obj instanceof Structure ) )
		{
			return false;
		}

		Structure other = (Structure)obj;

		if( dimension != other.dimension )
		{
			return false;
		}

		if( !structure.equals( other.structure ) )
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