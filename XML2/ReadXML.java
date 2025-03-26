package XML2;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class ReadXML {
    private static final String FILE_NAME = "C:\\Users\\Admin\\IdeaProjects\\ber\\src\\XML2\\company.xml";

    public static void main(String[] args) {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList employees = doc.getElementsByTagName("employee");
            for (int i = 0; i < employees.getLength(); i++) {
                Element employee = (Element) employees.item(i);
                String id = employee.getAttribute("id");
                String name = employee.getElementsByTagName("name").item(0).getTextContent();
                String email = employee.getElementsByTagName("email").item(0).getTextContent();
                String phone = employee.getElementsByTagName("phone").item(0).getTextContent();
                String department = employee.getElementsByTagName("name").item(1).getTextContent();
                String location = employee.getElementsByTagName("location").item(0).getTextContent();

                System.out.println("Employee ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Phone: " + phone);
                System.out.println("Department: " + department);
                System.out.println("Location: " + location);
                System.out.println("-------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

