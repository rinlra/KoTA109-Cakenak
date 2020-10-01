
package customtag;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import dbrepo.RepoKategori;
import model.Kategori;

/**
 *
 * @KoTA109
 */
public class SelectCategory extends SimpleTagSupport {

    private int selectID;

    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            out.print("<select name='category'>");
            String selected = ""; 
            //get all category
            ArrayList<Kategori> categoriess = new RepoKategori().getAllKategori();
            for (Kategori categories : categoriess) {
                if(selectID == categories.getIdkategori())
                    selected = "selected";
                out.print("<option value='"+categories.getIdkategori()+"' "+selected+">"+categories.getNama()+"</option>");
                
                selected ="";
            }
            out.print("</select>");
        } catch (java.io.IOException ex) {
            throw new JspException("Error in Category tag", ex);
        }
    }

    public void setSelectID(int selectID) {
        this.selectID = selectID;
    }
    
}
