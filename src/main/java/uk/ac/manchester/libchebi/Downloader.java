/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
package uk.ac.manchester.libchebi;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

/**
 * @author neilswainston
 */
class Downloader
{
	/**
	 * 
	 */
	private final static String GZIP_SUFFIX = ".gz"; //$NON-NLS-1$

	/**
	 * 
	 */
	private static Downloader downloader;

	/**
	 * 
	 */
	private final String source;

	/**
	 * 
	 */
	public final File destination;
	
	
	/**
	 * 
	 * @return singleton Downloader
	 */
	synchronized static Downloader getInstance()
	{
		if( downloader == null )
		{
			final String source = "ftp://ftp.ebi.ac.uk/pub/databases/chebi/Flat_file_tab_delimited/"; //$NON-NLS-1$
			final File destination = new File( System.getProperty( "user.home" ), "libChEBI" ); //$NON-NLS-1$ //$NON-NLS-2$
			downloader = new Downloader( source, destination );
		}

		return downloader;
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 */
	private Downloader( final String source, final File destination )
	{
		this.source = source;
		this.destination = destination;
	}

	/**
	 * 
	 * @param filename
	 * @return the file
	 * @throws ZipException
	 * @throws IOException
	 */
	File getFile( final String filename ) throws ZipException, IOException
	{
		final File destinationFile = new File( destination, filename );

		if( !isCurrent( destinationFile ) )
		{
			if( !destination.exists() )
			{
				if( !destination.mkdirs() )
				{
					throw new IOException( "Unable to make directory: " + destination ); //$NON-NLS-1$
				}
			}

			try ( InputStream is = new URL( source + filename ).openStream(); OutputStream os = new FileOutputStream( destinationFile ) )
			{
				copy( is, os );
			}
		}

		if( filename.endsWith( ".zip" ) ) //$NON-NLS-1$
		{
			return unzip( destinationFile, destination );
		}
		else if( filename.endsWith( GZIP_SUFFIX ) )
		{
			return ungzip( destinationFile, destination );
		}

		return destinationFile;
	}

	/**
	 * 
	 * @param file
	 * @return true if file is current, false otherwise (download required)
	 */
	private static boolean isCurrent( final File file )
	{
		if( !file.exists() )
		{
			return false;
		}

		return file.lastModified() > getLastUpdateTime();
	}

	/**
	 * 
	 * @return the last update time
	 */
	private static long getLastUpdateTime()
	{
		final Calendar calendar = Calendar.getInstance( TimeZone.getTimeZone( "UTC" ) ); //$NON-NLS-1$
		calendar.setTimeInMillis( System.currentTimeMillis() );
		final int currentMonth = calendar.get( Calendar.MONTH );
		long lastUpdateTime = Long.MAX_VALUE;

		// First Tuesday in the month:
		while( calendar.get( Calendar.MONTH ) == currentMonth )
		{
			calendar.add( Calendar.DAY_OF_MONTH, -1 );

			if( calendar.get( Calendar.DAY_OF_WEEK ) == Calendar.TUESDAY )
			{
				lastUpdateTime = calendar.getTime().getTime();
			}
		}

		return lastUpdateTime;
	}

	/**
	 * Assumes a single file in the zip.
	 * 
	 * @param sourceFile
	 * @param destinationDir
	 * @return the last File generated from the unzip
	 * @throws ZipException
	 * @throws IOException
	 */
	private static File unzip( final File sourceFile, final File destinationDir ) throws ZipException, IOException
	{
		try ( final ZipFile zipFile = new ZipFile( sourceFile ) )
		{
			final Enumeration<? extends ZipEntry> entries = zipFile.entries();
			File entryDestination = null;

			while( entries.hasMoreElements() )
			{
				final ZipEntry entry = entries.nextElement();
				entryDestination = new File( destinationDir, entry.getName() );
				entryDestination.getParentFile().mkdirs();

				try ( InputStream is = zipFile.getInputStream( entry ); OutputStream os = new FileOutputStream( entryDestination ) )
				{
					copy( is, os );
				}
			}

			return entryDestination;
		}
	}

	/**
	 * Assumes a single file in the gzip.
	 * 
	 * @param sourceFile
	 * @param destinationDir
	 * @return the last File generated from the unzip
	 * @throws ZipException
	 * @throws IOException
	 */
	private static File ungzip( final File sourceFile, final File destinationDir ) throws ZipException, IOException
	{
		final int BUFFER_SIZE = 1024 * 16;
		final byte[] buffer = new byte[ BUFFER_SIZE ];

		try ( final GZIPInputStream gzis = new GZIPInputStream( new FileInputStream( sourceFile ) ) )
		{
			final File entryDestination = new File( destinationDir, sourceFile.getName().replaceAll( GZIP_SUFFIX, "" ) ); //$NON-NLS-1$
			entryDestination.getParentFile().mkdirs();

			try ( final FileOutputStream out = new FileOutputStream( entryDestination ) )
			{
				int len;

				while( ( len = gzis.read( buffer ) ) > 0 )
				{
					out.write( buffer, 0, len );
				}
			}

			return entryDestination;
		}
	}

	/**
	 * 
	 * @param is
	 * @param os
	 * @throws IOException
	 */
	private static void copy( final InputStream is, final OutputStream os ) throws IOException
	{
		final int BUFFER_SIZE = 1024 * 8;
		final int EOF = -1;
		final byte[] buffer = new byte[ BUFFER_SIZE ];

		int n = 0;

		while( ( n = is.read( buffer ) ) != EOF )
		{
			os.write( buffer, 0, n );
		}
	}
}