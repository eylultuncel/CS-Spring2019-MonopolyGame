public class goToJail extends square {

    public goToJail(int id){
        super(id);
    }
    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        if(id!=30){
            return;
        }
        super.setId(id);
    }
}
