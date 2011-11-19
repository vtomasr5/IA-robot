/*
 * Caselles.java
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
import java.awt.event.*;
import javax.swing.*;

public class Caselles extends JPanel implements MouseListener {

    private Taulell taulell;
    private ImageIcon imgFons;
    private static int[] casellaMarcada = new int[2];
    private static boolean iniciarRobot = false;
    private boolean ocupada; // estat casella

    public Caselles(Taulell t) {
        initComponents();
        ocupada = false;
        this.taulell = t;
        if (this.taulell.getTipoTablero() == true) {
            addMouseListener(this);
        }
        this.setLocation(141, 0);
    }

    public boolean getEstadocasilla() {
        return ocupada;
    }

    public void setCasillaOcupada(boolean bool) {
        ocupada = bool;
    }

    public void setFondo(ImageIcon fondo) {
        this.imgFons = fondo;
    }

    public ImageIcon getFondo() {
        return this.imgFons;
    }

    public void resetRobot() {
        iniciarRobot = false;
    }

    private void initComponents() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 161, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 193, Short.MAX_VALUE));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgFons.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (!getFondo().toString().contains("wall-e.png")) {
                setCasillaMarcada(taulell.getCoordenadas((Caselles) e.getComponent()));
                this.taulell.pintar(getCasillaMarcada()[0], getCasillaMarcada()[1], false, false);
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (iniciarRobot == false) {
                if (!getEstadocasilla()) { // si hi ha "paret" no se pot posar es robot
                    iniciarRobot = true;
                    int i[] = (taulell.getCoordenadas((Caselles) e.getComponent()));
                    taulell.rob = new Robot();
                    taulell.rob.setPos(i[0], i[1]);
                    setCasillaMarcada(taulell.getCoordenadas((Caselles) e.getComponent()));
                    this.taulell.pintar(getCasillaMarcada()[0], getCasillaMarcada()[1], true, false);
                    taulell.veureMapa();
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                                taulell.accio(); // inici del moviment d'en wall-e
                            } catch (InterruptedException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    };
                    new Thread(r).start();
                }
            }
        } else if (e.getButton() == MouseEvent.BUTTON2) { // boton rueda del raton
            if (!getFondo().toString().contains("terra.jpg") && !getFondo().toString().contains("wall-e.png") && (getFondo().toString().contains("herba.jpg"))) {
                setCasillaMarcada(taulell.getCoordenadas((Caselles) e.getComponent()));
                this.taulell.pintar(getCasillaMarcada()[0], getCasillaMarcada()[1], false, true); // posa una paret de nou
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public static int[] getCasillaMarcada() {
        return casellaMarcada;
    }

    public static void setCasillaMarcada(int[] aCasillaMarcada) {
        casellaMarcada = aCasillaMarcada;
    }
}
