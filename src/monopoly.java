import java.util.*;

public class monopoly {

    gameboard myGameBoard = new gameboard();
    banker Banker = new banker(0,100000);
    player Player1 = new player(1,15000);
    player Player2 = new player(2,15000);

    public void addProperty(Iterator properties,int typeNum){
        String[] propertyArray= new String[3];

        while(properties.hasNext()) {
            String pro =properties.next().toString();
            //System.out.println(pro);
            String[] twopiece = pro.split("=",2);
            if (twopiece[0].equalsIgnoreCase("cost")){
                propertyArray[2] = (twopiece[1]);
            }
            if(twopiece[0].equalsIgnoreCase("name")){
                propertyArray[1] = twopiece[1];
            }
            if(twopiece[0].equalsIgnoreCase("id")){
                propertyArray[0] =(twopiece[1]);
            }
        }
        myGameBoard.addProperty(propertyArray,typeNum);
        //System.out.println(propertyArray[0]+propertyArray[1]);
    }

    public void addChanceCard(String chanceCard){
        String[] twopieces = chanceCard.split("=",2);
        myGameBoard.addChanceCards(twopieces[1]);
    }

    public void addCommunityCard(String communityCard){
        String[] twopieces = communityCard.split("=",2);
        myGameBoard.addCommunityChestCards(twopieces[1]);
    }

    public void squares(){
        myGameBoard.squares();
    }

    public boolean command(String command){
        if(Player1.bankrupt()==true){
            return false;
        }
        else if(Player2.bankrupt()==true){
            return false;
        }

        else if(command.equalsIgnoreCase("show()")){
            System.out.println("--------------------------------------------------------------------");
            System.out.printf("Player 1\t%d\thave: ",Player1.getMoney());
            for(int i=0;i<Player1.getProperties().length;i++){
                if(Player1.getProperties()[i]==null){
                    continue;
                }
                System.out.printf("%s, ",Player1.getProperties()[i]);
            }
            System.out.printf("\nPlayer 2\t%d\thave: ",Player2.getMoney());
            for(int i=0;i<Player2.getProperties().length;i++){
                if(Player2.getProperties()[i]==null){
                    continue;
                }
                System.out.printf("%s, ",Player2.getProperties()[i]);
            }
            System.out.printf("\nBanker\t%d\n",Banker.getMoney());
            if(Player1.getBeforeBankrupt()>Player2.getBeforeBankrupt()){
                System.out.printf("Winner\tPlayer 1\n");
            }
            else if(Player1.getBeforeBankrupt()<Player2.getBeforeBankrupt()){
                System.out.printf("Winner\tPlayer 2\n");
            }
            else {
                System.out.println("Scorless");
            }

            System.out.println("--------------------------------------------------------------------");

            return true;
        }
        else{
            String[] commmandStr = new String[2];
            commmandStr = command.split(";",2);
            int dice = Integer.parseInt(commmandStr[1]);
            if(myGameBoard.play(commmandStr[0],dice,Player1,Player2,Banker,true)==false){return false;}
            return true;
        }
    }

    public void bankrupt(String command){
        String[] commmandStr = new String[2];
        commmandStr = command.split(";",2);
        int dice = Integer.parseInt(commmandStr[1]);
        //System.out.println(commmandStr[0]);
        if(commmandStr[0].equalsIgnoreCase("Player 1")){
            System.out.printf("Player 1\t%d\t%d\t%d\t%d\t",dice,(Player1.getWhereNow()+1),Player1.getBeforeBankrupt(),Player2.getBeforeBankrupt());
            System.out.printf("Player 1 goes bankrupt \n");
        }
        else{
            System.out.printf("Player 2\t%d\t%d\t%d\t%d\t",dice,(Player2.getWhereNow()+1),Player1.getBeforeBankrupt(),Player2.getBeforeBankrupt());
            System.out.printf("Player 2 goes bankrupt \n");
        }
    }

    public void show(){
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("Player 1\t%d\thave: ",Player1.getBeforeBankrupt());
        for(int i=0;i<Player1.getProperties().length;i++){
            if(Player1.getProperties()[i]==null){
                continue;
            }
            System.out.printf("%s, ",Player1.getProperties()[i]);
        }
        System.out.printf("\nPlayer 2\t%d\thave: ",Player2.getBeforeBankrupt());
        for(int i=0;i<Player2.getProperties().length;i++){
            if(Player2.getProperties()[i]==null){
                continue;
            }
            System.out.printf("%s, ",Player2.getProperties()[i]);
        }
        int bankMoney= 130000 -(Player1.getBeforeBankrupt())-(Player2.getBeforeBankrupt());
        System.out.printf("\nBanker\t%d\n",bankMoney);
        if(Player1.getBeforeBankrupt()>Player2.getBeforeBankrupt()){
            System.out.printf("Winner\tPlayer 1\n");
        }
        else if(Player1.getBeforeBankrupt()<Player2.getBeforeBankrupt()){
            System.out.printf("Winner\tPlayer 2\n");
        }
        else {
            System.out.println("Scorless");
        }

        System.out.println("--------------------------------------------------------------------");

    }


}
