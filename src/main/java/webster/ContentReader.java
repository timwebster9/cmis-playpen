package webster;

import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Read a file from disk.
 */
@Component
public class ContentReader {

    private static final Logger LOG = LoggerFactory.getLogger(ContentReader.class);
    private static final Detector MIME_TYPE_DETECTOR = TikaConfig.getDefaultConfig().getDetector();

    public ContentStream getContentStream(final String filePath, final String documentName) {
        final byte[] bytes = this.readFileFromPath(filePath);
        final InputStream stream = new ByteArrayInputStream(bytes);

        final String mimeType;

        try {
            //mimeType = this.getMimeType(stream);
            mimeType = "application/octet-stream"; //this.getMimeType(stream);
            LOG.info("mime type is {}", mimeType);
        } catch (Exception e) {
            throw new RuntimeException("Unable to detect mime type for file: [" + filePath + "]", e);
        }

        return new ContentStreamImpl(documentName, BigInteger.valueOf(bytes.length), mimeType, stream);
    }

    private String getMimeType(final InputStream inputStream) throws Exception {
        return MIME_TYPE_DETECTOR.detect(TikaInputStream.get(inputStream), new Metadata()).toString();
    }

    private byte[] readFileFromPath(final String filePath) {

        final Path path = Paths.get(filePath);

        try {
            return this.readFile(path);
        }
        catch (final IOException e) {
            throw new RuntimeException("Unable to read file: [" + path + "]", e);
        }
    }

    private byte[] readFile(final Path path) throws IOException {
        try (final FileChannel inChannel = FileChannel.open(path)) {

            LOG.info("Read file {} size is: {} bytes.", path, inChannel.size());
            final MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            buffer.load();
            final byte[] data = new byte[(int)inChannel.size()];
            buffer.get(data);
            return data;
        }
    }
}
