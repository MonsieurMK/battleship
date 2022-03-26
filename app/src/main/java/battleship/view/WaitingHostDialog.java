package battleship.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.ResourceBundle;

public class WaitingHostDialog extends JDialog {
    public WaitingHostDialog(Frame owner, ResourceBundle resourceBundle) {
        super(owner);
        this.setLayout(new FlowLayout());
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setTitle(resourceBundle.getString("waiting"));
        ImageIcon loadingIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/images/loading.gif")));
        JLabel displayText = new JLabel(resourceBundle.getString("waitPlayer"),
                new ImageIcon(loadingIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)),
                JLabel.CENTER);
        this.add(new JLabel(resourceBundle.getString("showConnectInfo") + " " +
                ((MainMenu) owner).getMainController().getLocalAddress().getHostAddress()));
        this.add(displayText);
        JButton cancelButton = new JButton(resourceBundle.getString("cancel"));
        cancelButton.addActionListener(e -> WaitingHostDialog.this.dispose());
        this.add(cancelButton);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                ((MainMenu) owner).getMainController().interruptHosting();
            }
        });
        this.setLocationRelativeTo(owner);
        this.setSize(300, 100);
        this.setVisible(false);
    }
}
