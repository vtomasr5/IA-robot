/*
 * Taulell.java
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

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Taulell extends JPanel {

    private ImageIcon enterra, paret, robot;
    private JTextField[][] matriuSensors;
    private boolean tipusTaulell;
    private Caselles[][] caselles;
    private int tamany;
    private int velocitat = 500;
    private boolean aturar, sortir;
    protected Robot rob;


    public Taulell(int size, boolean tipo) {
        initComponents();
        int x, y;
        this.tamany = size;
        setLayout(new java.awt.GridLayout(size, size));
        this.tipusTaulell = tipo;
        carregarImatges();
        caselles = new Caselles[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                caselles[i][j] = new Caselles(this);
                caselles[i][j].setFondo(enterra);
                x = (i * 50) + 1;
                y = (j * 50) + 1;
                caselles[i][j].setBounds(x, y, 49, 49);
                caselles[i][j].setLocation(i*50+141, j*50+0);
                this.add(caselles[i][j]);
            }
        }
    }

    public void reiniciar() {
        for (int i = 0; i < tamany; i++) {
            for (int j = 0; j < tamany; j++) {
                caselles[i][j].setCasillaOcupada(false);
            }
        }
        
        velocitat = 500;
        
        if (rob == null) {
            limpiar();
            this.repaint();
        } else {
            caselles[0][0].resetRobot();
            rob.setPos(-1, -1);
            aturar = true;
            sortir = true;
            veureMapa();
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    matriuSensors[i][j].setText("");
                    matriuSensors[i][j].setBackground(Color.white);
                }
            }
            this.repaint();
        }
    }
    
    public void reiniciarRobot() {
        if (rob != null) {
            caselles[0][0].resetRobot();
            rob.setPos(-1, -1);
            aturar = true;
            sortir = true;
            veureMapa();

            velocitat = 500;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    matriuSensors[i][j].setText("");
                    matriuSensors[i][j].setBackground(Color.white);
                }
            }
            this.repaint();
        }
    }

    public void pausar(boolean pausa) {
        aturar = pausa;
    }
    
    public void limpiar() {
        for (int j = 0; j < tamany; j++) {
            for (int i = 0; i < tamany; i++) {
                if (caselles[i][j].getEstadocasilla() == false) {
                    System.out.print("0 ");
                    this.caselles[i][j].setFondo(enterra);
                } else {
                    System.out.print("1 ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void modificaVelocitat(char modo) {
        if (modo == '+') {
            if (velocitat != 100) {
                velocitat -= 100;
            }
        } else {
            velocitat += 100;
            if (velocitat >= 1500) {
                velocitat = 1500; 
            }
        }
    }

    public void accio() {
        // movAnt: estat intern o anterior
        char movAnt = 'E'; // N abans
        aturar = false;
        sortir = false;
        
        analitzarSensors();
        
        while (!sortir) {
            try {
                Thread.sleep(10); // velocitat en que s'executa l'acció
            } catch (InterruptedException ex) {
                Logger.getLogger(Taulell.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (!aturar) {
                if (rob.getPosx() != -1 && rob.getPosy() != -1) {
                    analitzarSensors();
                    if (((rob.getSensor(0) == 1 || rob.getSensor(7) == 1) && rob.getSensor(1) == 0)
                            && !(rob.getSensor(0) == 1 && rob.getSensor(6) == 1 && rob.getSensor(7) == 0 && movAnt == 'N')
                            && (movAnt == 'N' || movAnt == 'E' || (movAnt == 'O' && rob.getSensor(5) == 1 && rob.getSensor(7) == 1) || (movAnt == 'S' && rob.getSensor(3) == 1 && rob.getSensor(5) == 1 && rob.getSensor(7) == 1))) {
                        rob.setPos(rob.getPosx(), rob.getPosy() - 1); // NORD
                        System.out.println("NORD");
                        movAnt = 'N';

                    } else if (((rob.getSensor(1) == 1 || rob.getSensor(2) == 1) && rob.getSensor(3) == 0)
                            && !(rob.getSensor(0) == 1 && rob.getSensor(2) == 1 && rob.getSensor(1) == 0 && movAnt == 'E')
                            && !(rob.getSensor(1) == 1 && rob.getSensor(6) == 1 && rob.getSensor(7) == 0 && movAnt == 'N')
                            && (movAnt == 'E' || movAnt == 'S' || (movAnt == 'N' && rob.getSensor(1) == 1) || (movAnt == 'N' && rob.getSensor(2) == 1 && rob.getSensor(6) == 0) || (movAnt == 'O' && rob.getSensor(5) == 1 && rob.getSensor(7) == 1))) {
                        rob.setPos(rob.getPosx() + 1, rob.getPosy()); // EST
                        System.out.println("EST");
                        movAnt = 'E';

                    } else if (((rob.getSensor(3) == 1 || rob.getSensor(4) == 1) && rob.getSensor(5) == 0)
                            && !(rob.getSensor(2) == 1 && rob.getSensor(4) == 1 && rob.getSensor(3) == 0 && movAnt == 'S')
                            && (movAnt == 'O' || movAnt == 'S' || (movAnt == 'E' && rob.getSensor(3) == 1) || (movAnt == 'N' && rob.getSensor(1) == 1 && rob.getSensor(7) == 1))) {
                        rob.setPos(rob.getPosx(), rob.getPosy() + 1); // SUD
                        System.out.println("SUD");
                        movAnt = 'S';

                    } else if (((rob.getSensor(5) == 1 || rob.getSensor(6) == 1) && rob.getSensor(7) == 0)
                            && !(rob.getSensor(4) == 1 && rob.getSensor(6) == 1 && rob.getSensor(5) == 0 && movAnt == 'O')
                            && (movAnt == 'N' || movAnt == 'O' || (movAnt == 'S' && rob.getSensor(5) == 1) || (movAnt == 'E' && rob.getSensor(3) == 1))) {
                        rob.setPos(rob.getPosx() - 1, rob.getPosy()); // OEST
                        System.out.println("OEST");
                        movAnt = 'O';

                    // casos en que totes les possibilitats de moviment estan tancades
                    } else if ((rob.getSensor(1) == 1 && rob.getSensor(3) == 1 && rob.getSensor(5) == 1 && rob.getSensor(7) == 1)
                            && (rob.getSensor(0) == 1 || rob.getSensor(2) == 0 || rob.getSensor(4) == 0 || rob.getSensor(6) == 0)
                            && (rob.getSensor(0) == 0 || rob.getSensor(2) == 1 || rob.getSensor(4) == 0 || rob.getSensor(6) == 0)
                            && (rob.getSensor(0) == 0 || rob.getSensor(2) == 0 || rob.getSensor(4) == 1 || rob.getSensor(6) == 0)
                            && (rob.getSensor(0) == 0 || rob.getSensor(2) == 0 || rob.getSensor(4) == 0 || rob.getSensor(6) == 1)) {
                        aturar = true;
                        
                     } else { 
                         if (rob.getSensor(0) == 1 && rob.getSensor(1) == 1 && rob.getSensor(2) == 1 && rob.getSensor(3) == 1
                          && rob.getSensor(4) == 1 && rob.getSensor(5) == 1 && rob.getSensor(6) == 1 && rob.getSensor(7) == 1) {
                            aturar = true;
                         }else {
                             rob.setPos(rob.getPosx(), rob.getPosy() - 1);
                         }
                         movAnt = 'N'; // N abans.
                    }
                    analitzarSensors();
                }
                veureMapa();
            }
        }
    }

    public void analitzarSensors() {
        int x, y;
        x = rob.getPosx();
        y = rob.getPosy();
        int[] sens = {0, 0, 0, 0, 0, 0, 0, 0};
        
        if (esborde(x - 1, y + 1) || caselles[x - 1][y + 1].getEstadocasilla() == true) {
            sens[6] = 1;
        } else {
            sens[6] = 0;
        }
        if (esborde(x, y + 1) || caselles[x][y + 1].getEstadocasilla() == true) {
            sens[5] = 1;
        } else {
            sens[5] = 0;
        }
        if (esborde(x + 1, y + 1) || caselles[x + 1][y + 1].getEstadocasilla() == true) {
            sens[4] = 1;
        } else {
            sens[4] = 0;
        }
        if (esborde(x + 1, y) || caselles[x + 1][y].getEstadocasilla() == true) {
            sens[3] = 1;
        } else {
            sens[3] = 0;
        }
        if (esborde(x + 1, y - 1) || caselles[x + 1][y - 1].getEstadocasilla() == true) {
            sens[2] = 1;
        } else {
            sens[2] = 0;
        }
        if (esborde(x, y - 1) || caselles[x][y - 1].getEstadocasilla() == true) {
            sens[1] = 1;
        } else {
            sens[1] = 0;
        }
        if (esborde(x - 1, y - 1) || caselles[x - 1][y - 1].getEstadocasilla() == true) {
            sens[0] = 1;
        } else {
            sens[0] = 0;
        }
        if (esborde(x - 1, y) || caselles[x - 1][y].getEstadocasilla() == true) {
            sens[7] = 1;
        } else {
            sens[7] = 0;
        }
        pintaSensor(sens);
        rob.setSensores(sens);
    }

    public void pintaSensor(int[] sen) {
        if (sen[0] == 1) {
            matriuSensors[0][0].setBackground(Color.ORANGE);
        } else {
            matriuSensors[0][0].setBackground(Color.white);
        }
        if (sen[1] == 1) {
            matriuSensors[0][1].setBackground(Color.ORANGE);
        } else {
            matriuSensors[0][1].setBackground(Color.white);
        }
        if (sen[2] == 1) {
            matriuSensors[0][2].setBackground(Color.ORANGE);
        } else {
            matriuSensors[0][2].setBackground(Color.white);
        }
        if (sen[3] == 1) {
            matriuSensors[1][2].setBackground(Color.ORANGE);
        } else {
            matriuSensors[1][2].setBackground(Color.white);
        }
        if (sen[4] == 1) {
            matriuSensors[2][2].setBackground(Color.ORANGE);
        } else {
            matriuSensors[2][2].setBackground(Color.white);
        }
        if (sen[5] == 1) {
            matriuSensors[2][1].setBackground(Color.ORANGE);
        } else {
            matriuSensors[2][1].setBackground(Color.white);
        }
        if (sen[6] == 1) {
            matriuSensors[2][0].setBackground(Color.ORANGE);
        } else {
            matriuSensors[2][0].setBackground(Color.white);
        }
        if (sen[7] == 1) {
            matriuSensors[1][0].setBackground(Color.ORANGE);
        } else {
            matriuSensors[1][0].setBackground(Color.white);
        }
        
        matriuSensors[0][0].setText("0");
        matriuSensors[1][0].setText("7");
        matriuSensors[2][0].setText("6");
        matriuSensors[0][1].setText("1");
        matriuSensors[2][1].setText("5");
        matriuSensors[0][2].setText("2");
        matriuSensors[1][2].setText("3");
        matriuSensors[2][2].setText("4");
    }

    public boolean esborde(int x, int y) {
        boolean borde = false;
        if ((x < 0) || (x >= tamany)) {
            borde = true;
        } else if ((y < 0) || (y >= tamany)) {
            borde = true;
        }
        return (borde);
    }

    public void veureMapa() {
        System.out.println(rob.getPosx() + "  " + rob.getPosy());
        for (int j = 0; j < tamany; j++) {
            for (int i = 0; i < tamany; i++) {
                if (rob.getPosx() != i || rob.getPosy() != j) {
                    if (caselles[i][j].getEstadocasilla() == false) {
                        System.out.print("0 ");
                        this.caselles[i][j].setFondo(enterra);
                    } else {
                        System.out.print("1 ");
                    }
                } else {
                    System.out.print("W ");
                    this.caselles[i][j].setFondo(robot);
                }
            }
            System.out.println("");
        }
        System.out.println("");
        try {
            this.repaint();
            Thread.sleep(velocitat); // control de la velocitat d'en Wall-e
        } catch (InterruptedException ex) {
            Logger.getLogger(Taulell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getTipoTablero() {
        return this.isTipoTablero();
    }

    public void pintar(int x, int y, boolean esrobot, boolean esEnterra) {
        if (esrobot) {
            this.caselles[x][y].setFondo(robot);
            this.repaint();
        } else if (esEnterra) {
            this.caselles[x][y].setFondo(enterra);
            this.repaint();
            caselles[x][y].setCasillaOcupada(false);
        } else {
            this.caselles[x][y].setFondo(paret);
            this.repaint();
            caselles[x][y].setCasillaOcupada(true);
        }
    }

    private void carregarImatges() {
        this.enterra = Taulell.carregarFons("img/terra.jpg");
        this.paret = Taulell.carregarFons("img/herba.jpg");
        this.robot = Taulell.carregarFons("img/wall-e.png");
    }

    protected static ImageIcon carregarFons(String ruta) {
        java.net.URL localizacion = Taulell.class.getResource(ruta);
        if (localizacion != null) {
            return new ImageIcon(localizacion);
        } else {
            System.err.println("ERROR: No s'ha trobat l'arxiu: " + ruta);
            return null;
        }
    }

    public int[] getCoordenadas(Caselles casella) {
        int[] coordenadas = new int[2];
        for (int i = 0; i < this.caselles.length; i++) {
            for (int j = 0; j < this.caselles.length; j++) {
                if (this.caselles[i][j] == casella) {
                    coordenadas[0] = i;
                    coordenadas[1] = j;
                }
            }
        }
        return coordenadas;
    }

    public Caselles[][] getCasillas() {
        return caselles;
    }

    public void setCasillas(Caselles[][] casillas) {
        this.caselles = casillas;
    }

    public boolean isTipoTablero() {
        return tipusTaulell;
    }

    public void setTipoTablero(boolean tipoTablero) {
        this.tipusTaulell = tipoTablero;
    }

    private void initComponents() {
        setLayout(null);
        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(641, 500));

        JPanel panell = new JPanel();

        JButton btreiniciar = new JButton("Reset");
        btreiniciar.setPreferredSize(new Dimension(110, 25));
        
        JButton btresetRobot = new JButton("Reset robot");
        btresetRobot.setPreferredSize(new Dimension(110, 25));
        JButton btpausar = new JButton("Pausar");
        btpausar.setPreferredSize(new Dimension(110, 25));
  
        JLabel lblespai = new JLabel("");
        JLabel lblespai2 = new JLabel("");
        JButton botoMesVelocitat = new JButton("+ velocitat");
        botoMesVelocitat.setPreferredSize(new Dimension(110, 25));
        JButton botoMenysVelocitat = new JButton("- velocitat");
        botoMenysVelocitat.setPreferredSize(new Dimension(110, 25));        
        
        JButton botoInfo = new JButton("Informació");
        botoInfo.setPreferredSize(new Dimension(110, 25));

        JButton botoFun = new JButton("Manual");
        botoFun.setPreferredSize(new Dimension(110, 25));        
        
        botoInfo.addActionListener(new BotonsListener(this));
        botoInfo.setFocusable(false);
        botoFun.addActionListener(new BotonsListener(this));
        botoFun.setFocusable(false);        
        
        JLabel label = new JLabel("Estat sensors: ");
        
        label.setPreferredSize(new Dimension(100, 20));
        lblespai2.setPreferredSize(new Dimension(100, 10));
        panell.add(btreiniciar);
        panell.add(btresetRobot);
        panell.add(btpausar);
        panell.add(botoMenysVelocitat);
        panell.add(botoMesVelocitat);
        panell.add(lblespai2);
        panell.add(label);
        
        // matriu dels estats dels sensors
        JPanel panel2 = new JPanel(new GridLayout(3, 3));
        matriuSensors = new JTextField[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                    matriuSensors[i][j] = new JTextField(2);
                    matriuSensors[i][j].setHorizontalAlignment(JTextField.CENTER);
                    panel2.add(matriuSensors[i][j]).setFocusable(false);
            }
        }
        matriuSensors[1][1].setVisible(false);
        
        panell.add(panel2);
        
        lblespai.setPreferredSize(new Dimension(100, 10));
        panell.add(lblespai);
        panell.add(botoFun);
        panell.add(botoInfo);
        
//        panell.setSize(140, 500); // sim
//        panell.setLocation(502, 0); // loc
        panell.setSize(140, 500); // sim
        panell.setLocation(0, 0); // loc
        add(panell);

        btreiniciar.addActionListener(new BotonsListener(this));
        btpausar.addActionListener(new BotonsListener(this));
        botoMesVelocitat.addActionListener(new BotonsListener(this));
        botoMenysVelocitat.addActionListener(new BotonsListener(this));
        btresetRobot.addActionListener(new BotonsListener(this));
    }
}
