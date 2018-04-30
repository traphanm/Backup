
package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Explain: Each User will have a list of score that associated with each level
 */
public class User {
	
	private File users; 
	private ArrayList<User> levelOneScore;
	private ArrayList<User> levelTwoScore;
	private ArrayList<User> levelThreeScore;
	public static ArrayList<User> userList;
	private String userN;
	private int l1;
	private int l2;
	private int l3;

	public User() {
		users = new File( "users.txt" );
		levelOneScore = new ArrayList<User>();
		levelTwoScore = new ArrayList<User>();
		levelThreeScore = new ArrayList<User>();
		userList = new ArrayList<User>();
	}
	
	public User( String username, int level1, int level2, int level3 ) {
		this.userN = username;
		this.l1 = level1;
		this.l2 = level2;
		this.l3 = level3;
	}
	
	public void readFile() {
		try {
			Scanner infile = new Scanner( users );
			while( infile.hasNextLine() ) {
				// System.out.println( infile.nextLine() );
				String line = infile.nextLine();
				userList.add( parseUserInformation( line ) );
				addLevelOne( userList );
				addLevelTwo( userList );
				addLevelThree( userList );
				// splitLine( infile.nextLine() );
			}
			infile.close();
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
		}
		//System.out.println(userList.get(1).getL1());
	}
	
	public static User parseUserInformation( String in ) {
		String[] seperated = in.split( "," );
		return new User( seperated[0],Integer.parseInt( seperated[1] ), Integer.parseInt( seperated[2] ),Integer.parseInt( seperated[3] ) );
	}
////////////////////////////Getters and Setters////////////////////////////////

	
	
	@SuppressWarnings("unchecked")
	public void addLevelOne( ArrayList<User> list ) {
		levelOneScore = ( ArrayList<User> ) list.clone();
		levelOneScore.sort( (User u1, User u2) -> u1.getL1()-u2.getL1() );
		Collections.reverse( levelOneScore );
	}
	/**
	 * 
	 * @return list of users sorted by level 1 scores
	 */
	public ArrayList<User> getLevelOne() {
		return levelOneScore;
	}
	
	@SuppressWarnings("unchecked")
	public void addLevelTwo(ArrayList<User> list) {
		levelTwoScore = ( ArrayList<User> ) list.clone();
		levelTwoScore.sort( (User u1, User u2) -> u1.getL2()-u2.getL2() );
		Collections.reverse( levelTwoScore );
	}
	/**
	 * 
	 * @return list of users sorted by level 2 scores
	 */
	public ArrayList<User> getLevelTwo() {
		return levelTwoScore;
	}
	
	@SuppressWarnings("unchecked")
	public void addLevelThree(ArrayList<User> list) {
		levelThreeScore = ( ArrayList<User> ) list.clone();
		levelThreeScore.sort( (User u1, User u2) -> u1.getL3()-u2.getL3() );
		Collections.reverse( levelThreeScore );
	}
	/**
	 * 
	 * @return list of users sorted by level 3 scores
	 */
	public ArrayList<User> getLevelThree() {
		return levelThreeScore;
	}
	/**
	 * @return the l1
	 */
	public int getL1() {
		return l1;
	}
	/**
	 * @return the l2
	 */
	public int getL2() {
		return l2;
	}
	/**
	 * @return the l3
	 */
	public int getL3() {
		return l3;
	}
	
	/**
	 * @return the userN
	 */
	public String getUserN() {
		return userN;
	}
	
}
