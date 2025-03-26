package XML2;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;

public class WriteXML {
    private static final String FILE_NAME = "C:\\Users\\Admin\\IdeaProjects\\ber\\src\\XML2\\students.xml";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Student\n2. Delete Student\n3. Update Student\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    String age = scanner.nextLine();
                    System.out.print("Enter major: ");
                    String major = scanner.nextLine();
                    addStudentToXML(id, name, age, major);
                    break;
                case 2:
                    System.out.print("Enter student ID to delete: ");
                    String deleteId = scanner.nextLine();
                    deleteStudentFromXML(deleteId);
                    break;
                case 3:
                    System.out.print("Enter student ID to update: ");
                    String updateId = scanner.nextLine();
                    updateStudentInXML(updateId, scanner);
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addStudentToXML(String id, String name, String age, String major) {
        try {
            File file = new File(FILE_NAME);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;

            if (file.exists()) {
                doc = builder.parse(file);
                doc.getDocumentElement().normalize();
            } else {
                doc = builder.newDocument();
                Element root = doc.createElement("students");
                doc.appendChild(root);
            }

            Element student = doc.createElement("student");
            student.setAttribute("id", id);

            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(name));
            student.appendChild(nameElement);

            Element ageElement = doc.createElement("age");
            ageElement.appendChild(doc.createTextNode(age));
            student.appendChild(ageElement);

            Element majorElement = doc.createElement("major");
            majorElement.appendChild(doc.createTextNode(major));
            student.appendChild(majorElement);

            doc.getDocumentElement().appendChild(student);

            saveXML(doc);
            System.out.println("Student added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudentFromXML(String id) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                System.out.println("File not found!");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList students = doc.getElementsByTagName("student");

            for (int i = 0; i < students.getLength(); i++) {
                Element student = (Element) students.item(i);
                if (student.getAttribute("id").equals(id)) {
                    student.getParentNode().removeChild(student);
                    saveXML(doc);
                    System.out.println("Student deleted successfully!");
                    return;
                }
            }
            System.out.println("Student ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateStudentInXML(String id, Scanner scanner) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                System.out.println("File not found!");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList students = doc.getElementsByTagName("student");

            for (int i = 0; i < students.getLength(); i++) {
                Element student = (Element) students.item(i);
                if (student.getAttribute("id").equals(id)) {
                    System.out.print("Enter new name: ");
                    student.getElementsByTagName("name").item(0).setTextContent(scanner.nextLine());
                    System.out.print("Enter new age: ");
                    student.getElementsByTagName("age").item(0).setTextContent(scanner.nextLine());
                    System.out.print("Enter new major: ");
                    student.getElementsByTagName("major").item(0).setTextContent(scanner.nextLine());

                    saveXML(doc);
                    System.out.println("Student updated successfully!");
                    return;
                }
            }
            System.out.println("Student ID not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveXML(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(FILE_NAME));
        transformer.transform(source, result);
    }
}

