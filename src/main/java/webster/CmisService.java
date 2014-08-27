package webster;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.bindings.spi.LinkAccess;

/**
 * Created by timw on 27/08/14.
 */
public class CmisService {

    public String getDocumentURL(final CmisObject document, final Session session) {
        if (!(document instanceof Document)) {
            return null;
        }

        if (session.getBinding().getObjectService() instanceof LinkAccess) {
            return ((LinkAccess) session.getBinding().getObjectService()).loadContentLink(session.getRepositoryInfo()
                    .getId(), document.getId());
        }

        return null;
    }
}
