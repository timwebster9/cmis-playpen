package webster;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by timw on 27/08/14.
 */
@Component
public class CmisSessionFactory {

    @Value("${cmis.username}")
    private String cmisUsername;

    @Value("${cmis.password}")
    private String cmisPassword;

    @Value("${cmis.atompub.url}")
    private String cmisAtompubUrl;

    @Value("${cmis.repository.name}")
    private String cmisRepositoryName;

    public Session getSession() {
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap<String, String>();

        parameter.put(SessionParameter.USER, this.cmisUsername);
        parameter.put(SessionParameter.PASSWORD, this.cmisPassword);

        parameter.put(SessionParameter.ATOMPUB_URL, this.cmisAtompubUrl);
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, this.cmisRepositoryName);

        Session session = factory.createSession(parameter);
        return session;
    }
}
