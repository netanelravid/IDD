
//	----------------------------------------------------------------------
//	Copyright [2014] [Netanel.R]
//	
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//	
//	    http://www.apache.org/licenses/LICENSE-2.0
//	
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.
//	----------------------------------------------------------------------

package bi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;

@SuppressWarnings("serial")
public class JpgImage extends Image {
	//	#region data members
	
	/*	no data members.	*/
	
	//	#endRegion data members

	//	#region properties
	
	/* no properties */

	// #endRegion properties

	//	#region constructors
	
	/**
	 * Default constructor.
	 * @param path destination of the image.
	 */
	public JpgImage(String path) {
		super(path);
	}
	
	//	#endRegion constructors.
	
	//	#region private methods
	
	/*	no private methods.	*/
	
	//	#endRegion private methods

	//	#region public methods
	
	/**
	 * Return the date that the image created.
	 */
	public String getDateFromImage() {
		Metadata metadata = null;
		ExifSubIFDDirectory directory;		
		Date date;
		SimpleDateFormat dateFormat;
		
		try {
			metadata = ImageMetadataReader.readMetadata(this);
		} catch (ImageProcessingException e1) {
			return( "Image format isn't Jpeg." );
		} catch (IOException e1) {
			return("No access.");
		}
		
		directory = metadata.getDirectory(ExifSubIFDDirectory.class);
		try{
			date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
		} catch (NullPointerException e) {
			return("Edited picture");
		}
		if( date != null) {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String temp = dateFormat.format(date);
			return(temp.replace('-', '.'));
		}
		else {
			return( "No creation date for the image." );
		}
	}

	public void abc() {
		Metadata metadata = null;

		try {
			metadata = ImageMetadataReader.readMetadata(this);
		} catch (ImageProcessingException e1) {
			System.out.println("a");
		} catch (IOException e1) {
			System.out.println("b");
		}
		
		for( Directory dr : metadata.getDirectories() ){
			for( Tag tg : dr.getTags() ) {
				System.out.println(tg);
			}
		}
	}
	
	//	#endRegion public methods

	//	#region encapsulation methods
	
	/*	no encapsulation methods.	*/
	
	//	#endRegion encapsulation methods
}
