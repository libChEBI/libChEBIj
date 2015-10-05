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
abstract class FileParser extends Parser
{
	/**
	 * 
	 */
	protected final File file;

	/**
	 * 
	 * @param file
	 */
	FileParser( final File file )
	{
		this.file = file;
	}
}