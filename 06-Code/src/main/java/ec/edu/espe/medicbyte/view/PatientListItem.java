package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.model.Patient;
import ec.edu.espe.medicbyte.model.User;
import java.awt.Color;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import jiconfont.icons.font_awesome.FontAwesomeSolid;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author Andres Jonathan J.
 */
public class PatientListItem extends JPanel {
    private final Patient patient;
    private final User currentUser;
    private Runnable requestPatientReportCallback;
    
    /**
     * Creates new form AppointmentItem
     * 
     * @param patient
     * @param currentUser
     */
    public PatientListItem(Patient patient, User currentUser) {
        this.patient = patient;
        this.currentUser = currentUser;
        
        initComponents();
        setupComponents();
    }

    private void setupComponents() {
        lblPatientName.setText(patient.getDisplayName());
        lblIdentifyCardValue.setText(patient.getIdCard());
        lblPatientAvatar.setIcon(IconFontSwing.buildIcon(
            FontAwesomeSolid.USER, 52, new Color(90, 90, 90)));
        
        if (patient.getProfile() != null && patient.getProfile().getAvatar() != null) {
            new Thread(() -> {
                try {
                    byte[] avatar = patient.getProfile().getAvatar();
                    Image image = ImageIO.read(new ByteArrayInputStream(avatar));
                    Image scaled = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                    lblPatientAvatar.setIcon(new ImageIcon(scaled));
                    repaint();
                    revalidate();
                } catch (Exception e) {}
            }).start();
        }
        
        if (patient.getCreatedAt()!= null) {
            lblRegisteredAtValue.setText(
                patient.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        }
    }
    
    public void onRequestPatientReport(Runnable callback) {
        this.requestPatientReportCallback = callback;
    }

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
        lblPatientAvatar = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        lblPatientName = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblIdentifyCard = new javax.swing.JLabel();
        lblIdentifyCardValue = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblRegisteredAt = new javax.swing.JLabel();
        lblRegisteredAtValue = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnPatientReport = new javax.swing.JButton();
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

        lblPatientAvatar.setBackground(new java.awt.Color(234, 236, 237));
        lblPatientAvatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatientAvatar.setMaximumSize(new java.awt.Dimension(64, 64));
        lblPatientAvatar.setMinimumSize(new java.awt.Dimension(64, 64));
        lblPatientAvatar.setOpaque(true);
        lblPatientAvatar.setPreferredSize(new java.awt.Dimension(64, 64));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        jPanel12.add(lblPatientAvatar, gridBagConstraints);

        jPanel14.setOpaque(false);
        jPanel14.setLayout(new java.awt.GridBagLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel15.setOpaque(false);

        lblPatientName.setText("{{ name }}");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblPatientName)
                .addGap(0, 43, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPatientName)
        );

        jPanel3.add(jPanel15);

        jPanel2.setOpaque(false);

        lblIdentifyCard.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIdentifyCard.setForeground(new java.awt.Color(71, 71, 71));
        lblIdentifyCard.setText("Identify card:");

        lblIdentifyCardValue.setText("-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblIdentifyCard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIdentifyCardValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdentifyCard)
                    .addComponent(lblIdentifyCardValue)))
        );

        jPanel3.add(jPanel2);

        jPanel4.setOpaque(false);

        lblRegisteredAt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRegisteredAt.setForeground(new java.awt.Color(71, 71, 71));
        lblRegisteredAt.setText("Registered at:");

        lblRegisteredAtValue.setText("-");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblRegisteredAt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRegisteredAtValue)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegisteredAt)
                    .addComponent(lblRegisteredAtValue)))
        );

        jPanel3.add(jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel14.add(jPanel3, gridBagConstraints);

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.X_AXIS));

        btnPatientReport.setBackground(new java.awt.Color(241, 144, 102));
        btnPatientReport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPatientReport.setForeground(new java.awt.Color(255, 255, 255));
        btnPatientReport.setText("Generate report");
        btnPatientReport.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 10, 3, 10));
        btnPatientReport.setBorderPainted(false);
        btnPatientReport.setFocusPainted(false);
        btnPatientReport.setFocusable(false);
        btnPatientReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatientReportActionPerformed(evt);
            }
        });
        jPanel5.add(btnPatientReport);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        jPanel14.add(jPanel5, gridBagConstraints);

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
    
    private void btnPatientReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatientReportActionPerformed
        requestPatientReportCallback.run();
    }//GEN-LAST:event_btnPatientReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPatientReport;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblIdentifyCard;
    private javax.swing.JLabel lblIdentifyCardValue;
    private javax.swing.JLabel lblPatientAvatar;
    private javax.swing.JLabel lblPatientName;
    private javax.swing.JLabel lblRegisteredAt;
    private javax.swing.JLabel lblRegisteredAtValue;
    // End of variables declaration//GEN-END:variables
}
