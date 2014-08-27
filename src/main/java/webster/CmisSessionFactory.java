package webster;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by timw on 27/08/14.
 */
public class CmisSessionFactory {

    public static Session getSession() {
        SessionFactory factory = SessionFactoryImpl.newInstance();
        Map<String, String> parameter = new HashMap<String, String>();

// user credentials
        parameter.put(SessionParameter.USER, "admin");
        parameter.put(SessionParameter.PASSWORD, "webs5358");

// connection settings
        parameter.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.0/atom");
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameter.put(SessionParameter.REPOSITORY_ID, "-default-");

// create session
        Session session = factory.createSession(parameter);
        return session;
    }
}
