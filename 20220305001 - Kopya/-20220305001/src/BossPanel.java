// BossPanel.java
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class BossPanel extends JFrame {
    private MainMenu mainMenu;

    public BossPanel(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        setTitle("Boss Panel");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton loadProducts = new JButton("See All Products and Stocks");
        JButton addProductType = new JButton("Add a New Product Type");
        JButton adminSettings = new JButton("Administrative Settings");
        JButton back = new JButton("Back to Main Menu");
        JTable productTable = new JTable();

        // Set button colors to green
        loadProducts.setBackground(Color.GREEN);
        addProductType.setBackground(Color.GREEN);
        adminSettings.setBackground(Color.GREEN);
        back.setBackground(Color.GREEN);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.ORANGE);
        topPanel.add(loadProducts);
        topPanel.add(addProductType);
        topPanel.add(adminSettings);
        topPanel.add(back);

        getContentPane().setBackground(Color.ORANGE);

        String[] messages = {
                "Welcome to the Boss Panel!"

        };

        StringBuilder combined = new StringBuilder();
        for (String msg : messages) {
            combined.append(msg).append("\n");
        }

        JOptionPane.showMessageDialog(this, combined.toString(), "Boss Tips", JOptionPane.INFORMATION_MESSAGE);

        loadProducts.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection()) {
                String query = "SELECT p.product_code, p.product_name, b.name AS brand, c.name AS category, p.stock_quantity " +
                        "FROM products p " +
                        "JOIN brands b ON p.brand_id = b.brand_id " +
                        "JOIN categories c ON p.category_id = c.category_id";

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

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
        });

        addProductType.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "LİMİTED SERVİCE BRO !", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        adminSettings.addActionListener(e -> openAdminSettings());

        back.addActionListener(e -> {
            dispose();
            mainMenu.setVisible(true);
        });

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        setVisible(true);
    }

    private void openAdminSettings() {
        JFrame settingsFrame = new JFrame("Administrative Settings");
        settingsFrame.setSize(300, 150);
        settingsFrame.setLocationRelativeTo(this);

        JButton addUserButton = new JButton("Add a New User");
        JButton deleteUserButton = new JButton("Delete User");

        addUserButton.setBackground(Color.GREEN);
        deleteUserButton.setBackground(Color.GREEN);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.ORANGE);
        panel.add(addUserButton);
        panel.add(deleteUserButton);

        settingsFrame.add(panel);
        settingsFrame.setVisible(true);
    }
}