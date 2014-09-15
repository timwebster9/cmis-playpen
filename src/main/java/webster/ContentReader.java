package webster;

import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Read a file from disk.
 */
@Component
public class ContentReader {

    private static final Logger LOG = LoggerFactory.getLogger(ContentReader.class);
    private static final Detector MIME_TYPE_DETECTOR = TikaConfig.getDefaultConfig().getDetector();

    @Autowired
    private Session session;

    public ContentStream createContentStream(final String filePath) {
        final File file = new File(filePath);
        InputStream inputStream = this.getBufferedInputStream(file);
        final String mimeType = this.getMimeType(file);
        return session.getObjectFactory().createContentStream(file.getName(), file.length(), mimeType, inputStream);
    }

    private InputStream getBufferedInputStream(final File file) {
        try {
            return new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMimeType(final File file)  {
        try {
            final String mimeType = MIME_TYPE_DETECTOR.detect(TikaInputStream.get(file), new Metadata()).toString();
            return mimeType;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
