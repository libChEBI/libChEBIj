/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.io.*;

/**
 * @author neilswainston
 */
class MolTestUtils
{
	/**
	 * 
	 * @return String
	 * @throws IOException
	 */
	static String readMol( final int id ) throws IOException
	{
		return readMol( MolTestUtils.class.getResourceAsStream( "ChEBI_" + id + ".mol" ) ); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * 
	 * @return String
	 * @throws IOException
	 */
	static String readMol( final InputStream is ) throws IOException
	{
		final String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$
		final StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader( new InputStreamReader( is ) );
			String line = null; // Read header

			while( ( line = reader.readLine() ) != null )
			{
				stringBuilder.append( line );
				stringBuilder.append( LINE_SEPARATOR );
			}

			return stringBuilder.toString();
		}
		finally
		{
			if( reader != null )
			{
				reader.close();
			}
		}
	}
}