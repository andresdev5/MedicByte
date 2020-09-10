package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.model.UserProfile.Gender;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;


/**
 *
 * @author Andres Jonathan J.
 */
public class FrmEditProfile extends View {
    public static class UserProfileContext {
        public BufferedImage avatar;
        public String fullName;
        public String phone;
        public LocalDate birthDate;
        public Gender gender;
    }
    
    private final JFileChooser fileChooser = new JFileChooser();
    private UserProfileContext userProfileContext;
    private boolean savingProfile = false;
    private final AtomicBoolean processingAvatar = new AtomicBoolean(false);
    
    /**
     * Creates new form FrmEditProfile
     */
    public FrmEditProfile() {
        initComponents();
        setupComponents();
    }
    
    private void setupComponents() {
        FileFilter imageFilter = new FileNameExtensionFilter(
            "Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(imageFilter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        btnChangeAvatar.setIcon(IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 15, new Color(255, 255, 255)));
        btnChangeAvatar.setBackground(new Color(0, 0, 0, 100));
        lblUploadingAvatar.setIcon(IconFontSwing.buildIcon(FontAwesome.SPINNER, 15, new Color(255, 255, 255)));
        lblUploadingAvatar.setBackground(new Color(0, 0, 0, 100));
        lblUploadingAvatar.setVisible(false);
        
        lblErrorFullName.setVisible(false);
        lblErrorPhone.setVisible(false);
        lblErrorBirthdate.setVisible(false);
        
        datepkrBirthDate.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
        datepkrBirthDate.getSettings().setFormatForDatesBeforeCommonEra("dd-MM-yyyy");
        datepkrBirthDate.getSettings()
            .setDateRangeLimits(LocalDate.MIN, LocalDate.of(2004, Month.JANUARY, 1));
        
        lblStatusMessage.setVisible(false);
        
        listen("updatedProfile", (args) -> {
            savingProfile = false;
            btnSave.setEnabled(true);
            lblStatusMessage.setVisible(true);
            lblStatusMessage.setBackground(new Color(152, 250, 224));
            lblStatusMessage.setForeground(new Color(13, 166, 127));
            lblStatusMessage.setText("Profile successfully updated!");
            
            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                    lblStatusMessage.setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FrmEditProfile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        });
    }
    
    @Override
    protected void onChange(String name, Object oldValue, Object newValue) {
        if (name.equals("userProfileContext")) {
            userProfileContext = (UserProfileContext) newValue;
            updateProfileUI();
        }
    }

    @Override
    protected void onLeave() {}

    @Override
    protected void onEnter() {}
    
    private void updateProfileUI() {
        if (userProfileContext.avatar != null) {
            lblAvatar.setIcon(new ImageIcon(userProfileContext.avatar));
        }
        
        if (userProfileContext.fullName != null) {
            txtFullName.setText(userProfileContext.fullName);
        }
        
        if (userProfileContext.phone != null) {
            txtPhoneNumber.setText(userProfileContext.phone);
        }
        
        if (userProfileContext.birthDate != null) {
            datepkrBirthDate.setDate(userProfileContext.birthDate);
        }
        
        if (userProfileContext.gender != null) {
            for (Enumeration<AbstractButton> buttons = btngrpGender.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                String name = button.getName();
                String gender = userProfileContext.gender.name().toLowerCase();
                
                if (name.equals(gender)) {
                    button.setSelected(true);
                    break;
                }
            }
        }
        
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btngrpGender = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblUploadingAvatar = new javax.swing.JLabel();
        btnChangeAvatar = new javax.swing.JButton();
        lblAvatar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        datepkrBirthDate = new com.github.lgooddatepicker.components.DatePicker();
        jLabel5 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        lblErrorFullName = new javax.swing.JLabel();
        lblErrorPhone = new javax.swing.JLabel();
        lblErrorBirthdate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblStatusMessage = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 12), new java.awt.Dimension(10, 12), new java.awt.Dimension(10, 12));
        btnSave = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtPhoneNumber = new javax.swing.JTextField();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle"); // NOI18N
        jLabel2.setText(bundle.getString("FrmEditProfile.jLabel2.text")); // NOI18N

