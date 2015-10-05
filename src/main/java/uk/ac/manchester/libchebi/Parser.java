/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.io.*;
import java.text.*;

/**
 * @author neilswainston
 */
abstract class Parser
{
	/**
	 * 
	 */
	private boolean init = false;

	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	protected synchronized void checkInit() throws IOException, ParseException
	{
		if( !init )
		{
			init();
			init = true;
		}
	}

	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	protected abstract void init() throws IOException, ParseException;
}