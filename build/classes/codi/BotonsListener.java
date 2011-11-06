/*
 * BotonsListener.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BotonsListener implements ActionListener {

    private Finestra inter;
    private boolean pausa = false;

    public BotonsListener(Finestra t) {
        this.inter = t;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JButton boton = (JButton) ae.getSource();
        if (boton.getText().equals("Reiniciar")) {
            inter.reiniciar();
        } else if (boton.getText().equals("Reset Robot")) {
            inter.reiniciarRobot();
        } else if (boton.getText().equals("Pausar")) {
            boton.setText("Reanudar");
            if (pausa == false) {
                inter.pausar(true);
                pausa = true;
            } else {
                inter.pausar(false);
                pausa = false;
            }
        } else if (boton.getText().equals("Reanudar")) {
            boton.setText("Pausar");
            if (pausa == true) {
                inter.pausar(false);
                pausa = false;
            } else {
                inter.pausar(true);
                pausa = true;
            }
        } else if (boton.getText().equals("+ velocitat")) {
            inter.modificaVelocitat('+');
        } else if (boton.getText().equals("- velocitat")) {
            inter.modificaVelocitat('-');
        } else if (boton.getText().equals("Informació")) {
            JOptionPane.showMessageDialog(null, "Autor: Vicenç Juan Tomàs Montserrat\nLlicència: GPLv3\nVersió: 1.0","Informació", JOptionPane.INFORMATION_MESSAGE);
        } else if (boton.getText().equals("Manual")) {
            JOptionPane.showMessageDialog(null, "Funciona amb el ratolí.\nBOTÓ ESQUERRA: insertar herba (paret)\nBOTÓ DRET: iniciar el robot\nBOTÓ DE LA RODETA: eliminar herba\n","Funcionament", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
