package application.model;

// !Ended up being unused in the current state of the project!

public class LevelScore {

	private int level;
	private int score;
	
	/**
	 * LevelScore constructor
	 * @param level - the level that the user has reach
	 * @param score - the score that associates with that level
	 */
	public LevelScore(int level, int score){
		this.level = level;
		this.score = score;
	}
	/**
	 * This method return a level that associate with some score
	 * @return level - the level that associate with some score
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * This method set the level that associate with some score
	 * @param level - the level that associate with some score
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * This method get the score that associate with some level
	 * @return score - the score that associate with some level
	 */
	public int getScore() {
		return score;
	}
	/**
	 * This method set the score that associate with some level
	 * @param score - the score that associate with some level
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	
}

