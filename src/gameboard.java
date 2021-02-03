
public class gameboard {
    public square[] squaresOfBoard = new square[40];
    private chanceCards[] chanceCards = new chanceCards[6];
    private communityChestCards[] communityChestCards = new communityChestCards[11];
    private int whereCommunityChest=0;
    private int whereChance=0 ;


    public void addProperty(String[] propertyArray,int typeNum){
        String name = propertyArray[1];
        int id=Integer.parseInt(propertyArray[0]),cost=Integer.parseInt(propertyArray[2]);

        if (typeNum==1){
            squaresOfBoard[id-1]= new land(id,name,cost);
        }
        else if(typeNum==2){
            squaresOfBoard[id-1]= new railroad(id,name,cost);
        }
        else {
            squaresOfBoard[id-1]= new community(id,name,cost);
        }

    }

    public void addChanceCards(String content){
        for(int i=0;i<6;i++){
            if(chanceCards[i]==null){
                chanceCards[i]= new chanceCards(40,content);
                return;
            }
        }
    }

    public void addCommunityChestCards(String content){
        for(int i=0;i<11;i++){
            if(communityChestCards[i]==null){
                communityChestCards[i]= new communityChestCards(41,content);
                return;
            }
        }
    }

    public void squares(){

        squaresOfBoard[2] = new communityChestCards(2);
        squaresOfBoard[7] = new chanceCards(7);
        squaresOfBoard[17] = new communityChestCards(17);
        squaresOfBoard[22] = new chanceCards(22);
        squaresOfBoard[33] = new communityChestCards(33);
        squaresOfBoard[36] = new chanceCards(36);
        squaresOfBoard[0] = new go(0);
        squaresOfBoard[4] = new tax(4);
        squaresOfBoard[10] = new jail(10);
        squaresOfBoard[20] = new freeParking(20);
        squaresOfBoard[30] = new goToJail(30);
        squaresOfBoard[38] = new tax(38);

    }

    public boolean play(String playerNo,int dice,player player1,player player2,banker banker,boolean print){
        if(playerNo.equalsIgnoreCase("Player 1")){
            if(player1.getJailCount()!=0){
                System.out.printf("Player 1\t%d\t%d\t%d\t%d\tPlayer 1 in jail (count=%d)\n",dice,(player1.getWhereNow()+1),player1.getMoney(),player2.getMoney(),player1.getJailCount());
                player1.setJailCount(player1.getJailCount()+1);
                return true;
            }

            int whereWas= player1.getWhereNow();
            int whereto= whereWas + dice;
            if(whereto>39){
                //what should ı id ıf pass start point
                whereto = whereto-40;
                player1.increaseMoney(200);
                player1.setWhereNow(whereto);
                if(nextMove(1,player1,player2,banker,dice,true,print)==false){return false;}
            }
            else{
                player1.setWhereNow(whereto);
                if(nextMove(1,player1,player2,banker,dice,false,print)==false){return false;}

            }
        }
        else{
            if(player2.getJailCount()!=0){
                System.out.printf("Player 2\t%d\t%d\t%d\t%d\tPlayer 2 in jail (count=%d)\n",dice,(player2.getWhereNow()+1),player1.getMoney(),player2.getMoney(),player2.getJailCount());
                player2.setJailCount(player2.getJailCount()+1);
                return true;
            }
            int whereWas= player2.getWhereNow();
            int whereto= whereWas + dice;
            if(whereto>39){
                //what should ı id ıf pass start point
                whereto = whereto-40;
                player2.increaseMoney(200);
                banker.decreaseMoney(200);
                player2.setWhereNow(whereto);
                if(nextMove(2,player1,player2,banker,dice,true,print)==false){return false;}

            }
            else{
                player2.setWhereNow(whereto);
                if(nextMove(2,player1,player2,banker,dice,false,print)==false){return false;}

            }
        }
        return true;
    }


