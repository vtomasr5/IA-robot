/*
 * Main.java
 * 
 * Copyright (C) 2011 Vicenç Juan Tomàs Monserrat
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package codi;

import javax.swing.*;

public class Main extends JFrame {
    
    private Taulell taulell;

    public Main() {
        initComponents();
    }
    
    private static void initLookAndFeel() {
        String lookAndFeel = null;
        String osname = System.getProperty("os.name").toLowerCase();
        
        if (osname.equals("linux")) {
            lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        } else if (osname.startsWith("windows")) {
            lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else if (osname.startsWith("mac")) {
            lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        } else {
            lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
        }
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (Exception e) {
            try {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }    

    private void initComponents() {
        taulell = new Taulell(10, true);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        GroupLayout taulellLauout = new GroupLayout(taulell);

        taulell.setLayout(taulellLauout);

        taulellLauout.setHorizontalGroup(
                taulellLauout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(499, 499, Short.MAX_VALUE));
        taulellLauout.setVerticalGroup(
                taulellLauout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 499, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(taulell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap(176, Short.MAX_VALUE)) //                .addComponent(boton))
                );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(taulell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE) //                                            .addComponent(boton)
                .addContainerGap(13, Short.MAX_VALUE)));
        
        setLocation(300, 100);
        setTitle("UIB 2011/12 - IA - Robot amb passadissos estrets");
        setResizable(false);
        initLookAndFeel();
        SwingUtilities.updateComponentTreeUI(this);        

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}