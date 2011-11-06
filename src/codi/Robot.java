/*
 * Robot.java
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

public class Robot {

    private int sensor[];
    static int posicio_x, posicio_y;

    public Robot() {
        sensor = new int[8];
        for (int i = 0; i < 8; i++) {
            sensor[i] = 0;
        }
    }

    public int getPosx() {
        return posicio_x;
    }

    public int getPosy() {
        return posicio_y;
    }

    public int getSensor(int x) {
        return sensor[x];
    }

    public void setSensores(int[] sens) {
        sensor = sens;
    }

    public void setPos(int x, int y) {
        posicio_x = x;
        posicio_y = y;
    }

    public void visualitzaSensors() {
        System.out.print("Sensors: ");
        for (int i = 0; i < 8; i++) {
            System.out.print(sensor[i] + " ");
        }
        System.out.println("");
        System.out.println("");
    }
}
