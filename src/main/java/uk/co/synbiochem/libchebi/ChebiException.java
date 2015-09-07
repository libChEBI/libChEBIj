/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.co.synbiochem.libchebi;

/**
 * @author neilswainston
 */
public class ChebiException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public ChebiException( final String message )
	{
		super( message );
	}
}