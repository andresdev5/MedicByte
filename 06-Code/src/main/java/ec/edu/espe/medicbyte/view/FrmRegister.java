package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.util.StringUtils;
import ec.edu.espe.medicbyte.util.ValidationUtils;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Michael Cobacango
 */
public class FrmRegister extends View {
    public static enum Field {
        USERNAME, EMAIL, PASSWORD, PASSWORD2, IDENTIFY_CARD
    }
    
    private boolean registering = false;
    
    /**
     * Creates new form FrmRegister
     */
    public FrmRegister() {
        initComponents();
        setup();
        
        listen("showError", (args) -> {
            showError(args.get(0), args.get(1));
            btnSignup.setEnabled(true);
            registering = false;
        });
        listen("hideError", (args) -> hideError(args.get(0)));
        listen("hideErrors", (args) -> {
            for (Field field : Field.values()) {
                hideError(field);
            }
        });
        
        listen("success", (args) -> {
            btnSignup.setEnabled(true);
            registering = false;
        });
    }
    
    @Override
    protected void onChange(String name, Object oldValue, Object newValue) {}
    
    @Override
    protected void onEnter() {}
    
    @Override
    protected void onLeave() {}

    private void setup() {
        List<Component> components = Arrays.asList(this.getComponents());
        
        components.forEach(component -> {
            String name = component.getName();
            
            if (name != null && name.startsWith("E_")) {
                component.setVisible(false);
            }
        });
    }
    
    private void showError(Field field, String error) {
        JLabel errorLabel = (JLabel) Stream.of(getComponents())
            .filter(c -> {
                String name = c.getName();
                return name != null && name.trim().equals("E_" + field.name());
            }).findFirst().orElse(null);
        
        if (errorLabel == null) {
            return;
        }
        
        errorLabel.setText(error);
        errorLabel.setVisible(true);
    }
    
