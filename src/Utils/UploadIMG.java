package Utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB 
				maxFileSize=1024*1024*50,    	  	// 50 MB
				maxRequestSize=1024*1024*100)   	// 100 MB
public class UploadIMG extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5540405014381028141L;
	
	private static final String UPLOAD_DIR = "/images";
    
    public String imgKue(HttpServletRequest request, HttpServletResponse response, 
    		Part file) throws ServletException, IOException {
    	
    	if (!isImage(file)) {
            throw new RuntimeException("not an image");
        }
    	
        // gets absolute path of the web application
        //String applicationPath = request.getServletContext().getRealPath("/");
    	String applicationPath = System.getProperty("upload.location");
        // constructs path of the directory to save uploaded file
        //String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + "/kue";
        String uploadFilePath = applicationPath + UPLOAD_DIR + "/kue";
        
        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
        
        String fileName = null;
        //Get all the parts from request and write it to the file on server
        //Part part = request.getPart("gambarkue"); 
        fileName = getFileName(file);
        file.write(uploadFilePath + File.separator + fileName);
        return fileName;
 
        //request.setAttribute("message", fileName + " File uploaded successfully!");
    }
    
    public String imgPenjual(HttpServletRequest request,
            HttpServletResponse response, Part file) throws ServletException, IOException {
    	
    	if (!isImage(file)) {
            throw new RuntimeException("not an image");
        }
    	
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + "/penjual";
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
        
        String fileName = null;
        //Get all the parts from request and write it to the file on server
        fileName = getFileName(file);
        file.write(uploadFilePath + File.separator + fileName);
 
        request.setAttribute("message", fileName + " File uploaded successfully!");
        return fileName;
    }
    
    public String imgPembeli(HttpServletRequest request,
            HttpServletResponse response, Part file) throws ServletException, IOException {
    	
    	if (!isImage(file)) {
            throw new RuntimeException("not an image");
        }
    	
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + "/pembeli";
         
        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
        
        String fileName = null;
        //Get all the parts from request and write it to the file on server
        fileName = getFileName(file);
        file.write(uploadFilePath + File.separator + fileName);
 
        request.setAttribute("message", fileName + " File uploaded successfully!");
        return fileName;
    }
 
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    public String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
        	if (token.trim().startsWith("filename")) {
                return new Date().getTime() + token.substring(
                        token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }
    
    private static boolean isImage(final Part part) {
        String type = part.getContentType();
        return type.contains("image");
    }

    public static boolean deleteGambarKue(String photo) {

        if (photo.equalsIgnoreCase("upload/cakenak.png") || photo.equalsIgnoreCase("upload/profile.jpg")) {
            return true;
        }
        
        String applicationPath = System.getProperty("upload.location");
        // constructs path of the directory to save uploaded file
        //String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + "/kue";
        String uploadFilePath = applicationPath + UPLOAD_DIR + "/kue";
        
        File file = new File(uploadFilePath + photo);

        if (file.exists()) {
            boolean delete = file.delete();

            return true;
        }
        return false;
    }
    
    public static boolean deleteGambarPembeli(String photo) {

        if (photo.equalsIgnoreCase("upload/cakenak.png") || photo.equalsIgnoreCase("upload/profile.jpg")) {
            return true;
        }
        
        String applicationPath = System.getProperty("upload.location");
        // constructs path of the directory to save uploaded file
        //String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + "/kue";
        String uploadFilePath = applicationPath + UPLOAD_DIR + "/pembeli";
        
        File file = new File(uploadFilePath + photo);

        if (file.exists()) {
            boolean delete = file.delete();

            return true;
        }
        return false;
    }
    
    public static boolean deleteGambarPenjual(String photo) {

        if (photo.equalsIgnoreCase("upload/cakenak.png") || photo.equalsIgnoreCase("upload/profile.jpg")) {
            return true;
        }
        
        String applicationPath = System.getProperty("upload.location");
        // constructs path of the directory to save uploaded file
        //String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR + "/kue";
        String uploadFilePath = applicationPath + UPLOAD_DIR + "/penjual";
        
        File file = new File(uploadFilePath + photo);

        if (file.exists()) {
            boolean delete = file.delete();

            return true;
        }
        return false;
    }
}
