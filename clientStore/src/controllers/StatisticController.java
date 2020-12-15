package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import model.DataStatistic;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StatisticController extends BaseController implements Initializable, ISetUser {

    private User user;
    @FXML
    private AreaChart<Integer, Integer> areaChartProceeds;

    @FXML
    private ComboBox<String> TimePeriod;

    @Override
    public void setModelUser(User user) {
        this.user = user;
    }

    @FXML
    private javafx.scene.chart.BarChart<String, Integer> BarChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items = FXCollections.observableArrayList(
                "1 месяц",
                "Сравнить за 2 месяца"
        );
        TimePeriod.setItems(items);
        setDataForBar();
    }

    public void switchTimeTiCompere(ActionEvent event) {

        if(TimePeriod.getSelectionModel().getSelectedItem().equals("1 месяц")){
            areaChartProceeds.getData().setAll(getDataForThisMouth());

        } else{
            areaChartProceeds.getData().setAll(getDataForThisMouth(), getDataForPastMouth());
        }

    }
    private XYChart.Series<Integer, Integer> getDataForThisMouth(){
        client.sendObject(Commands.GET_SUM_FOR_THIS_MOUTH.toString());
        XYChart.Series<Integer, Integer> series;
        ArrayList<DataStatistic> data = (ArrayList<DataStatistic>)client.readObject();
        series = getDataForGraph(data);
        series.setName("Текущий месяц");
        return series;
    }
    private XYChart.Series<Integer, Integer> getDataForPastMouth(){
        client.sendMessage(Commands.GET_SUM_FOR_PAST_MOUTH.toString());
        XYChart.Series<Integer, Integer> series;
        ArrayList<DataStatistic> data = (ArrayList<DataStatistic>)client.readObject();
        series = getDataForGraph(data);
        series.setName("Прошлый месяц");
        return series;
    }

    private XYChart.Series<Integer, Integer> getDataForGraph(ArrayList<DataStatistic> data){
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        for(DataStatistic item : data) {
            // добавляем зависимость выбранного пункта от количества
            series.getData().add(new XYChart.Data<>(item.getDay(), (int)Math.round(item.getPrice())));
        }
        return series;
    }
    private void setDataForBar(){
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        client.sendMessage(Commands.GET_DATA_SUM_PRICE_PRODUCT_BY_CATEGORY.toString());
        ArrayList<DataStatistic> data = (ArrayList<DataStatistic>)client.readObject();

       for(DataStatistic item: data) {
            series.getData().add(new XYChart.Data<String, Integer>(
                    item.getName_category(),
                    (int)Math.round(item.getPrice())));
        }
        // передаем на график
        BarChart.getData().setAll(series);
    }

    @FXML
    void onBtnComeBack(ActionEvent event) {
        goHomeScene(event, user);
    }

    public void onBtnGetProductReport(ActionEvent event) {
        client.sendMessage(Commands.GET_REPORT.toString());
        if (client.getFile()){
            successfulAlert("Удача","Теперь можно посмотреть файл на компьютере");
        } else{
            warningAlert("Ошибка","Не удалось получить отчет");
        }
    }

    public void onBtnGetMoneyReport(ActionEvent event) {
        client.sendMessage(Commands.GET_SALES.toString());
        if (client.getFile()){
            successfulAlert("Удача","Теперь можно посмотреть файл на компьютере");
        } else{
            warningAlert("Ошибка","Не удалось получить отчет");
        }
    }
}