    private void hideError(Field field) {
        JLabel errorLabel = (JLabel) Stream.of(getComponents())
            .filter(c -> {
                String name = c.getName();
                return name != null && name.trim().equals("E_" + field.name());
            }).findFirst().orElse(null);
        
        if (errorLabel == null) {
            return;
        }
        
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
    
    private boolean validateField(Field field, Component component) {
        boolean isEmpty = false;
        String value = "";
        
        if (component instanceof JTextComponent) {
            String textValue = ((JTextField) component).getText();
            isEmpty = textValue.trim().isEmpty();
            value = textValue;
        } else if (component instanceof JCheckBox) {
            isEmpty = !((JCheckBox) component).isSelected();
        }
        
        if (isEmpty) {
            showError(field, "this field is required");
            return false;
        }
        
        switch (field) {
            case USERNAME:
                if (value.length() < 3 || value.length() > 24) {
                    showError(field, "Username must have between 3 and 24 characters");
                    return false;
                }
                
                if (!ValidationUtils.isValidUsername(value)) {
                    showError(field, "Username can only have letters, numbers, hyphens or dots");
                    return false;
                }
            break;
            case EMAIL:
                if (!ValidationUtils.isValidEmail(value)) {
                    showError(field, "Incorrect email");
                    return false;
                }
            break;
            case PASSWORD:
                if(txtPassword.getPassword().length < 4) {
                    showError(field, "The password must have at least 4 characters");
                    return false;
                }
            break;
            case PASSWORD2:
                String password = String.valueOf(txtPassword.getPassword());
                String password2 = String.valueOf(txtPassword2.getPassword());
                
                if (!password.equals(password2)) {
                    showError(field, "passwords doesn't match");
                    return false;
                }
            break;
            case IDENTIFY_CARD:
                if (!ValidationUtils.isValidIDCard(value)) {
                    showError(field, "Invalid identify card");
                    return false;
                }
            break;
        }
        
        hideError(field);
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnSignup = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnLogIn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtPassword2 = new javax.swing.JPasswordField();
        lblEmailPassword = new javax.swing.JLabel();
        lblUsernameError = new javax.swing.JLabel();
        lblPasswordError = new javax.swing.JLabel();
        lblPassword2Error = new javax.swing.JLabel();
        lblTermsError = new javax.swing.JLabel();
        ftxtIdentifyCard = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(103, 103, 103));
        jLabel1.setText("Register");

        jLabel2.setForeground(new java.awt.Color(99, 99, 99));
        jLabel2.setText("Username:");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

        txtUsername.setName("USERNAME"); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                signupFieldKeyUp(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(99, 99, 99));
        jLabel3.setText("Email:");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

        txtEmail.setName("EMAIL"); // NOI18N
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                signupFieldKeyUp(evt);
            }
        });

        btnSignup.setBackground(new java.awt.Color(29, 209, 161));
        btnSignup.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnSignup.setForeground(new java.awt.Color(255, 255, 255));
        btnSignup.setText("Register");
        btnSignup.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 1, 7, 1));
        btnSignup.setBorderPainted(false);
        btnSignup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSignup.setFocusPainted(false);
        btnSignup.setOpaque(true);
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(99, 99, 99));
        jLabel4.setText("Password:");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

        jLabel5.setForeground(new java.awt.Color(99, 99, 99));
        jLabel5.setText("Confirm password:");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

        btnLogIn.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnLogIn.setForeground(new java.awt.Color(54, 138, 185));
        btnLogIn.setText("Log in");
        btnLogIn.setBorder(null);
        btnLogIn.setBorderPainted(false);
        btnLogIn.setContentAreaFilled(false);
        btnLogIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogInActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(99, 99, 99));
        jLabel6.setText("Do you have an account?");

        txtPassword.setName("PASSWORD"); // NOI18N
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                signupFieldKeyUp(evt);
            }
        });

        txtPassword2.setName("PASSWORD2"); // NOI18N
        txtPassword2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                signupFieldKeyUp(evt);
            }
        });

        lblEmailPassword.setBackground(new java.awt.Color(240, 100, 100));
        lblEmailPassword.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblEmailPassword.setForeground(new java.awt.Color(240, 84, 84));
        lblEmailPassword.setText("error");
        lblEmailPassword.setName("E_EMAIL"); // NOI18N

        lblUsernameError.setBackground(new java.awt.Color(240, 100, 100));
        lblUsernameError.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblUsernameError.setForeground(new java.awt.Color(240, 84, 84));
        lblUsernameError.setText("error");
        lblUsernameError.setName("E_USERNAME"); // NOI18N

        lblPasswordError.setBackground(new java.awt.Color(240, 100, 100));
        lblPasswordError.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblPasswordError.setForeground(new java.awt.Color(240, 84, 84));
        lblPasswordError.setText("error");
        lblPasswordError.setName("E_PASSWORD"); // NOI18N

        lblPassword2Error.setBackground(new java.awt.Color(240, 100, 100));
        lblPassword2Error.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblPassword2Error.setForeground(new java.awt.Color(240, 84, 84));
        lblPassword2Error.setText("error");
        lblPassword2Error.setName("E_PASSWORD2"); // NOI18N

        lblTermsError.setBackground(new java.awt.Color(240, 100, 100));
        lblTermsError.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblTermsError.setForeground(new java.awt.Color(240, 84, 84));
        lblTermsError.setText("error");
        lblTermsError.setName("E_IDENTIFY_CARD"); // NOI18N

        ftxtIdentifyCard.setName("IDENTIFY_CARD"); // NOI18N

        jLabel7.setForeground(new java.awt.Color(99, 99, 99));
        jLabel7.setText("Identiy card:");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ftxtIdentifyCard, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblTermsError, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnLogIn))
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                    .addComponent(txtEmail)
                                    .addComponent(btnSignup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtPassword)
                                    .addComponent(txtPassword2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblEmailPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPassword2Error, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblUsernameError, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPasswordError, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblUsernameError)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmailPassword)
                .addGap(0, 0, 0)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPasswordError)
                .addGap(0, 0, 0)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lblPassword2Error)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ftxtIdentifyCard, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTermsError)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(btnSignup, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogIn)
                    .addComponent(jLabel6))
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogInActionPerformed
        emit("swapToLogin");
    }//GEN-LAST:event_btnLogInActionPerformed

    private void btnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignupActionPerformed
        if (registering) {
            return;
        }
        
        List<String> fieldNames = Stream.of(Field.values())
            .map(field -> field.name())
            .collect(Collectors.toList());
        
        List<Component> components = Stream.of(this.getComponents())
            .filter(component -> {
                String name = component.getName();
                return name != null && fieldNames.contains(name.trim());
            }).collect(Collectors.toList());
        
        int errors = 0;
        
        for (Component component : components) {
            String name = component.getName().trim();
            
            if (!validateField(Field.valueOf(name), component)) {
                errors++;
            }
        }
        
        if (errors > 0) {
            return;
        }
        
        registering = true;
        btnSignup.setEnabled(false);
        
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        char[] password = txtPassword.getPassword();
        String identifyCard = ftxtIdentifyCard.getText();
        
        emit("submit", username, email, password, identifyCard);
    }//GEN-LAST:event_btnSignupActionPerformed

    private void signupFieldKeyUp(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_signupFieldKeyUp
        Component component = evt.getComponent();
        String name = component.getName().trim();
        boolean isField = Stream.of(Field.values()).anyMatch(field -> field.name().equals(name));
        
        if (name == null || !isField) {
            return;
        }
        
        validateField(Field.valueOf(name), component);
    }//GEN-LAST:event_signupFieldKeyUp

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogIn;
    private javax.swing.JButton btnSignup;
    private javax.swing.JFormattedTextField ftxtIdentifyCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblEmailPassword;
    private javax.swing.JLabel lblPassword2Error;
    private javax.swing.JLabel lblPasswordError;
    private javax.swing.JLabel lblTermsError;
    private javax.swing.JLabel lblUsernameError;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
