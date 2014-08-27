package webster;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.bindings.spi.LinkAccess;
import org.springframework.stereotype.Component;

/**
 * Created by timw on 27/08/14.
 */
@Component
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

//    public Object getDocumentByUrl(final String documentUrl) {
//
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials(
//                new AuthScope("localhost", 443),
//                new UsernamePasswordCredentials("username", "password"));
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setDefaultCredentialsProvider(credsProvider)
//                .build();
//        try {
//            HttpGet httpget = new HttpGet("http://localhost/");
//
//            System.out.println("Executing request " + httpget.getRequestLine());
//            CloseableHttpResponse response = httpclient.execute(httpget);
//            try {
//                System.out.println("----------------------------------------");
//                System.out.println(response.getStatusLine());
//                EntityUtils.consume(response.getEntity());
//            } finally {
//                response.close();
//            }
//        } finally {
//            httpclient.close();
//        }
//
//        return null;
//    }
}
