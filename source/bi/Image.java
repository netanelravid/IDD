
package bi;

import java.io.File;

/** Class that handles with the distribution of images by date.
 * @author Natanel
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class Image extends File{
	
	//	#region data members

	/*	no data members.	*/
	
	//	#endRegion data members
	
	//	#region properties
	
	/* no properties */

	// #endRegion properties

	//	#region constructors
	
	/**
	 * Default constructor.
	 * @param path the destination of the file.
	 */
	public Image(String path){
		super(path);
	}
	
	//	#endRegion constructors
	
	//	#region private methods
	
	/*	no private methods.	*/
	
	//	#endRegion private methods

	//	#region public methods	

	/**
	 * Method that returns the date when the file was created.
	 * @return the date when the file was created.
	 */
	public abstract String getDateFromImage();	

	//	#endRegion public methods
	
	//	#region encapsulation methods

	/*	no encapsulation methods.	*/
	
	//	#endRegion encapsulation methods
}
