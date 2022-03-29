package battleship.view;

import battleship.controller.MainController;
import battleship.controller.GridButtonListener;
import battleship.controller.ShipButtonListener;
import battleship.model.Coordinate;
import battleship.model.Grid;
import battleship.model.Orientation;
import battleship.model.Ship;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@SuppressWarnings("Mnemonics")
public class Game extends JFrame {

    private MainController mainController;

    public static final int BUTTON_PIXEL_SIZE = 60;

    private JPanel mainPanel;
    private JPanel playerOne;
    private JPanel playerTwo;
    private JPanel playerOneGrid;
    private JPanel playerOneMenu;
    private JPanel playerTwoGrid;
    private JPanel playerTwoMenu;
    private JButton shipButton0P1;
    private JButton shipButton1P1;
    private JButton shipButton2P1;
    private JButton shipButton3P1;
    private JButton shipButton4P1;
    private JButton shipButton0P2;
    private JButton shipButton1P2;
    private JButton shipButton2P2;
    private JButton shipButton3P2;
    private JButton shipButton4P2;
    private JButton rotateButton;

    private final ResourceBundle resourceBundle;

    private final JButton[] shipButtons;

    private final GridButton[][] playerOneButtons;
    private final GridButton[][] playerTwoButtons;

    private int currentShipNum;
    private Ship currentShip;

    public Game(MainController mainController) throws HeadlessException {
        this.mainController = mainController;
        this.resourceBundle = ResourceBundle.getBundle("ui_strings");
        this.setTitle(this.resourceBundle.getString("gameName"));

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // grids

        this.playerOneButtons = new GridButton[Grid.GRID_SIZE][Grid.GRID_SIZE];
        this.playerTwoButtons = new GridButton[Grid.GRID_SIZE][Grid.GRID_SIZE];

        GridButtonListener phListener = new GridButtonListener(this, this.mainController);

        GridButton btn;
        this.playerOneGrid.setLayout(new GridLayout(Grid.GRID_SIZE, Grid.GRID_SIZE));
        this.playerTwoGrid.setLayout(new GridLayout(Grid.GRID_SIZE, Grid.GRID_SIZE));
        for (int i = 0; i < Grid.GRID_SIZE; i++) {
            for (int j = 0; j < Grid.GRID_SIZE; j++) {
                btn = new GridButton(i, j);
                btn.setPreferredSize(new Dimension(BUTTON_PIXEL_SIZE, BUTTON_PIXEL_SIZE));
                btn.addMouseListener(phListener);
                this.playerOneGrid.add(btn);
                this.playerOneButtons[i][j] = btn;

                btn = new GridButton(i, j);
                btn.setPreferredSize(new Dimension(BUTTON_PIXEL_SIZE, BUTTON_PIXEL_SIZE));
                this.playerTwoGrid.add(btn);
                this.playerTwoButtons[i][j] = btn;
            }
        }

        this.currentShipNum = -1;
        this.currentShip = null;

        this.setPlayerButtons(true, false);
        this.setPlayerButtons(false, false);
        
        this.shipButtons = new JButton[]{shipButton0P1, shipButton1P1, shipButton2P1, shipButton3P1, shipButton4P1};

        ShipButtonListener sbListener = new ShipButtonListener(this);

        for (JButton button :
                this.shipButtons) {
            button.addActionListener(sbListener);
        }

        this.rotateButton.addActionListener(e -> {
            if (this.currentShip != null) {
                Game.this.currentShip.rotate();
            }
        });

        this.pack();
    }

    public void showPlacement(GridButton buttonHovered, boolean show) {
        int x, y;
        Coordinate coordinate;
        if (this.currentShip.getOrientation() == Orientation.HORIZONTAL) {
            for (int i = 0; i < this.currentShip.getSize(); i++) {
                x = buttonHovered.getRow();
                y = buttonHovered.getColumn();
                if (x >= 0 && x < Grid.GRID_SIZE && (y + i) >= 0 && (y + i) < Grid.GRID_SIZE
                && this.playerOneButtons[x][y + i].isNotOccupied()) {
                    if (show) {
                        this.playerOneButtons[x][y + i].setText("P");
                    } else {
                        this.playerOneButtons[x][y + i].setText("");
                    }
                }
            }
        } else {
            for (int i = 0; i < this.currentShip.getSize(); i++) {
                x = buttonHovered.getRow();
                y = buttonHovered.getColumn();
                if ((x + i) >= 0 && (x + i) < Grid.GRID_SIZE && y >= 0 && y < Grid.GRID_SIZE
                && this.playerOneButtons[x + i][y].isNotOccupied()) {
                    if (show) {
                        this.playerOneButtons[x + i][y].setText("P");
                    } else {
                        this.playerOneButtons[x + i][y].setText("");
                    }
                }
            }
        } // TODO can be refactored
    }

    public void setCurrentShipNum(int currentShipNum) {
        this.currentShipNum = currentShipNum;
    }

