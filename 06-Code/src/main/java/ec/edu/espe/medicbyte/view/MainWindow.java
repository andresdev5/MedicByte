package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.common.core.Window;
import ec.edu.espe.medicbyte.model.User;
import ec.edu.espe.medicbyte.model.UserProfile;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import jiconfont.IconCode;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 *
 * @author Andres Jonathan J.
 */
public class MainWindow extends Window {
    public static class MenuItem {
        private String key = null;
        private final String label;
        private final IconCode icon;
        private final Callable<Boolean> callback;
        private MenuItemContext context;
        
        public MenuItem(String label, IconCode icon, Callable<Boolean> callback) {
            this.label = label;
            this.icon = icon;
            this.callback = callback;
        }
        
        public MenuItem(String label, Callable<Boolean> callback) {
            this.label = label;
            this.callback = callback;
            this.icon = null;
        }
        
        public MenuItem withKey(String key) {
            this.key = key;
            return this;
        }
        
        public void setContext(MenuItemContext context) {
            this.context = context;
        }
        
        public String getLabel() { return label; }
        public IconCode getIcon() { return icon; }
        public Callable<Boolean> getCallback() { return callback; }
        public MenuItemContext getContext() { return context; }
        public String getKey() { return key; }
    }
    
    public static class MenuItemContext {
        public MenuItem item;
        public JButton button;
        public JPanel wrapper;
    }
    
    private AtomicBoolean navigating = new AtomicBoolean(false);
    private List<MenuItemContext> menuItems = new ArrayList<>();
    private PnlLoadingOverlay pnlLoading = new PnlLoadingOverlay();
    
    /** Creates new form FrmAppointments */
    public MainWindow() {
        initComponents();
        setupComponents();
    }
    
    @Override
    public void reveal() {
        super.reveal();
        setAlwaysOnTop(true);
        toFront();
        repaint();
        setAlwaysOnTop(false);
    }
    
    @Override
    public void display(View view) {
        if (view == null) {
            content.removeAll();
            content.revalidate();
            content.repaint();
            return;
        }
        
        view.setOpaque(false);
        
        if (content.getComponents().length > 0) {
            Component child = content.getComponent(0);
            
            if (child instanceof View) {
                ((View) child).leave();
            }
        }
        
        content.removeAll();
        content.add(view);
        view.enter();
        content.revalidate();
        content.repaint();
    }

    @Override
    protected void onChange(String name, Object oldValue, Object newValue) {
        if (name.equals("userContext")) {
            setUserContext((User) newValue);
        }
        
        repaint();
        revalidate();
    }
    
    private void setupComponents() {
        setLocationRelativeTo(null);
        //btnNotifications.setVisible(false);
        avatar.setIcon(IconFontSwing.buildIcon(FontAwesome.USER, 52, new Color(90, 90, 90)));
        setIconImage(IconFontSwing.buildImage(FontAwesome.HEARTBEAT, 16, new Color(82, 116, 147)));
        
        listen("finishLoadingEditProfileView", (args) -> {
            setStatusBarContent("Done!");
            navigating.set(false);
        });
    }
    
