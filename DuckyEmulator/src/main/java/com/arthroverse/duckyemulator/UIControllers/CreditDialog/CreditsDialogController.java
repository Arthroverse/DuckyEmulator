package com.arthroverse.duckyemulator.UIControllers.CreditDialog;

import com.arthroverse.duckyemulator.UIs.Navigator;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreditsDialogController implements Initializable{

    @FXML
    private MFXButton btnBackgroundArtist;

    @FXML
    private MFXButton btnClose;

    @FXML
    private MFXButton btnDeveloper;

    @FXML
    private MFXButton btnLogoDesigner;

    @FXML
    private ImageView imageViewLogo;

    @FXML
    private Pane paneBackground;

    @FXML
    private Pane paneColorLayer;

    @FXML
    void btnBackgroundArtistClick(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://robtopgames.com"));
    }

    @FXML
    void btnCloseClick(ActionEvent event) {
        Navigator.getInstance().closeThirdStage();
    }

    @FXML
    void btnDeveloperClick(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://ducksabervn.com"));
    }

    @FXML
    void btnLogoDesignerClick(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://linktr.ee/sfalupi?fbclid=PAQ0xDSwLFlUhleHRuA2FlbQIxMQABpy3fyXAq_WgupZOeEQCZo5b9_EfxWieg2gjnm_dSfMew9POy_1zhCl8Dmt19_aem_0a155C2xJ1JG-_HIYHA4VQ"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final int[] colorFileCode = {1,2,3,4,5,6,40,46,49,53,5,4,55,56,58,59};
        paneBackground.setStyle(
                String.format("-fx-background-image: url('/com/arthroverse/duckyemulator/images/loginbg/game_bg_%d_001-uhd.png');", colorFileCode[(int)(Math.random() * colorFileCode.length)])
        );
        paneColorLayer.setStyle("-fx-background-color: linear-gradient(to bottom, #332D7B 0%, #3D4B9F 25%, #4984C4 100%);" + "-fx-opacity: 70%");
    }
}
