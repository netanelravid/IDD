
package bi;

import java.util.Vector;
import java.io.File;
import java.io.IOException;

import gui.Gui;

import org.apache.commons.io.FileUtils;

/**	This class represents the files management (include images management).
 * @author Netanel
 * @version 1.1
 *  <h1> Changes: </h1>
 * 		- class designed as "Singleton" design pattern.
 * 	<br>- new feature of copy image to folder which contain an image with the same name, and changing it.
 */
public class FilesManagement implements Runnable {
	
	//	#region data members
	
	/**	The instance of the class.
	 * 	@Version 1.1
	 */
	private static FilesManagement m_s_instance = null;
	
	/**	The new folders that created by the creation dates of the images.
	 * 
	 */
	private Vector<String> m_destinationFolders = new Vector<String>();

	/**	Counts the number of files in the source folder.
	 * 
	 */
	private int m_filesCounter;

	/**	The files in the source folder.
	 * 
	 */
	private File[] m_files;

	/**	The date when the image created.
	 * 
	 */
	private String m_imageDate;

	/**	The current image which copied.
	 * 
	 */
	private Image m_currentImage;
	
	//	#endRegion data members
	
	//	#region properties

	/**	The source folder of the images.
	 * 
	 */
	private File m_sourceFolder;

	/**	The number of images that moved.
	 * 
	 */
	private int m_imagesCounter = 0;
	
	//	#endRegion properties

	//	#region constructors

	/**	Default constructor.
	 * @Version 1.1 - "Singleton" design pattern.
	 */
	public FilesManagement() {
		
	}
	
	/**	Constructor that receives 1 parameter:
	 * @param sourceFolder is the source folder where all the images are stored.
	 */
	private FilesManagement(String sourceFolder) {
		if( setSourceFolder( sourceFolder ) == false ) {
				System.out.println( "Directory is not exist." );
		}
	}
	
	//	#endRegion constructors
	
	//	#region private methods

	/**	Sets the number of files in the source folder.
	 * 
	 */
	private void setFilesCounter() {
		this.m_filesCounter = this.m_files.length;
	}

	/**	Adds new destination folder.
	 * @param newFolder is the new folder.
	 */
	private void addDestinationFolders(String newFolder) {
		this.m_destinationFolders.add( newFolder );
	}

	/**	Copies single image from source folder to a new folder.
	 *	This method of copy is using the library APACHE.
	 * @param destinationPath is the destination folder.
	 */
	private void copySingleImage(Image imageFromSourceFolder, String destinationPath) {
		String destinationFileName = destinationPath + imageFromSourceFolder.getName();
		File destinationFile;
		
		destinationFileName = changeImageName( destinationFileName, 0 );
		destinationFile = new File( destinationFileName );

		try {
			FileUtils.copyFile(imageFromSourceFolder, destinationFile);
			this.m_imagesCounter++;
		} catch (IOException e) {
			System.out.println("App can't copies the file: " + imageFromSourceFolder.getAbsolutePath());
		}
	}

	/**	Creates new folder by the date that the image created.
	 * @param folderName is the name for the new folder.
	 */
	private void createNewFolder(String folderName) {
		new File( this.m_sourceFolder.getAbsolutePath() + "\\" + folderName ).mkdir();
		addDestinationFolders( this.m_imageDate );
	}

	/**	Checks if the file is an image.
	 * @param file the file
	 * @return type of image, if not image than return null.
	 */
	private String getImageFormat(File file) {
		if( file.isFile() ) {
			String typeOfFile = file.getName().substring( 1 + file.getName().lastIndexOf('.')).toLowerCase();
			
			if (typeOfFile.equals("jpg") || typeOfFile.equals("jpeg") || typeOfFile.equals("bmp") ||
					typeOfFile.equals("gif") ||	typeOfFile.equals("png") )
				return(typeOfFile);
			else {
				return( "file is not image" );
			}
		}
		return( "directory" );
	}

	/**	Checks if folder is already exist.
	 * @param folderName is the folder name to be check.
	 * @return true if the folder exist, false if it isn't .
	 */
	private boolean isFolderExist(String folderName) {
		if( new File( this.m_sourceFolder.getAbsolutePath() + "\\" + folderName ).exists()) {
			return( true );
		}
		else {
			return( false );
		}
	}

