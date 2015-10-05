/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.io.*;

import org.junit.*;

/**
 * @author neilswainston
 */
public class DownloaderTest
{
	/**
	 * @throws IOException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void getFile() throws IOException
	{
		Assert.assertNotNull( Downloader.getInstance().getFile( "chebiId_inchi.tsv" ) ); //$NON-NLS-1$
	}
}