module com.calculo.trabV1{
    requires javafx.controls;
    requires javafx.fxml;

    exports com.calculo.trabV1.view;
    opens com.calculo.trabV1.view to javafx.graphics, javafx.fxml;
}