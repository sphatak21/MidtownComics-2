import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class InventoryItemPanel extends JPanel implements ActionListener {
    private ViewManager manager;

    /**
     * Creates an instance of the InventoryItemPanel class.
     *
     * @param manager the controller
     * @param product the product represented by this panel
     */

    public InventoryItemPanel(ViewManager manager, Product product) {
        super(new BorderLayout());

        this.manager = manager;
        this.init(product);
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
    }

    /*
     * Initializes all UI components.
     */

    private void init(Product p) {
        JPanel content = getContentPanel(p);
        JPanel actions = getActionPanel(p);

        this.add(content, BorderLayout.CENTER);
        this.add(actions, BorderLayout.EAST);
        this.add(new JSeparator(), BorderLayout.SOUTH);
    }

    /*
     * Initializes the content panel UI components.
     */

    private JPanel getContentPanel(Product p) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel title = new JLabel(p.getTitle() + " #" + p.getIssue());
        JLabel author = new JLabel("Written by " + p.getAuthor() + " • Released on " + convertDate(p.getReleaseDate()));
        JLabel copies = new JLabel("Available: " + (p.getCopies() == 1 ? p.getCopies() + " copy" : p.getCopies() + " copies"));

        title.setFont(new Font("DialogInput", Font.BOLD, 18));
        author.setFont(new Font("DialogInput", Font.ITALIC, 12));
        copies.setFont(new Font("DialogInput", Font.ITALIC, 12));

        panel.add(title);
        panel.add(author);
        panel.add(copies);

        return panel;
    }

    /*
     * Initializes the action panel UI components.
     */

    private JPanel getActionPanel(Product p) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JLabel price = new JLabel("$" + p.getUnitPrice(), SwingConstants.CENTER);
        price.setFont(new Font("DialogInput", Font.BOLD, 15));

        JButton buy = new JButton("Buy");
        buy.putClientProperty("id", p.getProductId());
        buy.putClientProperty("type", "BUY");
        buy.addActionListener(this);

        JButton edit = new JButton("Edit");
        edit.putClientProperty("id", p.getProductId());
        edit.putClientProperty("type", "EDIT");
        edit.addActionListener(this);

        panel.add(price);
        panel.add(buy);
        panel.add(edit);

        return panel;
    }

    /*
     * Converts a date in YYYYMMDD format to Month DD, YYYY format.
     */

    private String convertDate(long date) {
        String dateStr = String.valueOf(date);

        int year = Integer.valueOf(dateStr.substring(0, 4));
        int month = Integer.valueOf(dateStr.substring(4, 6));
        int day = Integer.valueOf(dateStr.substring(6));

        return getMonth(month) + " " + day + ", " + year;
    }

    /*
     * Converts a numeric month to its string equivalent.
     */

    private String getMonth(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "";
        }
    }

    /*
     * Handles button clicks in this view.
     *
     * @param e the event that triggered this action
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        Long id = (Long) source.getClientProperty("id");
        String type = (String) source.getClientProperty("type");

        for (Product p : manager.getInventory()) {
            if (id.longValue() == p.getProductId() && type.equals("BUY")) {
                if (!manager.productExistsInOrder(p)) {
                    manager.addItemToOrder(new OrderItem(p));
                }
            } else if (id.longValue() == p.getProductId() && type.equals("EDIT")) {
                manager.attachProduct(p);
                manager.switchTo(MidtownComics.ProductView);
            }
        }

    }
}
