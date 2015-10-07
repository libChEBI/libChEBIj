/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.util.*;

/**
 * @author neilswainston
 */
public class Comment
{
	/**
	 * 
	 */
	private final String datatypeId;

	/**
	 * 
	 */
	private final String datatype;

	/**
	 * 
	 */
	private final String text;

	/**
	 * 
	 */
	private final Date createdOn;

	/**
	 * 
	 */
	Comment( final String datatypeId, final String datatype, final String text, final Date createdOn )
	{
		assert datatypeId != null;
		assert datatype != null;
		assert text != null;
		assert createdOn != null;

		this.datatypeId = datatypeId;
		this.datatype = datatype;
		this.text = text;
		this.createdOn = createdOn;
	}

	/**
	 * @return datatype
	 */
	public String getDatatype()
	{
		return datatype;
	}

	/**
	 * @return text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @return created on
	 */
	public Date getCreatedOn()
	{
		return createdOn;
	}
	
	/**
	 * Commonly, datatypeId is the same as datatype.
	 * 
	 * Method has been made package private to "hide" this duplicate.
	 * 
	 * @return datatype id
	 */
	String getDatatypeId()
	{
		return datatypeId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return createdOn + "\t" + datatypeId + "\t" + datatype + "\t" + text; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
		result = PRIME * result + datatype.hashCode();
		result = PRIME * result + datatypeId.hashCode();
		result = PRIME * result + text.hashCode();
		result = PRIME * result + createdOn.hashCode();
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
		if( !( obj instanceof Comment ) )
		{
			return false;
		}

		final Comment other = (Comment)obj;

		if( !datatype.equals( other.datatype ) )
		{
			return false;
		}

		if( !datatypeId.equals( other.datatypeId ) )
		{
			return false;
		}

		if( !text.equals( other.text ) )
		{
			return false;
		}

		if( !createdOn.equals( other.createdOn ) )
		{
			return false;
		}

		return true;
	}
}