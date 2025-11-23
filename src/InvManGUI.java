/*
 * Author: Halcionne (Martin Khlopin)
 * Contributor(s): N/A
 * Date: November 23, 2025
 *
 * InvManGUI - Inventory Management GUI
 * 
 * Resources used:
 * - Oracle Swing Tutorial: https://docs.oracle.com/javase/tutorial/uiswing/
 * - Stack Overflow for GridBagConstraints examples
 * - YouTube: Java Swing GUI Full Course ☕ by Bro Code
 * 
 * 
 * GUI Design - Tab Card-Based Layout
 * 
 * Design Plan:
 * - Simple interface with tabbed navigation
 * - Separate tabs for different operations (Add, Search, Update, Delete, View All)
 * - Color-coded sections for better visual organization
 * - Larger fonts and spacing for improved readability (first implementation had very small text)
 * - Status bar at bottom for feedback messages 
 * - Modern look with borders and padding
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.plaf.ColorUIResource;

/**
 * InvManGUI - GUI design with tabbed interface
 * 
 * GUI main features:
 * - Uses JTabbedPane for operation separation (cleaner workflow)
 * - Card-based layout instead of single-screen design
 * - Status bar for non-intrusive feedback
 * - Larger, more readable fonts and spacing
 * - Table in dedicated "View All" tab
 */
public class InvManGUI extends JFrame {
    // Core components
    private final InventoryManager manager;
    private final DefaultTableModel tableModel;
    private JTable productTable;
    private JTabbedPane tabbedPane;
    private JLabel statusBar;
    
