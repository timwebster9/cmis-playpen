package webster;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
/**
 * Hello world!
 *
 */
@ComponentScan
@EnableAutoConfiguration
public class App 
{
    public static void main( String[] args ) throws Exception {

        final ConfigurableApplicationContext ctx =
                new SpringApplicationBuilder(App.class).run(args);

        final Session session = ctx.getBean(CmisSessionFactory.class).getSession();
        final CmisObject doc = session.getObject("c51ba30c-68f2-46d7-b448-4e7c3b53da79;1.0");

        final CmisService service = ctx.getBean(CmisService.class);
        final String docUrl = service.getDocumentURL(doc, session);

        final Object response = service.getDocumentByUrlJdk(docUrl);
        //final byte[] content = service.getDocumentByUrl(docUrl);

        System.out.println("Document URL: " + docUrl);
    }
}
