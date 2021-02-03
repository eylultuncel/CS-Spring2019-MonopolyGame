public class property extends square{
    private String name;
    private int cost;
    private String status="free";
    private int whoOwns=0;

    public property(int id,String name,int cost){
        super(id);
        this.setName(name);
        this.setCost(cost);
    }

    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        super.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWhoOwns() {
        return whoOwns;
    }

    public void setWhoOwns(int whoOwns) {
        this.whoOwns = whoOwns;
    }
}
