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
public class Name extends SourcedData
{
	/**
	 * 
	 */
	private final String name;

	/**
	 * 
	 */
	private final String type;

	/**
	 * 
	 */
	private final boolean adapted;

	/**
	 * 
	 */
	private final String language;

	/**
	 * 
	 * @param name
	 * @param type
	 * @param source
	 * @param adapted
	 * @param language
	 */
	Name( final String name, final String type, final String source, final boolean adapted, final String language )
	{
		super( source );

		assert name != null;
		assert type != null;
		assert source != null;
		assert language != null;

		this.name = name;
		this.type = type;
		this.adapted = adapted;
		this.language = language;
	}

	/**
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return adapted
	 */
	public boolean isAdapted()
	{
		return adapted;
	}

	/**
	 * @return language
	 */
	public String getLanguage()
	{
		return language;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return type + "\t" + source + "\t" + name + "\t" + adapted + "\t" + language; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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
		result = PRIME * result + ( adapted ? 1231 : 1237 );
		result = PRIME * result + language.hashCode();
		result = PRIME * result + name.hashCode();
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
		if( !( obj instanceof Name ) )
		{
			return false;
		}

		final Name other = (Name)obj;

		if( adapted != other.adapted )
		{
			return false;
		}

		if( !language.equals( other.language ) )
		{
			return false;
		}

		if( !name.equals( other.name ) )
		{
			return false;
		}

		if( !type.equals( other.type ) )
		{
			return false;
		}

		return super.equals( other );
	}
}