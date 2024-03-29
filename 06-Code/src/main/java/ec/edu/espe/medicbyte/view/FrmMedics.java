package ec.edu.espe.medicbyte.view;

import ec.edu.espe.medicbyte.common.core.View;
import ec.edu.espe.medicbyte.model.Medic;
import ec.edu.espe.medicbyte.model.Speciality;
import ec.edu.espe.medicbyte.model.User;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres Jonathan J.
 */
public class FrmMedics extends View {
    private List<Medic> medics;
    private User currentUser;
    private boolean editingMedic = false;
    
    /**
     * Creates new form FrmMedics
     */
    public FrmMedics() {
        initComponents();
        setupComponents();
    }
    
    private void setupComponents() {
        medicsScrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        medicsScrollPanel.getViewport().setOpaque(false);
        
        listen("MedicUpdated", (args) -> {
            Medic medic = args.get(0);
            JOptionPane.showMessageDialog(
                getRootPane(),
                String.format("medic %s succesfully updated", medic.getDisplayName()),
                "Updated medic",
                JOptionPane.INFORMATION_MESSAGE
            );
            updateMedicsList(medics);
        });
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
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalItems = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        medicsWrapperPanel = new javax.swing.JPanel();
        medicsScrollPanel = new javax.swing.JScrollPane();
        medicsContainer = new javax.swing.JPanel();
        paginationPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        jPanel7.setOpaque(false);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ec/edu/espe/medicbyte/view/Bundle"); // NOI18N
        jLabel3.setText(bundle.getString("FrmMedics.jLabel3.text")); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel4.add(jLabel3);

        lblTotalItems.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalItems.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotalItems.setText(bundle.getString("FrmMedics.lblTotalItems.text")); // NOI18N
        lblTotalItems.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        lblTotalItems.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel4.add(lblTotalItems);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText(bundle.getString("FrmMedics.jLabel7.text")); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 5));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel4.add(jLabel7);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator1))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        jPanel3.add(jPanel7);

        medicsWrapperPanel.setBackground(new java.awt.Color(204, 204, 255));
        medicsWrapperPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        medicsWrapperPanel.setOpaque(false);
        medicsWrapperPanel.setLayout(new java.awt.BorderLayout());

        medicsScrollPanel.setBackground(new java.awt.Color(204, 204, 255));
        medicsScrollPanel.setBorder(null);
        medicsScrollPanel.setOpaque(false);

        medicsContainer.setBackground(new java.awt.Color(204, 204, 255));
        medicsContainer.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 8));
        medicsContainer.setOpaque(false);
        medicsContainer.setLayout(new javax.swing.BoxLayout(medicsContainer, javax.swing.BoxLayout.Y_AXIS));
        medicsScrollPanel.setViewportView(medicsContainer);

        medicsWrapperPanel.add(medicsScrollPanel, java.awt.BorderLayout.CENTER);

        jPanel3.add(medicsWrapperPanel);

        paginationPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        paginationPanel.setMaximumSize(new java.awt.Dimension(2147483647, 28));
        paginationPanel.setOpaque(false);
        paginationPanel.setLayout(new java.awt.BorderLayout());

        jLabel4.setText(bundle.getString("FrmMedics.jLabel4.text")); // NOI18N
        paginationPanel.add(jLabel4, java.awt.BorderLayout.CENTER);

        jLabel5.setText(bundle.getString("FrmMedics.jLabel5.text")); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        paginationPanel.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jPanel3.add(paginationPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel1.setText(bundle.getString("FrmMedics.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void onChange(String name, Object oldValue, Object newValue) {
        if (name.equals("currentUser")) {
            this.currentUser = (User) newValue;
        }
        
        if (name.equals("medics")) {
            medics = (List<Medic>) newValue;
            updateMedicsList(medics);
        }
    }
    
    @Override
    protected void onEnter() {}
    
    @Override
    protected void onLeave() {}
    
    private void updateMedicsList(List<Medic> medics) {
        medicsContainer.removeAll();
        medics.sort(Comparator.comparing(Medic::getCreatedAt).reversed());
        medics.forEach(medic -> {
            MedicListItem item = new MedicListItem(medic, currentUser);
            item.listen("editMedic", (args) -> editMedic(args.get(0)));
            medicsContainer.add(item);
        });
        lblTotalItems.setText(String.valueOf(medics.size()));
        
        repaint();
        revalidate();
    }
    
    private void editMedic(Medic medic) {
        List<Speciality> specialities = (List<Speciality>) get("specialities");
        
        if (editingMedic) {
            return;
        }
        
        editingMedic = true;
        
        DlgEditMedic dialog = new DlgEditMedic(medic, specialities, (saved) -> {
            emit("updateMedic", saved);
        });
        
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                editingMedic = false;
            }
        });
        
        dialog.setTitle("Edit medic");
        dialog.setLocationRelativeTo(this.getParent());
        dialog.setModal(true);
        dialog.setVisible(true);
        dialog.pack();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotalItems;
    private javax.swing.JPanel medicsContainer;
    private javax.swing.JScrollPane medicsScrollPanel;
    private javax.swing.JPanel medicsWrapperPanel;
    private javax.swing.JPanel paginationPanel;
    // End of variables declaration//GEN-END:variables
}
