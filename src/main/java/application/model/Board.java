package application.model;

import application.model.cards.Card;
import application.model.cards.MoneyCard;
import application.model.squares.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Board {

    private Queue<Card> chanceCards = new LinkedList<>();
    private Queue<Card> chestCards = new LinkedList<>();

    private final ArrayList<Square> squares = new ArrayList<>();

    private final ArrayList<PropertySquare> brownGroup = new ArrayList<>();
    private final ArrayList<PropertySquare> lightBlueGroup = new ArrayList<>();
    private final ArrayList<PropertySquare> pinkGroup = new ArrayList<>();
    private final ArrayList<PropertySquare> orangeGroup = new ArrayList<>();
    private final ArrayList<PropertySquare> redGroup = new ArrayList<>();
    private final ArrayList<PropertySquare> yellowGroup = new ArrayList<>();
    private final ArrayList<PropertySquare> greenGroup = new ArrayList<>();
    private final ArrayList<PropertySquare> blueGroup = new ArrayList<>();

    private static final int railwayPrice = 250;
    private static final int railwayRent = 25;
    private static final int energyPrice = 150;
    private static final int energyRent = 4;

    private static final int jailPosition = 10;

    public Board(){
        createBoardSquares();
        groupProperties();
        createCards();
    }

    public Card getChanceCard(){
        Card card = chanceCards.poll();
        chanceCards.add(card);
        return card;
    }

    public  Card getCommunityChestCard(){
        Card card = chestCards.poll();
        chestCards.add(card);
        return card;
    }

    private void createCards() {
        // Create community chest cards
        //Advance to "Go". (Collect $200)
        chestCards.add(new MoneyCard("Bank error in your favor. Collect $200. ",200 ));
        chestCards.add(new MoneyCard("Doctor's fees. Pay $50.",-50 ));
        chestCards.add(new MoneyCard("From sale of stock you get $50.",50 ));
        //Get Out of Jail Free.
        //Go to Jail
        //Grand Opera Night {Opening in previous US editions, not in the deck in UK editions}. Collect $50 from every player for opening night seats
        chestCards.add(new MoneyCard("Holiday Fund matures. Receive $100.",100 ));
        chestCards.add(new MoneyCard("Income tax refund. Collect $20. ",20 ));
        //It is your birthday. Collect $10 from every player.
        chestCards.add(new MoneyCard("Life insurance matures – Collect $100 ",100 ));
        chestCards.add(new MoneyCard("Hospital Fees. Pay $50. ",-50 ));
        chestCards.add(new MoneyCard("School fees. Pay $50.",-50 ));
        chestCards.add(new MoneyCard("Receive $25 consultancy fee.",50 ));
        //You are assessed for street repairs: Pay $40 per house and $115 per hotel you own.
        chestCards.add(new MoneyCard("You have won second prize in a beauty contest. Collect $10.",10 ));
        chestCards.add(new MoneyCard("You inherit $100. ",100 ));


        //Create chance Cards
        //Advance to "Go". (Collect $200)
        //Advance to Illinois Ave. {Avenue}. If you pass Go, collect $200.
        //Advance to St. Charles Place. If you pass Go, collect $200.
        //Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total 10 times the amount thrown.
        //Advance token to the nearest Railroad and pay owner twice the rental to which he/she {he} is otherwise entitled. If Railroad is unowned, you may buy it from the Bank.
        chanceCards.add(new MoneyCard("Bank pays you dividend of $50.", 50));
        //Get out of Jail Free. This card may be kept until needed, or traded/sold.
        //Go Back 3 Spaces.
        //Go to Jail. Go directly to Jail. Do not pass GO, do not collect $200.
        //Make general repairs on all your property: For each house pay $25, For each hotel {pay} $100.

        chanceCards.add(new MoneyCard("Pay poor tax of $15", -15));
        //Take a trip to Reading Railroad. If you pass Go, collect $200.
        //Take a walk on the Boardwalk. Advance token to Boardwalk.
        //You have been elected Chairman of the Board. Pay each player $50.
        chanceCards.add(new MoneyCard("Your building loan matures. Receive $150.", 150));
        chanceCards.add(new MoneyCard("You have won a crossword competition. Collect $100.", 100));

    }

    private void createBoardSquares() {

        squares.add(new GoSquare("Go", 200));
        squares.add(new PropertySquare("Old Kent Road",
                60,
                2, 10, 30, 90, 160, 250,
                50));
        squares.add(new CommunityChestSquare("Community Chest"));
        squares.add(new PropertySquare("Whitechapel Road",
                60,
                4, 20, 60, 180, 320, 450,
                50));
        squares.add(new TaxSquare("Income Tax", 200));
        squares.add(new RailwaySquare("Kings Cross Station",railwayPrice, railwayRent));
        squares.add(new PropertySquare("The Angel Islington",
                100,
                6, 30, 90, 270, 400, 550,
                50));
        squares.add(new ChanceSquare("Chance"));
        squares.add(new PropertySquare("Euston Road",
                100,
                6, 30, 90, 270, 400, 550,
                50));
        squares.add(new PropertySquare("Pentonville Road",
                120,
                8, 40, 100, 300, 450, 600,
                50));
        squares.add(new JailSquare("Jail"));
        squares.add(new PropertySquare("Pall Mall",
                140,
                10, 50, 150, 450, 625, 750,
                100));
        squares.add(new EnergySquare("Electric Company",energyPrice,energyRent));
        squares.add(new PropertySquare("Whitehall",
                140,
                10, 50, 150, 450, 625, 750,
                100));
        squares.add(new PropertySquare("Northumberland Avenue",
                160,
                12, 60, 180, 500, 700, 900,
                100));
        squares.add(new RailwaySquare("Marylebone Station",railwayPrice, railwayRent));
        squares.add(new PropertySquare("Bow Street",
                180,
                14, 70, 200, 550, 750, 950,
                100));
        squares.add(new CommunityChestSquare("Community Chest"));
        squares.add(new PropertySquare("Marlborough Street",
                180,
                14, 70, 200, 550, 750, 950,
                100));
        squares.add(new PropertySquare("Vine Street",
                200,
                16, 80, 220, 600, 800, 1000,
                100));
        squares.add(new FreeParkingSquare("Free Parking"));
        squares.add(new PropertySquare("Strand",
                220,
                18, 90, 250, 700, 875, 1050,
                150));
        squares.add(new ChanceSquare("Chance"));
        squares.add(new PropertySquare("Fleet Street",
                220,
                18, 90, 250, 700, 875, 1050,
                150));
        squares.add(new PropertySquare("Trafalgar Square",
                240,
                20, 100, 300, 750, 925, 1100,
                150));
        squares.add(new RailwaySquare("Fenchurch St. Station",railwayPrice, railwayRent));
        squares.add(new PropertySquare("Leicester Square",
                260,
                22, 110, 330, 800, 975, 1150,
                150));
        squares.add(new PropertySquare("Coventry Street",
                260,
                22, 110, 330, 800, 975, 1150,
                150));
        squares.add(new EnergySquare("Waterworks",energyPrice,energyRent));
        squares.add(new PropertySquare("Piccadilly",
                280,
                24, 120, 360, 850, 1025, 1200,
                150));
        squares.add(new GoToJailSquare("Go To Jail"));
        squares.add(new PropertySquare("Regent Street",
                300,
                26, 130, 390, 900, 1100, 1275,
                200));
        squares.add(new PropertySquare("Oxford Street",
                300,
                26, 130, 390, 900, 1100, 1275,
                200));
        squares.add(new CommunityChestSquare("Community Chest"));
        squares.add(new PropertySquare("Bond Street",
                320,
                28, 150, 450, 1000, 1200, 1400,
                200));
        squares.add(new RailwaySquare("Liverpool St. Station",railwayPrice, railwayRent));
        squares.add(new ChanceSquare("Chance"));
        squares.add(new PropertySquare("Park Lane",
                350,
                35, 175, 500, 1100, 1300, 1500,
                200));
        squares.add(new TaxSquare("Super Tax", 100));
        squares.add(new PropertySquare("Mayfair",
                400,
                50, 200, 600, 1400, 1700, 2000,
                200));

    }

    private void groupProperties(){
        brownGroup.add((PropertySquare) squares.get(1));
        brownGroup.add((PropertySquare) squares.get(3));

        lightBlueGroup.add((PropertySquare) squares.get(6));
        lightBlueGroup.add((PropertySquare) squares.get(8));
        lightBlueGroup.add((PropertySquare) squares.get(9));

        pinkGroup.add((PropertySquare) squares.get(11));
        pinkGroup.add((PropertySquare) squares.get(13));
        pinkGroup.add((PropertySquare) squares.get(14));

        orangeGroup.add((PropertySquare) squares.get(16));
        orangeGroup.add((PropertySquare) squares.get(18));
        orangeGroup.add((PropertySquare) squares.get(19));

        redGroup.add((PropertySquare) squares.get(21));
        redGroup.add((PropertySquare) squares.get(23));
        redGroup.add((PropertySquare) squares.get(24));

        yellowGroup.add((PropertySquare) squares.get(26));
        yellowGroup.add((PropertySquare) squares.get(27));
        yellowGroup.add((PropertySquare) squares.get(29));

        greenGroup.add((PropertySquare) squares.get(31));
        greenGroup.add((PropertySquare) squares.get(32));
        greenGroup.add((PropertySquare) squares.get(34));

        blueGroup.add((PropertySquare) squares.get(37));
        blueGroup.add((PropertySquare) squares.get(39));
    }

    public ArrayList<PropertySquare> getGroupByProperty(PropertySquare property) {
        if(brownGroup.contains(property)) return brownGroup;
        if(lightBlueGroup.contains(property)) return lightBlueGroup;
        if(pinkGroup.contains(property)) return pinkGroup;
        if(orangeGroup.contains(property)) return orangeGroup;
        if(redGroup.contains(property)) return redGroup;
        if(yellowGroup.contains(property)) return yellowGroup;
        if(greenGroup.contains(property)) return greenGroup;
        if(blueGroup.contains(property)) return blueGroup;
        else return null;
    }

    public Square getSquareByPosition(int position) {
        if(position >= 0 && position <= squares.size())
            return squares.get(position);
        else
            return null;
    }

    public int getPositionBySquare(Square square){
        for (int i = 0; i < squares.size(); i++) {
            if (squares.get(i).name.equals(square.name)){
                return i;
            }
        }
        return -1;
    }

    public static int getJailPosition(){
        return jailPosition;
    }

    public Square getJailSquare() {
        return squares.get(getJailPosition());
    }

    public ArrayList<Square> getSquares() {
        return squares;
    }

    public List<Square> getSquaresFromTo(int from, int to) {

        if (from >= 0) {
            return squares.subList(from,to+1);
        } else {
            from += GameOptions.SQUARES_COUNT;
            List<Square> squareList = new ArrayList<>(squares.subList(from,squares.size()));
            squareList.addAll(squares.subList(0,to+1));

            return squareList;
        }
    }

}
