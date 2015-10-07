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
public class CompoundOrigin
{
	/**
	 * 
	 */
	private final String speciesText;

	/**
	 * 
	 */
	private final String speciesAccession;

	/**
	 * 
	 */
	private final String componentText;

	/**
	 * 
	 */
	private final String componentAccession;

	/**
	 * 
	 */
	private final String strainText;

	/**
	 * 
	 */
	private final String strainAccession;

	/**
	 * 
	 */
	private final String sourceType;

	/**
	 * 
	 */
	private final String sourceAccession;

	/**
	 * 
	 */
	private final String comments;

	/**
	 * 
	 * @param speciesText
	 * @param speciesAccession
	 * @param componentText
	 * @param componentAccession
	 * @param strainText
	 * @param strainAccession
	 * @param sourceType
	 * @param sourceAccession
	 * @param comments
	 */
	CompoundOrigin( final String speciesText, final String speciesAccession, final String componentText, final String componentAccession, final String strainText, final String strainAccession, final String sourceType, final String sourceAccession, final String comments )
	{
		assert speciesText != null;
		assert speciesAccession != null;
		assert sourceType != null;
		assert sourceAccession != null;

		final String NULL = "null"; //$NON-NLS-1$

		this.speciesText = NULL.equals( speciesText ) ? null : speciesText;
		this.speciesAccession = NULL.equals( speciesAccession ) ? null : speciesAccession;
		this.componentText = NULL.equals( componentText ) ? null : componentText;
		this.componentAccession = NULL.equals( componentAccession ) ? null : componentAccession;
		this.strainText = NULL.equals( strainText ) ? null : strainText;
		this.strainAccession = NULL.equals( strainAccession ) ? null : strainAccession;
		this.sourceType = NULL.equals( sourceType ) ? null : sourceType;
		this.sourceAccession = NULL.equals( sourceAccession ) ? null : sourceAccession;
		this.comments = NULL.equals( comments ) ? null : comments;
	}

	/**
	 * @return species text
	 */
	public String getSpeciesText()
	{
		return speciesText;
	}

	/**
	 * @return component text
	 */
	public String getComponentText()
	{
		return componentText;
	}

	/**
	 * @return component accession
	 */
	public String getComponentAccession()
	{
		return componentAccession;
	}

	/**
	 * @return strain text
	 */
	public String getStrainText()
	{
		return strainText;
	}

	/**
	 * @return strain accession
	 */
	public String getStrainAccession()
	{
		return strainAccession;
	}

	/**
	 * @return source type
	 */
	public String getSourceType()
	{
		return sourceType;
	}

	/**
	 * @return source accession
	 */
	public String getSourceAccession()
	{
		return sourceAccession;
	}

	/**
	 * @return comments
	 */
	public String getComments()
	{
		return comments;
	}
	
	/**
	 * Appears that this value is always null.
	 * 
	 * Therefore method made package private to "hide" it.
	 * 
	 * @return species accession
	 */
	String getSpeciesAccession()
	{
		return speciesAccession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return speciesText + "\t" + speciesAccession + "\t" + componentText + "\t" + componentAccession + "\t" + strainText + "\t" + strainAccession + "\t" + sourceType + "\t" + sourceAccession + "\t" + comments; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
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
		result = PRIME * result + ( ( comments == null ) ? 0 : comments.hashCode() );
		result = PRIME * result + ( ( componentAccession == null ) ? 0 : componentAccession.hashCode() );
		result = PRIME * result + ( ( componentText == null ) ? 0 : componentText.hashCode() );
		result = PRIME * result + ( ( sourceAccession == null ) ? 0 : sourceAccession.hashCode() );
		result = PRIME * result + ( ( sourceType == null ) ? 0 : sourceType.hashCode() );
		result = PRIME * result + ( ( speciesAccession == null ) ? 0 : speciesAccession.hashCode() );
		result = PRIME * result + ( ( speciesText == null ) ? 0 : speciesText.hashCode() );
		result = PRIME * result + ( ( strainAccession == null ) ? 0 : strainAccession.hashCode() );
		result = PRIME * result + ( ( strainText == null ) ? 0 : strainText.hashCode() );
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

		if( !( obj instanceof CompoundOrigin ) )
		{
			return false;
		}

		CompoundOrigin other = (CompoundOrigin)obj;

		if( comments == null )
		{
			if( other.comments != null )
			{
				return false;
			}
		}
		else if( !comments.equals( other.comments ) )
		{
			return false;
		}

		if( componentAccession == null )
		{
			if( other.componentAccession != null )
			{
				return false;
			}
		}
		else if( !componentAccession.equals( other.componentAccession ) )
		{
			return false;
		}

		if( componentText == null )
		{
			if( other.componentText != null )
			{
				return false;
			}
		}
		else if( !componentText.equals( other.componentText ) )
		{
			return false;
		}

		if( sourceAccession == null )
		{
			if( other.sourceAccession != null )
			{
				return false;
			}
		}
		else if( !sourceAccession.equals( other.sourceAccession ) )
		{
			return false;
		}

		if( sourceType == null )
		{
			if( other.sourceType != null )
			{
				return false;
			}
		}
		else if( !sourceType.equals( other.sourceType ) )
		{
			return false;
		}

		if( speciesAccession == null )
		{
			if( other.speciesAccession != null )
			{
				return false;
			}
		}
		else if( !speciesAccession.equals( other.speciesAccession ) )
		{
			return false;
		}

		if( speciesText == null )
		{
			if( other.speciesText != null )
			{
				return false;
			}
		}
		else if( !speciesText.equals( other.speciesText ) )
		{
			return false;
		}

		if( strainAccession == null )
		{
			if( other.strainAccession != null )
			{
				return false;
			}
		}
		else if( !strainAccession.equals( other.strainAccession ) )
		{
			return false;
		}

		if( strainText == null )
		{
			if( other.strainText != null )
			{
				return false;
			}
		}
		else if( !strainText.equals( other.strainText ) )
		{
			return false;
		}

		return true;
	}

}