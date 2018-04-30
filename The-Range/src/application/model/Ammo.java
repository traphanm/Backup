package application.model;

public class Ammo {
	
	private int fullSize;
	private int remaining;
		
	/**
	 * The constructor will set the number of ammo
	 * @param a - the number of ammo
	 */
	public Ammo( int a ) {
		fullSize = a;
		remaining = fullSize;
	}

	/**
	 * This method will decrease the number of ammo by 1
	 */
	public void reduceAmmo() {
		remaining--;
	}
	
	public void reload() {
		remaining = fullSize;
	}
	
	/**
	 * This method will return the amount of ammo remaining
	 * @return the remaining
	 */
	public int getRemaining() {
		return remaining;
	}

	/**
	 * This method will set the remaining amount of ammo
	 * @param remaining the remaining
	 */
	public void setRemaining( int remaining ) {
		this.remaining = remaining;
	}
	
	
}