    public boolean nextMove(int playerNo,player player1,player player2,banker banker,int dice,boolean isPassZero,boolean print) {
        if (playerNo == 1) {

            if (squaresOfBoard[player1.getWhereNow()] instanceof property) {
                if(check(playerNo, player1, player2, banker,player1.getWhereNow(), dice,print)==false){return false;}

            } else if (squaresOfBoard[player1.getWhereNow()] instanceof chanceCards) {
                if(drawChanceCard(1,player1,player2,banker,dice)==false){return false;}

            } else if (squaresOfBoard[player1.getWhereNow()] instanceof communityChestCards) {
                if(drawComChestCard(playerNo,player1,player2,banker,dice)==false){return false;}

            } else if (squaresOfBoard[player1.getWhereNow()] instanceof go) {
                if(print==false){
                    System.out.println("at go (collect 200)");
                    return true;
                }
                System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer at go (collect 200)\n",player1.getMoney(),player2.getMoney());

            } else if (squaresOfBoard[player1.getWhereNow()] instanceof jail) {
                if(isPassZero==true){
                    if(player1.decreaseMoney(200)==false){return false;}
                    banker.increaseMoney(200);
                }
                player1.setJailCount(1);
                if(print==false){
                    System.out.println("went to jail");
                    return true;
                }
                System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 1 went to jail\n",player1.getMoney(),player2.getMoney());

            } else if (squaresOfBoard[player1.getWhereNow()] instanceof freeParking) {
                if(print==false){
                    System.out.println("in free parking");
                    return true;
                }
                System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 1 is in free parking \n",player1.getMoney(),player2.getMoney());

            } else if (squaresOfBoard[player1.getWhereNow()] instanceof goToJail) {
                player1.setWhereNow(10);
                if(nextMove(playerNo,player1,player2,banker,dice,isPassZero,print)==false){return false;}

            } else if (squaresOfBoard[player1.getWhereNow()] instanceof tax) {
                if(player1.decreaseMoney(100)==false){return false;}
                banker.increaseMoney(100);
                if(print==false){
                    System.out.println("paid tax");
                    return true;
                }
                System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 1 paid tax \n",player1.getMoney(),player2.getMoney());

            }

        } else {
            if (squaresOfBoard[player2.getWhereNow()] instanceof property) {
                if(check(playerNo, player1, player2,banker,player2.getWhereNow(), dice,print)==false){return false;}

            } else if (squaresOfBoard[player2.getWhereNow()] instanceof chanceCards) {
                if(drawChanceCard(2,player1,player2,banker,dice)==false){return false;}

            } else if (squaresOfBoard[player2.getWhereNow()] instanceof communityChestCards) {
                if(drawComChestCard(2,player1,player2,banker,dice)==false){return false;}

            } else if (squaresOfBoard[player2.getWhereNow()] instanceof go) {
                if(print==false){
                    System.out.println("at go (collect 200)");
                    return true;
                }
                System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 2 at go (collect 200)\n",player1.getMoney(),player2.getMoney());

            } else if (squaresOfBoard[player2.getWhereNow()] instanceof jail) {
                if(isPassZero==true){
                    if(player2.decreaseMoney(200)==false){return false;}
                    banker.increaseMoney(200);
                }
                player2.setJailCount(1);
                if(print==false){
                    System.out.println("went to jail)");
                    return true;
                }
                System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 2 went to jail\n",player1.getMoney(),player2.getMoney());

            } else if (squaresOfBoard[player2.getWhereNow()] instanceof freeParking) {
                if(print==false){
                    System.out.println("in free parking)");
                    return true;
                }
                System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 2 is in free parking \n",player1.getMoney(),player2.getMoney());

            } else if (squaresOfBoard[player2.getWhereNow()] instanceof goToJail) {
                player2.setWhereNow(10);
                if(nextMove(playerNo,player1,player2,banker,dice,isPassZero,print)==false){return false;}

            } else if (squaresOfBoard[player2.getWhereNow()] instanceof tax) {
               if(player2.decreaseMoney(100)==false){return false;}
                banker.increaseMoney(100);
                if(print==false){
                    System.out.println("paid tax");
                    return true;
                }
                System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 2 paid tax \n",player1.getMoney(),player2.getMoney());
            }
        }
        return true;
    }