    private void setUserContext(User context) {
        UserProfile profile = context.getProfile();
        String displayName = context.getDisplayName();
        
        txaUsername.setText(displayName.substring(0, 1).toUpperCase() + displayName.substring(1));
        
        // set avatar
        if (profile.getAvatar() != null) {
            try {
                Image image = ImageIO.read(new ByteArrayInputStream(profile.getAvatar()));
                Image scaled = image.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                avatar.setIcon(new ImageIcon(scaled));
                avatar.repaint();
                avatar.validate();
            } catch (IOException exception) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
        
        repaint();
        validate();
    }
    
    public void setStatusBarContent(String content) {
        lblStatusbarContent.setText(content);
        repaint();
        revalidate();
    }
    
    public void clearMenuItems() {
        menuItems.clear();
        menu.removeAll();
        repaint();
        revalidate();
    }
    
    public void selectMenuItem(String key) {
        MenuItemContext context = menuItems.stream().filter(i -> i.item.getKey().equals(key)).findFirst().orElse(null);
        selectMenuItem(context.item);
    }
    
    public void selectMenuItem(MenuItem item) {
        if (navigating.get()) {
            repaint();
            revalidate();
            return;
        }
        
        display(pnlLoading);
        setStatusBarContent(String.format("Navigating to %s...", item.label));
        
        new Thread(() -> {
            boolean accepted;
            
            navigating.set(true);
            
            try {
                accepted = item.getCallback().call();
            } catch (Exception ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                accepted = false;
            }
            
            setStatusBarContent("Done!");
            
            if (!accepted) {
                repaint();
                revalidate();
                navigating.set(false);
                return;
            }

            MenuItemContext context = item.getContext();

            context.wrapper.setOpaque(true);
            context.button.setForeground(new Color(255, 255, 255));

            if (item.getIcon() != null) {
                context.button.setIcon(IconFontSwing.buildIcon(
                        item.getIcon(), 15, new Color(255, 255, 255)));
            }

            menuItems.forEach(menuItem -> {
                if (menuItem.item == item) {
                    return;
                }

                menuItem.button.setForeground(new Color(71, 71, 71));
                menuItem.wrapper.setOpaque(false);

                if (menuItem.item.getIcon() != null) {
                    menuItem.button.setIcon(IconFontSwing.buildIcon(
                            menuItem.item.getIcon(), 15, new Color(71, 71, 71)));
                }
            });

            navigating.set(false);
            repaint();
            revalidate();
        }).start();
    }
    
    public void addMenuItem(MenuItem item) {
        JPanel wrapper = new JPanel();
        JButton button = new JButton();
        
        wrapper.setBackground(new java.awt.Color(60, 171, 219));
        wrapper.setMaximumSize(new java.awt.Dimension(2147483647, 28));
        wrapper.setMinimumSize(new java.awt.Dimension(180, 28));
        wrapper.setOpaque(false);
        wrapper.setPreferredSize(new java.awt.Dimension(295, 28));
        wrapper.setLayout(new java.awt.BorderLayout());
        
        button.setForeground(new java.awt.Color(71, 71, 71));
        button.setText(item.getLabel());
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 7, 4, 4));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button.setMaximumSize(new java.awt.Dimension(180, 26));
        button.setMinimumSize(new java.awt.Dimension(180, 30));
        button.setPreferredSize(new java.awt.Dimension(180, 30));
        
        if (item.getIcon() != null) {
            button.setIcon(IconFontSwing.buildIcon(item.getIcon(), 15));
            button.setIconTextGap(8);
        }
        
        MenuItemContext context = new MenuItemContext();
        context.item = item;
        context.button = button;
        context.wrapper = wrapper;
        
        item.setContext(context);
        
        button.addActionListener(e -> {
            (new Thread() {
                @Override public void run() {
                    selectMenuItem(item);
                }
            }).start();
        });
        
        wrapper.add(button);
        menu.add(wrapper);
        menuItems.add(context);
        
        // revalidate this frame to show changes
        revalidate();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        root = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        userInfoPanel = new javax.swing.JPanel();
        avatar = new javax.swing.JLabel();
        usernameScrollPanel = new javax.swing.JScrollPane();
        usernameScrollPanel.getViewport().setOpaque(false);
        txaUsername = new javax.swing.JTextArea();
        btnNotifications = new javax.swing.JButton();
        btnEditProfile = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        menu = new javax.swing.JPanel();
        container = new javax.swing.JPanel();
        content = new javax.swing.JPanel();
        statusbar = new javax.swing.JPanel();
        innerStatusbar = new javax.swing.JPanel();
        lblStatusbarContent = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MedicByte");
        setMinimumSize(new java.awt.Dimension(900, 400));
        setPreferredSize(new java.awt.Dimension(900, 550));

        root.setLayout(new java.awt.GridBagLayout());

        sidebar.setBackground(new java.awt.Color(234, 236, 236));
        sidebar.setMinimumSize(new java.awt.Dimension(180, 100));
        sidebar.setPreferredSize(new java.awt.Dimension(180, 227));
        sidebar.setLayout(new java.awt.BorderLayout());

        userInfoPanel.setMinimumSize(new java.awt.Dimension(180, 40));
        userInfoPanel.setOpaque(false);
        userInfoPanel.setPreferredSize(new java.awt.Dimension(180, 120));

        avatar.setBackground(new java.awt.Color(212, 212, 213));
        avatar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        avatar.setOpaque(true);

