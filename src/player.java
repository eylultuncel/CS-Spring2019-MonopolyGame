public class player extends person {
    private String[] properties = new String[28];
    private int whereNow;
    private int numberofRailroads;
    private int jailCount;

    public player(int playerNo,int money){
        super(playerNo,money);
        this.setWhereNow(0);
        this.setNumberofRailroads(0);
        this.jailCount=0;
    }

    public void addProperty(String propertyName){
        for(int i=0;i<28;i++){
            if(getProperties()[i]==null){
                getProperties()[i]=propertyName;
                break;
            }
        }
    }



    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public int getWhereNow() {
        return whereNow;
    }

    public void setWhereNow(int whereNow) {
        this.whereNow = whereNow;
    }

    public int getNumberofRailroads() {
        return numberofRailroads;
    }

    public void setNumberofRailroads(int numberofRailroads) {
        this.numberofRailroads = numberofRailroads;
    }

    public int getJailCount() {
        return jailCount;
    }

    public void setJailCount(int jailCount) {
        if(jailCount>3){
            this.jailCount=0;
            return;
        }
        this.jailCount = jailCount;
    }
}
