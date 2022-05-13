import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for the Card Game Graphical User Interface
 *
 * @author Kuan Jia Hong
 *
 */
public class CardGameGUI {
    // Swing components
    JFrame frame;
    JLabel dealerCard1, dealerCard2, dealerCard3;
    JLabel playerCard1, playerCard2, playerCard3;
    JButton btnReplaceCard1, btnReplaceCard2, btnReplaceCard3;
    JButton startButton, resultButton;
    JLabel betLabel, infoLabel, moneyLabel;
    JTextField inputBetText;
    ImageIcon dealerCardImage1, dealerCardImage2, dealerCardImage3;
    ImageIcon playerCardImage1, playerCardImage2, playerCardImage3;
    JPanel MainPanel, DealerPanel, PlayerPanel;
    JPanel ReplaceCardPanel, ButtonPanel, InfoPanel;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;

    // variable to represent the game state
    int budget = 100;
    int betAmount = 0;
    int replaceCount = 0;

    ArrayList<String> playerCards = new ArrayList<>();
    ArrayList<String> dealerCards = new ArrayList<>();
    ArrayList<String> deckOfCards = new ArrayList<>();


    public static void main(String[] args) {
        CardGameGUI gui = new CardGameGUI();
        gui.run();
    }

    /**
     * Create all the components for the card game
     */
    public void createComponent() {

        dealerCard1 = new JLabel();
        dealerCard2 = new JLabel();
        dealerCard3 = new JLabel();
        playerCard1 = new JLabel();
        playerCard2 = new JLabel();
        playerCard3 = new JLabel();

        btnReplaceCard1 = new JButton("Replace Card 1");
        btnReplaceCard2 = new JButton("Replace Card 2");
        btnReplaceCard3 = new JButton("Replace Card 3");

        startButton = new JButton("Start");
        resultButton = new JButton("Result");
        betLabel = new JLabel("Bet: $");

        infoLabel = new JLabel();
        moneyLabel = new JLabel();

        inputBetText = new JTextField(10);

        hideCard();

        MainPanel = new JPanel();
        DealerPanel = new JPanel();
        PlayerPanel = new JPanel();
        ReplaceCardPanel = new JPanel();
        ButtonPanel = new JPanel();
        InfoPanel = new JPanel();

        JLabel dealerTitle = new JLabel("Dealer");
        DealerPanel.add(dealerTitle);
        DealerPanel.add(dealerCard1);
        DealerPanel.add(dealerCard2);
        DealerPanel.add(dealerCard3);

        JLabel playerTitle = new JLabel("Player");
        PlayerPanel.add(playerTitle);
        PlayerPanel.add(playerCard1);
        PlayerPanel.add(playerCard2);
        PlayerPanel.add(playerCard3);

        ReplaceCardPanel.add(btnReplaceCard1);
        ReplaceCardPanel.add(btnReplaceCard2);
        ReplaceCardPanel.add(btnReplaceCard3);

        ButtonPanel.add(betLabel);
        ButtonPanel.add(inputBetText);
        ButtonPanel.add(startButton);
        ButtonPanel.add(resultButton);

        InfoPanel.add(infoLabel);
        InfoPanel.add(moneyLabel);

        MainPanel.setLayout(new GridLayout(5,1));

        MainPanel.add(DealerPanel);
        MainPanel.add(PlayerPanel);
        MainPanel.add(ReplaceCardPanel);
        MainPanel.add(ButtonPanel);
        MainPanel.add(InfoPanel);

        // Setting the background color
        DealerPanel.setBackground(Color.green);
        PlayerPanel.setBackground(Color.orange);
        ReplaceCardPanel.setBackground(Color.cyan);
        ButtonPanel.setBackground(Color.magenta);
        InfoPanel.setBackground(Color.pink);

        // Setting up the menu bar
        menuBar = new JMenuBar();
        menu = new JMenu("Control");
        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);
        menuBar.add(menu);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(MainPanel);
        frame.setTitle("A Simple Card Game");
        frame.setSize(400,700);
        frame.setVisible(true);

        // result button is disabled in the beginning
        resultButton.setEnabled(false);
        btnReplaceCard1.setEnabled(false);
        btnReplaceCard2.setEnabled(false);
        btnReplaceCard3.setEnabled(false);

