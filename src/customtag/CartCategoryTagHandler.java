package customtag;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import dbrepo.RepoKategori;
import model.Kategori;
//import org.Cakenak.beans.Category;
//import org.Cakenak.model.CategoryModel;


public class CartCategoryTagHandler extends SimpleTagSupport {


    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            
            ArrayList<Kategori> AllCategoriess = new RepoKategori().getAllKategori();
            for (Kategori category : AllCategoriess) {
                out.print("<div class='panel panel-default'>\n" +
"                            <div class='panel-heading'>\n" +
"                                <h4 class='panel-title'><a href='Shop?page=1&cate="+category.getIdkategori()+"'>"+category.getNama()+"</a></h4>\n" +
"                            </div>\n" +
"                        </div>");
            }  
           
        } catch (java.io.IOException ex) {
            throw new JspException("Error in CartCategoryTagHandler tag", ex);
        }
    }
    
}
