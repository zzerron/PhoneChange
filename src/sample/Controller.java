package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField url_field;

    @FXML
    private TextField phone_field;

    @FXML
    private Button inputFileButton;

    @FXML
    private Button changeFieldPhone;

    @FXML
    private TextField message_field;

//    У контроллера есть поля Модель, объект которой, в свою очередь, ссылается на сам контроллер
    private Model model = new Model(this);

//    Вызывает методы объекта Модель, в зависимости от нажатой кнопки
    @FXML
    void initialize() {
        inputFileButton.setOnAction(event -> {model.getText(url_field.getText());});
        changeFieldPhone.setOnAction(event -> {model.setText(phone_field.getText());});
    }

    void setPhone_field(String phone){
        phone_field.setText(phone);
    }

    void setMessage_field(String message){ message_field.setText(message);
    }
}
