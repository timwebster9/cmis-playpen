package webster;

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
    private static final String ADD_FILENAME = "msword.doc";
    //private static final String ADD_FILENAME = "test.xlsx";


    public static void main( String[] args ) throws Exception {

        final ConfigurableApplicationContext ctx =
                new SpringApplicationBuilder(App.class).run(args);

//        final Session session = ctx.getBean(CmisSessionFactory.class).getSession();
//        final CmisObject doc = session.getObject("idd_AD5347B1-B47D-4070-AB64-176E3666AD1F");

        final CmisService service = ctx.getBean(CmisService.class);
        service.addDocumentMultipleTimes("src/main/resources/" + ADD_FILENAME, ADD_FILENAME, 50);
//        final String docUrl = service.getDocumentURL(doc, session);
//
//        final Object response = service.getDocumentByUrlJdk(docUrl);
        //final byte[] content = service.getDocumentByUrl(docUrl);

        //System.out.println("Document URL: " + docUrl);
    }
}
