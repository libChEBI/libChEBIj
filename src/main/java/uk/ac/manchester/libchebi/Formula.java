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
public class Formula extends SourcedData
{
	/**
	 * 
	 */
	private final String formula;

	/**
	 * 
	 * @param formula
	 * @param source
	 */
	Formula( final String formula, final String source )
	{
		super( source );

		assert formula != null;

		this.formula = formula;
	}

	/**
	 * @return formula
	 */
	public String getFormula()
	{
		return formula;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return source + "\t" + formula; //$NON-NLS-1$
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
		result = PRIME * result + formula.hashCode();
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

		if( !( obj instanceof Formula ) )
		{
			return false;
		}

		final Formula other = (Formula)obj;

		if( !formula.equals( other.formula ) )
		{
			return false;
		}

		return super.equals( other );
	}
}