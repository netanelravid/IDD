
package bi;

/**
 * This class represents thread that perform an image copy in a couple ways.
 * @author Natanel
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ImageCopy extends JpgImage implements Runnable {

	//	#region data members

	/**	
	 * count the number of threads that created.
	 */
	private static int m_s_counter = 1;
	
	//	#endRegion data members
	
	//	#region properties
	
	/**
	 * The thread's ID.
	 */
	private final int ID;
	
	// #endRegion properties

	//	#region constructors
	
	/**
	 * Default constructor.
	 */
	public ImageCopy(String path){
		super(path);
		ID = m_s_counter;
		m_s_counter++;
	}
	
	//	#endRegion constructors
	
	//	#region private methods
	
	/*	no private methods.	*/
	
	//	#endRegion private methods

	//	#region public methods

	/**
	 * Copies couple of images at once.
	 */
	public void run() {
	}
	
	//	#endRegion public methods
	
	//	#region encapsulation methods

	/**
	 * @return	the ID of the thread.
	 */
	public int getImageID() {
		return(this.ID);
	}
	
	//	#endRegion encapsulation methods

}