        jLabel3.setText(bundle.getString("FrmEditProfile.jLabel3.text")); // NOI18N

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUploadingAvatar.setBackground(new java.awt.Color(0, 0, 0));
        lblUploadingAvatar.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        lblUploadingAvatar.setForeground(new java.awt.Color(255, 255, 255));
        lblUploadingAvatar.setText(bundle.getString("FrmEditProfile.lblUploadingAvatar.text")); // NOI18N
        lblUploadingAvatar.setOpaque(true);
        jPanel1.add(lblUploadingAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 40, 80, 18));

        btnChangeAvatar.setBackground(new java.awt.Color(0, 0, 0));
        btnChangeAvatar.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2));
        btnChangeAvatar.setBorderPainted(false);
        btnChangeAvatar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChangeAvatar.setFocusPainted(false);
        btnChangeAvatar.setFocusable(false);
        btnChangeAvatar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeAvatarActionPerformed(evt);
            }
        });
        jPanel1.add(btnChangeAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 18, 18));

        lblAvatar.setBackground(new java.awt.Color(204, 204, 204));
        lblAvatar.setOpaque(true);
        jPanel1.add(lblAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 96, 96));

        jLabel1.setText(bundle.getString("FrmEditProfile.jLabel1.text")); // NOI18N

        jLabel5.setText(bundle.getString("FrmEditProfile.jLabel5.text")); // NOI18N

        btngrpGender.add(jRadioButton1);
        jRadioButton1.setText(bundle.getString("FrmEditProfile.jRadioButton1.text")); // NOI18N
        jRadioButton1.setName("male"); // NOI18N

        btngrpGender.add(jRadioButton2);
        jRadioButton2.setText(bundle.getString("FrmEditProfile.jRadioButton2.text")); // NOI18N
        jRadioButton2.setName("female"); // NOI18N

        btngrpGender.add(jRadioButton3);
        jRadioButton3.setText(bundle.getString("FrmEditProfile.jRadioButton3.text")); // NOI18N
        jRadioButton3.setName("unspecified"); // NOI18N

        lblErrorFullName.setText(bundle.getString("FrmEditProfile.lblErrorFullName.text")); // NOI18N
        lblErrorFullName.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblErrorFullName.setForeground(new java.awt.Color(255, 102, 102));
        lblErrorFullName.setName("error:FULLNAME"); // NOI18N

        lblErrorPhone.setText(bundle.getString("FrmEditProfile.lblErrorPhone.text")); // NOI18N
        lblErrorPhone.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblErrorPhone.setForeground(new java.awt.Color(255, 102, 102));
        lblErrorPhone.setName("error:PHONE"); // NOI18N

        lblErrorBirthdate.setText(bundle.getString("FrmEditProfile.lblErrorBirthdate.text")); // NOI18N
        lblErrorBirthdate.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblErrorBirthdate.setForeground(new java.awt.Color(255, 102, 102));
        lblErrorBirthdate.setName("error:BIRTHDATE"); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText(bundle.getString("FrmEditProfile.jLabel4.text")); // NOI18N
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        jPanel2.setOpaque(false);

        lblStatusMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusMessage.setOpaque(true);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatusMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(filler1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnSave.setText(bundle.getString("FrmEditProfile.btnSave.text")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel6.setText(bundle.getString("FrmEditProfile.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(lblErrorFullName)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3)
                            .addComponent(jLabel1)
                            .addComponent(lblErrorBirthdate, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addComponent(txtFullName)
                            .addComponent(datepkrBirthDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblErrorPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(lblErrorFullName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addComponent(lblErrorPhone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datepkrBirthDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblErrorBirthdate)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSave)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChangeAvatarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeAvatarActionPerformed
        if (processingAvatar.get()) {
            return;
        }
        
        int selection = fileChooser.showOpenDialog(this);
        
        processingAvatar.set(true);
        
        if (selection == JFileChooser.APPROVE_OPTION) {
            new Thread(() -> {
                File imageFile = fileChooser.getSelectedFile();
                BufferedImage bufferedImage = null;
                lblUploadingAvatar.setVisible(true);

                try {
                    bufferedImage = ImageIO.read(imageFile);
                } catch (IOException exception) {}

                if (bufferedImage == null) {
                    JOptionPane.showMessageDialog(
                        getRootPane(),
                        "Selected file is not a valid image or has an invalid format",
                        "Wrong image format",
                        JOptionPane.ERROR_MESSAGE
                    );
                    lblUploadingAvatar.setVisible(false);
                    return;
                }

                if (bufferedImage.getWidth() < 96 || bufferedImage.getHeight() < 96) {
                    JOptionPane.showMessageDialog(
                        getRootPane(),
                        "Image size must be greater than or equal to 96x96",
                        "Image too small",
                        JOptionPane.ERROR_MESSAGE
                    );
                    lblUploadingAvatar.setVisible(false);
                    return;
                }

                Image scaled = bufferedImage.getScaledInstance(96, 96, Image.SCALE_SMOOTH);


                BufferedImage bimage = new BufferedImage(
                    scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                Graphics2D bGr = bimage.createGraphics();
                bGr.drawImage(scaled, 0, 0, null);
                bGr.dispose();

                userProfileContext.avatar = bimage;
                updateProfileUI();
                lblUploadingAvatar.setVisible(false);
                processingAvatar.set(false);
            }).start();
        } else {
            processingAvatar.set(false);
        }
    }//GEN-LAST:event_btnChangeAvatarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String fullName = txtFullName.getText().trim();
        String phone = txtPhoneNumber.getText().replace("(593)", "").trim();
        String dateText = datepkrBirthDate.getText();
        
        if (savingProfile) {
            return;
        }
        
        if (!fullName.isEmpty()) {
            if (fullName.length() < 2) {
                lblErrorFullName.setText("Full name must have minimum 2 characters");
                lblErrorFullName.setVisible(true);
                return;
            } else if (!fullName.matches("^[\\w'\\-,.][^0-9_!¡?÷?¿\\/\\+=@#$%ˆ&*(){}|~<>;:[\\\\]]{2,}$")) {
                lblErrorFullName.setText("Fullname has invalid characters");
                lblErrorFullName.setVisible(true);
                return;
            } else {
                userProfileContext.fullName = fullName;
            }
        }
        
        if (!phone.isEmpty() && !phone.matches("^0?(2[0-9]{7}|9[0-9]{8})$")) {
            lblErrorPhone.setText("invalid phone number");
            lblErrorPhone.setVisible(true);
            return;
        } else {
            userProfileContext.phone = phone;
        }
        
        if (!dateText.isEmpty()) {
            userProfileContext.birthDate = datepkrBirthDate.getDate();
        }
        
        for (Enumeration<AbstractButton> buttons = btngrpGender.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            String name = button.getName();

            if (button.isSelected()) {
                userProfileContext.gender = Gender.valueOf(name.toUpperCase());
                break;
            }
        }
        
        savingProfile = true;
        
        lblErrorFullName.setVisible(false);
        lblErrorPhone.setVisible(false);
        lblErrorBirthdate.setVisible(false);
        
        btnSave.setEnabled(false);
        emit("submit", userProfileContext);
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeAvatar;
    private javax.swing.JButton btnSave;
    private javax.swing.ButtonGroup btngrpGender;
    private com.github.lgooddatepicker.components.DatePicker datepkrBirthDate;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JLabel lblAvatar;
    private javax.swing.JLabel lblErrorBirthdate;
    private javax.swing.JLabel lblErrorFullName;
    private javax.swing.JLabel lblErrorPhone;
    private javax.swing.JLabel lblStatusMessage;
    private javax.swing.JLabel lblUploadingAvatar;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtPhoneNumber;
    // End of variables declaration//GEN-END:variables
}
