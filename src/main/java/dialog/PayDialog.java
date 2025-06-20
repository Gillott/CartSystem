package dialog;

import javax.swing.*;
import java.awt.*;

public class PayDialog extends JDialog {

    private JButton button;
    public PayDialog(JFrame parent) {
        super(parent, "支付成功", true);
        initUI();
    }
    private void initUI() {
        button = new JButton("确定");
        button.addActionListener(ac->{
            // 关闭模态框
            dispose();
        });

        this.add(button, BorderLayout.CENTER);
        setSize(220, 85);
        setLocationRelativeTo(null);
    }
}