        usernameScrollPanel.setBackground(new java.awt.Color(204, 204, 255));
        usernameScrollPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 0));
        usernameScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        usernameScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        usernameScrollPanel.setOpaque(false);

        txaUsername.setEditable(false);
        txaUsername.setColumns(20);
        txaUsername.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        txaUsername.setForeground(new java.awt.Color(126, 126, 126));
        txaUsername.setLineWrap(true);
        txaUsername.setRows(5);
        txaUsername.setText("{{ username }}");
        txaUsername.setWrapStyleWord(true);
        txaUsername.setBorder(null);
        txaUsername.setOpaque(false);
        usernameScrollPanel.setViewportView(txaUsername);

        btnNotifications.setIcon(IconFontSwing.buildIcon(FontAwesome.BELL, 16));
        btnNotifications.setForeground(new java.awt.Color(176, 187, 190));
        btnNotifications.setContentAreaFilled(false);
        btnNotifications.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNotifications.setFocusPainted(false);

        btnEditProfile.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnEditProfile.setForeground(new java.awt.Color(126, 126, 126));
        btnEditProfile.setText("Edit profile");
        btnEditProfile.setBorder(null);
        btnEditProfile.setBorderPainted(false);
        btnEditProfile.setContentAreaFilled(false);
        btnEditProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditProfile.setFocusPainted(false);
        btnEditProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProfileActionPerformed(evt);
            }
        });

        btnLogout.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(126, 126, 126));
        btnLogout.setText("Logout");
        btnLogout.setBorder(null);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userInfoPanelLayout = new javax.swing.GroupLayout(userInfoPanel);
        userInfoPanel.setLayout(userInfoPanelLayout);
        userInfoPanelLayout.setHorizontalGroup(
            userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(userInfoPanelLayout.createSequentialGroup()
                        .addComponent(avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditProfile)
                            .addComponent(btnLogout))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(btnNotifications, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        userInfoPanelLayout.setVerticalGroup(
            userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNotifications, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(userInfoPanelLayout.createSequentialGroup()
                        .addComponent(btnEditProfile)
                        .addGap(0, 0, 0)
                        .addComponent(btnLogout)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        sidebar.add(userInfoPanel, java.awt.BorderLayout.PAGE_START);

        menu.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 10, 0));
        menu.setOpaque(false);
        menu.setLayout(new javax.swing.BoxLayout(menu, javax.swing.BoxLayout.Y_AXIS));
        sidebar.add(menu, java.awt.BorderLayout.LINE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.weighty = 1.0;
        root.add(sidebar, gridBagConstraints);

        container.setBackground(new java.awt.Color(248, 248, 248));
        container.setPreferredSize(new java.awt.Dimension(350, 371));

        content.setBackground(new java.awt.Color(248, 248, 248));
        content.setOpaque(false);
        content.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        root.add(container, gridBagConstraints);

        statusbar.setMaximumSize(new java.awt.Dimension(2147483647, 30));
        statusbar.setPreferredSize(new java.awt.Dimension(629, 30));
        statusbar.setLayout(new java.awt.BorderLayout());

        innerStatusbar.setBackground(new java.awt.Color(213, 217, 219));
        innerStatusbar.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        innerStatusbar.setLayout(new javax.swing.BoxLayout(innerStatusbar, javax.swing.BoxLayout.LINE_AXIS));

        lblStatusbarContent.setForeground(new java.awt.Color(100, 112, 113));
        lblStatusbarContent.setText("Done!");
        innerStatusbar.add(lblStatusbarContent);

        statusbar.add(innerStatusbar, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BELOW_BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        root.add(statusbar, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(root, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(root, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        emit("logout");
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnEditProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProfileActionPerformed
        if (navigating.get()) {
            return;
        }
        
        display(pnlLoading);
        setStatusBarContent(String.format("Navigating to 'Edit profile'..."));
        navigating.set(true);
        
        menuItems.forEach(menuItem -> {
            menuItem.button.setForeground(new Color(71, 71, 71));
            menuItem.wrapper.setOpaque(false);

            if (menuItem.item.getIcon() != null) {
                menuItem.button.setIcon(IconFontSwing.buildIcon(
                        menuItem.item.getIcon(), 15, new Color(71, 71, 71)));
            }
        });
        
        emit("editProfile");
    }//GEN-LAST:event_btnEditProfileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avatar;
    private javax.swing.JButton btnEditProfile;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnNotifications;
    private javax.swing.JPanel container;
    private javax.swing.JPanel content;
    private javax.swing.JPanel innerStatusbar;
    private javax.swing.JLabel lblStatusbarContent;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel root;
    private javax.swing.JPanel sidebar;
    private javax.swing.JPanel statusbar;
    private javax.swing.JTextArea txaUsername;
    private javax.swing.JPanel userInfoPanel;
    private javax.swing.JScrollPane usernameScrollPanel;
    // End of variables declaration//GEN-END:variables

}
