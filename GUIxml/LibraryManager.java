package GUIxml;

import org.w3c.dom.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.util.Vector;

public class LibraryManager {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField[] fields;
    private final String[] labels = {"Mã sách", "Tên sách", "Tác giả", "Năm XB", "Nhà XB", "Số trang", "Thể loại", "Giá"};
    private final String FILE_PATH = "books.xml";

    public LibraryManager() {
        frame = new JFrame("Quản Lý Thư Viện");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        tableModel = new DefaultTableModel(labels, 0);
        table = new JTable(tableModel);
        loadXML();

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        fields = new JTextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            inputPanel.add(new JLabel(labels[i]));
            fields[i] = new JTextField();
            inputPanel.add(fields[i]);
        }

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Sửa");
        JButton deleteButton = new JButton("Xóa");

        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void loadXML() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("book");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element book = (Element) nodeList.item(i);
                Vector<String> row = new Vector<>();
                for (String label : labels) {
                    row.add(book.getElementsByTagName(label.replace(" ", "").toLowerCase()).item(0).getTextContent());
                }
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element root = doc.createElement("library");
            doc.appendChild(root);

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Element book = doc.createElement("book");
                root.appendChild(book);

                for (int j = 0; j < labels.length; j++) {
                    Element element = doc.createElement(labels[j].replace(" ", "").toLowerCase());
                    element.appendChild(doc.createTextNode(tableModel.getValueAt(i, j).toString()));
                    book.appendChild(element);
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBook() {
        Vector<String> row = new Vector<>();
        for (JTextField field : fields) {
            row.add(field.getText());
        }
        tableModel.addRow(row);
        saveXML();
    }

    private void updateBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            for (int i = 0; i < fields.length; i++) {
                tableModel.setValueAt(fields[i].getText(), selectedRow, i);
            }
            saveXML();
        }
    }

    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveXML();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryManager::new);
    }
}
