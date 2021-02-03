public class freeParking extends square {

    public freeParking(int id){
        super(id);
    }
    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        if(id!=20){
            return;
        }
        super.setId(id);
    }
}
