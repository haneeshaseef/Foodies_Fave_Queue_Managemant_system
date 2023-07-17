module com.example.coursework04 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens com.example.courseWorkSubmission02 to javafx.fxml;
    exports com.example.courseWorkSubmission02;
}