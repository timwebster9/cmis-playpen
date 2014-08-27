package webster;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Session;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        final Session session = CmisSessionFactory.getSession();
        final CmisObject doc = session.getObject("c724d5e6-31b4-4fc2-afa4-8f384066bc3b;1.0");

        final CmisService service = new CmisService();
        final String docUrl = service.getDocumentURL(doc, session);
        System.out.println("Document URL: " + docUrl);
    }
}
