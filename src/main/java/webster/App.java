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
    public static void main( String[] args ) {

        final ConfigurableApplicationContext ctx =
                new SpringApplicationBuilder(App.class).run(args);

        final Session session = ctx.getBean(CmisSessionFactory.class).getSession();
        final CmisObject doc = session.getObject("idd_740F356E-5D0D-46A9-A0E4-24FFE0B74DC7");

        final CmisService service = ctx.getBean(CmisService.class);
        final String docUrl = service.getDocumentURL(doc, session);

       // service.getDocumentByUrl("http://10.35.1.151:9080/fncmis/resources/aquilaDEV/ContentStream/idd_740F356E-5D0D-46A9-A0E4-24FFE0B74DC7/0/38333+Java+7+JNLP+Security+Changes+DE.docx");

        System.out.println("Document URL: " + docUrl);
    }
}
