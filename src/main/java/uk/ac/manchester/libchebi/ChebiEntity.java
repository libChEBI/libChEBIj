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
import java.util.*;

/**
 * @author neilswainston
 */
public class ChebiEntity
{
	/**
	 * 
	 */
	public static final short UNDEFINED_VALUE = Short.MIN_VALUE;

	/**
	 * 
	 */
	private static final int FIRST = 0;

	/**
	 * 
	 */
	private final int id;

	/**
	 * 
	 */
	private int[] allIds = null;

	/**
	 * @throws ChebiException
	 * @throws ParseException
	 * @throws IOException
	 */
	public ChebiEntity( final String id ) throws IOException, ParseException, ChebiException
	{
		this.id = Integer.parseInt( id.replace( "CHEBI:", "" ) ); //$NON-NLS-1$ //$NON-NLS-2$

		if( getName() == null )
		{
			throw new ChebiException( "ChEBI id " + id + " invalid" ); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * 
	 * @return id
	 */
	public String getId()
	{
		return "CHEBI:" + id; //$NON-NLS-1$
	}

	/**
	 * 
	 * @return parent id
	 * @throws IOException
	 * @throws ParseException
	 */
	public int getParentId() throws IOException, ParseException
	{
		return CompoundsParser.getInstance().getParentId( id );
	}

	/**
	 * 
	 * @return formulae
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Formula> getFormulae() throws IOException, ParseException
	{
		return ChemicalDataParser.getInstance().getFormulae( getAllIds() );
	}

	/**
	 * 
	 * @return formula
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getFormula() throws IOException, ParseException
	{
		final List<Formula> formulae = getFormulae();
		return formulae.size() == 0 ? null : formulae.get( FIRST ).getFormula();
	}

	/**
	 * 
	 * @return mass
	 * @throws IOException
	 * @throws ParseException
	 */
	public float getMass() throws IOException, ParseException
	{
		float mass = ChemicalDataParser.getInstance().getMass( id );

		if( mass == ChebiEntity.UNDEFINED_VALUE )
		{
			mass = ChemicalDataParser.getInstance().getMass( getParentId() );

			if( mass != ChebiEntity.UNDEFINED_VALUE )
			{
				return mass;
			}

			for( final int childOrParentId : getAllIds() )
			{
				mass = ChemicalDataParser.getInstance().getMass( childOrParentId );

				if( mass != ChebiEntity.UNDEFINED_VALUE )
				{
					return mass;
				}
			}
		}

		return mass;
	}

	/**
	 * 
	 * @return charge
	 * @throws IOException
	 * @throws ParseException
	 */
	public int getCharge() throws IOException, ParseException
	{
		int charge = ChemicalDataParser.getInstance().getCharge( id );

		if( charge == ChebiEntity.UNDEFINED_VALUE )
		{
			charge = ChemicalDataParser.getInstance().getCharge( getParentId() );

			if( charge != ChebiEntity.UNDEFINED_VALUE )
			{
				return charge;
			}

			for( final int childOrParentId : getAllIds() )
			{
				charge = ChemicalDataParser.getInstance().getCharge( childOrParentId );

				if( charge != ChebiEntity.UNDEFINED_VALUE )
				{
					return charge;
				}
			}
		}

		return charge;
	}

	/**
	 * 
	 * @return comments
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Comment> getComments() throws IOException, ParseException
	{
		return CommentsParser.getInstance().getComments( getAllIds() );
	}

	/**
	 * 
	 * @return source
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getSource() throws IOException, ParseException
	{
		return CompoundsParser.getInstance().getSource( id );
	}

	/**
	 * 
	 * @return name
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getName() throws IOException, ParseException
	{
		String name = CompoundsParser.getInstance().getName( id );

		if( name == null )
		{
			name = CompoundsParser.getInstance().getName( getParentId() );

			if( name != null )
			{
				return name;
			}

			for( final int childOrParentId : getAllIds() )
			{
				name = CompoundsParser.getInstance().getName( childOrParentId );

				if( name != null )
				{
					return name;
				}
			}
		}

		return name;
	}

	/**
	 * 
	 * @return definition
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getDefinition() throws IOException, ParseException
	{
		String definition = CompoundsParser.getInstance().getDefinition( id );

		if( definition == null )
		{
			definition = CompoundsParser.getInstance().getDefinition( getParentId() );

			if( definition != null )
			{
				return definition;
			}

			for( final int childOrParentId : getAllIds() )
			{
				definition = CompoundsParser.getInstance().getDefinition( childOrParentId );

				if( definition != null )
				{
					return definition;
				}
			}
		}

		return definition;
	}

	/**
	 * 
	 * @return modified on
	 * @throws IOException
	 * @throws ParseException
	 */
	public Date getModifiedOn() throws IOException, ParseException
	{
		return CompoundsParser.getInstance().getModifiedOn( getAllIds() );
	}

	/**
	 * 
	 * @return created by
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getCreatedBy() throws IOException, ParseException
	{
		String createdBy = CompoundsParser.getInstance().getCreatedBy( id );

		if( createdBy == null )
		{
			createdBy = CompoundsParser.getInstance().getCreatedBy( getParentId() );

			if( createdBy != null )
			{
				return createdBy;
			}

			for( final int childOrParentId : getAllIds() )
			{
				createdBy = CompoundsParser.getInstance().getCreatedBy( childOrParentId );

				if( createdBy != null )
				{
					return createdBy;
				}
			}
		}

		return createdBy;
	}

	/**
	 * 
	 * @return star
	 * @throws IOException
	 * @throws ParseException
	 */
	public short getStar() throws IOException, ParseException
	{
		return CompoundsParser.getInstance().getStar( id );
	}

	/**
	 * 
	 * @return database accessions
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<DatabaseAccession> getDatabaseAccessions() throws IOException, ParseException
	{
		return DatabaseAccessionParser.getInstance().getDatabaseAccessions( getAllIds() );
	}

	/**
	 * 
	 * @return InChI string
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getInchi() throws IOException, ParseException
	{
		String inchi = InchiParser.getInstance().getInchi( id );

		if( inchi == null )
		{
			inchi = InchiParser.getInstance().getInchi( getParentId() );

			if( inchi != null )
			{
				return inchi;
			}

			for( final int childOrParentId : getAllIds() )
			{
				inchi = InchiParser.getInstance().getInchi( childOrParentId );

				if( inchi != null )
				{
					return inchi;
				}
			}
		}

		return inchi;
	}

	/**
	 * 
	 * @return InChI key string
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getInchiKey() throws IOException, ParseException
	{
		Structure structure = StructuresParser.getInstance().getInchiKey( id );

		if( structure != null )
		{
			return structure.getStructure();
		}

		structure = StructuresParser.getInstance().getInchiKey( getParentId() );

		if( structure != null )
		{
			return structure.getStructure();
		}

		for( final int childOrParentId : getAllIds() )
		{
			structure = StructuresParser.getInstance().getInchiKey( childOrParentId );

			if( structure != null )
			{
				return structure.getStructure();
			}
		}

		return null;
	}

	/**
	 * 
	 * @return SMILES string
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getSmiles() throws IOException, ParseException
	{
		Structure structure = StructuresParser.getInstance().getSmiles( id );

		if( structure != null )
		{
			return structure.getStructure();
		}

		structure = StructuresParser.getInstance().getSmiles( getParentId() );

		if( structure != null )
		{
			return structure.getStructure();
		}

		for( final int childOrParentId : getAllIds() )
		{
			structure = StructuresParser.getInstance().getSmiles( childOrParentId );

			if( structure != null )
			{
				return structure.getStructure();
			}
		}

		return null;
	}

	/**
	 * 
	 * @return mol string
	 * @throws IOException
	 * @throws ParseException
	 */
	public String getMol() throws IOException, ParseException
	{
		Structure structure = StructuresParser.getInstance().getMol( id );

		if( structure != null )
		{
			return structure.getStructure();
		}

		structure = StructuresParser.getInstance().getMol( getParentId() );

		if( structure != null )
		{
			return structure.getStructure();
		}

		for( final int childOrParentId : getAllIds() )
		{
			structure = StructuresParser.getInstance().getMol( childOrParentId );

			if( structure != null )
			{
				return structure.getStructure();
			}
		}

		return null;
	}

	/**
	 * 
	 * @return mol file
	 * @throws MolFileException
	 * @throws ParseException
	 * @throws IOException
	 */
	public File getMolFile() throws IOException, ParseException
	{
		File molFile = StructuresParser.getInstance().getMolFile( id );

		if( molFile != null )
		{
			return molFile;
		}

		molFile = StructuresParser.getInstance().getMolFile( getParentId() );

		if( molFile != null )
		{
			return molFile;
		}

		for( final int childOrParentId : getAllIds() )
		{
			molFile = StructuresParser.getInstance().getMolFile( childOrParentId );

			if( molFile != null )
			{
				return molFile;
			}
		}

		return null;
	}

	/**
	 * 
	 * @return names
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Name> getNames() throws IOException, ParseException
	{
		return NamesParser.getInstance().getNames( getAllIds() );
	}

	/**
	 * 
	 * @return references
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Reference> getReferences() throws IOException, ParseException
	{
		return ReferenceParser.getInstance().getReferences( getAllIds() );
	}

	/**
	 * 
	 * @return compound origins
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<CompoundOrigin> getCompoundOrigins() throws IOException, ParseException
	{
		return CompoundOriginsParser.getInstance().getCompoundOrigins( getAllIds() );
	}

	/**
	 * 
	 * @return outgoing Relations
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Relation> getOutgoings() throws IOException, ParseException
	{
		return RelationParser.getInstance().getOutgoings( getAllIds() );
	}

	/**
	 * 
	 * @return incoming Relations
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<Relation> getIncomings() throws IOException, ParseException
	{
		return RelationParser.getInstance().getIncomings( getAllIds() );
	}

	/**
	 * 
	 * @return status
	 * @throws IOException
	 * @throws ParseException
	 */
	String getStatus() throws IOException, ParseException
	{
		return CompoundsParser.getInstance().getStatus( id );
	}

	/**
	 * 
	 * @return allIds
	 * @throws IOException
	 * @throws ParseException
	 */
	int[] getAllIds() throws IOException, ParseException
	{
		if( allIds == null )
		{
			final int parentId = getParentId();
			allIds = CompoundsParser.getInstance().getAllIds( parentId == ChebiEntity.UNDEFINED_VALUE ? id : parentId );

			if( allIds == null )
			{
				allIds = new int[ 0 ];
			}
		}

		return allIds;
	}
	
	/**
	 * Example code, showing the instantiation of a ChebiEntity, a call to getNames(),
	 * and the calling of a number of methods of the returned Names objects.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 * @throws ChebiException
	 */
	@SuppressWarnings("nls")
	public static void main( final String[] args ) throws IOException, ParseException, ChebiException
	{
		ChebiEntity chebiEntity = new ChebiEntity( "CHEBI:17634" );
		
		for( Name name : chebiEntity.getNames() )
		{
			System.out.println( name.getName() + "\t" + name.getSource() + "\t" + name.getLanguage() ); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
}