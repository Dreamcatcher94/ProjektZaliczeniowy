package battleships;

import Plansze.PlanszaGracza;
import Plansze.PlanszaPrzeciwnika;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public GridPane enemyBoard, playerBoard;
    public PlanszaGracza player;
    public PlanszaPrzeciwnika przeciwnik;
    public Label numberOfPlayerShootsLabel;
    public Label numberOfPlayerUnitsLabel;
    public Label numberOfEnemyShootsLabel;
    public Label numberOfEnemyUnitsLabel;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void strzelaj(MouseEvent event){
        if (event.getSource() instanceof Button && !player.jednostkiIterator.hasNext()){

            if(!numberOfPlayerShootsLabel.isDisable()) {
                numberOfPlayerUnitsLabel.setVisible(true);
                numberOfEnemyUnitsLabel.setVisible(true);
                numberOfPlayerShootsLabel.setVisible(true);
                numberOfEnemyShootsLabel.setVisible(true);
            }

            player.zestrzelJednostke(enemyBoard,(Button) event.getSource(), przeciwnik);
            pokazWynikGry();

            if(!player.getCzyTuraGracza()){
                przeciwnik.shootUnit(playerBoard, player);
                pokazWynikGry();
            }
            ustawTekst();
        }
    }

    public void pokolourujKomorke(MouseEvent event){
        if (event.getSource() instanceof Button)
            player.ustawPodglad(playerBoard, (Button) event.getSource(), "-fx-base: #F7CA88;");
    }

    public void przywrocKolor(MouseEvent event){
        if (event.getSource() instanceof Button && player.jednostkiIterator.hasNext()) {
            player.odswierzPlansze(playerBoard);
       }
    }

    private void ustawTekst(){
        numberOfPlayerUnitsLabel.setText(String.valueOf(przeciwnik.getLiczbaPozostalychJednostek()));
        numberOfEnemyUnitsLabel.setText(String.valueOf(player.getLiczbaPozostalychJednostek()));
        numberOfPlayerShootsLabel.setText(String.valueOf(player.getLiczbaStrzalow()));
        numberOfEnemyShootsLabel.setText(String.valueOf(przeciwnik.getLiczbaStrzalow()));
    }


    private void pokazWynikGry(){
        if(player.getLiczbaPozostalychJednostek() == 0){
            alert.setTitle("Koniec gry!");
            alert.setHeaderText("Koniec gry. Wygrałeś");
            alert.showAndWait();
            playerBoard.setDisable(true);
            enemyBoard.setDisable(true);
        }
        if(przeciwnik.getLiczbaPozostalychJednostek() == 0){
            alert.setTitle("Koniec gry!");
            alert.setHeaderText("Koniec gry. Przegrałeś");
            alert.showAndWait();
            playerBoard.setDisable(true);
            enemyBoard.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfPlayerUnitsLabel.setVisible(false);
        numberOfEnemyUnitsLabel.setVisible(false);
        numberOfPlayerShootsLabel.setVisible(false);
        numberOfEnemyShootsLabel.setVisible(false);
        przeciwnik = new PlanszaPrzeciwnika();
        przeciwnik.przygotujJednostki();
        przeciwnik.ustawJednostkiLosowo(enemyBoard, "-fx-text-fill: green");

        player = new PlanszaGracza();
        player.odswierzPlansze(playerBoard);
        player.przygotujJednostki();
    }

    public void rozmiescLosowoWszystkieJednostki(Event event) {
        if(player.jednostkiIterator.hasNext())
        player.ustawJednostkiLosowo(playerBoard, "-fx-base: #2F4F4F;");
    }

    public void klknietoPlansze(MouseEvent event){
        if(event.getButton() == MouseButton.SECONDARY) {
            przywrocKolor(event);
            player.setHorizontalOrientation();
            pokolourujKomorke(event);
        }
        else {
            if (event.getSource() instanceof Button && player.jednostkiIterator.hasNext())
                player.ustawJednostke(playerBoard, "-fx-base: #2F4F4F;");
            else{
                alert.setTitle("Informacja");
                alert.setHeaderText("Umieściłeś już wszystkie jednostki. Zacznij strzelać do statków znajdujących się na planszy przeciwnika");
                alert.showAndWait();
            }
        }

    }

}





























