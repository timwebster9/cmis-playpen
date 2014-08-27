package webster;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.bindings.spi.LinkAccess;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by timw on 27/08/14.
 */
@Component
public class CmisService {

    @Value("${cmis.username}")
    private String cmisUserName;

    @Value("${cmis.password}")
    private String cmisPassword;

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

    public Object getDocumentByUrlJdk(final String documentUrl) throws Exception {

        final Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(CmisService.this.cmisUserName, CmisService.this.cmisPassword.toCharArray());
            }
        };

        Authenticator.setDefault(authenticator);

        URL url = new URL(documentUrl);
        URLConnection connection = url.openConnection();
//        String login = this.cmisUserName + ":" + this.cmisPassword;
//        String encodedLogin = new BASE64Encoder().encodeBuffer(login.getBytes());
//        connection.setRequestProperty("Authorization", "Basic " + encodedLogin);
        connection.connect();
        return connection.getContent();
    }


    public byte[] getDocumentByUrl(final String documentUrl) throws Exception {

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("localhost", AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(this.cmisUserName, this.cmisPassword));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpGet httpget = new HttpGet(documentUrl);

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());

                HttpEntity entity = response.getEntity();
                return EntityUtils.toByteArray(entity);

            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
