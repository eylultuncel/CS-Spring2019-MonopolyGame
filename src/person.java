public class person {
    private int playerNo;
    private int money;
    private int beforeBankrupt=15000;

    public person(int playerNo,int money) {
        this.setPlayerNo(playerNo);
        this.setMoney(money);
    }

    public void increaseMoney(int money){
        int old= this.getMoney();
        this.setMoney(old+money);
        this.setBeforeBankrupt(old+money);
    }

    public boolean decreaseMoney(int money){
        int old= this.getMoney();
        if(old<money){
            beforeBankrupt=this.money;
            this.setMoney(old-money);
            return false;
        }
        this.setBeforeBankrupt(old-money);
        this.setMoney(old-money);
        return true;
    }

    public boolean bankrupt(){
        if(this.getMoney() <0){
            return true;
        }
        else{
            return false;
        }
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if(money<0){
            beforeBankrupt=this.money;
        }
        this.money = money;
    }

    public int getBeforeBankrupt() {
        return beforeBankrupt;
    }

    public void setBeforeBankrupt(int beforeBankrupt) {
        this.beforeBankrupt = beforeBankrupt;
    }
}
