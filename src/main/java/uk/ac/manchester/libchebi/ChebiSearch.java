package uk.ac.manchester.libchebi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Vector;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ChebiSearch {

	private static ChebiSearch search;
	private static final String ID = "COMPOUND_ID";
	private static final String NAME = "NAME";
	private static final String ACCESSION_TYPE = "TYPE";
	private static final String ACCESSION_NUMBER = "ACCESSION_NUMBER";

	private static String ChEBIID= "ChEBIID";
	private static String COMPOUNDNAME = "COMPOUNDNAME";
	private static String SEARCHFIELD = "SEARCHNAME";
	private static String ACCESSIONFIELD = "ACCESSIONID";
	private int NAME_pos;
	private int ID_pos;
	private int ACCESSION_TYPE_pos;
	private int ACCESSION_NUMBER_pos;
	private Directory searchDir;
	private Analyzer analyzer;
	private IndexSearcher isearcher;	
	public enum DatabaseAccession
	{
		Chemspider_accession("Chemspider accession"),
		Chinese_Abstracts_citation("Chinese Abstracts citation"),
		COMe_accession("COMe accession"),
		UM_BBD_compID("UM-BBD compID"),
		LINCS_accession("LINCS accession"),
		DrugBank_accession("DrugBank accession"),
		KNApSAcK_accession("KNApSAcK accession"),
		Gmelin_Registry_Number("Gmelin Registry Number"),
		Pubchem_accession("Pubchem accession"),
		Agricola_citation("Agricola citation"),
		CiteXplore_citation("CiteXplore citation"),
		SMID_accession("SMID accession"),
		Beilstein_Registry_Number("Beilstein Registry Number"),
		LIPID_MAPS_instance_accession("LIPID MAPS instance accession"),
		CAS_Registry_Number("CAS Registry Number"),
		RESID_accession("RESID accession"),
		Pesticides_accession("Pesticides accession"),
		WebElements_accession("WebElements accession"),
		Reaxys_Registry_Number("Reaxys Registry Number"),
		PubMed_citation("PubMed citation"),
		MolBase_accession("MolBase accession"),
		LIPID_MAPS_class_accession("LIPID MAPS class accession"),
		PDBeChem_accession("PDBeChem accession"),
		KEGG_GLYCAN_accession("KEGG GLYCAN accession"),
		MetaCyc_accession("MetaCyc accession"),
		YMDB_accession("YMDB accession"),
		KEGG_DRUG_accession("KEGG DRUG accession"),
		Wikipedia_accession("Wikipedia accession"),
		ECMDB_accession("ECMDB accession"),
		HMDB_accession("HMDB accession"),
		PubMed_Central_citation("PubMed Central citation"),
		PDB_accession("PDB accession"),
		ChemIDplus_accession("ChemIDplus accession"),
		Patent_accession("Patent accession"),
		KEGG_COMPOUND_accession("KEGG COMPOUND accession");
		/**
		 * The Textual Representation of the Database Accession.
		 */
		private final String ACCESSION_TYPE;

		private DatabaseAccession(final String name)
		{
			ACCESSION_TYPE = name;
		}
		@Override
		public String toString()
		{
			return ACCESSION_TYPE;
		}

	}


	/**
	 * Default constructor using the names and the accession file provided by ChEBI.
	 * Access to this clas is provided by the getInstance method, which makes sure, that there are no conflicting 
	 * instances.
	 * @param namesFile
	 * @param accessionFile
	 * @throws IOException
	 */
	ChebiSearch(File namesFile, File accessionFile) throws IOException {
		//we need two files. The names file and the database_accession file, in order to allow searching for specific identifiers from other DBs.

		analyzer = new StandardAnalyzer();
		boolean newIndex = true;
		File luceneDir = Paths.get(Downloader.getInstance().destination.getPath() + File.separator + "names.lucene.index").toFile();
		if(luceneDir.exists())
		{
			newIndex = false;
		}		
		// Store the index at the given position		
		searchDir = FSDirectory.open(Paths.get(Downloader.getInstance().destination.getPath() + File.separator + "names.lucene.index"));

		// To store an index on disk, use this instead:
		//Directory directory = FSDirectory.open("/tmp/testindex");
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		if(!newIndex)
		{
			config.setOpenMode(OpenMode.CREATE_OR_APPEND);
			try
			{
				DirectoryReader ireader = DirectoryReader.open(searchDir);		
				isearcher = new IndexSearcher(ireader);				
			}
			catch(Exception e)
			{
				//recreate the index, something went wrong.
				config.setOpenMode(OpenMode.CREATE);
				newIndex = true;
				e.printStackTrace(System.out);
			}
		}
		else
		{
			config.setOpenMode(OpenMode.CREATE);
		}
		IndexWriter iwriter = new IndexWriter(searchDir, config);
		BufferedReader br = new BufferedReader(new FileReader(namesFile));
		//Skip the header
		String headerline = br.readLine();
		String[] headers = headerline.split("\t");
		setIndices(headers);
		String currentline = br.readLine();

		while(currentline != null)
		{
			String[] tokens = currentline.split("\t");
			Long ID = Long.parseLong(tokens[ID_pos].trim());
			String IDString = tokens[ID_pos].trim();
			String name = tokens[NAME_pos].trim();
			TopDocs results = new TopDocs(0, new ScoreDoc[0], 0);
			if(!newIndex)
			{
				results = isearcher.search(new TermQuery(new Term(COMPOUNDNAME, name)), 1);
			}
			if (results.totalHits == 0)
			{
				Document namedoc = new Document();
				namedoc.add(new TextField(SEARCHFIELD, name, Store.NO));	    	
				namedoc.add(new StringField(COMPOUNDNAME, name, Store.YES));	    	
				namedoc.add(new StoredField(ChEBIID, ID));	    	
				iwriter.addDocument(namedoc);
			}
			if(!newIndex)
			{
				results = isearcher.search(new TermQuery(new Term(COMPOUNDNAME, IDString)), 1);
			}
			if (results.totalHits == 0)
			{
				Document iddoc = new Document();				
				iddoc.add(new StringField(COMPOUNDNAME, IDString, Store.YES));	    	
				iddoc.add(new StoredField(ChEBIID, ID));	    	
				iwriter.addDocument(iddoc);
			}
			currentline = br.readLine();

		}

		//now, read the accession file
		br.close();
		br = new BufferedReader(new FileReader(accessionFile));
		//Skip the header
		headerline = br.readLine();
		headers = headerline.split("\t");
		setIndices(headers);
		currentline = br.readLine();
		while(currentline != null)
		{
			String[] tokens = currentline.split("\t");
			Long ID = Long.parseLong(tokens[ID_pos].trim());
			String IDString = tokens[ID_pos].trim();
			String accession_type = tokens[ACCESSION_TYPE_pos].trim();
			String accession_number = tokens[ACCESSION_NUMBER_pos].trim();			
			TopDocs results = new TopDocs(0, new ScoreDoc[0], 0);
			if(!newIndex)
			{
				results = isearcher.search(new TermQuery(new Term(accession_type, accession_number)), 1);
			}
			if (results.totalHits == 0)
			{
				Document namedoc = new Document();
				//namedoc.add(new TextField(SEARCHFIELD, accession_number, Store.NO));
				namedoc.add(new StringField(ACCESSIONFIELD, accession_number, Store.NO));
				namedoc.add(new StringField(accession_type, accession_number, Store.YES));	    	
				namedoc.add(new StoredField(ChEBIID, ID));	    	
				iwriter.addDocument(namedoc);
			}
			currentline = br.readLine();

		}
		iwriter.close();
		if(newIndex)
		{
			DirectoryReader ireader = DirectoryReader.open(searchDir);		
			isearcher = new IndexSearcher(ireader);
		}
		br.close();
	}

	/**
	 * @return singleton ChebiSearch
	 * @throws IOException
	 */
	public synchronized static ChebiSearch getInstance() throws IOException
	{
		if( search == null )
		{
			final File namesfile = Downloader.getInstance().getFile( "names.tsv.gz" ); //$NON-NLS-1$
			final File accessionfile = Downloader.getInstance().getFile( "database_accession.tsv" ); //$NON-NLS-1$
			search = new ChebiSearch(namesfile, accessionfile);
		}

		return search;
	}

	/**
	 * Get Entities which have an non ChEBI accesion stored in ChEBI that matches the search string perfectly.
	 * @param Accession The String for which to find exact accession matches.
	 * @return All entities which have a accession that matches the search string (this accession could originate from different databases. If only a specific DB is of interest use getMatchingEntitiesByAccession. 
	 * @throws IOException
	 * @throws ParseException
	 * @throws ChebiException
	 * @throws java.text.ParseException
	 */
	public synchronized Vector<ChebiEntity> getEntitiesWithMatchingAccessions(String Accession) throws IOException,ParseException, ChebiException, java.text.ParseException
	{
		Vector<ChebiEntity> matches = new Vector<ChebiEntity>();

		Query query = new TermQuery(new Term(ACCESSIONFIELD,Accession));
		//there can be multiple matches but we will only return precise matches.
		ScoreDoc[] hits = isearcher.search(query, 100, Sort.RELEVANCE).scoreDocs;
		for(int i = 0; i < hits.length; i++)
		{
			ChebiEntity entity = new ChebiEntity(isearcher.doc(hits[i].doc).get(ChEBIID));
			matches.add(entity);		
		}
		return matches;
	}

	/**
	 * Find entities which have the given Accession in the provided DataBase 
	 * @param Accession The identifier to search for.
	 * @param AccessionType The type of Database accession (as used in the Chebi Accession File. Any of the {@link DatabaseAccession} enum values can be used.
	 * @return the entities which have a matching accession in the database or an empty vector if none.
	 * @throws IOException
	 * @throws ParseException
	 * @throws ChebiException
	 * @throws java.text.ParseException
	 */
	public synchronized  Vector<ChebiEntity> getMatchingEntitiesByAccession(String Accession, DatabaseAccession AccessionType) throws IOException,ParseException, ChebiException, java.text.ParseException
	{
		Vector<ChebiEntity> matches = new Vector<ChebiEntity>();

		Query query = new TermQuery(new Term(AccessionType.toString(),Accession));
		//there can be multiple matches but we will only return precise matches.
		ScoreDoc[] hits = isearcher.search(query, 100, Sort.RELEVANCE).scoreDocs;
		for(int i = 0; i < hits.length; i++)
		{
			ChebiEntity entity = new ChebiEntity(isearcher.doc(hits[i].doc).get(ChEBIID));
			matches.add(entity);		
		}
		return matches;
	}


	/**
	 * Search for matching names/ids in ChEBI and obtain a set of potential Matches. 
	 * @param CompoundName - The Name to search for
	 * @param maxresults - the maximal number of returned matches
	 * @param stopatexactmatching - stop the search if a result is an exact (case insensitive) match. This is highly dependent on the available alternative names in ChEBI and it could happen, that two compounds exist with the same alt name leading to this search only returning one if stopatexactmatching is set to true.  
	 * @return a set of ChebiEntities that have names similar to the searched name. With the most likely at earlier positions.
	 * @throws IOException
	 * @throws ParseException
	 * @throws ChebiException
	 * @throws java.text.ParseException
	 */
	public synchronized  Vector<ChebiEntity> getPotentialMatches(String CompoundName, int maxresults,boolean stopatexactmatching) throws IOException,ParseException, ChebiException, java.text.ParseException
	{
		Vector<ChebiEntity> matches = new Vector<ChebiEntity>();
		try{
			Long id = Long.parseLong(CompoundName); // if this succeeds we got a Long as query, so we will simply look for that entry and return it.			
			Query query = new TermQuery(new Term(COMPOUNDNAME,CompoundName));
			//there should be only one. 
			ScoreDoc[] hits = isearcher.search(query, 1, Sort.RELEVANCE).scoreDocs;			
			ChebiEntity entity = new ChebiEntity(CompoundName);
			if(entity.getParentId() != ChebiEntity.UNDEFINED_VALUE)
			{
				entity = new ChebiEntity(Integer.toString(entity.getParentId()));
			}
			matches.add(entity);
			return matches;
		}
		catch(NumberFormatException e)
		{
			//So this is not a valid long, i.e. we have to search for the ID.
		}
		// Parse a simple query that searches for "text":
		SimpleQueryParser parser = new SimpleQueryParser( analyzer, SEARCHFIELD);
		Query query = parser.parse(CompoundName);
		ScoreDoc[] hits = isearcher.search(query, maxresults*20, Sort.RELEVANCE).scoreDocs;
		// Iterate through the results:
		for (int i = 0; i < hits.length & matches.size() < maxresults; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			ChebiEntity entity = new ChebiEntity(hitDoc.get(ChEBIID));
			if(entity.getParentId() != ChebiEntity.UNDEFINED_VALUE)
			{
				entity = new ChebiEntity(Integer.toString(entity.getParentId()));
			}
			if(!matches.contains(entity))
			{
				matches.add(entity);
			}
			//If we find an exact match, we will return that exact match and not continue adding additional options.
			//We consider any string that exactly matches the query in a case insensitive way or has any secondary names that match the query
			//to be a perfect match.
			if(stopatexactmatching)
			{
				for( Name currentname : entity.getNames())
				{
					if(currentname.getName().toLowerCase().equals(CompoundName.toLowerCase()))
					{					
						return matches;
					}
				}
			}

		}
		return matches;
	}

	private synchronized void setIndices(String[] headers)
	{
		int i = 0;
		for(String header : headers)
		{
			switch(header)
			{
			case ID:
			{
				ID_pos = i;
				break;
			}
			case NAME:
			{
				NAME_pos = i;
				break;
			}
			case ACCESSION_TYPE:
			{
				ACCESSION_TYPE_pos = i;
				break;
			}
			case ACCESSION_NUMBER:
			{
				ACCESSION_NUMBER_pos = i;
				break;
			}
			}
			i++;
		}
	}
}