    // Color scheme for modern look
    private final Color ACCENT_COLOR = new Color(52, 152, 219);      // Blue
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);     // Green
    private final Color WARNING_COLOR = new Color(230, 126, 34);     // Orange
    private final Color DANGER_COLOR = new Color(231, 76, 60);       // Red
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light gray
    
    /**
     * Constructor - Creates and initializes the GUI.
     * Sets up the inventory manager, table model, interface components, and loads sample data.
     */
    public InvManGUI() {
        // Initialize backend manager
        manager = new InventoryManager();
        
        // Create table model with 6 columns, starting with 0 rows
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Price", "Quantity", "Type", "Expiration"}, 0) {
            
            // Make all cells read-only
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Build GUI components
        initializeGUI();
        
        // Load sample data
        loadSampleData();
    }
    
    /**
     * Initializes the GUI window, creates the tabbed interface, and adds a status bar.
     */
    private void initializeGUI() {
        setTitle("Inventory Management System - Modern Interface");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Add window listener to print goodbye message when GUI closes
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("\nThank you for using the Inventory Manager Solution!");
            }
        });
        
        // Create tabbed pane at top
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(BACKGROUND_COLOR);

        
        // Add tabs
        tabbedPane.addTab("Add Product", createAddProductPanel());
        tabbedPane.addTab("Search Product", createSearchProductPanel());
        tabbedPane.addTab("Update Product", createUpdateProductPanel());
        tabbedPane.addTab("Delete Product", createDeleteProductPanel());
        tabbedPane.addTab("View All Products", createViewAllPanel());        // Tab 5: Table showing all products
        

        add(tabbedPane, BorderLayout.CENTER);
        
        // Create status bar
        statusBar = new JLabel(" Ready");
        statusBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusBar.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, Color.GRAY),
            new EmptyBorder(5, 10, 5, 10)
        ));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.WHITE);
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private JPanel createAddProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Add New Product");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        JTextField addIdField = createStyledTextField();
        JTextField addNameField = createStyledTextField();
        JTextField addPriceField = createStyledTextField();
        JTextField addQuantityField = createStyledTextField();
        
        JCheckBox addPerishableBox = new JCheckBox("Perishable Product");
        addPerishableBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addPerishableBox.setBackground(Color.WHITE);
        
        JTextField addExpirationField = createStyledTextField();
        addExpirationField.setEnabled(false);
        
        // Lambda expression - learned these are shorthand for anonymous inner classes
        // This enables/disables expiration field based on checkbox state
        addPerishableBox.addActionListener(perishableCheckboxToggleEvent -> 
            addExpirationField.setEnabled(addPerishableBox.isSelected())
        );
        
        int formRowNumber = 0;
        addFormRow(formPanel, gbc, formRowNumber++, "Product ID:", addIdField);
        addFormRow(formPanel, gbc, formRowNumber++, "Product Name:", addNameField);
        addFormRow(formPanel, gbc, formRowNumber++, "Price ($):", addPriceField);
        addFormRow(formPanel, gbc, formRowNumber++, "Quantity:", addQuantityField);
        
        gbc.gridx = 0;
        gbc.gridy = formRowNumber++;
        gbc.gridwidth = 2;
        formPanel.add(addPerishableBox, gbc);
        gbc.gridwidth = 1;
        
        addFormRow(formPanel, gbc, formRowNumber++, "Expiration (YYYY-MM-DD):", addExpirationField);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        JButton addButton = createStyledButton("Add Product", SUCCESS_COLOR);
        JButton clearButton = createStyledButton("Clear Fields", Color.GRAY);
        
        addButton.addActionListener(addButtonClickEvent -> {
            addProductFromFields(addIdField, addNameField, addPriceField, 
                addQuantityField, addPerishableBox, addExpirationField);
        });
        
        clearButton.addActionListener(clearButtonClickEvent -> {
            addIdField.setText("");
            addNameField.setText("");
            addPriceField.setText("");
            addQuantityField.setText("");
            addPerishableBox.setSelected(false);
            addExpirationField.setText("");
            addExpirationField.setEnabled(false);
            setStatus("Fields cleared", Color.BLACK);
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates the Search Product tab with ID input field and results display area.
     * 
     * @return A JPanel containing the complete Search Product interface
     */
    private JPanel createSearchProductPanel() {
        // Create main panel with BorderLayout and spacing
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Search Product by ID");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        searchPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel searchLabel = new JLabel("Product ID:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        JTextField searchIdField = createStyledTextField();
        searchIdField.setPreferredSize(new Dimension(200, 35));
        
        JButton searchButton = createStyledButton("Search", ACCENT_COLOR);
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchIdField);
        searchPanel.add(searchButton);
        panel.add(searchPanel, BorderLayout.CENTER);
        
        // Results display
        JTextArea resultArea = new JTextArea(15, 50);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(new TitledBorder("Search Results"));
        
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBackground(BACKGROUND_COLOR);
        resultPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(resultPanel, BorderLayout.SOUTH);
        
        // DEFINE WHAT HAPPENS WHEN SEARCH BUTTON IS CLICKED
        searchButton.addActionListener(searchActionEvent -> {
            // Get the text from the input field and remove leading/trailing spaces
            String searchProductIdText = searchIdField.getText().trim();
            
            // Check if the field is empty
            if (searchProductIdText.isEmpty()) {
                showWarning("Please enter a Product ID"); // Show popup warning
                resultArea.setText("Please enter a Product ID");
                return;
            }
            
            try {
                int currentSearchedProductId = Integer.parseInt(searchProductIdText);
                Product[] allProductsArrayInSearch = manager.getProducts();
                Product foundProductInSearch = null;
                
                // Find product with matching ID
                for (int searchLoopIndex = 0; searchLoopIndex < manager.getProductCount(); searchLoopIndex++) {
                    if (allProductsArrayInSearch[searchLoopIndex] != null && 
                        allProductsArrayInSearch[searchLoopIndex].getProductId() == currentSearchedProductId) {
                        foundProductInSearch = allProductsArrayInSearch[searchLoopIndex];
                        break;
                    }
                }
                
                if (foundProductInSearch != null) {
                    // Build result display
                    StringBuilder searchResultBuilder = new StringBuilder();
                    searchResultBuilder.append("PRODUCT FOUND\n");
                    searchResultBuilder.append("═".repeat(50)).append("\n\n");
                    
                    searchResultBuilder.append(String.format("Product ID:      %d\n", foundProductInSearch.getProductId()));
                    searchResultBuilder.append(String.format("Product Name:    %s\n", foundProductInSearch.productName));
                    searchResultBuilder.append(String.format("Price:           %s\n", formatPrice(foundProductInSearch.getPrice())));
                    searchResultBuilder.append(String.format("Quantity:        %s\n", formatQuantity(foundProductInSearch.getQuantity())));
                    
                    if (foundProductInSearch instanceof PerishableProduct foundPerishableProductInSearch) {
                        searchResultBuilder.append(String.format("Type:            Perishable\n"));
                        
                        if (foundPerishableProductInSearch.getExpirationDate().isPresent()) {
                            searchResultBuilder.append(String.format("Expiration:      %s\n", 
                                foundPerishableProductInSearch.getExpirationDate().get()));
                            searchResultBuilder.append(String.format("Is Expired:      %s\n", 
                                foundPerishableProductInSearch.isExpired() ? "YES" : "NO"));
                        } else {
                            searchResultBuilder.append("Expiration:      No expiration date\n");
                        }
                    } else {
                        searchResultBuilder.append("Type:            Standard\n");
                    }
                    
                    resultArea.setText(searchResultBuilder.toString());
                    setStatus("Product found: " + foundProductInSearch.productName, SUCCESS_COLOR);
                } else {
                    // Product not found - show messages in both text area and popup
                    resultArea.setText("Product with ID " + currentSearchedProductId + " not found.");
                    showError("Product with ID " + currentSearchedProductId + " not found.");
                }
            } catch (NumberFormatException numberFormatException) {
                // User entered non-numeric ID (e.g., "abc" instead of "123")
                resultArea.setText("Invalid Product ID format. Please enter a number.");
                showError("Invalid Product ID format!\nPlease enter a valid number.");
            }
        });
        
        // Return the completed search panel
        return panel;
    }
    
    /**
     * Creates the "Update Product" tab - allows users to modify existing product details.
     * This is a TWO-STEP process: First LOAD the product, then MODIFY and UPDATE it.
     * 
     * What it includes:
     * - Title at the top ("Update Product")
     * - Form with the same fields as Add Product (ID, name, price, quantity, expiration)
     * - Three buttons: "Load Product", "Update Product", and "Clear"
     * 
     * @return A JPanel containing the complete Update Product interface
     */
    private JPanel createUpdateProductPanel() {
        // Create main panel with standard layout and styling
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TITLE SECTION
        JLabel titleLabel = new JLabel("Update Product");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_COLOR); // Blue color
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // FORM SECTION
        // This form is almost identical to the Add Product form
        // The difference: it has a "Load" button to populate fields with existing data
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Set up grid layout rules
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);
        
        // Create all input fields (same as Add Product tab)
        JTextField updateIdField = createStyledTextField();       // Product ID to load/update
        JTextField updateNameField = createStyledTextField();     // Product name
        JTextField updatePriceField = createStyledTextField();    // Price
        JTextField updateQuantityField = createStyledTextField(); // Quantity in stock
        JCheckBox updatePerishableBox = new JCheckBox("Perishable Product");
        updatePerishableBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        updatePerishableBox.setBackground(Color.WHITE);
        JTextField updateExpirationField = createStyledTextField(); // Expiration date (optional)
        updateExpirationField.setEnabled(false); // Start disabled
        
        // Link checkbox to expiration field (enable/disable based on checkbox)
        updatePerishableBox.addActionListener(updatePerishableCheckboxToggleEvent -> 
            updateExpirationField.setEnabled(updatePerishableBox.isSelected())
        );
        
        // Add all form rows in order
        int formRowNumber = 0;
        addFormRow(formPanel, gbc, formRowNumber++, "Product ID:", updateIdField);
        addFormRow(formPanel, gbc, formRowNumber++, "Product Name:", updateNameField);
        addFormRow(formPanel, gbc, formRowNumber++, "Price ($):", updatePriceField);
        addFormRow(formPanel, gbc, formRowNumber++, "Quantity:", updateQuantityField);
        
        // Add checkbox (spans 2 columns)
        gbc.gridx = 0;
        gbc.gridy = formRowNumber++;
        gbc.gridwidth = 2;
        formPanel.add(updatePerishableBox, gbc);
        gbc.gridwidth = 1;
        
        addFormRow(formPanel, gbc, formRowNumber++, "Expiration (YYYY-MM-DD):", updateExpirationField);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // BUTTON SECTION
        // Three buttons: Load, Update, and Clear
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        // Create three buttons with different purposes
        JButton loadButton = createStyledButton("Load Product", ACCENT_COLOR);      // Blue - Load existing data
        JButton updateButton = createStyledButton("Update Product", SUCCESS_COLOR); // Green - Save changes
        JButton clearButton = createStyledButton("Clear", Color.GRAY);              // Gray - Reset form
        
        // LOAD BUTTON ACTION - Fetches existing product data and fills the form
        // Load Product button - fetches existing product data into form
        loadButton.addActionListener(loadActionEvent -> {
            // Get the Product ID from the ID field
            String loadProductIdText = updateIdField.getText().trim();
            
            // Validate: Make sure user entered an ID
            if (loadProductIdText.isEmpty()) {
                showWarning("Enter Product ID first"); // Show popup warning
                return; // Stop here
            }
            
            try {
                // Convert ID text to integer
                int parsedLoadProductId = Integer.parseInt(loadProductIdText);
                
                // Search through all products to find matching ID
                Product[] allProductsArrayForLoad = manager.getProducts();
                Product foundProductToLoad = null; // Will hold the found product
                
                // Loop through all products
                for (int loadLoopIndex = 0; loadLoopIndex < manager.getProductCount(); loadLoopIndex++) {
                    if (allProductsArrayForLoad[loadLoopIndex] != null && 
                        allProductsArrayForLoad[loadLoopIndex].getProductId() == parsedLoadProductId) {
                        foundProductToLoad = allProductsArrayForLoad[loadLoopIndex];
                        break; // Found it! Stop searching
                    }
                }
                
                // Check if we found the product
                if (foundProductToLoad != null) {
                    // POPULATE THE FORM with existing product data
                    // This lets the user see current values and modify what they want
                    updateNameField.setText(foundProductToLoad.productName);
                    updatePriceField.setText(String.valueOf(foundProductToLoad.getPrice()));
                    updateQuantityField.setText(String.valueOf(foundProductToLoad.getQuantity()));
                    
                    // Check if it's a perishable product
                    if (foundProductToLoad instanceof PerishableProduct foundPerishableProductToLoad) {
                        updatePerishableBox.setSelected(true);     // Check the checkbox
                        updateExpirationField.setEnabled(true);    // Enable expiration field
                        
                        // If it has an expiration date, show it
                        if (foundPerishableProductToLoad.getExpirationDate().isPresent()) {
                            updateExpirationField.setText(
                                foundPerishableProductToLoad.getExpirationDate().get().toString());
                        }
                    } else {
                        // Not perishable - uncheck and disable expiration field
                        updatePerishableBox.setSelected(false);
                        updateExpirationField.setEnabled(false);
                    }
                    
                    // Show success message
                    setStatus("Product loaded: " + foundProductToLoad.productName, SUCCESS_COLOR);
                } else {
                    // Product not found
                    showError("Product ID " + parsedLoadProductId + " not found!");
                }
            } catch (NumberFormatException numberFormatException) {
                // User entered non-numeric ID
                showError("Invalid Product ID format!\nPlease enter a valid number.");
            }
        });
        
        // UPDATE BUTTON ACTION - Saves the modified product data
        // Update Product button - saves changes
        // Delegates to another method that handles validation and saving
        updateButton.addActionListener(updateButtonClickEvent -> {
            updateProductFromFields(updateIdField, updateNameField, updatePriceField,
                updateQuantityField, updatePerishableBox, updateExpirationField);
        });
        
        // CLEAR BUTTON ACTION - Resets all fields to empty
        // Useful if user wants to start over or load a different product
        clearButton.addActionListener(clearButtonClickEvent -> {
            updateIdField.setText("");              // Clear ID
            updateNameField.setText("");            // Clear name
            updatePriceField.setText("");           // Clear price
            updateQuantityField.setText("");        // Clear quantity
            updatePerishableBox.setSelected(false); // Uncheck checkbox
            updateExpirationField.setText("");      // Clear expiration
            updateExpirationField.setEnabled(false); // Disable expiration field
            setStatus("Fields cleared", Color.BLACK); // Update status bar
        });
        
        // Add all three buttons to the button panel
        buttonPanel.add(loadButton);    // Load button on the left
        buttonPanel.add(updateButton);  // Update button in the middle
        buttonPanel.add(clearButton);   // Clear button on the right
        
        // Place button panel at the bottom of the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Return the completed Update Product panel
        return panel;
    }
    
    /**
     * Creates the "Delete Product" tab - allows users to permanently remove products from inventory.
     * This is a DANGEROUS operation that cannot be undone, so it includes a confirmation dialog.
     * 
     * What it includes:
     * - Title at the top ("Delete Product") in red to indicate danger
     * - Simple input field for Product ID
     * - "Delete Product" button (red for warning)
     * - Warning message about permanent deletion
     * - Confirmation popup before deleting
     * 
     * @return A JPanel containing the complete Delete Product interface
     */
    private JPanel createDeleteProductPanel() {
        // Create main panel with standard layout
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // TITLE SECTION
        // Use red color (DANGER_COLOR) to warn users this is a destructive action
        JLabel titleLabel = new JLabel("Delete Product");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(DANGER_COLOR); // RED to indicate danger/warning
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // DELETE INPUT SECTION (center area)
        // FlowLayout arranges components horizontally in a row
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        deletePanel.setBackground(BACKGROUND_COLOR);
        
        // Create the input components
        JLabel deleteLabel = new JLabel("Product ID:");
        deleteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        JTextField deleteIdField = createStyledTextField();
        deleteIdField.setPreferredSize(new Dimension(200, 35)); // 200px wide, 35px tall
        
        // Delete button in red to emphasize this is a dangerous action
        JButton deleteButton = createStyledButton("Delete Product", DANGER_COLOR);
        
        // Add components to delete panel in left-to-right order
        deletePanel.add(deleteLabel);
        deletePanel.add(deleteIdField);
        deletePanel.add(deleteButton);
        
        // WARNING MESSAGE
        // Show a clear warning that deletion is permanent
        JLabel warningLabel = new JLabel("Warning: This action cannot be undone!");
        warningLabel.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Bold for emphasis
        warningLabel.setForeground(DANGER_COLOR); // Red text
        warningLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        
        // Combine delete panel and warning into one center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(deletePanel, BorderLayout.CENTER);   // Input field and button
        centerPanel.add(warningLabel, BorderLayout.SOUTH);   // Warning below
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // DELETE BUTTON ACTION - Handles the deletion process with confirmation
        deleteButton.addActionListener(deleteActionEvent -> {
            // Get the Product ID from input field
            String deleteProductIdText = deleteIdField.getText().trim();
            
            // Validate: Check if field is empty
            if (deleteProductIdText.isEmpty()) {
                showWarning("Please enter a Product ID");
                return; // Stop here
            }
            
            try {
                int currentDeletingProductId = Integer.parseInt(deleteProductIdText);
                
                // Find the product
                Product[] allProductsArrayForDelete = manager.getProducts();
                Product foundProductToDelete = null;
                
                for (int deleteLoopIndex = 0; deleteLoopIndex < manager.getProductCount(); deleteLoopIndex++) {
                    if (allProductsArrayForDelete[deleteLoopIndex] != null && 
                        allProductsArrayForDelete[deleteLoopIndex].getProductId() == currentDeletingProductId) {
                        foundProductToDelete = allProductsArrayForDelete[deleteLoopIndex];
                        break;
                    }
                }
                
                if (foundProductToDelete == null) {
                    showError("Product ID " + currentDeletingProductId + " not found!");
                    return;
                }
                
                // Show confirmation dialog
                
                String deleteMessage = String.format(
                    "Are you sure you want to delete:\n\n" +
                    "Product ID: %d\n" +
                    "Name: %s\n" +
                    "Price: %s\n" +
                    "Quantity: %s",
                    foundProductToDelete.getProductId(),
                    foundProductToDelete.productName,
                    formatPrice(foundProductToDelete.getPrice()),
                    formatQuantity(foundProductToDelete.getQuantity())
                );
                
                int userDeleteConfirmationResponse = JOptionPane.showConfirmDialog(
                    this,                                                           // Parent component
                    deleteMessage,                                                  // Message with product details
                    "Confirm Deletion",                                            // Dialog title
                    JOptionPane.YES_NO_OPTION,                                     // Buttons: Yes and No
                    JOptionPane.WARNING_MESSAGE                                    // Warning icon (yellow triangle)
                );
                
                // If user clicked YES, delete the product
                if (userDeleteConfirmationResponse == JOptionPane.YES_OPTION) {
                    manager.deleteProduct(currentDeletingProductId);  // Remove from inventory
                    refreshTable();                                 // Update the View All table
                    deleteIdField.setText("");                      // Clear the input field
                    setStatus("Product deleted successfully", SUCCESS_COLOR); // Show success message
                }
                // If user clicked NO, nothing happens (dialog just closes)
                
            } catch (NumberFormatException numberFormatException) {
                // User entered non-numeric ID
                showError("Invalid Product ID format!\nPlease enter a valid number.");
            } catch (ProductNotFoundException productNotFoundException) {
                // Product was not found in inventory (shouldn't happen since we check above)
                showError(productNotFoundException.getMessage());
            }
        });
        
        // Return the completed panel
        return panel;
    }
    
    /**
     * Creates the "View All Products" tab - displays all products in a sortable table.
     * This is the main viewing interface where users can see their entire inventory at a glance.
     * 
     * What it includes:
     * - Title and sort controls (dropdown menu to sort by different criteria)
     * - Large table showing all products with 6 columns (ID, Name, Price, Quantity, Type, Expiration)
     * - "Refresh" button to update the table after changes
     * - Stats panel at bottom showing total product count
     * 
     * Features:
     * - Table is read-only (users can't edit directly, must use Update tab)
     * - Supports 12 different sorting options (ID, Name, Price, Quantity, Type, Expiration)
     * - Automatically formats prices with $ symbol
     * - Shows "EXPIRED" tag for products past their expiration date
     * 
     * @return A JPanel containing the complete View All Products interface
     */
    private JPanel createViewAllPanel() {
        // Create main panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // HEADER SECTION (title + controls at top)
        // Split into two sides: title on left, controls on right
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        
        // Left side: Title
        JLabel titleLabel = new JLabel("All Products");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(ACCENT_COLOR); // Blue color
        
        // Right side: Sort controls (label + dropdown + refresh button)
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controlsPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Dropdown menu with 12 sorting options
        // JComboBox = dropdown list that user can click to select an option
        JComboBox<String> sortDropdown = new JComboBox<>(new String[]{
            "ID (Ascending)", "ID (Descending)",               // Sort by ID number
            "Name (A-Z)", "Name (Z-A)",                        // Sort alphabetically
            "Price (Low to High)", "Price (High to Low)",      // Sort by price
            "Quantity (Low to High)", "Quantity (High to Low)", // Sort by stock amount
            "Type (Perishable First)", "Type (Standard First)", // Sort by product type
            "Expiration (Earliest First)", "Expiration (Latest First)" // Sort by expiration date
        });
        sortDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sortDropdown.setPreferredSize(new Dimension(200, 30)); // 200px wide, 30px tall
        
        JButton refreshButton = createStyledButton("Refresh", ACCENT_COLOR); // Blue button
        
        // DEFINE WHAT HAPPENS WHEN USER SELECTS A DIFFERENT SORT OPTION
        // This listener fires whenever the dropdown selection changes
        sortDropdown.addActionListener(sortActionEvent -> {
            // Get the selected sorting option (e.g., "Name (A-Z)")
            String selectedSortOption = (String) sortDropdown.getSelectedItem();
            
            // Re-sort and refresh the table with the new sort order
            refreshTableWithSort(selectedSortOption);
            
            // Show confirmation message in status bar
            setStatus("Table sorted by: " + selectedSortOption, SUCCESS_COLOR);
        });
        
        // DEFINE WHAT HAPPENS WHEN USER CLICKS REFRESH BUTTON
        // Useful after adding/updating/deleting products to see latest data
        refreshButton.addActionListener(refreshActionEvent -> {
            String selectedSortOption = (String) sortDropdown.getSelectedItem();
            refreshTableWithSort(selectedSortOption); // Re-sort with current selection
            setStatus("Table refreshed", SUCCESS_COLOR);
        });
        
        // Add all control components to controls panel
        controlsPanel.add(sortLabel);
        controlsPanel.add(sortDropdown);
        controlsPanel.add(refreshButton);
        
        // Combine title and controls into header panel
        headerPanel.add(titleLabel, BorderLayout.WEST);     // Title on left
        headerPanel.add(controlsPanel, BorderLayout.EAST);  // Controls on right
        panel.add(headerPanel, BorderLayout.NORTH);          // Header at top
        
        // TABLE SECTION (center - takes up most of the space)
        // JTable displays data in rows and columns (like Excel spreadsheet)
        // tableModel (created in constructor) controls what data is shown
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        productTable.setRowHeight(30); // Make rows 30 pixels tall for readability
        
        // Style the table header (column names at top)
        productTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14)); // Bold header
        productTable.getTableHeader().setBackground(ACCENT_COLOR); // Blue background
        productTable.getTableHeader().setForeground(Color.WHITE);  // White text
        
        // Table selection settings
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only one row at a time can be selected
        productTable.setGridColor(Color.LIGHT_GRAY); // Light gray lines between cells
        
        // Wrap table in a scroll pane (adds scrollbars if table is too large)
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1)); // Gray border around table
        panel.add(scrollPane, BorderLayout.CENTER); // Table takes up center space
        
        // STATS PANEL (bottom - shows summary info)
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(new CompoundBorder(
            new LineBorder(Color.LIGHT_GRAY, 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        
        // Label showing total products and max capacity
        JLabel statsLabel = new JLabel("Total Products: 0 | Capacity: 50");
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statsPanel.add(statsLabel);
        panel.add(statsPanel, BorderLayout.SOUTH); // Stats at bottom
        
        // UPDATE STATS WHEN TABLE REFRESHES
        // Attach another listener to refresh button to update the product count
        refreshButton.addActionListener(statsUpdateActionEvent -> {
            // Count how many products actually exist (skip null slots)
            int actualProductCount = 0;
            Product[] allProductsArrayForStats = manager.getProducts();
            
            // Loop through array and count non-null products
            for (int statsLoopIndex = 0; statsLoopIndex < manager.getProductCount(); statsLoopIndex++) {
                if (allProductsArrayForStats[statsLoopIndex] != null) {
                    actualProductCount++; // Found a product, increment counter
                }
            }
            
            // Update the stats label with actual count
            statsLabel.setText(String.format("Total Products: %d | Capacity: 50", actualProductCount));
        });
        
        // Return the completed panel
        return panel;
    }
    
    /**
     * Refreshes and sorts the product table based on the selected sort option.
     * Collects products into a list, applies sorting, then repopulates the table.
     * 
     * @param sortOption Sorting criterion (e.g., "Name (A-Z)", "Price (Low to High)")
     */
    private void refreshTableWithSort(String sortOption) {
        // Collect products into list
        Product[] allProductsArray = manager.getProducts();
        ArrayList<Product> productList = new ArrayList<>();
        
        for (int arrayIndex = 0; arrayIndex < manager.getProductCount(); arrayIndex++) {
            if (allProductsArray[arrayIndex] != null) {
                productList.add(allProductsArray[arrayIndex]);
            }
        }
        
        // Sort based on selected option
        switch (sortOption) {
            case "ID (Ascending)" -> productList.sort((firstProduct, secondProduct) -> 
                Integer.compare(firstProduct.getProductId(), secondProduct.getProductId()));
            
            case "ID (Descending)" -> productList.sort((firstProduct, secondProduct) -> 
                Integer.compare(secondProduct.getProductId(), firstProduct.getProductId()));
            
            case "Name (A-Z)" -> productList.sort((firstProduct, secondProduct) -> 
                firstProduct.productName.compareToIgnoreCase(secondProduct.productName));
            
            case "Name (Z-A)" -> productList.sort((firstProduct, secondProduct) -> 
                secondProduct.productName.compareToIgnoreCase(firstProduct.productName));
            
            case "Price (Low to High)" -> productList.sort((firstProduct, secondProduct) -> 
                Double.compare(firstProduct.getPrice(), secondProduct.getPrice()));
            
            case "Price (High to Low)" -> productList.sort((firstProduct, secondProduct) -> 
                Double.compare(secondProduct.getPrice(), firstProduct.getPrice()));
            
            case "Quantity (Low to High)" -> productList.sort((firstProduct, secondProduct) -> 
                Integer.compare(firstProduct.getQuantity(), secondProduct.getQuantity()));
            
            case "Quantity (High to Low)" -> productList.sort((firstProduct, secondProduct) -> 
                Integer.compare(secondProduct.getQuantity(), firstProduct.getQuantity()));
            
            case "Type (Perishable First)" -> productList.sort((firstProduct, secondProduct) -> {
                boolean firstProductIsPerishable = firstProduct instanceof PerishableProduct;
                boolean secondProductIsPerishable = secondProduct instanceof PerishableProduct;
                return Boolean.compare(secondProductIsPerishable, firstProductIsPerishable);
            });
            
            case "Type (Standard First)" -> productList.sort((firstProduct, secondProduct) -> {
                boolean firstProductIsPerishable = firstProduct instanceof PerishableProduct;
                boolean secondProductIsPerishable = secondProduct instanceof PerishableProduct;
                return Boolean.compare(firstProductIsPerishable, secondProductIsPerishable);
            });
            
            // Expiration date sorting
            case "Expiration (Earliest First)" -> productList.sort((firstProduct, secondProduct) -> {
                // Handle non-perishable products
                if (!(firstProduct instanceof PerishableProduct) && !(secondProduct instanceof PerishableProduct)) return 0;
                if (!(firstProduct instanceof PerishableProduct)) return 1;
                if (!(secondProduct instanceof PerishableProduct)) return -1;
                
                PerishableProduct firstPerishableProduct = (PerishableProduct) firstProduct;
                PerishableProduct secondPerishableProduct = (PerishableProduct) secondProduct;
                
                // Handle missing expiration dates
                if (firstPerishableProduct.getExpirationDate().isEmpty() && secondPerishableProduct.getExpirationDate().isEmpty()) return 0;
                if (firstPerishableProduct.getExpirationDate().isEmpty()) return 1;
                if (secondPerishableProduct.getExpirationDate().isEmpty()) return -1;
                
                return firstPerishableProduct.getExpirationDate().get()
                    .compareTo(secondPerishableProduct.getExpirationDate().get());
            });
            
            case "Expiration (Latest First)" -> productList.sort((firstProduct, secondProduct) -> {
                if (!(firstProduct instanceof PerishableProduct) && !(secondProduct instanceof PerishableProduct)) return 0;
                if (!(firstProduct instanceof PerishableProduct)) return 1;
                if (!(secondProduct instanceof PerishableProduct)) return -1;
                
                PerishableProduct firstPerishableProduct = (PerishableProduct) firstProduct;
                PerishableProduct secondPerishableProduct = (PerishableProduct) secondProduct;
                
                if (firstPerishableProduct.getExpirationDate().isEmpty() && secondPerishableProduct.getExpirationDate().isEmpty()) return 0;
                if (firstPerishableProduct.getExpirationDate().isEmpty()) return 1;
                if (secondPerishableProduct.getExpirationDate().isEmpty()) return -1;
                
                return secondPerishableProduct.getExpirationDate().get()
                    .compareTo(firstPerishableProduct.getExpirationDate().get());
            });
        }
        
        // Clear table and repopulate
        tableModel.setRowCount(0);
        
        for (Product currentProductForDisplay : productList) {
            String productTypeDisplayText = (currentProductForDisplay instanceof PerishableProduct) 
                ? "Perishable"
                : "Standard";
            
            String expirationDisplayText;
            
            if (currentProductForDisplay instanceof PerishableProduct perishableProductForDisplay) {
                if (perishableProductForDisplay.getExpirationDate().isPresent()) {
                    expirationDisplayText = perishableProductForDisplay.getExpirationDate().get().toString();
                    
                    if (perishableProductForDisplay.isExpired()) {
                        expirationDisplayText += " EXPIRED";
                    }
                } else {
                    expirationDisplayText = "No expiration";
                }
            } else {
                expirationDisplayText = "N/A";
            }
            
            tableModel.addRow(new Object[]{
                currentProductForDisplay.getProductId(),
                currentProductForDisplay.productName,
                formatPrice(currentProductForDisplay.getPrice()),
                formatQuantity(currentProductForDisplay.getQuantity()),
                productTypeDisplayText,
                expirationDisplayText
            });
        }
    }
    
    /**
     * Adds a labeled form row to a panel using GridBagLayout.
     * Label takes 30% width on left, input field takes 70% width on right.
     * 
     * @param panel The JPanel to add the row to (must use GridBagLayout)
     * @param gbc GridBagConstraints object for positioning
     * @param row The row number
     * @param labelText The label text
     * @param field The input component
     */
    private void addFormRow(JPanel panel, GridBagConstraints gridConstraints, int row, 
                           String labelText, JComponent field) {
        // Create and position label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gridConstraints.gridx = 0;
        gridConstraints.gridy = row;
        gridConstraints.weightx = 0.3;
        panel.add(label, gridConstraints);
        
        // Position input field
        gridConstraints.gridx = 1;
        gridConstraints.weightx = 0.7;
        panel.add(field, gridConstraints);
    }
    
    /**
     * Creates a text field with consistent styling (Segoe UI font, gray border, padding).
     * 
     * @return Configured JTextField
     */
    private JTextField createStyledTextField() {
        // 20 is the preferred width in characters
        JTextField field = new JTextField(20);
        
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // CompoundBorder: took me a while to figure out how to nest borders
        // Outer border is the gray line, inner border adds padding
        // Learned from Oracle Swing border tutorial
        field.setBorder(new CompoundBorder(
            new LineBorder(Color.GRAY, 1),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        return field;
    }
    
    /**
     * Creates a button with consistent styling (bold font, colored background, white text).
     * 
     * @param text Button label
     * @param color Background color (use ACCENT_COLOR or DANGER_COLOR)
     * @return Configured JButton
     */
    private JButton createStyledButton(String text, Color color) {
        // Create button with the text label
        JButton button = new JButton(text);
        
        // Set bold font for emphasis (makes button text stand out)
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        // Set button background color (blue for normal actions, red for dangerous ones)
        button.setBackground(color);
        
        // Set text color to white (provides good contrast against colored background)
        button.setForeground(Color.WHITE);
        
        // Disable focus painting (removes the dotted rectangle that appears when button is clicked)
        // Makes the UI look cleaner and more modern
        button.setFocusPainted(false);
        
        // Add padding inside the button for comfortable clicking
        // EmptyBorder(top, left, bottom, right) = (10, 20, 10, 20)
        // More horizontal padding (20px) than vertical (10px) creates a nice rectangular shape
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Change mouse cursor to hand pointer when hovering over button
        // Visual feedback that the button is clickable
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Return the fully styled button
        return button;
    }
    
    /**
     * Updates the status bar text and color.
     * Use green for success, red for errors, orange for warnings, black for info.
     * 
     * @param message Status text to display
     * @param color Text color
     */
    private void setStatus(String message, Color color) {
        // Set the text in the status bar
        // Adding a space at the start (" " + message) creates a small left margin for readability
        statusBar.setText(" " + message);
        
        // Set the text color based on message type
        // Green = success, Red = error, Orange = warning, Black = neutral
        statusBar.setForeground(color);
    }
    
    /**
     * Shows an error dialog with a red X icon and updates the status bar.
     * Use for validation errors or failed operations that need immediate attention.
     * 
     * @param message The error text to display
     */
    private void showError(String message) {
        // Remove focus border from dialog buttons (makes dialog look cleaner)
        // UIManager controls the look-and-feel of all Swing components
        // Setting alpha to 0 makes the focus border fully transparent (invisible)
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        
        // Display a modal error dialog
        // Modal = blocks all other interaction until user clicks OK
        JOptionPane.showMessageDialog(
            this,                          // Parent window (centers dialog over main window)
            message,                       // The error message to display
            "Validation Error",            // Title bar text
            JOptionPane.ERROR_MESSAGE      // Icon type (shows red X icon)
        );
        
        // Also update status bar with same message (backup notification)
        // DANGER_COLOR = red text to indicate error
        setStatus(message, DANGER_COLOR);
    }
    
    /**
     * Shows a warning dialog with a yellow triangle icon and updates the status bar.
     * Use for non-critical issues that need user attention.
     * 
     * @param message The warning text to display
     */
    private void showWarning(String message) {
        // Remove focus border from dialog buttons for cleaner appearance
        // Same technique as showError() - makes button focus invisible
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        
        // Display a modal warning dialog
        JOptionPane.showMessageDialog(
            this,                            // Parent window
            message,                         // Warning message text
            "Warning",                       // Title bar text
            JOptionPane.WARNING_MESSAGE      // Icon type (shows yellow triangle with ! icon)
        );
        
        // Update status bar with warning (orange text)
        setStatus(message, WARNING_COLOR);
    }
    
    /**
     * Validates input, creates a product (Standard or Perishable), and adds it to inventory.
     * Handles all validation rules: empty fields, name format, numeric ranges, duplicate IDs, and capacity limits.
     * 
     * @param idField Product ID input
     * @param nameField Product name input
     * @param priceField Price input
     * @param quantityField Quantity input
     * @param perishableBox Checkbox for perishable type
     * @param expirationField Expiration date input (YYYY-MM-DD)
     */
    private void addProductFromFields(JTextField idField, JTextField nameField,
                                     JTextField priceField, JTextField quantityField,
                                     JCheckBox perishableBox, JTextField expirationField) {
        try {
            // Get input values
            String productIdText = idField.getText().trim();
            String productName = nameField.getText().trim();
            String productPriceText = priceField.getText().trim();
            String productQuantityText = quantityField.getText().trim();
            
            // Check for empty required fields
            if (productIdText.isEmpty() || productName.isEmpty() || productPriceText.isEmpty() || productQuantityText.isEmpty()) {
                showError("All fields except expiration are required!");
                return;
            }
            
            // Validate product name
            
            // Check maximum length (100 characters)
            if (productName.length() > 100) {
                showError("Product name too long (max 100 characters)!");
                return;
            }
            
            // Check minimum length (2 characters)
            if (productName.length() < 2) {
                showError("Product name too short (min 2 characters)!");
                return;
            }
            
            // Reject names that are ONLY numbers (e.g., "12345" is not a valid name)
            // ^[0-9 ]+$ means: start to end, only digits and spaces
            if (productName.matches("^[0-9 ]+$")) {
                showError("Product name cannot be numbers only!");
                return;
            }
            
            // Check for valid characters only
            // ^[a-zA-Z0-9 .,'%-]+$ allows: letters, numbers, spaces, and punctuation . , ' % -
            if (!productName.matches("^[a-zA-Z0-9 .,'%-]+$")) {
                showError("Product name contains invalid characters!\nAllowed: letters, numbers, spaces, and . , ' % -");
                return;
            }
            
            // Parse numeric values
            int parsedProductId = Integer.parseInt(productIdText);
            double parsedProductPrice = Double.parseDouble(productPriceText);
            int parsedProductQuantity = Integer.parseInt(productQuantityText);
            
            // Check if price resulted in Infinity
            if (Double.isInfinite(parsedProductPrice) || Double.isNaN(parsedProductPrice)) {
                showError("Price is too large!\nMaximum allowed: $" + String.format("%,.2f", Double.MAX_VALUE));
                return;
            }
            
            // Validate numeric ranges
            
            // ID must be at least 1 (user-friendly numbering starts from 1, not 0)
            if (parsedProductId < 1) {
                showError("Product ID must be at least 1!\nValid range: 1-50");
                return;
            }
            
            // ID must be at most 50 (matching the 50-product inventory limit)
            // Valid IDs are 1-50
            if (parsedProductId > 50) {
                showError("Product ID must be 50 or less!\nValid range: 1-50");
                return;
            }
            
            // Price must be greater than zero (can't have free or negative-priced products)
            if (parsedProductPrice <= 0) {
                showError("Price must be greater than zero!");
                return;
            }
            
            // Price must be reasonable (prevent absurdly high values)
            // Maximum: $999,999,999,999.99 (nearly one trillion dollars)
            if (parsedProductPrice > 999999999999.99) {
                showError("Price is too high!\nMaximum allowed: $999,999,999,999.99");
                return;
            }
            
            // Quantity must be non-negative (upper limit is Integer.MAX_VALUE = 2,147,483,647)
            if (parsedProductQuantity < 0) {
                showError("Quantity cannot be negative!");
                return;
            }
            
            // Check inventory capacity
            if (manager.getProductCount() >= 50) {
                showError("Inventory is full! Maximum 50 products.");
                return;
            }
            
            // Check for duplicate product IDs
            Product[] existingProductsArray = manager.getProducts();
            
            for (int arrayIndex = 0; arrayIndex < manager.getProductCount(); arrayIndex++) {
                if (existingProductsArray[arrayIndex] != null && existingProductsArray[arrayIndex].getProductId() == parsedProductId) {
                    showError("Product with ID " + parsedProductId + " already exists!");
                    return;
                }
            }
            
            // Create product object
            Product newProductToAdd;
            
            // Check if user selected "Perishable" checkbox
            if (perishableBox.isSelected()) {
                // Creating a PerishableProduct (has expiration date)
                
                String expirationDateText = expirationField.getText().trim();
                
                // Check if user entered an expiration date
                if (!expirationDateText.isEmpty()) {
                    // Parse date string to LocalDate object
                    // Expected format: YYYY-MM-DD (e.g., "2025-12-31")
                    LocalDate parsedExpirationDate = LocalDate.parse(expirationDateText);
                    
                    // Validate that date components are not negative - year, month, day must be positive
                    if (parsedExpirationDate.getYear() < 1 || 
                        parsedExpirationDate.getMonthValue() < 1 || 
                        parsedExpirationDate.getDayOfMonth() < 1) {
                        JOptionPane.showMessageDialog(this, 
                            "Invalid date: Year, month, and day must be positive values.",
                            "Invalid Date", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // WARN IF PRODUCT IS ALREADY EXPIRED
                    // Compare expiration date to today's date
                    if (parsedExpirationDate.isBefore(LocalDate.now())) {
                        // Show confirmation dialog asking user if they want to add expired product anyway
                        int userConfirmationResponse = JOptionPane.showConfirmDialog(
                            this,
                            "Warning: This product has already expired.\nAdd anyway?",
                            "Expired Product",
                            JOptionPane.YES_NO_OPTION,      // Show Yes/No buttons
                            JOptionPane.WARNING_MESSAGE      // Show warning icon
                        );
                        
                        // If user clicked "No", abort the add operation
                        if (userConfirmationResponse != JOptionPane.YES_OPTION) return;
                    }
                    
                    // Create PerishableProduct with expiration date
                    newProductToAdd = new PerishableProduct(parsedProductId, productName, parsedProductPrice, parsedProductQuantity, parsedExpirationDate);
                } else {
                    // User checked "Perishable" but didn't enter date - create with no expiration
                    newProductToAdd = new PerishableProduct(parsedProductId, productName, parsedProductPrice, parsedProductQuantity);
                }
            } else {
                // Creating a Standard (non-perishable) product
                newProductToAdd = new Product(parsedProductId, productName, parsedProductPrice, parsedProductQuantity);
            }
            
            // Add product to inventory
            manager.addProduct(newProductToAdd);
            
            // Refresh table
            refreshTable();
            
            // Clear all input fields
            idField.setText("");
            nameField.setText("");
            priceField.setText("");
            quantityField.setText("");
            perishableBox.setSelected(false);
            expirationField.setText("");
            expirationField.setEnabled(false);
            
            // Show success message
            setStatus("Product added successfully: " + productName, SUCCESS_COLOR);
            
        } catch (NumberFormatException numberFormatException) {
            // User entered non-numeric text OR number exceeds valid range
            String errorMsg = numberFormatException.getMessage();
            
            // Check if it's a quantity overflow (exceeds Integer.MAX_VALUE)
            if (errorMsg != null && errorMsg.contains("For input string")) {
                // Parse failed - could be format error or overflow
                // Try to determine which field caused the error
                String productQuantityText = quantityField.getText().trim();
                String productPriceText = priceField.getText().trim();
                String productIdText = idField.getText().trim();
                
                // Check if quantity is too large (more than 10 digits suggests overflow)
                if (productQuantityText.length() > 10) {
                    showError("Quantity is too large!\nMaximum allowed: " + String.format("%,d", Integer.MAX_VALUE) + " (2.1 billion)");
                    return;
                }
                
                // Check if price is too large (more than 15 digits before decimal)
                if (productPriceText.replace(".", "").length() > 15) {
                    showError("Price is too large!\nMaximum allowed: $999,999,999,999.99");
                    return;
                }
                
                // Check if ID is too large
                if (productIdText.length() > 10) {
                    showError("Product ID is too large!\nValid range: 1-50");
                    return;
                }
            }
            
            // Default error message for invalid format
            showError("Invalid number format!\nPlease enter valid numbers for ID, Price, and Quantity.\n\nExamples:\n  ID: 1\n  Price: 99.99\n  Quantity: 1000");
        } catch (DateTimeParseException dateTimeParseException) {
            // User entered invalid date format in expiration field
            // Example: "12/31/2025" instead of "2025-12-31", or "2025-13-01" (month 13 doesn't exist)
            showError("Invalid date format!\nPlease use YYYY-MM-DD format (e.g., 2025-12-31)");
        } catch (InvalidInputException invalidInputException) {
            // Custom exception from InventoryManager (e.g., negative price caught internally)
            showError(invalidInputException.getMessage());
        }
    }
    
    /**
     * Updates a product by deleting the old entry and re-adding it with new values.
     * Validates all fields using the same rules as addProductFromFields.
     * 
     * @param idField Product ID to update (must exist)
     * @param nameField Updated product name
     * @param priceField Updated price
     * @param quantityField Updated quantity
     * @param perishableBox Updated perishable status
     * @param expirationField Updated expiration date
     */
    private void updateProductFromFields(JTextField idField, JTextField nameField,
                                        JTextField priceField, JTextField quantityField,
                                        JCheckBox perishableBox, JTextField expirationField) {
        try {
            // Get and validate product ID
            String productIdTextToUpdate = idField.getText().trim();
            
            if (productIdTextToUpdate.isEmpty()) {
                showError("Please enter Product ID!");
                return;
            }
            
            int parsedProductIdToUpdate = Integer.parseInt(productIdTextToUpdate);
            
            // Get and validate other fields
            String updatedProductName = nameField.getText().trim();
            String updatedProductPriceText = priceField.getText().trim();
            String updatedProductQuantityText = quantityField.getText().trim();
            
            // Check for empty required fields
            if (updatedProductName.isEmpty() || updatedProductPriceText.isEmpty() || updatedProductQuantityText.isEmpty()) {
                showError("All fields are required!");
                return;
            }
            
            // Validate product name
            
            if (updatedProductName.length() > 100) {
                showError("Product name too long (max 100 characters)!");
                return;
            }
            
            // Minimum length check
            if (updatedProductName.length() < 2) {
                showError("Product name too short (min 2 characters)!");
                return;
            }
            
            // Reject numbers-only names
            if (updatedProductName.matches("^[0-9]+$")) {
                showError("Product name cannot be numbers only!");
                return;
            }
            
            // Check for valid characters only
            if (!updatedProductName.matches("^[a-zA-Z0-9 .,'%-]+$")) {
                showError("Product name contains invalid characters!\nAllowed: letters, numbers, spaces, and . , ' % -");
                return;
            }
            
            // Parse and validate numeric values
            double parsedUpdatedProductPrice = Double.parseDouble(updatedProductPriceText);
            int parsedUpdatedProductQuantity = Integer.parseInt(updatedProductQuantityText);
            
            // Check if price resulted in Infinity
            if (Double.isInfinite(parsedUpdatedProductPrice) || Double.isNaN(parsedUpdatedProductPrice)) {
                showError("Price is too large!\nMaximum allowed: $" + String.format("%,.2f", Double.MAX_VALUE));
                return;
            }
            
            // Validate ranges
            if (parsedProductIdToUpdate < 1) {
                showError("Product ID must be at least 1!\nValid range: 1-50");
                return;
            }
            
            if (parsedProductIdToUpdate > 50) {
                showError("Product ID must be 50 or less!\nValid range: 1-50");
                return;
            }
            
            if (parsedUpdatedProductPrice <= 0 || parsedUpdatedProductQuantity < 0) {
                showError("Invalid values provided!\nCheck Price (>0) and Quantity (≥0).");
                return;
            }
            
            // Check price cap
            if (parsedUpdatedProductPrice > 999999999999.99) {
                showError("Price is too high!\nMaximum allowed: $999,999,999,999.99");
                return;
            }
            
            // Verify product exists
            Product[] existingProductsArrayForUpdate = manager.getProducts();
            boolean productExistsInInventory = false;
            
            for (int searchIndex = 0; searchIndex < manager.getProductCount(); searchIndex++) {
                if (existingProductsArrayForUpdate[searchIndex] != null && 
                    existingProductsArrayForUpdate[searchIndex].getProductId() == parsedProductIdToUpdate) {
                    productExistsInInventory = true; // Found it!
                    break; // Stop searching
                }
            }
            
            // If product doesn't exist, show error
            if (!productExistsInInventory) {
                showError("Product with ID " + parsedProductIdToUpdate + " not found!");
                return;
            }
            
            // Delete old product entry
            manager.deleteProduct(parsedProductIdToUpdate);
            
            // Create new product with updated values
            Product updatedProductInstance;
            
            // Check if product is perishable
            if (perishableBox.isSelected()) {
                String updatedExpirationDateText = expirationField.getText().trim();
                
                // If expiration date was entered
                if (!updatedExpirationDateText.isEmpty()) {
                    LocalDate parsedUpdatedExpirationDate = LocalDate.parse(updatedExpirationDateText);
                    
                    // Validate that date components are not negative
                    if (parsedUpdatedExpirationDate.getYear() < 1 || 
                        parsedUpdatedExpirationDate.getMonthValue() < 1 || 
                        parsedUpdatedExpirationDate.getDayOfMonth() < 1) {
                        JOptionPane.showMessageDialog(this, 
                            "Invalid date: Year, month, and day must be positive values.",
                            "Invalid Date", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Create PerishableProduct with expiration date
                    updatedProductInstance = new PerishableProduct(parsedProductIdToUpdate, updatedProductName, 
                                                                   parsedUpdatedProductPrice, parsedUpdatedProductQuantity, 
                                                                   parsedUpdatedExpirationDate);
                } else {
                    // Create PerishableProduct without expiration date
                    updatedProductInstance = new PerishableProduct(parsedProductIdToUpdate, updatedProductName, 
                                                                   parsedUpdatedProductPrice, parsedUpdatedProductQuantity);
                }
            } else {
                // Create Standard (non-perishable) product
                updatedProductInstance = new Product(parsedProductIdToUpdate, updatedProductName, 
                                                    parsedUpdatedProductPrice, parsedUpdatedProductQuantity);
            }
            
            // Add updated product back to inventory
            manager.addProduct(updatedProductInstance);
            
            // Refresh table to show changes
            refreshTable();
            
            // Show success message
            setStatus("Product updated successfully: " + updatedProductName, SUCCESS_COLOR);
            
        } catch (NumberFormatException caughtNumberFormatException) {
            // User entered non-numeric text OR number exceeds valid range
            String errorMessageFromException = caughtNumberFormatException.getMessage();
            
            // Check if it's a quantity overflow (exceeds Integer.MAX_VALUE)
            if (errorMessageFromException != null && errorMessageFromException.contains("For input string")) {
                // Parse failed - could be format error or overflow
                // Try to determine which field caused the error
                String updatedProductQuantityText = quantityField.getText().trim();
                String updatedProductPriceText = priceField.getText().trim();
                String updatedProductIdText = idField.getText().trim();
                
                // Check if quantity is too large (more than 10 digits suggests overflow)
                if (updatedProductQuantityText.length() > 10) {
                    showError("Quantity is too large!\nMaximum allowed: " + String.format("%,d", Integer.MAX_VALUE) + " (2.1 billion)");
                    return;
                }
                
                // Check if price is too large (more than 15 digits before decimal)
                if (updatedProductPriceText.replace(".", "").length() > 15) {
                    showError("Price is too large!\nMaximum allowed: $999,999,999,999.99");
                    return;
                }
                
                // Check if ID is too large
                if (updatedProductIdText.length() > 10) {
                    showError("Product ID is too large!\nValid range: 1-50"); // 
                    return;
                }
            }
            
            // Default error message for invalid format
            showError("Invalid number format!\nPlease enter valid numbers for ID, Price, and Quantity.\n\nExamples:\n  ID: 1\n  Price: 99.99\n  Quantity: 1000");
        } catch (DateTimeParseException caughtDateTimeParseException) {
            // Invalid date format in expiration field
            showError("Invalid date format!\nPlease use YYYY-MM-DD format (e.g., 2025-12-31)");
        } catch (ProductNotFoundException caughtProductNotFoundException) {
            // Product was not found during delete operation
            showError("Product not found!");
        } catch (InvalidInputException caughtInvalidInputException) {
            // Custom validation error from InventoryManager
            showError(caughtInvalidInputException.getMessage());
        }
    }
    
    /**
     * Refreshes the table with default ID ascending sort.
     * Wrapper method - could have called refreshTableWithSort directly but this is cleaner.
     */
    private void refreshTable() {
        refreshTableWithSort("ID (Ascending)");
    }
    
    /**
     * Loads 6 sample products for testing and demonstration.
     * Includes 3 standard products (Laptop, Mouse, Keyboard) and 3 perishable products (Milk, Bread, Yogurt).
     * Expiration dates are set relative to today's date.
     */
    private void loadSampleData() {
        try {
            // ADD 3 STANDARD (NON-PERISHABLE) PRODUCTS
            
            // Product 1: High-value electronics item
            manager.addProduct(new Product(1, "Laptop", 999.99, 5));
            
            // Product 2: Low-cost accessory with high stock
            manager.addProduct(new Product(2, "Mouse", 19.99, 50));
            
            // Product 3: Mid-range peripheral
            manager.addProduct(new Product(3, "Keyboard", 49.99, 30));
            
            // ADD 3 PERISHABLE PRODUCTS WITH EXPIRATION DATES
            // Using LocalDate.now().plusDays(X) to set dates relative to today
            
            // Product 4: Dairy product expiring in 1 week
            manager.addProduct(new PerishableProduct(4, "Milk", 3.99, 20,
                LocalDate.now().plusDays(7))); // Expires 7 days from today
            
            // Product 5: Bakery item expiring soon (3 days)
            manager.addProduct(new PerishableProduct(5, "Bread", 2.49, 15,
                LocalDate.now().plusDays(3))); // Expires 3 days from today
            
            // Product 6: Dairy product with longer shelf life (2 weeks)
            manager.addProduct(new PerishableProduct(6, "Yogurt", 1.99, 40,
                LocalDate.now().plusDays(14))); // Expires 14 days from today
            
            // REFRESH TABLE TO DISPLAY ALL SAMPLE PRODUCTS
            refreshTable();
            
            // SHOW SUCCESS MESSAGE
            // manager.getProductCount() returns how many products are now in inventory
            setStatus("Sample data loaded - " + manager.getProductCount() + " products", SUCCESS_COLOR);
            
        } catch (InvalidInputException caughtInvalidInputException) {
            // If any validation fails during sample data creation, show error
            // (This should rarely happen since sample data is hardcoded with valid values)
            setStatus("Error loading sample data", DANGER_COLOR);
        }
    }
    
    /**
     * Main entry point - creates and displays the GUI on the Event Dispatch Thread.
     * 
     * @param args Command-line arguments (not used)
     */
    
    /**
     * Helper method to format price with commas for better readability.
     * Adds commas for values >= 100 (e.g., $1,234.56).
     * 
     * @param price The price value to format
     * @return Formatted price string with dollar sign
     */
    private String formatPrice(double price) {
        if (price >= 100) {
            return String.format("$%,.2f", price); // With commas
        } else {
            return String.format("$%.2f", price); // Without commas
        }
    }
    
    /**
     * Helper method to format quantity with commas for better readability.
     * Adds commas for values >= 100 (e.g., 1,234).
     * 
     * @param quantity The quantity value to format
     * @return Formatted quantity string
     */
    private String formatQuantity(int quantity) {
        if (quantity >= 100) {
            return String.format("%,d", quantity); // With commas
        } else {
            return String.valueOf(quantity); // Without commas
        }
    }

    /* 
     * Used exlusively in this file, but commented out to use Main.java instead
     * This was used to test the GUI independently without running the full application

    // public static void main(String[] args) {
    //     // Schedule GUI creation on Event Dispatch Thread for thread safety
    //     SwingUtilities.invokeLater(() -> {
    //         // Create new instance of the GUI
    //         InvManGUI gui = new InvManGUI();
            
    //         // Make the window visible to the user
    //         // At this point, the constructor has already:
    //         // - Initialized the inventory manager
    //         // - Created all tabs and components
    //         // - Loaded sample data
    //         // Now we just need to show it!
    //         gui.setVisible(true);
    //     });
    // }
    */
}
