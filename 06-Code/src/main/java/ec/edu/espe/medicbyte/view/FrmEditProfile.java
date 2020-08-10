package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.model.UserProfile.Gender;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;
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
    
    /**
     * Creates new form FrmEditProfile
     */
    public FrmEditProfile() {
        initComponents();
        setupComponents();
        
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
    
    private void setupComponents() {
        FileFilter imageFilter = new FileNameExtensionFilter(
            "Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.setFileFilter(imageFilter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        btnChangeAvatar.setIcon(IconFontSwing.buildIcon(FontAwesome.PLUS_CIRCLE, 15, new Color(255, 255, 255)));
        btnChangeAvatar.setBackground(new Color(0, 0, 0, 100));
        
        try {
            MaskFormatter mask = new MaskFormatter("(593) ##########") {
                @Override
                public char getPlaceholderCharacter() {
                    return '0';
                }
            };
            mask.install(fmttxtPhoneNumber);
        } catch (ParseException ex) {
            Logger.getLogger(FrmEditProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lblErrorFullName.setVisible(false);
        lblErrorPhone.setVisible(false);
        lblErrorBirthdate.setVisible(false);
        
        datepkrBirthDate.getSettings().setFormatForDatesCommonEra("dd-MM-yyyy");
        datepkrBirthDate.getSettings().setFormatForDatesBeforeCommonEra("dd-MM-yyyy");
        datepkrBirthDate.getSettings()
            .setDateRangeLimits(LocalDate.MIN, LocalDate.of(2004, Month.JANUARY, 1));
        
        lblStatusMessage.setVisible(false);
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
            fmttxtPhoneNumber.setText(userProfileContext.phone);
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
        btnChangeAvatar = new javax.swing.JButton();
        lblAvatar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        datepkrBirthDate = new com.github.lgooddatepicker.components.DatePicker();
        jLabel5 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        fmttxtPhoneNumber = new javax.swing.JFormattedTextField();
        lblErrorFullName = new javax.swing.JLabel();
        lblErrorPhone = new javax.swing.JLabel();
        lblErrorBirthdate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblStatusMessage = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 12), new java.awt.Dimension(10, 12), new java.awt.Dimension(10, 12));
        btnSave = new javax.swing.JButton();

        jLabel2.setText("Full name:");

        jLabel3.setText("Phone:");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jLabel1.setText("Birth date:");

        jLabel5.setText("Gender:");

        btngrpGender.add(jRadioButton1);
        jRadioButton1.setText("Male");
        jRadioButton1.setName("male"); // NOI18N

        btngrpGender.add(jRadioButton2);
        jRadioButton2.setText("Female");
        jRadioButton2.setName("female"); // NOI18N

        btngrpGender.add(jRadioButton3);
        jRadioButton3.setText("Unspecified");
        jRadioButton3.setName("unspecified"); // NOI18N

        try {
            fmttxtPhoneNumber.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(593) ##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        fmttxtPhoneNumber.setName(""); // NOI18N

        lblErrorFullName.setText("{{ error_fullName }}");
        lblErrorFullName.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblErrorFullName.setForeground(new java.awt.Color(255, 102, 102));
        lblErrorFullName.setName("error:FULLNAME"); // NOI18N

        lblErrorPhone.setText("{{ error_phone }}");
        lblErrorPhone.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblErrorPhone.setForeground(new java.awt.Color(255, 102, 102));
        lblErrorPhone.setName("error:PHONE"); // NOI18N

        lblErrorBirthdate.setText("{{ error_birthdate }}");
        lblErrorBirthdate.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblErrorBirthdate.setForeground(new java.awt.Color(255, 102, 102));
        lblErrorBirthdate.setName("error:BIRTHDATE"); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("min size: 96 x 96");
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

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

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
                            .addComponent(lblErrorPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(lblErrorFullName)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3)
                            .addComponent(jLabel1)
                            .addComponent(lblErrorBirthdate, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtFullName, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fmttxtPhoneNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                            .addComponent(datepkrBirthDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                        .addComponent(fmttxtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
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
        int selection = fileChooser.showOpenDialog(this);
        
        if (selection == JFileChooser.APPROVE_OPTION) {
            File imageFile = fileChooser.getSelectedFile();
            BufferedImage bufferedImage = null;

            try {
                bufferedImage = ImageIO.read(imageFile);
            } catch (Exception exception) {}

            if (bufferedImage == null) {
                JOptionPane.showMessageDialog(
                    getRootPane(),
                    "Selected file is not a valid image or has an invalid format",
                    "Wrong image format",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
               
            System.out.println(new MimetypesFileTypeMap()
                .getContentType(imageFile)
                .split("/")[0]);

            if (bufferedImage.getWidth() < 96 || bufferedImage.getHeight() < 96) {
                JOptionPane.showMessageDialog(
                    getRootPane(),
                    "Image size must be greater than or equal to 96x96",
                    "Image too small",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            Image scaled = bufferedImage.getScaledInstance(96, 96, Image.SCALE_SMOOTH);
            
            new Thread(() -> {
                BufferedImage bimage = new BufferedImage(
                    scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                Graphics2D bGr = bimage.createGraphics();
                bGr.drawImage(scaled, 0, 0, null);
                bGr.dispose();

                userProfileContext.avatar = bimage;
                updateProfileUI();
            }).start();
        }
    }//GEN-LAST:event_btnChangeAvatarActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String fullName = txtFullName.getText().trim();
        String phone = fmttxtPhoneNumber.getText().replace("(593)", "").trim();
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
    private javax.swing.JFormattedTextField fmttxtPhoneNumber;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JTextField txtFullName;
    // End of variables declaration//GEN-END:variables
}
