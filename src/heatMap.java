/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Trial;

/**
 *
 * @author Tani
 */
public class heatMap {

    double[][] data = new double[][]{{3,2,3,4,5,6},
                                 {2,3,4,5,6,7},
                                 {3,4,5,6,7,6},
                                 {4,5,6,7,6,5}};

// Step 1: Create our heat map chart using our data.
HeatChart map = new HeatChart(data);
    public heatMap() {
    }


// Step 2: Customise the chart.
map.setTitle("This is my heat chart title");
map.setXAxisLabel("X Axis");
map.setYAxisLabel("Y Axis");

// Step 3: Output the chart to a file.
map.saveToFile(new File("java-heat-chart.png"));
}
