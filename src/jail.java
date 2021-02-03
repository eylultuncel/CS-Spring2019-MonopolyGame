public class jail extends square {

    public jail(int id){
        super(id);
    }
    public int getId() {
        return super.getId();
    }
    public void setId(int id) {
        if(id!=10){
            return;
        }
        super.setId(id);
    }
}
