import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class DOMParserExample {
    public static void main(String[] args) {
        try {
            // Tạo DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Phân tích tài liệu XML thành đối tượng Document
            Document document = builder.parse("C:\\Users\\Admin\\IdeaProjects\\ber\\src\\products.xml");

            // Lấy danh sách các phần tử product
            NodeList productList = document.getElementsByTagName("product");

            // Duyệt qua các phần tử product và hiển thị thông tin
            for (int i = 0; i < productList.getLength(); i++) {
                Element product = (Element) productList.item(i);
                String name = product.getElementsByTagName("name").item(0).getTextContent();
                String price = product.getElementsByTagName("price").item(0).getTextContent();
                System.out.println("Product: " + name + ", Price: " + price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}