import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTExample {
    public static void main(String[] args) {
        try {
            // Đường dẫn tuyệt đối của các file
            File xsltFile = new File("C:\\Users\\Admin\\IdeaProjects\\ber\\src\\products.xsl");
            File xmlFile = new File("C:\\Users\\Admin\\IdeaProjects\\ber\\src\\products.xml");
            File outputFile = new File("C:\\Users\\Admin\\IdeaProjects\\ber\\src\\output.html");

            // Kiểm tra file có tồn tại không
            if (!xsltFile.exists()) {
                System.out.println("Error: XSLT file not found -> " + xsltFile.getAbsolutePath());
                return;
            }
            if (!xmlFile.exists()) {
                System.out.println("Error: XML file not found -> " + xmlFile.getAbsolutePath());
                return;
            }

            // Tạo TransformerFactory
            TransformerFactory factory = TransformerFactory.newInstance();

            // Tạo Transformer từ XSLT
            Source xslt = new StreamSource(xsltFile);
            Transformer transformer = factory.newTransformer(xslt);

            // Chuyển đổi XML thành HTML
            Source xml = new StreamSource(xmlFile);
            transformer.transform(xml, new StreamResult(outputFile));

            System.out.println("Transformation completed. Output: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