    public void setCurrentShipNum(JButton shipButton) {
        for (int i = 0; i < Grid.SHIP_COUNT; i++) {
            if (this.shipButtons[i].equals(shipButton)) {
                this.currentShipNum = i;
            }
        }
        if (this.currentShip != null) {
            if (this.currentShip.getOrientation() == Orientation.VERTICAL) {
                this.currentShip.rotate();
            }
        }
        this.currentShip = this.mainController.getShipWithNumber(this.currentShipNum);
    }

    public void setPlayerButtons(boolean isPlayerOne, boolean enable) {
        GridButton[][] buttonsGrid;
        if (isPlayerOne) {
            buttonsGrid = this.playerOneButtons;
        } else {
            buttonsGrid = this.playerTwoButtons;
        }
        for (GridButton[] buttons :
                buttonsGrid) {
            for (GridButton button :
                    buttons) {
                if (button.isNotOccupied()) {
                    button.setEnabled(enable);
                }
            }
        }
    }

    public Ship getCurrentShip() {
        return currentShip;
    }

    public void placeShip(GridButton gridButton) {
        int x, y;
        Coordinate coordinate;
        if (this.currentShip.getOrientation() == Orientation.HORIZONTAL) {
            for (int i = 0; i < this.currentShip.getSize(); i++) {
                x = gridButton.getRow();
                y = gridButton.getColumn();
                if (x >= 0 && x < Grid.GRID_SIZE && (y + i) >= 0 && (y + i) < Grid.GRID_SIZE) {
                    this.playerOneButtons[x][y + i].setText("-");
                    this.playerOneButtons[x][y + i].setEnabled(false);
                    this.playerOneButtons[x][y + i].setOccupied(true);
                }
            }
        } else {
            for (int i = 0; i < this.currentShip.getSize(); i++) {
                x = gridButton.getRow();
                y = gridButton.getColumn();
                if ((x + i) >= 0 && (x + i) < Grid.GRID_SIZE && y >= 0 && y < Grid.GRID_SIZE) {
                    this.playerOneButtons[x + i][y].setText("-");
                    this.playerOneButtons[x + i][y].setEnabled(false);
                    this.playerOneButtons[x + i][y].setOccupied(true);
                }
            }
        } // TODO can be refactored

        this.shipButtons[this.currentShip.getNumber()].setEnabled(false);
        this.currentShip = null;
        this.setPlayerButtons(true, false);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        playerOne = new JPanel();
        playerOne.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(playerOne, gbc);
        playerOneGrid = new JPanel();
        playerOneGrid.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 5.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        playerOne.add(playerOneGrid, gbc);
        playerOneMenu = new JPanel();
        playerOneMenu.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        playerOne.add(playerOneMenu, gbc);
        shipButton0P1 = new JButton();
        shipButton0P1.setText("Carrier");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerOneMenu.add(shipButton0P1, gbc);
        shipButton1P1 = new JButton();
        shipButton1P1.setText("Battleship");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerOneMenu.add(shipButton1P1, gbc);
        shipButton2P1 = new JButton();
        shipButton2P1.setText("Cruiser");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerOneMenu.add(shipButton2P1, gbc);
        shipButton3P1 = new JButton();
        shipButton3P1.setText("Submarine");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerOneMenu.add(shipButton3P1, gbc);
        shipButton4P1 = new JButton();
        shipButton4P1.setText("Destroyer");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerOneMenu.add(shipButton4P1, gbc);
        rotateButton = new JButton();
        rotateButton.setText("Rotate");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerOneMenu.add(rotateButton, gbc);
        playerTwo = new JPanel();
        playerTwo.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(playerTwo, gbc);
        playerTwoGrid = new JPanel();
        playerTwoGrid.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 5.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        playerTwo.add(playerTwoGrid, gbc);
        playerTwoMenu = new JPanel();
        playerTwoMenu.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        playerTwo.add(playerTwoMenu, gbc);
        shipButton0P2 = new JButton();
        shipButton0P2.setEnabled(false);
        shipButton0P2.setText("Carrier");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerTwoMenu.add(shipButton0P2, gbc);
        shipButton1P2 = new JButton();
        shipButton1P2.setEnabled(false);
        shipButton1P2.setText("Battleship");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerTwoMenu.add(shipButton1P2, gbc);
        shipButton2P2 = new JButton();
        shipButton2P2.setEnabled(false);
        shipButton2P2.setText("Cruiser");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerTwoMenu.add(shipButton2P2, gbc);
        shipButton3P2 = new JButton();
        shipButton3P2.setEnabled(false);
        shipButton3P2.setText("Submarine");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerTwoMenu.add(shipButton3P2, gbc);
        shipButton4P2 = new JButton();
        shipButton4P2.setEnabled(false);
        shipButton4P2.setText("Destroyer");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        playerTwoMenu.add(shipButton4P2, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