	/**	Return new name for image if the it's name is already taken by other file.
	 * @Version 1.1
	 * @param fileName is the file name for check.
	 * @param numberOfCopy is postfix name for the new file (if there is already file with the same name.
	 * @return new name if the old name is taken, else return null.
	 */
	private String changeImageName(String fileName, int numberOfCopy) {
		String fileFormat;
		String tempFileName;
		
		if( numberOfCopy == 0 ) {
			if( new File( fileName ).exists() ) {
				fileName = changeImageName( fileName, ++numberOfCopy );
			}
		} 
		else {
			fileFormat = fileName.substring( fileName.lastIndexOf( "." ) );
			fileFormat = "_copy_" + numberOfCopy + fileFormat;
			tempFileName = fileName.substring( 0, fileName.lastIndexOf( "." ) ) + fileFormat;
			if( new File( tempFileName ).exists() ) {
				fileName = changeImageName( fileName, ++numberOfCopy );
			}
			else {
				return( tempFileName );
			}
		}
		return( fileName );
	}
	
	/**	Copies all images to new folders by their creation date.
	 * 
	 */
	private void copyImages() {
		Gui tempGui = new Gui().getInstance();
		
		for( int i = 0; i < this.m_filesCounter; i++ ) {
			switch( getImageFormat( this.m_files[i] ) ) {
			case "file is not image":
				tempGui.appendTextToProgressTextArea( "The file \"" + this.m_files[i].getName() + "\" is not image." );
				break;
			case "directory":
				break;
			case "jpeg":
			case "jpg":
				this.m_currentImage = new JpgImage( this.m_files[i].getAbsolutePath() );
				this.m_imageDate = this.m_currentImage.getDateFromImage();
				switch(this.m_imageDate){
				case "Image format isn't Jpeg.":
					tempGui.appendTextToProgressTextArea( "The image \"" + this.m_currentImage.getName()
							+ "\" format isn't Jpeg." );
					break;
				case "No creation date for the image.":
					tempGui.appendTextToProgressTextArea( "There is no valid date for the image: "
				+ this.m_currentImage.getName() + "." );
					break;
				case "No access.":
					tempGui.appendTextToProgressTextArea( "Can't access to the file: " + this.m_currentImage.getName() + ".");
					break;
				case "Edited picture":
					tempGui.appendTextToProgressTextArea( "Edited picture, There is no valid date for the image: "
				+ this.m_currentImage.getName() + "." );
					break;
				default:
					if( isFolderExist( this.m_imageDate ) == false ) {
						createNewFolder( this.m_imageDate );
					}
					else {
						if ( this.m_destinationFolders.contains(this.m_imageDate) ) {
							addDestinationFolders( this.m_imageDate );
						}
					}
					copySingleImage( this.m_currentImage, this.m_sourceFolder + "\\" + this.m_imageDate + "\\" );
					break;
				}
			case "bmp":		/*	in version 1.1 the program will only take care to jpg images	*/
				break;
			case "png":		/*	in version 1.1 the program will only take care to jpg images	*/
				break;
			case "gif":		/*	in version 1.1 the program will only take care to jpg images	*/
				break;
			}
			tempGui.updatePercentage( ( ( ( float ) i +1 ) / this.m_filesCounter ) * 100 );
		}
		tempGui.appendTextToProgressTextArea( "Done!" );
	}

	//	#endRegion private methods

	//	#region public methods

	/**	Creates 1 object's instance from this class.
	 * @Version 1.1
	 * @param sourceFolder is the source folder which all the pics ar stored in.
	 * @return the instance of this class.
	 */
	public FilesManagement getInstance() {
		if( FilesManagement.m_s_instance == null ){
			FilesManagement.m_s_instance = new FilesManagement(null);
		}
		return( FilesManagement.m_s_instance );
	}
	
	/**	Do the process of distributing images in parallel.
	 * 
	 */
	@Override
	public void run() {
		copyImages();
	}

	
	//	#endRegion public methods
	
	//	#region encapsulation methods

	/**	Return the source folder, where all the images is stored
	 * @return the source folder, where all the images is stored.
	 */
	public File getSourceFolder() {
		return( this.m_sourceFolder );
	}

	/**	Sets new source folder.
	 * @param sourceFolder the new source folder.
	 */
	public Boolean setSourceFolder(String sourceFolder) {
		if( sourceFolder != null ) {
			if( new File( sourceFolder ).isDirectory() ) {
				this.m_sourceFolder = new File(sourceFolder);
				this.m_files = this.m_sourceFolder.listFiles();
				setFilesCounter();
				return( true );
			}
		}
		return( false );
	}
	
	/**	Return the number of images that successfully moved to new folders.
	 * @return the number of images that successfully moved to new folders.
	 */
	public int getImagesCounter() {
		return( this.m_imagesCounter );
	}
	
	//	#endRegion encapsulation methods
}
