package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.User;
import java.awt.Color;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author Andres Jonathan J.
 */
public class MedicListItem extends View {
    private final Medic medic;
    private final User currentUser;
    
    /**
     * Creates new form AppointmentItem
     * 
     * @param medic
     * @param currentUser
     */
    public MedicListItem(Medic medic, User currentUser) {
        this.medic = medic;
        this.currentUser = currentUser;
        
        initComponents();
        setupComponents();
    }
    
    private void setupComponents() {
        lblMedicName.setText(medic.getDisplayName());
        lblSpecialityValue.setText(medic.getSpeciality().getName());
        lblMedicAvatar.setIcon(IconFontSwing.buildIcon(
            FontAwesome.USER_MD, 52, new Color(90, 90, 90)));
        btnEditMedic.setVisible(false);
        
        if (medic.getProfile() != null && medic.getProfile().getAvatar() != null) {
            new Thread(() -> {
                try {
                    byte[] avatar = medic.getProfile().getAvatar();
                    Image image = ImageIO.read(new ByteArrayInputStream(avatar));
                    Image scaled = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                    lblMedicAvatar.setIcon(new ImageIcon(scaled));
                    repaint();
                    revalidate();
                } catch (Exception e) {}
            }).start();
        }
        
        if (currentUser.hasRole("admin")) {
            btnEditMedic.setVisible(true);
            btnEditMedic.addActionListener((l) -> emit("editMedic", medic));
        }
    }

    @Override
    protected void onChange(String name, Object oldValue, Object newValue) {}

    @Override
    protected void onLeave() {}

    @Override
    protected void onEnter() {}
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        lblMedicAvatar = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        lblMedicName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblSpeciality = new javax.swing.JLabel();
        lblSpecialityValue = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnEditMedic = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        setMaximumSize(new java.awt.Dimension(32767, 95));
        setMinimumSize(new java.awt.Dimension(560, 95));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(560, 95));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(216, 216, 216), 1, true));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 7, 8, 7));
        jPanel12.setPreferredSize(new java.awt.Dimension(550, 100));
        jPanel12.setLayout(new java.awt.GridBagLayout());

        lblMedicAvatar.setBackground(new java.awt.Color(234, 236, 237));
        lblMedicAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMedicAvatar.setMaximumSize(new java.awt.Dimension(64, 64));
        lblMedicAvatar.setMinimumSize(new java.awt.Dimension(64, 64));
        lblMedicAvatar.setOpaque(true);
        lblMedicAvatar.setPreferredSize(new java.awt.Dimension(64, 64));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(lblMedicAvatar, gridBagConstraints);

        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.GridBagLayout());

        jPanel15.setOpaque(false);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle"); // NOI18N
        lblMedicName.setText(bundle.getString("MedicListItem.lblMedicName.text")); // NOI18N

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblMedicName)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMedicName)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        jPanel14.add(jPanel15, gridBagConstraints);

        jPanel2.setOpaque(false);

        lblSpeciality.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSpeciality.setForeground(new java.awt.Color(71, 71, 71));
        lblSpeciality.setText(bundle.getString("MedicListItem.lblSpeciality.text")); // NOI18N

        lblSpecialityValue.setText(bundle.getString("MedicListItem.lblSpecialityValue.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblSpeciality)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSpecialityValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSpeciality)
                    .addComponent(lblSpecialityValue)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel14.add(jPanel2, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.X_AXIS));

        btnEditMedic.setBackground(new java.awt.Color(52, 152, 219));
        btnEditMedic.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditMedic.setForeground(new java.awt.Color(255, 255, 255));
        btnEditMedic.setText(bundle.getString("MedicListItem.btnEditMedic.text")); // NOI18N
        btnEditMedic.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 9, 4, 9));
        btnEditMedic.setBorderPainted(false);
        btnEditMedic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditMedic.setFocusPainted(false);
        btnEditMedic.setFocusable(false);
        jPanel3.add(btnEditMedic);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        jPanel14.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel12.add(jPanel14, gridBagConstraints);

        jPanel1.add(jPanel12, java.awt.BorderLayout.CENTER);

        add(jPanel1);

        jSeparator1.setVisible(false);
        jSeparator1.setBackground(new java.awt.Color(175, 192, 196));
        jSeparator1.setMinimumSize(new java.awt.Dimension(0, 1));
        jSeparator1.setOpaque(true);
        add(jSeparator1);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditMedic;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblMedicAvatar;
    private javax.swing.JLabel lblMedicName;
    private javax.swing.JLabel lblSpeciality;
    private javax.swing.JLabel lblSpecialityValue;
    // End of variables declaration//GEN-END:variables
}
