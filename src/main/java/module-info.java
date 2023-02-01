module Wargames {

    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    exports edu.ntnu.idatt2001.alekol.Wargames.Model.Army;
    exports edu.ntnu.idatt2001.alekol.Wargames.Model.Units;
    exports edu.ntnu.idatt2001.alekol.Wargames.Model.FileManagement;
    exports edu.ntnu.idatt2001.alekol.Wargames.Model.Battle;
    exports edu.ntnu.idatt2001.alekol.Wargames.Controller;
    exports edu.ntnu.idatt2001.alekol.Wargames.View;

    opens edu.ntnu.idatt2001.alekol.Wargames.Controller to javafx.fxml;
    opens edu.ntnu.idatt2001.alekol.Wargames.Model.Army to javafx.fxml;
    opens edu.ntnu.idatt2001.alekol.Wargames.Model.Battle to javafx.fxml;
    opens edu.ntnu.idatt2001.alekol.Wargames.Model.FileManagement to javafx.fxml;
    opens edu.ntnu.idatt2001.alekol.Wargames.Model.Units to javafx.fxml;
    opens edu.ntnu.idatt2001.alekol.Wargames.View to javafx.fxml;
    exports edu.ntnu.idatt2001.alekol.Wargames.Model.Enum;
    opens edu.ntnu.idatt2001.alekol.Wargames.Model.Enum to javafx.fxml;
}
