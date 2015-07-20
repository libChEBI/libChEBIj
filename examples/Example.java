/*
 * libChEBIj (c) University of Manchester 2015
 *
 * libChEBIj is licensed under the MIT License.
 * 
 * To view a copy of this license, visit <http://opensource.org/licenses/MIT/>.
 */
import java.io.*;
import java.text.*;
import libchebi.*;

/**
 * @author neilswainston
 */
public class Example
{
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ChebiException 
	 */
	public static void main( String[] args ) throws IOException, ParseException, ChebiException
	{
		ChebiEntity chebiEntity = new ChebiEntity( 17634 );
		
		for( Name name : chebiEntity.getNames() )
		{
			System.out.println( name.getName() + "\t" + name.getSource() + "\t" + name.getLanguage() ); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}