        // Implement event listener
        menuItem.addActionListener(new MenuItemListener());
        startButton.addActionListener(new StartButtonListener());
        resultButton.addActionListener(new ResultButtonListener());
        btnReplaceCard1.addActionListener(new ButtonReplaceCard1());
        btnReplaceCard2.addActionListener(new ButtonReplaceCard2());
        btnReplaceCard3.addActionListener(new ButtonReplaceCard3());



    }

    /**
     * Event handler for the menu item.
     */
    class MenuItemListener implements ActionListener {

        /**
         * Exit the system user click on the menu item
         * @param event mouseclick on menu item
         */
        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    /**
     * Event handler for start button
     */
    class StartButtonListener implements ActionListener{

        /**
         * Retrieve the bet amount from the text field and update
         * the bet amount. The start button will be disabled
         * if the bet amount is valid (i.e. more than zero
         * and not exceeding player's budget)。The result button and all 3 replace buttons
         * will be enabled after the start button is disabled.
         * @param event mouseclick event
         */
        public void actionPerformed(ActionEvent event) {
            try {
                betAmount = Integer.parseInt(inputBetText.getText());
                if (betAmount > 0 && betAmount <= budget) {
                    System.out.println("Start Button disabled");
                    startButton.setEnabled(false);
                    btnReplaceCard1.setEnabled(true);
                    btnReplaceCard2.setEnabled(true);
                    btnReplaceCard3.setEnabled(true);
                    resultButton.setEnabled(true);

                    infoLabel.setText("Your current bet is: $" + betAmount);
                    moneyLabel.setText("Amount of money you have: $" + budget);
                    populateCards();
                    Collections.shuffle(deckOfCards);
                    playerCards = distributeCards();
                    dealerCards = distributeCards();

                    // Set dealer card with their corresponding image
                    dealerCardImage1 = new ImageIcon(getClass().getResource("images/" + dealerCards.get(0)));
                    dealerCardImage2 = new ImageIcon(getClass().getResource("images/" + dealerCards.get(1)));
                    dealerCardImage3 = new ImageIcon(getClass().getResource("images/" + dealerCards.get(2)));

                    // Set player card with their corresponding image
                    playerCardImage1 = new ImageIcon(getClass().getResource("images/" + playerCards.get(0)));
                    playerCardImage2 = new ImageIcon(getClass().getResource("images/" + playerCards.get(1)));
                    playerCardImage3 = new ImageIcon(getClass().getResource("images/" + playerCards.get(2)));


                    playerCard1.setIcon(playerCardImage1);
                    playerCard2.setIcon(playerCardImage2);
                    playerCard3.setIcon(playerCardImage3);


                    // Debug
                    System.out.print("Dealer: ");
                    for (String i: dealerCards) {
                        System.out.print(i);
                        System.out.print(" ");
                    }
                    System.out.println();

                    System.out.print("Player: ");
                    for (String i: playerCards) {
                        System.out.print(i);
                        System.out.print(" ");
                    }
                    System.out.println();



                } else {
                    System.out.println("Bet amount is invalid");
                    JOptionPane.showMessageDialog(frame,"Your bet amount is invalid");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame,"Please input a positive integer number.");
                System.out.println("Invalid text detected");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame,"An error occurred");
                System.out.println("Error: " + e);
            }
        }
    }

    /**
     * Event handler for result button
     */
    class ResultButtonListener implements ActionListener {

        /**
         * Show the result of the game. The game will reset if player still has money.
         * If player hos no money, the player will not be able to continue the game.
         * @param event mouse click event
         */
        public void actionPerformed(ActionEvent event) {
            // Reveal dealer's cards
            dealerCard1.setIcon(dealerCardImage1);
            dealerCard2.setIcon(dealerCardImage2);
            dealerCard3.setIcon(dealerCardImage3);

            boolean win = checkWin();

            if (win) {
                budget += betAmount;
                infoLabel.setText("You won $" + betAmount + "!");
                moneyLabel.setText("Amount of money you have: $" + budget);
                JOptionPane.showMessageDialog(frame, "Congratulations! You win this round!");
                System.out.println("Budget is now $" + budget);
            } else {
                budget -= betAmount;
                infoLabel.setText("You lose $" + betAmount + "!");
                moneyLabel.setText("Amount of money you have: $" + budget);
                System.out.println("Budget is now $" + budget);
                JOptionPane.showMessageDialog(frame, "Sorry! The Dealer wins this round!");
            }

            if (budget > 0) {
                System.out.println("Resetting...");
                resultButton.setEnabled(false);
                startButton.setEnabled(true);
                disableAllReplaceButton();
                hideCard();
                betAmount = 0;
                replaceCount = 0;
                playerCards.clear();
                dealerCards.clear();
                deckOfCards.clear();
                inputBetText.setText("");
                infoLabel.setText("Please place your bet!");
                moneyLabel.setText("Amount of money you have is $"+ budget);
                System.out.println("Resetting finished.");
            } else {
                infoLabel.setText("You have no more money!");
                moneyLabel.setText("Please start a new game!");
                JOptionPane.showMessageDialog(frame, "Game over! You have no more money! " +
                                               "Please start a new game");
                resultButton.setEnabled(false);
                disableAllReplaceButton();
            }


        }
    }

    /**
     * Event handler for button replace card one
     */
    class ButtonReplaceCard1 implements ActionListener{
        /**
         * Replace player's first card.
         * @param event mouse click event
         */
        public void actionPerformed(ActionEvent event) {
            if (replaceCount < 2) {
                btnReplaceCard1.setEnabled(false);
                String newCardFileName = deckOfCards.get(0);
                deckOfCards.remove(0);
                System.out.println("Deck of card now: " + deckOfCards.size());
                playerCards.set(0, newCardFileName);
                playerCardImage1 = new ImageIcon(getClass().getResource("images/" + playerCards.get(0)));
                playerCard1.setIcon(playerCardImage1);
                replaceCount++;

                if (replaceCount > 1) {
                    System.out.println("Disabling... the replace count: " + replaceCount);
                    disableAllReplaceButton();
                }


                // Debug
                System.out.print("Player replace first card: ");
                for (String card: playerCards) {
                    System.out.print(card);
                    System.out.print(" ");
                }
                System.out.println();

            } else {
                System.out.println("Replace count: " + replaceCount);
                System.out.println("You exceeded replacement count");
            }
        }
    }

    /**
     * Event handler for button replace card 2
     */
    class ButtonReplaceCard2 implements ActionListener{
        /**
         * Replace player's second card.
         * @param event mouse click event
         */
        public void actionPerformed(ActionEvent event) {
            if (replaceCount < 2) {
                btnReplaceCard2.setEnabled(false);
                String newCardFileName = deckOfCards.get(0);
                deckOfCards.remove(0);
                System.out.println("Deck of card now: " + deckOfCards.size());
                playerCards.set(1, newCardFileName);
                playerCardImage2 = new ImageIcon(getClass().getResource("images/" + playerCards.get(1)));
                playerCard2.setIcon(playerCardImage2);
                replaceCount++;

                if (replaceCount > 1) {
                    System.out.println("Disabling... the replace count: " + replaceCount);
                    disableAllReplaceButton();
                }


                // Debug
                System.out.print("Player replace second card: ");
                for (String card: playerCards) {
                    System.out.print(card);
                    System.out.print(" ");
                }
                System.out.println();

            } else {
                System.out.println("Replace count: " + replaceCount);
                System.out.println("You exceeded replacement count");
            }
        }
    }

    /**
     * Event handler for button replace card 3
     */
    class ButtonReplaceCard3 implements ActionListener{
        /**
         * Replace player's third card
         * @param event mouse click event
         */
        public void actionPerformed(ActionEvent event) {
            if (replaceCount < 2) {
                btnReplaceCard3.setEnabled(false);
                String newCardFileName = deckOfCards.get(0);
                deckOfCards.remove(0);
                System.out.println("Deck of card now: " + deckOfCards.size());
                playerCards.set(2, newCardFileName);
                playerCardImage3 = new ImageIcon(getClass().getResource("images/" + playerCards.get(2)));
                playerCard3.setIcon(playerCardImage3);
                replaceCount++;

                if (replaceCount > 1) {
                    System.out.println("Disabling... the replace count: " + replaceCount);
                    disableAllReplaceButton();
                }


                // Debug
                System.out.print("Player replace third card: ");
                for (String card: playerCards) {
                    System.out.print(card);
                    System.out.print(" ");
                }
                System.out.println();

            } else {
                System.out.println("Replace count: " + replaceCount);
                System.out.println("You exceeded replacement count");
            }
        }
    }

    /**
     * Hide all the card away from letting it be viewed by player
     */
    public void hideCard()  {
        dealerCardImage1 = new ImageIcon(getClass().getResource("images/card_back.gif"));
        dealerCardImage2 = new ImageIcon(getClass().getResource("images/card_back.gif"));
        dealerCardImage3 = new ImageIcon(getClass().getResource("images/card_back.gif"));

        playerCardImage1 = new ImageIcon(getClass().getResource("images/card_back.gif"));
        playerCardImage2 = new ImageIcon(getClass().getResource("images/card_back.gif"));
        playerCardImage3 = new ImageIcon(getClass().getResource("images/card_back.gif"));

        dealerCard1.setIcon(dealerCardImage1);
        dealerCard2.setIcon(dealerCardImage2);
        dealerCard3.setIcon(dealerCardImage3);

        playerCard1.setIcon(playerCardImage1);
        playerCard1.setIcon(playerCardImage1);
        playerCard2.setIcon(playerCardImage2);
        playerCard3.setIcon(playerCardImage3);
    }

    /**
     * A method to disable all replace button
     */
    public void disableAllReplaceButton() {
        btnReplaceCard1.setEnabled(false);
        btnReplaceCard2.setEnabled(false);
        btnReplaceCard3.setEnabled(false);
    }

    /**
     * Populate 52 cards with their
     * corresponding image file name
     */
    public void populateCards() {
        System.out.println("Populate card...");
        deckOfCards.clear();
        ArrayList<String> shapes = new ArrayList<>();
        shapes.add("1"); // clubs
        shapes.add("2"); // spades
        shapes.add("3"); // diamonds
        shapes.add("4"); // hearts

        ArrayList<String> value = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            value.add(Integer.toString(i));
        }

        for (String shape : shapes) {
            for (String s : value) {
                String imageName = "card_" + shape + s + ".gif";
                deckOfCards.add(imageName);
            }
        }

        System.out.println("Deck of card size: " + deckOfCards.size());

    }

    /**
     * A method to distribute three cards for player
     * @return An ArrayList containing three cards' image file name for the player
     */
    public ArrayList<String> distributeCards() {
        System.out.println("Card distributed");
        ArrayList<String> card = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            card.add(deckOfCards.get(i));
        }

        deckOfCards.subList(0, 3).clear();

        System.out.println("Deck of card now: "+ deckOfCards.size());

        return card;

    }

    /**
     * A method to slice off the image file name and get the value of the card from
     * the file name
     * @param cardDeck an ArrayList containing the image file name of the cards
     * @return an ArrayList containing the integer value of the cards.
     */
    public ArrayList<Integer> filterCardFileName (ArrayList<String> cardDeck) {
        ArrayList<Integer> cardValue = new ArrayList<>();
        for (String cardFileName: cardDeck) {
            // E.g. card_31.gif will become 1.gif or card_411.gif will become 11.gif
            String sliceString = cardFileName.substring(6);
            // 1.gif will become 1 or 11.gif will become 11
            sliceString = sliceString.substring(0, sliceString.length()-4);
            int value = Integer.parseInt(sliceString);
            cardValue.add(value);
        }
        return cardValue;

    }

    /**
     * Method to check if the player wins or lose
     * @return true if player win. false if player lose
     */
    public boolean checkWin() {
        ArrayList<Integer> playerCardValue = filterCardFileName(playerCards);
        ArrayList<Integer> dealerCardValue = filterCardFileName(dealerCards);

        // debug
        System.out.print("Dealer: ");
        for (int i = 0; i < 3; i++) {
            System.out.print(dealerCardValue.get(i));
            System.out.print(" ");
        }
        System.out.println();

        System.out.print("Player: ");
        for (int i = 0; i < 3; i++) {
            System.out.print(playerCardValue.get(i));
            System.out.print(" ");
        }
        System.out.println();

        // Rule 1: The one with more special cards wins
        int dealerSpecialCardCount = 0;
        int playerSpecialCardCount = 0;
        for (int i = 0; i < 3; i++) {
            if (playerCardValue.get(i) > 10) {
                playerSpecialCardCount++;
            }
            if (dealerCardValue.get(i) > 10) {
                dealerSpecialCardCount++;
            }
        }

        System.out.println("Checking rule one...");
        if (playerSpecialCardCount > dealerSpecialCardCount) {
            System.out.println("Player has more special card than dealer");
            System.out.println("Player win");
            return true;
        } else if (dealerSpecialCardCount > playerSpecialCardCount) {
            System.out.println("Dealer has more special card then player");
            System.out.println("Player lose");
            return false;
        }

        System.out.println("No outcome for rule one");
        System.out.println("Checking rule two...");

        // Rule 2: Add the face values of other cards and divide it by 10
        int playerCardSum = 0;
        int dealerCardSum = 0;
        for (int i = 0; i < 3; i++) {
            if (playerCardValue.get(i) <= 10) {
                playerCardSum += playerCardValue.get(i);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (dealerCardValue.get(i) <= 10) {
                dealerCardSum += dealerCardValue.get(i);
            }
        }

        if (playerCardSum % 10 > dealerCardSum % 10) {
            System.out.println("Player has higher face value than dealer");
            System.out.println("Player win");
            return true;
        } else if (dealerCardSum % 10 > playerCardSum % 10) {
            System.out.println("Dealer has higher face value than player");
            System.out.println("Player lose");
            return false;
        } else {
            System.out.println("No outcome for rule two.");
            System.out.println("Player lose as a result");
            return false;
        }


    }


    /**
     * A method to start the game
     */
    public void run() {
        createComponent();
        infoLabel.setText("Please place your bet!");
        moneyLabel.setText("Amount of money you have is $"+ budget);
    }


}