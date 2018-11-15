import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class WeightLineChart extends Application {
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Weight History");
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Count of Weigh In");
		yAxis.setLabel("Weight");
		
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("User's Weight History");
		XYChart.Series series = new XYChart.Series();
		series.setName(WeightServlet.n + "'s Weight");
		for (int i = 1; i <= Welcome.intArray.length; i++) {
			series.getData().add(new XYChart.Data(i, Welcome.intArray[i - 1]));
		}
		Scene scene = new Scene(lineChart, 800, 600);
		lineChart.getData().add(series);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