    public boolean check(int playerNo,player player1,player player2,banker banker,int whereNow,int dice,boolean print){
        int id = whereNow+1;
        if(playerNo==1){
            for(int i=0;i<40;i++){
                if(squaresOfBoard[i] instanceof property){
                    property propertyNow = (property)squaresOfBoard[i];
                    if(squaresOfBoard[i].getId()==id && propertyNow.getStatus().equalsIgnoreCase("free")){
                        if(player1.decreaseMoney(propertyNow.getCost())==false){return false;}
                        banker.increaseMoney(propertyNow.getCost());
                        player1.addProperty(propertyNow.getName());
                        propertyNow.setStatus("sold");
                        propertyNow.setWhoOwns(player1.getPlayerNo());
                        if(propertyNow instanceof  railroad){
                            int num= player1.getNumberofRailroads();
                            player1.setNumberofRailroads(num+1);
                        }

                        if(print==false){
                            System.out.printf("bought %s\n",propertyNow.getName());
                            return true;
                        }
                        System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                        System.out.printf("%d\t%d\tPlayer 1 bought %s\n",player1.getMoney(),player2.getMoney(),propertyNow.getName());

                    }
                    else if(squaresOfBoard[i].getId()==id && propertyNow.getStatus().equalsIgnoreCase("sold")) {
                        if(propertyNow.getWhoOwns()!=player1.getPlayerNo()){
                            if(propertyNow instanceof land){
                                if(propertyNow.getCost()<=2000){
                                    int rent=(propertyNow.getCost()*4)/10;
                                    if(player1.decreaseMoney(rent)==false){return false;}
                                    player2.increaseMoney(rent);
                                }
                                else if(propertyNow.getCost()>2000 && propertyNow.getCost()<=3000){
                                    int rent=(propertyNow.getCost()*3)/10;
                                    if(player1.decreaseMoney(rent)==false){return false;}
                                    player2.increaseMoney(rent);
                                }
                                else if(propertyNow.getCost()>3000 && propertyNow.getCost()<=4000){
                                    int rent=(propertyNow.getCost()*35)/100;
                                    if(player1.decreaseMoney(rent)==false){return false;}
                                    player2.increaseMoney(rent);
                                }
                            }
                            else if(propertyNow instanceof railroad){
                                int rent= 25*player2.getNumberofRailroads();
                                if(player1.decreaseMoney(rent)==false){return false;}
                                player2.increaseMoney(rent);
                            }
                            else if(propertyNow instanceof community){
                                int rent= 4*dice;
                                if(player1.decreaseMoney(rent)==false){return false;}
                                player2.increaseMoney(rent);
                            }

                            if(print==false){
                                System.out.printf("paid rent for %s\n",propertyNow.getName());
                                return true;
                            }
                            System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                            System.out.printf("%d\t%d\tPlayer 1 paid rent for %s\n",player1.getMoney(),player2.getMoney(),propertyNow.getName());
                        }
                        else{
                            if(print==false){
                                System.out.println("Player 1 has "+propertyNow.getName());
                                return true;
                            }
                            System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                            System.out.printf("%d\t%d\tPlayer 1 has %s\n",player1.getMoney(),player2.getMoney(),propertyNow.getName());
                        }
                    }
                }
            }
        }

        else{
            for(int i=0;i<40;i++){
                if(squaresOfBoard[i] instanceof property){
                    property propertyNow = (property)squaresOfBoard[i];
                    if(squaresOfBoard[i].getId()==id && propertyNow.getStatus().equalsIgnoreCase("free")){
                        if(player2.decreaseMoney(propertyNow.getCost())==false){return false;}
                        banker.increaseMoney(propertyNow.getCost());
                        propertyNow.setStatus("sold");
                        propertyNow.setWhoOwns(player2.getPlayerNo());
                        player2.addProperty(propertyNow.getName());
                        if(propertyNow instanceof  railroad){
                            int num= player2.getNumberofRailroads();
                            player2.setNumberofRailroads(num+1);
                        }
                        if(print==false){
                            System.out.printf("bought %s\n",propertyNow.getName());
                            return true;
                        }
                        System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
                        System.out.printf("%d\t%d\tPlayer 2 bought %s\n",player1.getMoney(),player2.getMoney(),propertyNow.getName());

                    }

                    else if(squaresOfBoard[i].getId()==id && propertyNow.getStatus().equalsIgnoreCase("sold")){
                        if(propertyNow.getWhoOwns()!=player2.getPlayerNo()){
                            if(propertyNow instanceof land){
                                if(propertyNow.getCost()<=2000){
                                    int rent=(propertyNow.getCost()*4)/10;
                                    if(player2.decreaseMoney(rent)==false){return false;}
                                    player1.increaseMoney(rent);
                                }
                                else if(propertyNow.getCost()>2000 && propertyNow.getCost()<=3000){
                                    int rent=(propertyNow.getCost()*3)/10;
                                    if(player2.decreaseMoney(rent)==false){return false;}
                                    player1.increaseMoney(rent);
                                }
                                else if(propertyNow.getCost()>3000 && propertyNow.getCost()<=4000){
                                    int rent=(propertyNow.getCost()*35)/100;
                                    if(player2.decreaseMoney(rent)==false){return false;}
                                    player1.increaseMoney(rent);
                                }
                            }
                            else if(propertyNow instanceof railroad){
                                int rent= 25*player1.getNumberofRailroads();
                                if(player2.decreaseMoney(rent)==false){return false;}
                                player1.increaseMoney(rent);

                            }
                            else if(propertyNow instanceof community){
                                int rent= 4*dice;
                                if(player2.decreaseMoney(rent)==false){return false;}
                                player1.increaseMoney(rent);
                            }

                            if(print==false){
                                System.out.printf("paid rent for %s\n",propertyNow.getName());
                                return true;
                            }
                            System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
                            System.out.printf("%d\t%d\tPlayer 2 paid rent for %s\n",player1.getMoney(),player2.getMoney(),propertyNow.getName());

                        }
                        else{
                            if(print==false){
                                System.out.println("Player 2 has "+propertyNow.getName());
                                return true;
                            }
                            else {
                                System.out.printf("Player 2\t%d\t%d\t", dice, (player2.getWhereNow() + 1));
                                System.out.printf("%d\t%d\tPlayer 2 has %s\n", player1.getMoney(), player2.getMoney(),propertyNow.getName());
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    public boolean drawChanceCard(int playerNo,player player1,player player2,banker banker,int dice){
        if(playerNo==1){
            if(whereChance==0){
                player1.increaseMoney(200);
                banker.decreaseMoney(200);
                player1.setWhereNow(0);
            } else if (whereChance==1){
                int whereNow= player1.getWhereNow();
                if(whereNow>26){
                    player1.increaseMoney(200);
                    banker.decreaseMoney(200);
                }
                player1.setWhereNow(26);
                System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 1 draw Chance -",player1.getMoney(),player2.getMoney());
                System.out.print(chanceCards[whereChance].getContent()+" ,");
                setWhereChance(getWhereChance()+1);
                play("Player 1",0,player1,player2,banker,false);
                return true;
            } else if (whereChance==2){
                System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1-3));
                System.out.printf("%d\t%d\tPlayer 1 draw Chance -",player1.getMoney(),player2.getMoney());
                System.out.printf(chanceCards[whereChance].getContent()+" ,");
                setWhereChance(getWhereChance()+1);
                play("Player 1",-3,player1,player2,banker,false);
                return true;
            } else if (whereChance==3){
                if(player1.decreaseMoney(15)==false){return false;}
                banker.increaseMoney(15);
            } else if (whereChance==4){
                player1.increaseMoney(150);
                banker.decreaseMoney(150);
            } else if (whereChance==5){
                player1.increaseMoney(100);
                banker.decreaseMoney(100);
            }

            System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
            System.out.printf("%d\t%d\tPlayer 1 draw Chance -",player1.getMoney(),player2.getMoney());
            System.out.println(chanceCards[whereChance].getContent());
            setWhereChance(getWhereChance()+1);

        }

        else{
            if(whereChance==0){
                player2.increaseMoney(200);
                banker.decreaseMoney(200);
                player2.setWhereNow(0);
            } else if (whereChance==1){
                int whereNow= player2.getWhereNow();
                if(whereNow>26){
                    player2.increaseMoney(200);
                    banker.decreaseMoney(200);
                }
                player2.setWhereNow(26);
                System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
                System.out.printf("%d\t%d\tPlayer 2 draw Chance -",player1.getMoney(),player2.getMoney());
                System.out.print(chanceCards[whereChance].getContent()+" ,");
                setWhereChance(getWhereChance()+1);
                play("Player 2",0,player1,player2,banker,false);
                return true;
            } else if (whereChance==2){
                System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1-3));
                System.out.printf("%d\t%d\tPlayer 2 draw Chance -",player1.getMoney(),player2.getMoney());
                System.out.print(chanceCards[whereChance].getContent()+" ,");
                setWhereChance(getWhereChance()+1);
                play("Player 2",-3,player1,player2,banker,false);
                return true;
            } else if (whereChance==3){
                if(player2.decreaseMoney(15)==false){return false;}
                banker.increaseMoney(15);
            } else if (whereChance==4){
                player2.increaseMoney(150);
                banker.decreaseMoney(150);
            } else if (whereChance==5){
                player2.increaseMoney(100);
                banker.decreaseMoney(100);
            }

            System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
            System.out.printf("%d\t%d\tPlayer 2 draw Chance -",player1.getMoney(),player2.getMoney());
            System.out.println(chanceCards[whereChance].getContent());
            setWhereChance(getWhereChance()+1);
        }
        return true;
    }

    public boolean drawComChestCard(int playerNo,player player1,player player2,banker banker,int dice){
        if(playerNo==1){
            if(whereCommunityChest==0){
                player1.setWhereNow(0);
                player1.increaseMoney(200);
                banker.decreaseMoney(200);
            } else if(whereCommunityChest==1){
                player1.increaseMoney(75);
                banker.decreaseMoney(75);
            } else if(whereCommunityChest==2){
                if(player1.decreaseMoney(50)==false){return false;}
                banker.increaseMoney(50);
            } else if(whereCommunityChest==3){
                if(player2.decreaseMoney(10)==false){return false;}
                player1.increaseMoney(10);
            } else if(whereCommunityChest==4){
                if(player2.decreaseMoney(50)==false){return false;}
                player1.increaseMoney(50);
            } else if(whereCommunityChest==5){
                player1.increaseMoney(20);
                banker.decreaseMoney(20);
            } else if(whereCommunityChest==6){
                player1.increaseMoney(100);
                banker.decreaseMoney(100);
            } else if(whereCommunityChest==7){
                if(player1.decreaseMoney(100)==false){return false;}
                banker.increaseMoney(100);
            } else if(whereCommunityChest==8){
                if(player1.decreaseMoney(50)==false){return false;}
                banker.increaseMoney(50);
            } else if(whereCommunityChest==9){
                player1.increaseMoney(100);
                banker.decreaseMoney(100);
            } else if(whereCommunityChest==10){
                player1.increaseMoney(50);
                banker.decreaseMoney(50);
            }
            System.out.printf("Player 1\t%d\t%d\t",dice,(player1.getWhereNow()+1));
            System.out.printf("%d\t%d\tPlayer 1 draw Community Chest -",player1.getMoney(),player2.getMoney());
            System.out.println(communityChestCards[whereCommunityChest].getContent());
            setWhereCommunityChest(getWhereCommunityChest()+1);
        }
        else{
            if(whereCommunityChest==0){
                player2.setWhereNow(0);
                player2.increaseMoney(200);
                banker.decreaseMoney(200);
            } else if(whereCommunityChest==1){
                player2.increaseMoney(75);
                banker.decreaseMoney(75);
            } else if(whereCommunityChest==2){
                if(player2.decreaseMoney(50)==false){return false;}
                banker.increaseMoney(50);
            } else if(whereCommunityChest==3){
                if(player1.decreaseMoney(10)==false){return false;}
                player2.increaseMoney(10);
            } else if(whereCommunityChest==4){
                if(player1.decreaseMoney(50)==false){return false;}
                player2.increaseMoney(50);
            } else if(whereCommunityChest==5){
                player2.increaseMoney(20);
                banker.decreaseMoney(20);
            } else if(whereCommunityChest==6){
                player2.increaseMoney(100);
                banker.decreaseMoney(100);
            } else if(whereCommunityChest==7){
                if(player2.decreaseMoney(100)==false){return false;}
                banker.increaseMoney(100);
            } else if(whereCommunityChest==8){
                if(player2.decreaseMoney(50)==false){return false;}
                banker.increaseMoney(50);
            } else if(whereCommunityChest==9){
                player2.increaseMoney(100);
                banker.decreaseMoney(100);
            } else if(whereCommunityChest==10){
                player2.increaseMoney(50);
                banker.decreaseMoney(50);
            }
            System.out.printf("Player 2\t%d\t%d\t",dice,(player2.getWhereNow()+1));
            System.out.printf("%d\t%d\tPlayer 2 draw Community Chest -",player1.getMoney(),player2.getMoney());
            System.out.println(communityChestCards[whereCommunityChest].getContent());
            setWhereCommunityChest(getWhereCommunityChest()+1);
        }



        return true;
        }



    public int getWhereChance() {
        return whereChance;
    }

    public void setWhereChance(int whereChance) {
        if (whereChance>5){
            this.whereChance=0;
            return;
        }
        this.whereChance = whereChance;
    }

    public int getWhereCommunityChest() { return whereCommunityChest; }

    public void setWhereCommunityChest(int whereComunityChest) {
        if(whereComunityChest>10){
            this.whereCommunityChest=0;
            return;
        }
        this.whereCommunityChest = whereComunityChest;
    }


}
