package linecount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import static java.rmi.server.LogStream.log;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ldpereira
 * @since May 18, 2015
 */
public class LineCount extends JFrame {

    public static void main(String[] args) {
        new LineCount().ativar();
    }
    private File caminho;
    private int maiorNivel;
    private Map<String, Integer> functionsCount = new HashMap();

    public LineCount() {
        initComponents();
    }

    public void ativar() {
        try {
            setSize(1200, 800);
            setLocationRelativeTo(null);
            setVisible(true);
            setTitle("Java Line Count");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findProject(File project) throws FileNotFoundException, IOException, URISyntaxException {
        if (project.isDirectory()) {
            findFolder(project, 0);

            //   File f = new File("C:/Java/Projetos/Projetos Comuns/WhebServidorSwingCorPls/src/br/com/wheb/socket");
            // readServerFiles(f);
//            for (Map.Entry<String, Integer> entry : functionsCount.entrySet()) {
//                String string = entry.getKey();
//                Integer integer = entry.getValue();
//                System.out.println("\"" + string + "\"," + "\"" + integer + "\"");
//            }
        }

    }

    private void readServerFiles(File file) throws FileNotFoundException, IOException {
        for (File f : file.listFiles()) {
            BufferedReader in = new BufferedReader(new FileReader(f));
            int count = CountLines.count(in);
            String functionName = f.getName().substring(0, 9);
            if (functionsCount.containsKey(functionName)) {
                int total = functionsCount.get(functionName) + count;
                functionsCount.put(functionName, total);
                // System.out.println("Function: " + functionName + "update with "+count +" lines! :)");
            } else {
                functionsCount.put(functionName, count);
            }
        }
    }

    public int findFolder(final File file, int nivel) throws FileNotFoundException, IOException {
        int count = 0;
        if (nivel == 2) {
            count = 0;
        }
        if (file.exists()) {
            if (file.isDirectory()) {
                int nivelAtual = nivel + 1;
                if (nivelAtual > maiorNivel) {
                    maiorNivel = nivelAtual;
                }
                for (File f : file.listFiles()) {
                    logDirectoryTree(false, nivelAtual, f);
                    count += findFolder(f, nivelAtual);
                }
            } else if (file.getAbsolutePath().endsWith(".js")) {
                BufferedReader in = new BufferedReader(new FileReader(file));
                count += CountLines.count(in);
                logFileCount(false, nivel, file, count);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No such file", "Attention!", JOptionPane.WARNING_MESSAGE);
        }
        if (nivel == 1 || nivel == 2) {
            if (file.isDirectory()) {
                System.out.println("\"" + file.getName() + "\"" + ", \"" + count + "\"");
                functionsCount.put(file.getName(), count);
            }
            //   System.out.println("--------------------FIM " + file.getName().toUpperCase() + " - TOTAL: " + count + "-------------------");
        }
        return count;
    }

    private void logFileCount(boolean log, int nivel, final File file, int count) {
        if (!log) {
            return;
        }
        String path = "";
        for (int i = 1; i < nivel; i++) {
            path += "\t";
        }
        path += file.getName();
        System.out.println("\"" + path + "\"" + ", \"" + count + "\"");
        System.out.println(path + ":\t" + count);
    }

    private void logDirectoryTree(boolean log, int nivelAtual, File f) {
        if (!log) {
            return;
        }
        if (nivelAtual != 1 && f.isDirectory()) {;
            String path = "";
            for (int i = 1; i < nivelAtual; i++) {
                path += "\t";
            }
            path += "- " + f.getName();
            System.out.println(path);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        efetuarPesquisaJB = new javax.swing.JButton();
        localizarJB = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        diretorioJTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaJTA = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Localizador e gerador de visões");

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 100));

        efetuarPesquisaJB.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        efetuarPesquisaJB.setText("Analyze");
        efetuarPesquisaJB.setEnabled(false);
        efetuarPesquisaJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                efetuarPesquisaJBActionPerformed(evt);
            }
        });

        localizarJB.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        localizarJB.setText("Search");
        localizarJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localizarJBActionPerformed(evt);
            }
        });

        jLabel3.setText("Directory:");

        diretorioJTF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(diretorioJTF, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(localizarJB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(efetuarPesquisaJB, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(594, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(efetuarPesquisaJB, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(localizarJB, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diretorioJTF, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        textAreaJTA.setEditable(false);
        textAreaJTA.setColumns(20);
        textAreaJTA.setRows(5);
        jScrollPane1.setViewportView(textAreaJTA);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void localizarJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localizarJBActionPerformed
        try {

            textAreaJTA.setText("");

            JFileChooser fileChooser = null;
            if (fileChooser == null) {
                try {
                    fileChooser = new JFileChooser("C:/Java/Projetos");
                } catch (Exception ex) {
                    fileChooser = new JFileChooser();
                }
            }
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int i = fileChooser.showSaveDialog(null);
            if (1 != i) {
                caminho = fileChooser.getSelectedFile();
                log("File = " + caminho.getAbsolutePath());
                diretorioJTF.setText(caminho.getAbsolutePath());
                efetuarPesquisaJB.setEnabled(caminho != null);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error when attempting to find: \n" + ex.getMessage());
        }
    }//GEN-LAST:event_localizarJBActionPerformed

    private void efetuarPesquisaJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_efetuarPesquisaJBActionPerformed
        try {

            findProject(caminho);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_efetuarPesquisaJBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField diretorioJTF;
    private javax.swing.JButton efetuarPesquisaJB;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton localizarJB;
    private javax.swing.JTextArea textAreaJTA;
    // End of variables declaration//GEN-END:variables

}
