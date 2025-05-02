// EmployeePanel.java
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class EmployeePanel extends JFrame {
    private JTextField searchField;
    private JTable productTable;

    public EmployeePanel() {
        setTitle("Employee Panel");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        searchField = new JTextField(20);
        productTable = new JTable();

        JButton searchButton = new JButton("Search");
        JButton addNoteButton = new JButton("Add Note to Product");
        JButton back = new JButton("Back to Main Menu");

        // Set button colors to green
        searchButton.setBackground(Color.GREEN);
        addNoteButton.setBackground(Color.GREEN);
        back.setBackground(Color.GREEN);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.ORANGE);
        topPanel.add(new JLabel("Search:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(addNoteButton);
        topPanel.add(back);

        getContentPane().setBackground(Color.ORANGE);

        searchButton.addActionListener(e -> searchProducts());

        addNoteButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sorry,enough ! ", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        back.addActionListener(e -> {
            dispose();
            new MainMenu();
        });

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        setVisible(true);
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim();
        String query = "SELECT p.product_code, p.product_name, b.name AS brand, c.name AS category, p.stock_quantity " +
                "FROM products p " +
                "JOIN brands b ON p.brand_id = b.brand_id " +
                "JOIN categories c ON p.category_id = c.category_id " +
                "WHERE p.product_name LIKE ? OR p.product_code LIKE ? OR b.name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);

            ResultSet rs = pstmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Product Code");
            columnNames.add("Product Name");
            columnNames.add("Brand");
            columnNames.add("Category");
            columnNames.add("Stock Quantity");

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getObject("product_code"));
                row.add(rs.getObject("product_name"));
                row.add(rs.getObject("brand"));
                row.add(rs.getObject("category"));
                row.add(rs.getObject("stock_quantity"));
                data.add(row);
            }

            productTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB error: " + ex.getMessage());
        }
    }
}
