package uk.ac.manchester.libchebi;

import java.io.IOException;
import java.util.Vector;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Assert;
import org.junit.Test;


public class ChebiSearchTest {

	
	
	/**
	 * 
	 */
	private final ChebiSearch Searcher = ChebiSearch.getInstance(); //$NON-NLS-1$


	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void searchForCompoundName() throws IOException, ParseException, java.text.ParseException, ChebiException
	{
		System.out.println("Testing Search for Name");
		final String id = "Glucose";
		Vector<ChebiEntity> res = Searcher.getPotentialMatches(id,5,true);
		for(ChebiEntity hit : res)
		{
			System.out.println(hit.getName() + " : " + hit.getId());			
		}
		
		ChebiEntity Glucose = new ChebiEntity("17234");
		System.out.println("Result entry 0: " + res.firstElement().getId() + " ; " + res.firstElement().getName()); 
		System.out.println("Glucose: " + Glucose.getId() + " ; " + Glucose.getName());
		Assert.assertTrue( Glucose.equals(res.firstElement()));
		Assert.assertTrue( res.size() <= 5);
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void searchForCompoundID() throws IOException, ParseException, java.text.ParseException, ChebiException
	{
		System.out.println("Testing Search for ID");

		final String id = "17234";
		Vector<ChebiEntity> res = Searcher.getPotentialMatches(id,5,true);
		for(ChebiEntity hit : res)
		{
			System.out.println(hit.getName() + " : " + hit.getId());
			
		}
		ChebiEntity Glucose = new ChebiEntity("17234");
		Assert.assertTrue( Glucose.equals(res.get(0)));
		Assert.assertTrue( res.size() <= 1);
	}
	
	
	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void searchForPyruvate() throws IOException, ParseException, java.text.ParseException, ChebiException
	{
		System.out.println("Testing Search for Name");

		final String id = "pyruvate";
		Vector<ChebiEntity> res = Searcher.getPotentialMatches(id,5,true);
		for(ChebiEntity hit : res)
		{
			System.out.println(hit.getName() + " : " + hit.getId());
			for(Name n :  hit.getNames())
			{
				System.out.println("Alt Name: " + n.getName());
			}
		}
		ChebiEntity pyruvate = new ChebiEntity("15361");
		Assert.assertTrue( pyruvate.equals(res.get(0)));
		Assert.assertTrue( res.size() <= 1);
	}

	
	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void searchForAccession() throws IOException, ParseException, java.text.ParseException, ChebiException
	{
		System.out.println("Testing Search for Accession");

		final String id = "C06018";
		Vector<ChebiEntity> res = Searcher.getEntitiesWithMatchingAccessions(id);
		for(ChebiEntity hit : res)
		{
			System.out.println(hit.getName() + " : " + hit.getId());
			for(Name n :  hit.getNames())
			{
				System.out.println("Alt Name: " + n.getName());
			}
		}
		ChebiEntity MatchinChebii = new ChebiEntity("68677");
		Assert.assertTrue( MatchinChebii.equals(res.get(0)));		
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 * @throws ChebiException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void searchForAccessionInWrongDB() throws IOException, ParseException, java.text.ParseException, ChebiException
	{
		System.out.println("Testing Search for Wrong ACCESSION");

		final String id = "C06018";
		Vector<ChebiEntity> res = Searcher.getMatchingEntitiesByAccession(id, ChebiSearch.DatabaseAccession.PubMed_citation);
		Assert.assertTrue( res.size() == 0);		
	}
	
	/**
	 * 
	 */
	public ChebiSearchTest() throws IOException, ParseException, ChebiException
	{
		// No implementation.
	}	
	
}
